package javax.microedition.midlet;

/** Desktop shim for the MIDP {@code MIDletStateChangeException}. */
public class MIDletStateChangeException extends Exception {
    public MIDletStateChangeException() {
        super();
    }

    public MIDletStateChangeException(String message) {
        super(message);
    }
}
