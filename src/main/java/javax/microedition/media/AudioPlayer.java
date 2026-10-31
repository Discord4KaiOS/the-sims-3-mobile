package javax.microedition.media;

import java.util.ArrayList;
import java.util.List;
import javax.microedition.media.control.Control;
import javax.microedition.media.control.VolumeControl;

/**
 * Desktop shim for the MIDP {@code Player} state machine.
 *
 * Implements the lifecycle, loop counting, listeners and volume handling shared
 * by all media types and leaves the actual rendering to the subclasses.
 *
 * MIDP loop counting is kept as-is: 1 plays once, -1 loops forever. Every
 * implementation is expected to report the natural end of the media through
 * {@link #finish()}, which fires {@code endOfMedia} - the game relies on that
 * event to clear its "sound is playing" state.
 */
abstract class AudioPlayer implements Player, VolumeControl {
    private final String contentType;
    private final List<PlayerListener> listeners = new ArrayList<>();
    private int state = UNREALIZED;
    private int loopCount = 1;
    private volatile int level = 100;
    private volatile boolean muted;

    AudioPlayer(String contentType) {
        this.contentType = contentType;
    }

    /** True when audio output is disabled for this JVM. */
    static boolean audioDisabled() {
        return "false".equalsIgnoreCase(System.getProperty("midp.audio.enabled", "true"));
    }

    // ------------------------------------------------------------------ hooks

    /** Opens the media source. Called once, exactly when the player is realized. */
    protected abstract void open() throws MediaException;

    /** Starts rendering; returns as soon as the media is running. */
    protected abstract void play() throws MediaException;

    /** Stops rendering, leaving the media ready to start again. */
    protected abstract void halt();

    /** Releases everything {@link #open()} acquired. */
    protected abstract void release();

    protected abstract long mediaTime();

    protected abstract void seek(long millis);

    protected abstract long duration();

    /** Applies the current level/mute state to a running player. */
    protected void applyVolume() {
    }

    // ----------------------------------------------------------------- Player

    public synchronized void realize() throws MediaException {
        if (state == UNREALIZED) {
            open();
            state = REALIZED;
        }
    }

    public synchronized void prefetch() throws MediaException {
        realize();
        if (state == REALIZED) {
            state = PREFETCHED;
        }
    }

    public void start() throws MediaException {
        List<PlayerListener> listeners;
        synchronized (this) {
            if (state == CLOSED) {
                throw new MediaException("player is closed");
            }
            prefetch();
            if (state != STARTED) {
                play();
                state = STARTED;
            }
            listeners = snapshot();
        }
        for (PlayerListener listener : listeners) {
            listener.playerUpdate(this, PlayerListener.STARTED, null);
        }
    }

    public void stop() throws MediaException {
        List<PlayerListener> listeners;
        synchronized (this) {
            if (state != STARTED) {
                return;
            }
            halt();
            state = PREFETCHED;
            listeners = snapshot();
        }
        for (PlayerListener listener : listeners) {
            listener.playerUpdate(this, PlayerListener.STOPPED, null);
        }
    }

    public synchronized void deallocate() throws MediaException {
        if (state == STARTED) {
            stop();
        }
        state = REALIZED;
    }

    public void close() {
        List<PlayerListener> listeners;
        synchronized (this) {
            if (state == CLOSED) {
                return;
            }
            if (state == STARTED) {
                halt();
            }
            release();
            state = CLOSED;
            listeners = snapshot();
        }
        for (PlayerListener listener : listeners) {
            listener.playerUpdate(this, PlayerListener.CLOSED, null);
        }
    }

    public void setLoopCount(int count) {
        if (count == 0) {
            throw new IllegalArgumentException("loop count 0 is not valid");
        }
        this.loopCount = count;
    }

    public synchronized long setMediaTime(long now) throws MediaException {
        if (state == CLOSED) {
            throw new MediaException("player is closed");
        }
        seek(Math.max(0, now));
        return mediaTime();
    }

    public synchronized long getMediaTime() {
        return state == CLOSED ? TIME_UNKNOWN : mediaTime();
    }

    public synchronized long getDuration() {
        return state == CLOSED ? TIME_UNKNOWN : duration();
    }

    public synchronized int getState() {
        return state;
    }

    public String getContentType() {
        return contentType;
    }

    public synchronized void addPlayerListener(PlayerListener playerListener) {
        if (playerListener != null) {
            listeners.add(playerListener);
        }
    }

    public synchronized void removePlayerListener(PlayerListener playerListener) {
        listeners.remove(playerListener);
    }

    // ---------------------------------------------------------- VolumeControl

    public int setLevel(int newLevel) {
        int clamped = Math.max(0, Math.min(100, newLevel));
        if (clamped != level) {
            level = clamped;
            applyVolume();
        }
        return level;
    }

    public int getLevel() {
        return level;
    }

    public void setMute(boolean mute) {
        if (muted != mute) {
            muted = mute;
            applyVolume();
        }
    }

    public boolean isMuted() {
        return muted;
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

    // ------------------------------------------------------------- for subclasses

    /** Loop count in MIDP terms: 1 plays once, -1 loops forever. */
    protected final int loopCount() {
        return loopCount;
    }

    /** Gain the subclasses should apply, as a factor between 0 and 1. */
    protected final float gain() {
        return muted ? 0f : level / 100f;
    }

    /** Reports the natural end of the media. */
    protected final void finish() {
        List<PlayerListener> listeners;
        synchronized (this) {
            if (state != STARTED) {
                return;
            }
            state = PREFETCHED;
            listeners = snapshot();
        }
        for (PlayerListener listener : listeners) {
            listener.playerUpdate(this, PlayerListener.END_OF_MEDIA, null);
        }
    }

    /** Runs the media on a daemon thread. */
    protected static Thread startThread(String name, Runnable body) {
        Thread thread = new Thread(body, name);
        thread.setDaemon(true);
        thread.start();
        return thread;
    }

    private List<PlayerListener> snapshot() {
        return new ArrayList<>(listeners);
    }
}
