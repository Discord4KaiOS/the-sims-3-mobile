package javax.microedition.media;

/** Desktop shim for the MIDP {@code PlayerListener}. */
public interface PlayerListener {
    String BUFFERING_STARTED = "bufferingStarted";
    String BUFFERING_STOPPED = "bufferingStopped";
    String CLOSED = "closed";
    String DEVICE_AVAILABLE = "deviceAvailable";
    String DEVICE_UNAVAILABLE = "deviceUnavailable";
    String DURATION_UPDATED = "durationUpdated";
    String END_OF_MEDIA = "endOfMedia";
    String ERROR = "error";
    String RECORD_ERROR = "recordError";
    String RECORD_STARTED = "recordStarted";
    String RECORD_STOPPED = "recordStopped";
    String SIZE_CHANGED = "sizeChanged";
    String STARTED = "started";
    String STOPPED = "stopped";
    String VOLUME_CHANGED = "volumeChanged";

    void playerUpdate(Player player, String event, Object eventData);
}
