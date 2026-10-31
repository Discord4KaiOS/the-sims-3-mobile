package javax.microedition.media;

import javax.microedition.media.control.Control;

/** Desktop shim for the MIDP {@code Player} interface. */
public interface Player extends Controllable {
    int UNREALIZED = 100;
    int REALIZED = 200;
    int PREFETCHED = 300;
    int STARTED = 400;
    int CLOSED = 0;

    long TIME_UNKNOWN = -1;

    void realize() throws MediaException;

    void prefetch() throws MediaException;

    void start() throws MediaException;

    void stop() throws MediaException;

    void deallocate() throws MediaException;

    void close();

    void setLoopCount(int count);

    long setMediaTime(long now) throws MediaException;

    long getMediaTime();

    long getDuration();

    int getState();

    String getContentType();

    void addPlayerListener(PlayerListener playerListener);

    void removePlayerListener(PlayerListener playerListener);
}
