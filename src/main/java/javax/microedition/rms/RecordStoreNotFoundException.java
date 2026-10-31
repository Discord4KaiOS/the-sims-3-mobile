package javax.microedition.rms;

/** Desktop shim for the MIDP {@code RecordStoreNotFoundException}. */
public class RecordStoreNotFoundException extends RecordStoreException {
    public RecordStoreNotFoundException() {
        super();
    }

    public RecordStoreNotFoundException(String message) {
        super(message);
    }
}
