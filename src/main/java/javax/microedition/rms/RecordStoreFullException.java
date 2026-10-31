package javax.microedition.rms;

/** Desktop shim for the MIDP {@code RecordStoreFullException}. */
public class RecordStoreFullException extends RecordStoreException {
    public RecordStoreFullException() {
        super();
    }

    public RecordStoreFullException(String message) {
        super(message);
    }
}
