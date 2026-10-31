package javax.microedition.rms;

/** Desktop shim for the MIDP {@code RecordStoreNotOpenException}. */
public class RecordStoreNotOpenException extends RecordStoreException {
    public RecordStoreNotOpenException() {
        super();
    }

    public RecordStoreNotOpenException(String message) {
        super(message);
    }
}
