package javax.microedition.lcdui;

/**
 * Desktop shim for the MIDP {@code Alert}.
 *
 * The game only shows alerts when the EA SDK reports a fatal error, so a plain
 * text screen is enough; {@code setCurrent} on an alert logs the message.
 */
public class Alert extends Screen {
    private final String text;
    private final Image image;
    private final AlertType type;
    private int timeout = DEFAULT_TIMEOUT;
    private boolean reported;

    public static final int FOREVER = -2;
    public static final int DEFAULT_TIMEOUT = 3000;

    public Alert(String title, String text, Image image, AlertType type) {
        setTitle(title);
        this.text = text == null ? "" : text;
        this.image = image;
        this.type = type == null ? AlertType.INFO : type;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getTimeout() {
        return timeout;
    }

    public String getString() {
        return text;
    }

    public Image getImage() {
        return image;
    }

    public AlertType getType() {
        return type;
    }

    void paintTo(Graphics graphics) {
        graphics.setColor(0x000000);
        graphics.fillRect(0, 0, getWidth(), getHeight());
        if (!reported) {
            reported = true;
            System.err.println("[Alert/" + type.getName() + "] " + getTitle() + ": " + text);
        }
        graphics.setColor(0xFFFFFF);
        int y = 8;
        String message = getTitle().isEmpty() ? text : getTitle() + "\n" + text;
        for (String line : message.split("\n")) {
            graphics.drawString(line, getWidth() / 2, y, Graphics.TOP | Graphics.HCENTER);
            y += Font.getDefaultFont().getHeight() + 2;
        }
    }
}
