package javax.microedition.media;

import java.io.ByteArrayInputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

/**
 * Plays in-memory 16 bit signed PCM.
 *
 * Both the WAV and the AMR paths decode to this form, so looping, seeking and
 * volume behave the same for either container.
 */
final class PcmPlayer extends AudioPlayer {
    private static final int CHUNK = 2048;

    private final byte[] pcm;
    private final int sampleRate;
    private final int channels;

    private volatile boolean running;
    private volatile long seekMillis;
    private volatile long playedMicros;
    private volatile Thread pump;

    PcmPlayer(byte[] pcm, int sampleRate, int channels, String contentType) {
        super(contentType);
        this.pcm = pcm;
        this.sampleRate = sampleRate;
        this.channels = channels;
    }

    private int bytesPerMilli() {
        return Math.max(1, sampleRate * channels * 2 / 1000);
    }

    @Override
    protected void open() {
    }

    @Override
    protected void play() {
        running = true;
        playedMicros = 0;
        int startByte = (int) Math.min(pcm.length, seekMillis * bytesPerMilli());
        pump = startThread("midp-pcm-player", () -> pump(startByte));
    }

    @Override
    protected void halt() {
        running = false;
        Thread thread = pump;
        if (thread != null) {
            thread.interrupt();
        }
    }

    @Override
    protected void release() {
        halt();
    }

    @Override
    protected long mediaTime() {
        return seekMillis * 1000 + playedMicros;
    }

    @Override
    protected void seek(long millis) {
        seekMillis = millis;
        playedMicros = 0;
    }

    @Override
    protected long duration() {
        return pcm.length / bytesPerMilli();
    }

    private void pump(int startByte) {
        int loops = loopCount();
        int remaining = loops;
        int offset = startByte;
        SourceDataLine line = null;
        try {
            line = audioDisabled() ? null : openLine();
            if (line == null) {
                playSilently(remaining);
                return;
            }
            line.start();
            byte[] buffer = new byte[CHUNK];
            while (running) {
                if (offset >= pcm.length) {
                    if (remaining > 0 && --remaining == 0) {
                        break;
                    }
                    offset = 0;
                }
                int length = Math.min(buffer.length, pcm.length - offset);
                length &= ~1;
                if (length <= 0) {
                    break;
                }
                scale(buffer, offset, length);
                line.write(buffer, 0, length);
                offset += length;
                playedMicros += length * 1000L / bytesPerMilli();
            }
            if (running) {
                line.drain();
            }
        } catch (Exception e) {
            System.err.println("[media] playback failed: " + e);
        } finally {
            if (line != null) {
                line.stop();
                line.close();
            }
            if (running) {
                finish();
            }
        }
    }

    /** Keeps the state machine timing correct when there is no audio device. */
    private void playSilently(int loops) {
        long deadline = loops < 0 ? Long.MAX_VALUE : System.currentTimeMillis() + duration() * loops;
        while (running && System.currentTimeMillis() < deadline) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        if (running && loops >= 0) {
            finish();
        }
    }

    private SourceDataLine openLine() {
        AudioFormat format = new AudioFormat(sampleRate, 16, channels, true, false);
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
        if (!AudioSystem.isLineSupported(info)) {
            return null;
        }
        try {
            SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(format, Math.max(CHUNK, sampleRate * channels / 4));
            return line;
        } catch (Exception e) {
            return null;
        }
    }

    /** Copies with the current volume applied. */
    private void scale(byte[] buffer, int offset, int length) {
        float gain = gain();
        for (int i = 0; i < length; i += 2) {
            int sample = (short) ((pcm[offset + i] & 0xFF) | (pcm[offset + i + 1] << 8));
            int scaled = Math.round(sample * gain);
            buffer[i] = (byte) scaled;
            buffer[i + 1] = (byte) (scaled >> 8);
        }
    }

    /** Decodes a RIFF/WAVE file to the PCM form this player expects. */
    static PcmPlayer fromWav(byte[] data) throws MediaException {
        try (AudioInputStream source = AudioSystem.getAudioInputStream(new ByteArrayInputStream(data))) {
            AudioFormat in = source.getFormat();
            AudioFormat target = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, in.getSampleRate(), 16,
                    in.getChannels(), in.getChannels() * 2, in.getSampleRate(), false);
            try (AudioInputStream pcm = AudioSystem.getAudioInputStream(target, source)) {
                return new PcmPlayer(pcm.readAllBytes(), (int) target.getSampleRate(),
                        target.getChannels(), "audio/x-wav");
            }
        } catch (Exception e) {
            throw new MediaException("cannot decode WAV: " + e.getMessage());
        }
    }

    /** Decodes an AMR-NB clip to 8 kHz mono PCM. */
    static PcmPlayer fromAmr(byte[] data) {
        byte[] pcm = AmrDecoder.decode(data);
        if (pcm == null) {
            pcm = new byte[(int) Math.min(Integer.MAX_VALUE, AmrDecoder.durationMillis(data) * 16)];
        }
        return new PcmPlayer(pcm, 8000, 1, "audio/amr");
    }

}
