package javax.microedition.media;

/** Desktop shim for the MIDP {@code MediaException}. */
public class MediaException extends Exception {
    public MediaException() {
        super();
    }

    public MediaException(String message) {
        super(message);
    }
}
