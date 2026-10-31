package javax.microedition.lcdui;

/**
 * Desktop shim for the MIDP {@code Displayable} class.
 *
 * A displayable is the base of everything that can be shown on the emulated
 * screen. The logical screen size is fixed (see {@link Display}) and does not
 * depend on whether the displayable is currently visible, because the game
 * queries {@link #getWidth()} from its constructor.
 */
public abstract class Displayable {
    Display display;
    private boolean shown;
    private String title = "";

    Displayable() {
    }

    public int getWidth() {
        return Display.logicalWidth();
    }

    public int getHeight() {
        return Display.logicalHeight();
    }

    public boolean isShown() {
        return shown;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? "" : title;
    }

    /** Called by {@link Display} when the displayable becomes the current one. */
    void notifyShown() {
        shown = true;
        showNotify();
    }

    /** Called by {@link Display} when another displayable replaces this one. */
    void notifyHidden() {
        shown = false;
        hideNotify();
    }

    protected void showNotify() {
    }

    protected void hideNotify() {
    }
}
