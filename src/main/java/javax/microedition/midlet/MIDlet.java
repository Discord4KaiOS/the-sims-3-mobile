package javax.microedition.midlet;

/**
 * Desktop shim for the MIDP {@code MIDlet} class.
 *
 * Lifecycle calls that would go to the device's application manager are routed
 * to the desktop runtime: {@link #notifyDestroyed()} closes the window and ends
 * the process.
 */
public abstract class MIDlet {
    private static final java.util.Hashtable<String, String> PROPERTIES = new java.util.Hashtable<>();

    static {
        PROPERTIES.put("MIDlet-Name", "The Sims 3");
        PROPERTIES.put("MIDlet-Version", "7.7.65");
        PROPERTIES.put("MIDlet-Vendor", "Electronic Arts");
        PROPERTIES.put("MIDlet-1", "The Sims 3, /icon.png, com.ea.sims3.MonkeyApp");
        PROPERTIES.put("MicroEdition-Profile", "MIDP-2.0");
        PROPERTIES.put("MicroEdition-Configuration", "CLDC-1.1");
    }

    protected MIDlet() {
    }

    public final String getAppProperty(String key) {
        String override = System.getProperty("midlet." + key);
        if (override != null) {
            return override;
        }
        return PROPERTIES.get(key);
    }

    public final void notifyDestroyed() {
        System.exit(0);
    }

    public final void notifyPaused() {
    }

    public final void resumeRequest() {
    }

    public final boolean platformRequest(String url) {
        System.out.println("[platformRequest] " + url);
        return false;
    }

    public final void checkPermission(String permission) throws SecurityException {
    }

    protected abstract void startApp() throws MIDletStateChangeException;

    protected abstract void pauseApp();

    protected abstract void destroyApp(boolean unconditional) throws MIDletStateChangeException;
}
