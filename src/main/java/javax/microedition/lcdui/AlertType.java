package javax.microedition.lcdui;

/**
 * Desktop shim for the MIDP {@code AlertType} class.
 *
 * Alerts are only used by the EA SDK to report fatal errors, so the type is a
 * pure marker here.
 */
public class AlertType {
    public static final AlertType INFO = new AlertType("info", true);
    public static final AlertType WARNING = new AlertType("warning", true);
    public static final AlertType ERROR = new AlertType("error", true);
    public static final AlertType ALARM = new AlertType("alarm", true);
    public static final AlertType CONFIRMATION = new AlertType("confirmation", true);

    private final String name;
    private final boolean sound;

    protected AlertType(String name, boolean sound) {
        this.name = name;
        this.sound = sound;
    }

    public String getName() {
        return name;
    }

    boolean isSoundEnabled() {
        return sound;
    }
}
