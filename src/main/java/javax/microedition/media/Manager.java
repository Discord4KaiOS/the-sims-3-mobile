package javax.microedition.media;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

/**
 * Desktop shim for the MIDP {@code Manager}.
 *
 * Creates real players for the three formats the game ships: standard MIDI
 * files (music), AMR-NB (speech and ambience) and RIFF/WAVE.
 */
public final class Manager {
    public static final String TONE_DEVICE_LOCATOR = "device://tone";

    private Manager() {
    }

    public static Player createPlayer(String locator) throws IOException, MediaException {
        if (locator == null) {
            throw new IllegalArgumentException("locator is null");
        }
        if (locator.startsWith(TONE_DEVICE_LOCATOR) || locator.startsWith("device://midi")) {
            throw new MediaException("tone and MIDI device locators are not supported: " + locator);
        }
        throw new MediaException("locator playback is not supported: " + locator);
    }

    public static Player createPlayer(InputStream stream, String type) throws IOException, MediaException {
        if (stream == null) {
            throw new IllegalArgumentException("stream is null");
        }
        return createPlayer(stream.readAllBytes(), type);
    }

    static Player createPlayer(byte[] data, String type) throws MediaException {
        String format = type == null ? sniff(data) : normalize(type);
        if (format == null) {
            throw new MediaException("unsupported content type: " + type);
        }
        switch (format) {
            case "audio/midi":
                return new MidiPlayer(data);
            case "audio/amr":
                return PcmPlayer.fromAmr(data);
            case "audio/wav":
                return PcmPlayer.fromWav(data);
            default:
                throw new MediaException("unsupported content type: " + type);
        }
    }

    private static String normalize(String type) {
        String base = type.trim().toLowerCase(Locale.ROOT);
        int semicolon = base.indexOf(';');
        if (semicolon >= 0) {
            base = base.substring(0, semicolon).trim();
        }
        switch (base) {
            case "audio/midi":
            case "audio/x-midi":
            case "audio/mid":
            case "audio/sp-midi":
                return "audio/midi";
            case "audio/amr":
            case "audio/3gpp":
                return "audio/amr";
            case "audio/x-wav":
            case "audio/wav":
            case "audio/wave":
            case "audio/x-pn-wav":
                return "audio/wav";
            default:
                return null;
        }
    }

    /** Identifies a stream by its magic bytes, for callers that pass no type. */
    private static String sniff(byte[] data) {
        if (data.length >= 4 && data[0] == 'M' && data[1] == 'T' && data[2] == 'h' && data[3] == 'd') {
            return "audio/midi";
        }
        if (data.length >= 6 && data[0] == '#' && data[1] == '!' && data[2] == 'A') {
            return "audio/amr";
        }
        if (data.length >= 12 && data[0] == 'R' && data[1] == 'I' && data[2] == 'F' && data[3] == 'F') {
            return "audio/wav";
        }
        return null;
    }
}
