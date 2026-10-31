package javax.microedition.media;

import java.io.IOException;
import java.io.InputStream;
import javax.microedition.media.control.Control;
import javax.microedition.media.control.VolumeControl;

/** Desktop shim for the MIDP {@code Manager}. */
public final class Manager {
    public static final String TONE_DEVICE_LOCATOR = "device://tone";

    private Manager() {
    }

    public static Player createPlayer(String locator) throws IOException, MediaException {
        throw new MediaException("locator playback is not supported: " + locator);
    }

    public static Player createPlayer(InputStream stream, String type) throws IOException, MediaException {
        if (stream == null) {
            throw new IllegalArgumentException("stream is null");
        }
        return new SilentPlayer(type);
    }

    /**
     * The retail MIDlet ships without its audio assets and this port has no
     * sound files, so players are accepted but stay silent.
     */
    private static final class SilentPlayer implements Player, VolumeControl {
        private final String contentType;
        private int state = UNREALIZED;
        private int loopCount = 1;
        private long mediaTime;
        private int level = 100;
        private boolean muted;
        private PlayerListener listener;

        SilentPlayer(String contentType) {
            this.contentType = contentType;
        }

        public void realize() throws MediaException {
            if (state == UNREALIZED) {
                state = REALIZED;
            }
        }

        public void prefetch() throws MediaException {
            if (state == UNREALIZED) {
                state = REALIZED;
            }
            if (state == REALIZED) {
                state = PREFETCHED;
            }
        }

        public void start() throws MediaException {
            if (state == CLOSED) {
                throw new MediaException("player is closed");
            }
            prefetch();
            state = STARTED;
            notifyListener(PlayerListener.STARTED, null);
        }

        public void stop() throws MediaException {
            if (state == STARTED) {
                state = PREFETCHED;
                notifyListener(PlayerListener.STOPPED, null);
            }
        }

        public void deallocate() throws MediaException {
            if (state == PREFETCHED || state == STARTED) {
                state = REALIZED;
            }
        }

        public void close() {
            state = CLOSED;
            notifyListener(PlayerListener.CLOSED, null);
        }

        public void setLoopCount(int count) {
            this.loopCount = count;
        }

        public long setMediaTime(long now) throws MediaException {
            this.mediaTime = now;
            return now;
        }

        public long getMediaTime() {
            return mediaTime;
        }

        public long getDuration() {
            return TIME_UNKNOWN;
        }

        public int getState() {
            return state;
        }

        public String getContentType() {
            return contentType;
        }

        public void addPlayerListener(PlayerListener playerListener) {
            this.listener = playerListener;
        }

        public void removePlayerListener(PlayerListener playerListener) {
            if (listener == playerListener) {
                listener = null;
            }
        }

        public Control getControl(String controlType) {
            if (controlType == null) {
                return null;
            }
            String name = controlType.substring(controlType.lastIndexOf('.') + 1);
            return "VolumeControl".equals(name) ? this : null;
        }

        public Control[] getControls() {
            return new Control[]{this};
        }

        public int setLevel(int level) {
            this.level = Math.max(0, Math.min(100, level));
            return this.level;
        }

        public int getLevel() {
            return level;
        }

        public void setMute(boolean mute) {
            this.muted = mute;
        }

        public boolean isMuted() {
            return muted;
        }

        private void notifyListener(String event, Object data) {
            PlayerListener l = listener;
            if (l != null) {
                l.playerUpdate(this, event, data);
            }
        }
    }
}
