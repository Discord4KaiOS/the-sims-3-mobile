package javax.microedition.media;

/**
 * AMR-NB decoder for the two modes the game's assets use.
 *
 * The fourteen AMR files in the archive are 4.75 kbit/s (frame type 0) and
 * 12.2 kbit/s (frame type 7); those are the two decoding chains implemented
 * here, each with its own LSF quantiser, pitch and gain resolution and
 * algebraic codebook. Frames of any other mode, and frames that carry no
 * speech, decode to silence so the clip keeps its timing.
 *
 * Both chains follow 3GPP TS 26.073: split-VQ LSF dequantisation, LSP to LPC
 * conversion, pitch-lag decoding with a fractional adaptive codebook (1/6
 * sample at 12.2, 1/3 at 4.75 or 4 bit relative), the mode's algebraic
 * codebook (ten pulses at 12.2, two at 4.75), gain dequantisation with its MA
 * predictor and the LPC synthesis filter. The signal path is kept in the standard's
 * fixed-point arithmetic (Word16 in {@code int} with the same saturation,
 * Word32 in {@code long}); the two stages that are pure algebra - LSP to LPC
 * and the gain predictor - are evaluated in floating point, which is
 * numerically equivalent for playback and far easier to follow.
 *
 * The post filter, phase dispersion and the background-noise/DTX handling of
 * the reference decoder are not implemented: they shape the output but are not
 * needed to reconstruct it.
 */
final class AmrDecoder {
    private static final int L_FRAME = 160;
    private static final int L_SUBFR = 40;
    private static final int M = 10;
    private static final int MP1 = M + 1;
    private static final int LSF_GAP = 205;
    private static final int LSP_PRED_FAC = 21299;
    private static final int PIT_MIN = 18;
    private static final int PIT_MAX = 143;
    private static final int L_INTER10 = 10;
    private static final int SHARPMAX = 13017;
    private static final int UP_SAMP_MAX = 6;
    private static final int FRAME_MILLIS = 20;

    /** Bits per parameter, in transmission order, for frame types 0 and 7. */
    private static final int[] MODE_475_BITS = {
        8, 8, 7, 8, 7, 2, 8, 4, 7, 2, 4, 7, 2, 8, 4, 7, 2,
    };
    private static final int[] MODE_122_BITS = {
        7, 8, 9, 8, 6,
        9, 4, 4, 4, 4, 4, 4, 3, 3, 3, 3, 3, 5,
        6, 4, 4, 4, 4, 4, 4, 3, 3, 3, 3, 3, 5,
        9, 4, 4, 4, 4, 4, 4, 3, 3, 3, 3, 3, 5,
        6, 4, 4, 4, 4, 4, 4, 3, 3, 3, 3, 3, 5,
    };
    /** LSF indices per mode: three below 12.2 kbit/s, five at 12.2. */
    private static final int MODE_475_LSF_PARAMS = 3;

    /** Payload size in bytes for frame types 0..8; type 15 carries no data. */
    private static final int[] PAYLOAD = {12, 13, 15, 17, 19, 20, 26, 31, 5};

    private static final int[] LSP_INIT = {30000, 26000, 21000, 15000, 8000, 0, -8000, -15000, -21000, -26000};
    /** 14 dB expressed in log2, Q10 - the initial gain-predictor history. */
    private static final int MIN_ENERGY_LOG2 = -2381;
    /** 14 dB in the dB domain, Q10 - the initial history of the other modes. */
    private static final int MIN_ENERGY_DB = -14336;

    private final int[] pastResidual = new int[M];
    private final int[] lspOld = LSP_INIT.clone();
    private final int[] pastQuaEnMr122 = {MIN_ENERGY_LOG2, MIN_ENERGY_LOG2, MIN_ENERGY_LOG2, MIN_ENERGY_LOG2};
    private final int[] pastQuaEnDb = {MIN_ENERGY_DB, MIN_ENERGY_DB, MIN_ENERGY_DB, MIN_ENERGY_DB};
    private final int[] excitation = new int[PIT_MAX + L_INTER10 + L_SUBFR];
    private int[] synthesisMemory = new int[M];
    private int oldT0 = 40;
    private int sharp;

    /** Decodes a complete {@code #!AMR\n} file to 8 kHz mono 16 bit PCM. */
    static byte[] decode(byte[] amr) {
        if (!isAmr(amr)) {
            return null;
        }
        AmrDecoder decoder = new AmrDecoder();
        java.io.ByteArrayOutputStream pcm = new java.io.ByteArrayOutputStream(amr.length * 8);
        int offset = 6;
        while (offset < amr.length) {
            int frameType = (amr[offset] >> 3) & 0x0F;
            if (frameType == 15) {
                offset++;
                decoder.writeSilenceFrame(pcm);
                continue;
            }
            if (frameType > 8) {
                break;
            }
            int size = 1 + (frameType == 8 ? 5 : PAYLOAD[frameType]);
            if (offset + size > amr.length) {
                break;
            }
            short[] frame;
            if (frameType == 0 || frameType == 7) {
                frame = decoder.decodeSpeechFrame(frameType, amr, offset + 1, size - 1);
            } else {
                // modes the game never uses, and SID frames, keep the timing
                // but contribute silence
                frame = new short[L_FRAME];
            }
            for (short sample : frame) {
                pcm.write(sample & 0xFF);
                pcm.write((sample >> 8) & 0xFF);
            }
            offset += size;
        }
        return pcm.toByteArray();
    }

    /** Number of 20 ms frames in a clip. */
    static long durationMillis(byte[] amr) {
        if (!isAmr(amr)) {
            return amr.length / 16;
        }
        int offset = 6;
        int frames = 0;
        while (offset < amr.length) {
            int frameType = (amr[offset] >> 3) & 0x0F;
            if (frameType == 15) {
                offset++;
            } else if (frameType <= 8) {
                offset += 1 + (frameType == 8 ? 5 : PAYLOAD[frameType]);
            } else {
                break;
            }
            frames++;
        }
        return (long) frames * FRAME_MILLIS;
    }

    private static boolean isAmr(byte[] data) {
        return data.length > 6 && data[0] == '#' && data[1] == '!' && data[2] == 'A'
                && data[3] == 'M' && data[4] == 'R' && data[5] == '\n';
    }

    private void writeSilenceFrame(java.io.ByteArrayOutputStream pcm) {
        for (int i = 0; i < L_FRAME; i++) {
            pcm.write(0);
            pcm.write(0);
        }
    }

    // ------------------------------------------------------------------ frame

    private short[] decodeSpeechFrame(int mode, byte[] data, int offset, int bytes) {
        boolean wideband122 = mode == 7;
        int[] parm = unpack(wideband122, data, offset, bytes);

        int[][] az = new int[4][MP1];
        int[] lspNew = new int[M];
        int[] interpolated = new int[M];
        if (wideband122) {
            int[] lspMid = new int[M];
            decodeLsf122(parm, lspMid, lspNew);
            for (int i = 0; i < M; i++) {
                interpolated[i] = (lspOld[i] >> 1) + (lspMid[i] >> 1);
            }
            lspToLpc(interpolated, az[0]);
            lspToLpc(lspMid, az[1]);
            for (int i = 0; i < M; i++) {
                interpolated[i] = (lspMid[i] >> 1) + (lspNew[i] >> 1);
            }
            lspToLpc(interpolated, az[2]);
            lspToLpc(lspNew, az[3]);
        } else {
            decodeLsf475(parm, lspNew);
            // subframe 1: 3/4 of the previous frame plus 1/4 of this one
            for (int i = 0; i < M; i++) {
                interpolated[i] = (lspOld[i] - (lspOld[i] >> 2)) + (lspNew[i] >> 2);
            }
            lspToLpc(interpolated, az[0]);
            for (int i = 0; i < M; i++) {
                interpolated[i] = (lspNew[i] >> 1) + (lspOld[i] >> 1);
            }
            lspToLpc(interpolated, az[1]);
            for (int i = 0; i < M; i++) {
                interpolated[i] = (lspNew[i] - (lspNew[i] >> 2)) + (lspOld[i] >> 2);
            }
            lspToLpc(interpolated, az[2]);
            lspToLpc(lspNew, az[3]);
        }
        System.arraycopy(lspNew, 0, lspOld, 0, M);

        short[] synth = new short[L_FRAME];
        int codeBase = PIT_MAX + L_INTER10;
        int p = wideband122 ? 5 : MODE_475_LSF_PARAMS;
        int evenSubframe = 0;
        int sharedGainIndex = 0;
        for (int subframe = 0; subframe < 4; subframe++) {
            evenSubframe = 1 - evenSubframe;
            int pitchIndex = parm[p++];
            int[] lag = new int[2];
            if (wideband122) {
                decodeLag6(pitchIndex, PIT_MIN, PIT_MAX, (subframe == 0 || subframe == 2) ? 0 : 1, lag);
            } else {
                decodeLag3(pitchIndex, subframe == 0 || subframe == 2, lag);
            }
            int t0 = lag[0];
            interpolateExcitation(t0, lag[1], wideband122 ? 6 : 3);

            int[] code = new int[L_SUBFR];
            int pitchSharp;
            int gainPitch = 0;
            if (wideband122) {
                gainPitch = AmrGainTables.qua_gain_pitch[parm[p++]] & 0xFFFC;
                codebook10i40_35bits(parm, p, code);
                p += 10;
                pitchSharp = shl(gainPitch, 1);
            } else {
                int positionIndex = parm[p++];
                int signIndex = parm[p++];
                codebook2i40_9bits(subframe, signIndex, positionIndex, code);
                pitchSharp = shl(sharp, 1);
            }

            // pitch sharpening of the innovation
            for (int i = t0; i < L_SUBFR; i++) {
                code[i] = add(code[i], mult(code[i - t0], pitchSharp));
            }

            int[] gains;
            if (wideband122) {
                gains = decodeGain(parm[p++], code);
            } else {
                if (evenSubframe != 0) {
                    sharedGainIndex = parm[p++];
                }
                gains = decodeGain475(sharedGainIndex, code, evenSubframe);
            }
            gainPitch = gains[0];
            int gainCode = gains[1];
            sharp = Math.min(gainPitch, SHARPMAX);

            // total excitation: the scaled adaptive part plus the innovation
            int pitchFac = wideband122 ? (gainPitch >> 1) : gainPitch;
            int shift = wideband122 ? 2 : 1;
            int[] ltp = new int[L_SUBFR];
            System.arraycopy(excitation, codeBase, ltp, 0, L_SUBFR);
            int[] frameExcitation = new int[L_SUBFR];
            for (int i = 0; i < L_SUBFR; i++) {
                long value = lMult(ltp[i], pitchFac);
                value = lMac(value, code[i], gainCode);
                value = lShl(value, shift);
                frameExcitation[i] = round(value);
                excitation[codeBase + i] = frameExcitation[i];
            }

            if (!synthesize(az[subframe], frameExcitation, synth, subframe * L_SUBFR)) {
                for (int i = 0; i < excitation.length; i++) {
                    excitation[i] >>= 2;
                }
                for (int i = 0; i < L_SUBFR; i++) {
                    frameExcitation[i] >>= 2;
                    excitation[codeBase + i] = frameExcitation[i];
                }
                synthesize(az[subframe], frameExcitation, synth, subframe * L_SUBFR);
            }

            // shift the excitation history left by one subframe
            System.arraycopy(excitation, L_SUBFR, excitation, 0, excitation.length - L_SUBFR);
            oldT0 = t0;
        }
        return synth;
    }

    /** Unpacks the parameters of one frame in transmission order. */
    private static int[] unpack(boolean wideband122, byte[] data, int offset, int bytes) {
        int[] bits = wideband122 ? MODE_122_BITS : MODE_475_BITS;
        int[] parm = new int[bits.length];
        int bit = 0;
        for (int i = 0; i < bits.length; i++) {
            int value = 0;
            for (int b = 0; b < bits[i]; b++) {
                int byteIndex = offset + ((bit + b) >> 3);
                int bitIndex = 7 - ((bit + b) & 7);
                int packed = byteIndex < offset + bytes ? (data[byteIndex] >> bitIndex) & 1 : 0;
                value = (value << 1) | packed;
            }
            bit += bits[i];
            parm[i] = value;
        }
        return parm;
    }

    // ------------------------------------------------------------ LSF / LSP

    private void decodeLsf122(int[] parm, int[] lspMid, int[] lspNew) {
        int[] lsf1 = new int[M];
        int[] lsf2 = new int[M];

        int index = parm[0];
        lsf1[0] = AmrLsfTables122.dico1_lsf_5[index * 4];
        lsf1[1] = AmrLsfTables122.dico1_lsf_5[index * 4 + 1];
        lsf2[0] = AmrLsfTables122.dico1_lsf_5[index * 4 + 2];
        lsf2[1] = AmrLsfTables122.dico1_lsf_5[index * 4 + 3];

        index = parm[1];
        lsf1[2] = AmrLsfTables122.dico2_lsf_5[index * 4];
        lsf1[3] = AmrLsfTables122.dico2_lsf_5[index * 4 + 1];
        lsf2[2] = AmrLsfTables122.dico2_lsf_5[index * 4 + 2];
        lsf2[3] = AmrLsfTables122.dico2_lsf_5[index * 4 + 3];

        int sign = parm[2] & 1;
        index = parm[2] >> 1;
        for (int i = 0; i < 2; i++) {
            int first = AmrLsfTables122.dico3_lsf_5[index * 4 + i];
            int second = AmrLsfTables122.dico3_lsf_5[index * 4 + 2 + i];
            lsf1[4 + i] = sign == 0 ? first : -first;
            lsf2[4 + i] = sign == 0 ? second : -second;
        }

        index = parm[3];
        lsf1[6] = AmrLsfTables122.dico4_lsf_5[index * 4];
        lsf1[7] = AmrLsfTables122.dico4_lsf_5[index * 4 + 1];
        lsf2[6] = AmrLsfTables122.dico4_lsf_5[index * 4 + 2];
        lsf2[7] = AmrLsfTables122.dico4_lsf_5[index * 4 + 3];

        index = parm[4];
        lsf1[8] = AmrLsfTables122.dico5_lsf_5[index * 4];
        lsf1[9] = AmrLsfTables122.dico5_lsf_5[index * 4 + 1];
        lsf2[8] = AmrLsfTables122.dico5_lsf_5[index * 4 + 2];
        lsf2[9] = AmrLsfTables122.dico5_lsf_5[index * 4 + 3];

        // the codebook values are residuals around a predicted mean; the next
        // frame predicts from the residual, not from the reconstructed value
        int[] residual = lsf2.clone();
        for (int i = 0; i < M; i++) {
            int predicted = add(AmrLsfTables122.mean_lsf_5[i], mult(pastResidual[i], LSP_PRED_FAC));
            lsf1[i] = add(lsf1[i], predicted);
            lsf2[i] = add(lsf2[i], predicted);
        }
        System.arraycopy(residual, 0, pastResidual, 0, M);

        reorderLsf(lsf1);
        reorderLsf(lsf2);
        lsfToLsp(lsf1, lspMid);
        lsfToLsp(lsf2, lspNew);
    }

    /** Keeps the decoded frequencies at least {@link #LSF_GAP} apart. */
    private static void reorderLsf(int[] lsf) {
        for (int i = 0; i < M - 1; i++) {
            if (lsf[i] > lsf[i + 1] - LSF_GAP) {
                lsf[i + 1] = add(lsf[i], LSF_GAP);
            }
        }
    }

    /** Normalised LSF (0..0.5) to the cosine domain. */
    private static void lsfToLsp(int[] lsf, int[] lsp) {
        short[] table = AmrTables.lsfCosTable;
        for (int i = 0; i < M; i++) {
            int index = lsf[i] >> 8;
            int offset = lsf[i] & 0xFF;
            if (index < 0) {
                index = 0;
            } else if (index > 63) {
                index = 63;
            }
            int delta = table[index + 1] - table[index];
            lsp[i] = add(table[index], (int) (((long) delta * offset) >> 8));
        }
    }

    /**
     * Line spectral pairs to LPC coefficients (Q12).
     *
     * Builds F1(z) = (1 + z^-1) * prod(even pairs) and
     * F2(z) = (1 - z^-1) * prod(odd pairs) - where every pair contributes
     * (1 - 2cos(w) z^-1 + z^-2) - and averages them into A(z).
     */
    private static void lspToLpc(int[] lsp, int[] az) {
        double[] f1 = {1};
        double[] f2 = {1};
        for (int i = 0; i < M; i++) {
            double cosine = Math.max(-1.0, Math.min(1.0, lsp[i] / 32768.0));
            double[] factor = {1, -2 * cosine, 1};
            if ((i & 1) == 0) {
                f1 = multiply(f1, factor);
            } else {
                f2 = multiply(f2, factor);
            }
        }
        f1 = multiply(f1, new double[]{1, 1});
        f2 = multiply(f2, new double[]{1, -1});
        for (int i = 0; i <= M; i++) {
            double value = (f1[i] + f2[i]) / 2.0;
            az[i] = (int) Math.round(value * 4096.0);
        }
        az[0] = 4096;
    }

    private static double[] multiply(double[] left, double[] right) {
        double[] product = new double[left.length + right.length - 1];
        for (int i = 0; i < left.length; i++) {
            for (int j = 0; j < right.length; j++) {
                product[i + j] += left[i] * right[j];
            }
        }
        return product;
    }

    // ---------------------------------------------------------------- pitch

    private void decodeLag6(int index, int min, int max, int subframe, int[] lag) {
        if (subframe == 0) {
            if (index < 463) {
                int t0 = ((index + 5) * 5462 >> 15) + 17;
                lag[0] = t0;
                lag[1] = index - 6 * t0 + 105;
            } else {
                lag[0] = index - 368;
                lag[1] = 0;
            }
            return;
        }
        int t0Min = oldT0 - 5;
        if (t0Min < min) {
            t0Min = min;
        }
        int t0Max = t0Min + 9;
        if (t0Max > max) {
            t0Max = max;
            t0Min = t0Max - 9;
        }
        int t0 = ((index + 5) * 5462 >> 15) - 1 + t0Min;
        lag[0] = t0;
        lag[1] = index - 3 - 6 * (t0 - t0Min);
        if (t0 > t0Max) {
            lag[0] = t0Max;
            lag[1] = 0;
        }
    }

    /**
     * Interpolates the history at the fractional pitch lag into the current
     * subframe slot of the excitation buffer (1/6 sample resolution).
     */
    private void interpolateExcitation(int t0, int frac, int resolution) {
        int base = PIT_MAX + L_INTER10;
        short[] filter = AmrTables.inter6;
        int f = -frac;
        if (resolution == 3) {
            f <<= 1;
        }
        int x0 = base - t0;
        if (f < 0) {
            f += UP_SAMP_MAX;
            x0--;
        }

        int[] coefficients = new int[L_INTER10 * 2];
        int dest = 0;
        int k = 0;
        for (int i = L_INTER10 >> 1; i > 0; i--) {
            coefficients[dest++] = filter[f + k];
            coefficients[dest++] = filter[UP_SAMP_MAX - f + k];
            k += UP_SAMP_MAX;
            coefficients[dest++] = filter[f + k];
            coefficients[dest++] = filter[UP_SAMP_MAX - f + k];
            k += UP_SAMP_MAX;
        }

        int out = 0;
        for (int j = L_SUBFR >> 1; j != 0; j--) {
            x0++;
            int x2 = x0;
            int x3 = x0++;

            long s1 = 0x4000;
            long s2 = 0x4000;
            int c = 0;
            for (int i = L_INTER10 >> 1; i > 0; i--) {
                s2 += (long) excitation[x3--] * coefficients[c];
                s1 += (long) excitation[x3] * coefficients[c++];
                s1 += (long) excitation[x2++] * coefficients[c];
                s2 += (long) excitation[x2] * coefficients[c++];
                s2 += (long) excitation[x3--] * coefficients[c];
                s1 += (long) excitation[x3] * coefficients[c++];
                s1 += (long) excitation[x2++] * coefficients[c];
                s2 += (long) excitation[x2] * coefficients[c++];
            }
            excitation[base + out++] = (int) (s1 >> 15);
            excitation[base + out++] = (int) (s2 >> 15);
        }
    }

    // ------------------------------------------------------------- codebook

    /** 10 pulses on 5 tracks, 35 bits: 5 positions with sign, 5 second pulses. */
    private static void codebook10i40_35bits(int[] parm, int start, int[] code) {
        for (int track = 0; track < 5; track++) {
            int index = parm[start + track];
            int position = AmrTables.dgray[index & 7] * 5;
            int pos1 = position + track;
            int sign = ((index >> 3) & 1) == 0 ? 4096 : -4096;
            if (pos1 < L_SUBFR) {
                code[pos1] = sign;
            }

            index = parm[start + track + 5];
            position = AmrTables.dgray[index & 7] * 5;
            int pos2 = position + track;
            if (pos2 < pos1) {
                sign = -sign;
            }
            if (pos2 < L_SUBFR) {
                code[pos2] = add(code[pos2], sign);
            }
        }
    }

    /** Places coded pulses, flipping the sign convention of the track pairs. */

    // ---------------------------------------------------------------- gains

    /**
     * Returns {pitch gain (Q14), code gain (Q1)}.
     *
     * The code gain is the quantised correction added to the energy predicted
     * from the four previous frames by the MA predictor. Modes up to 10.2
     * kbit/s predict in the dB domain, 12.2 kbit/s in the log2 domain, which
     * is why the two use different history arrays.
     */
    private int[] decodeGain(int index, int[] code) {
        short[] table = AmrGainTables.table_gain_lowrates;
        int base = index * 4;
        int gainPitch = table[base];
        double gFac = table[base + 1] / 4096.0;
        int quaEnerMr122 = table[base + 2];
        int quaEnerDb = table[base + 3];

        double logGain = logGain122(code);
        int gainCode = (int) Math.round(2.0 * gFac * Math.pow(2.0, logGain));

        updatePrediction(quaEnerDb, quaEnerMr122);
        return new int[]{gainPitch, gainCode};
    }

    /** Predicted gain of a 12.2 kbit/s subframe, in log2. */
    private double logGain122(int[] code) {
        double predicted = MEAN_ENERGY_LOG2;
        for (int i = 0; i < 4; i++) {
            predicted += PRED_122[i] * (pastQuaEnMr122[i] / 1024.0);
        }
        double energy = meanSquare(code, 4096.0);
        return predicted - 0.5 * Math.log(energy) / Math.log(2.0);
    }

    /** Predicted gain of the other modes, converted to log2. */
    private double logGainDb(int[] code) {
        double predicted = MEAN_ENERGY_475;
        for (int i = 0; i < 4; i++) {
            predicted += PRED_DB[i] * (pastQuaEnDb[i] / 1024.0);
        }
        double energy = meanSquare(code, 8192.0);
        double decibels = predicted - 10.0 * Math.log10(energy);
        return decibels / (20.0 * Math.log10(2.0));
    }

    private static double meanSquare(int[] code, double scale) {
        double energy = 0;
        for (int value : code) {
            double sample = value / scale;
            energy += sample * sample;
        }
        return Math.max(energy / L_SUBFR, 1e-9);
    }

    private void updatePrediction(int quaEnerDb, int quaEnerMr122) {
        pastQuaEnDb[3] = pastQuaEnDb[2];
        pastQuaEnDb[2] = pastQuaEnDb[1];
        pastQuaEnDb[1] = pastQuaEnDb[0];
        pastQuaEnDb[0] = quaEnerDb;
        pastQuaEnMr122[3] = pastQuaEnMr122[2];
        pastQuaEnMr122[2] = pastQuaEnMr122[1];
        pastQuaEnMr122[1] = pastQuaEnMr122[0];
        pastQuaEnMr122[0] = quaEnerMr122;
    }

    /** 36 dB expressed in log2 - the 12.2 kbit/s predictor's mean energy. */
    private static final double MEAN_ENERGY_LOG2 = 36.0 / (20.0 * Math.log10(2.0));
    /** 12.2 kbit/s MA prediction coefficients, Q6. */
    private static final double[] PRED_122 = {44 / 64.0, 37 / 64.0, 22 / 64.0, 12 / 64.0};
    /** Mean energy of the 4.75 kbit/s predictor, in dB. */
    private static final double MEAN_ENERGY_475 = 33.0;
    /** dB-domain MA prediction coefficients, Q13. */
    private static final double[] PRED_DB = {5571 / 8192.0, 4751 / 8192.0, 2785 / 8192.0, 1556 / 8192.0};

    // ----------------------------------------------------------- synthesis

    /**
     * LPC synthesis filter: {@code y[i] = (x[i]*a[0] - sum a[j]*y[i-j]) >> 12}.
     *
     * Returns false when the filter output overflows 16 bits, which the caller
     * answers by scaling the excitation down and running the subframe again -
     * the same guard the reference decoder uses.
     */
    private boolean synthesize(int[] az, int[] input, short[] output, int outOffset) {
        int[] memory = synthesisMemory.clone();
        int[] samples = new int[L_SUBFR];
        boolean clipped = false;
        for (int i = 0; i < L_SUBFR; i++) {
            long sum = (long) input[i] * az[0] + 0x800;
            for (int j = 1; j <= M; j++) {
                sum -= (long) az[j] * memory[M - j];
            }
            long value = sum >> 12;
            if (value > 32767 || value < -32768) {
                clipped = true;
                value = Math.max(-32768, Math.min(32767, value));
            }
            samples[i] = (int) value;
            System.arraycopy(memory, 1, memory, 0, M - 1);
            memory[M - 1] = (int) value;
        }
        if (clipped) {
            return false;
        }
        for (int i = 0; i < L_SUBFR; i++) {
            output[outOffset + i] = (short) samples[i];
        }
        synthesisMemory = memory;
        return true;
    }

    /**
     * LSF decoder of the 4.75 kbit/s mode: three indices, the middle codebook
     * using every second entry.
     */
    private void decodeLsf475(int[] parm, int[] lspNew) {
        int[] raw = new int[M];
        int index = Math.min(parm[0] * 3, (AmrLsfTables.dico1_lsf_3.length / 3 - 1) * 3);
        raw[0] = AmrLsfTables.dico1_lsf_3[index];
        raw[1] = AmrLsfTables.dico1_lsf_3[index + 1];
        raw[2] = AmrLsfTables.dico1_lsf_3[index + 2];

        index = Math.min((parm[1] << 1) * 3, (AmrLsfTables.dico2_lsf_3.length / 3 - 1) * 3);
        raw[3] = AmrLsfTables.dico2_lsf_3[index];
        raw[4] = AmrLsfTables.dico2_lsf_3[index + 1];
        raw[5] = AmrLsfTables.dico2_lsf_3[index + 2];

        index = Math.min(parm[2] << 2, (AmrLsfTables.mr515_3_lsf.length / 4 - 1) * 4);
        raw[6] = AmrLsfTables.mr515_3_lsf[index];
        raw[7] = AmrLsfTables.mr515_3_lsf[index + 1];
        raw[8] = AmrLsfTables.mr515_3_lsf[index + 2];
        raw[9] = AmrLsfTables.mr515_3_lsf[index + 3];

        int[] lsf = new int[M];
        for (int i = 0; i < M; i++) {
            int predicted = add(AmrLsfTables.mean_lsf_3[i],
                    mult(pastResidual[i], AmrLsfTables.pred_fac_3[i]));
            lsf[i] = add(raw[i], predicted);
        }
        System.arraycopy(raw, 0, pastResidual, 0, M);
        reorderLsf(lsf);
        lsfToLsp(lsf, lspNew);
    }

    /**
     * Pitch lag of the 4.75 kbit/s mode: 8 bit absolute in the first and third
     * subframe, 4 bit relative in the second and fourth.
     */
    private void decodeLag3(int index, boolean firstOrThird, int[] lag) {
        if (firstOrThird) {
            if (index < 197) {
                int t0 = ((index + 2) * 10923 >> 15) + 19;
                lag[0] = t0;
                lag[1] = index - 3 * t0 + 58;
            } else {
                lag[0] = index - 112;
                lag[1] = 0;
            }
            return;
        }
        int t0Min = Math.max(oldT0 - 5, 20);
        int t0Max = t0Min + 9;
        if (t0Max > PIT_MAX) {
            t0Max = PIT_MAX;
            t0Min = t0Max - 9;
        }
        int reference = oldT0;
        if (reference - t0Min > 5) {
            reference = t0Min + 5;
        }
        if (t0Max - reference > 4) {
            reference = t0Max - 4;
        }
        if (index < 4) {
            lag[0] = reference - 5 + index;
            lag[1] = 0;
        } else if (index < 12) {
            int i = ((index - 5) * 10923 >> 15) - 1;
            lag[0] = i + reference;
            lag[1] = index - 9 - 3 * i;
        } else {
            lag[0] = index - 12 + reference + 1;
            lag[1] = 0;
        }
    }

    /** Places the two coded pulses, flipping the sign of the second track. */
    private static void fillPulses(int[] code, int[] positions, int signs, int count,
                                   int positive, int negative) {
        for (int i = 0; i < count; i++) {
            int sign = (signs & 1) == 1 ? positive : negative;
            if (positions[i] < L_SUBFR) {
                code[positions[i]] = sign;
            }
            signs >>= 1;
        }
    }

    /** Two pulses on two tracks, 9 bits (4.75 kbit/s). */
    private static void codebook2i40_9bits(int subframe, int signs, int index, int[] code) {
        int track = (index & 64) >> 3;
        int position = index & 7;
        int k = subframe * 2 + track;
        int[] positions = new int[2];
        positions[0] = position * 5 + AmrTables.startPos[k];
        index >>= 3;
        position = index & 7;
        positions[1] = position * 5 + AmrTables.startPos[k + 1];
        fillPulses(code, positions, signs, 2, 8191, -8192);
    }






    /** 4.75 kbit/s gain: the table packs two subframes per entry. */
    private int[] decodeGain475(int index, int[] code, int evenSubframe) {
        int offset = index * 4 + ((1 ^ evenSubframe) << 1);
        short[] table = AmrGainTables.table_gain_MR475;
        int gainPitch = table[offset];
        double gFac = table[offset + 1] / 4096.0;
        int gainCode = (int) Math.round(2.0 * gFac * Math.pow(2.0, logGainDb(code)));
        double log2Gain = Math.log(gFac) / Math.log(2.0);
        int quaEnerMr122 = (int) Math.round(log2Gain * 1024);
        int quaEnerDb = (int) Math.round(20.0 * Math.log10(gFac) * 1024);
        updatePrediction(quaEnerDb, quaEnerMr122);
        return new int[]{gainPitch, gainCode};
    }

    // ------------------------------------------------------------ arithmetic

    private static int add(int left, int right) {
        int sum = left + right;
        if (sum > 32767) {
            return 32767;
        }
        if (sum < -32768) {
            return -32768;
        }
        return sum;
    }

    private static int saturate(int value) {
        if (value > 32767) {
            return 32767;
        }
        if (value < -32768) {
            return -32768;
        }
        return value;
    }

    private static int mult(int left, int right) {
        return saturate((left * right) >> 15);
    }

    private static int shr(int value, int count) {
        return value >> count;
    }

    private static int shl(int value, int count) {
        long shifted = (long) value << count;
        if (shifted > 32767) {
            return 32767;
        }
        if (shifted < -32768) {
            return -32768;
        }
        return (int) shifted;
    }

    private static long lMult(int left, int right) {
        return saturateLong((long) left * right * 2);
    }

    private static long lMac(long accumulator, int left, int right) {
        return saturateLong(accumulator + (long) left * right * 2);
    }

    private static long lShr(long value, int count) {
        if (count <= 0) {
            return count == 0 ? value : value << -count;
        }
        return value >> count;
    }

    private static long lShl(long value, int count) {
        return count <= 0 ? lShr(value, -count) : saturateLong(value << count);
    }

    private static long saturateLong(long value) {
        if (value > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        if (value < Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        }
        return value;
    }

    private static int round(long value) {
        return saturate((int) ((value + 0x8000) >> 16));
    }
}
