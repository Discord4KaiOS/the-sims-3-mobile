package javax.microedition.rms;

/** Desktop shim for the MIDP {@code RecordListener} interface. */
public interface RecordListener {
    void recordAdded(RecordStore recordStore, int recordId);

    void recordChanged(RecordStore recordStore, int recordId);

    void recordDeleted(RecordStore recordStore, int recordId);
}
