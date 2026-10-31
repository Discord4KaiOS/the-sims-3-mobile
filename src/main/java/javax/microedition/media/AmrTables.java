package javax.microedition.media;

/**
 * Shared signal-processing tables of the AMR-NB decoder (3GPP TS 26.073):
 * the cosine lookup used for LSF/LSP conversion, the 1/6-sample interpolation
 * filter, the Gray table of the algebraic codebook and the pulse start offsets.
 */
final class AmrTables {
    private AmrTables() {
    }

    /** 65 entries. */
    static final short[] lsfCosTable = {
        32767, 32729, 32610, 32413, 32138, 31786, 31357, 30853, 30274, 29622, 28899, 28106,
        27246, 26320, 25330, 24279, 23170, 22006, 20788, 19520, 18205, 16846, 15447, 14010,
        12540, 11039, 9512, 7962, 6393, 4808, 3212, 1608, 0, -1608, -3212, -4808,
        -6393, -7962, -9512, -11039, -12540, -14010, -15447, -16846, -18205, -19520, -20788, -22006,
        -23170, -24279, -25330, -26320, -27246, -28106, -28899, -29622, -30274, -30853, -31357, -31786,
        -32138, -32413, -32610, -32729, -32768,
    };

    /** 61 entries. */
    static final short[] inter6 = {
        29443, 28346, 25207, 20449, 14701, 8693, 3143, -1352, -4402, -5865, -5850, -4673,
        -2783, -672, 1211, 2536, 3130, 2991, 2259, 1170, 0, -1001, -1652, -1868,
        -1666, -1147, -464, 218, 756, 1060, 1099, 904, 550, 135, -245, -514,
        -634, -602, -451, -231, 0, 191, 308, 340, 296, 198, 78, -36,
        -120, -163, -165, -132, -79, -19, 34, 73, 91, 89, 70, 38,
        0,
    };

    /** 8 entries. */
    static final short[] dgray = {
        0, 1, 3, 2, 5, 6, 4, 7,
    };

    /** 16 entries. */
    static final short[] startPos = {
        0, 2, 0, 3, 0, 2, 0, 3, 1, 3, 2, 4,
        1, 4, 1, 4,
    };

}
