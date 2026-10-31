package javax.microedition.rms;

/** Desktop shim for the MIDP {@code InvalidRecordIDException}. */
public class InvalidRecordIDException extends RecordStoreException {
    public InvalidRecordIDException() {
        super();
    }

    public InvalidRecordIDException(String message) {
        super(message);
    }
}
