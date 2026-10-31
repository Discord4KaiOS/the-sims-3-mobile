package javax.microedition.rms;

/** Desktop shim for the MIDP {@code RecordStoreException}. */
public class RecordStoreException extends Exception {
    public RecordStoreException() {
        super();
    }

    public RecordStoreException(String message) {
        super(message);
    }
}
