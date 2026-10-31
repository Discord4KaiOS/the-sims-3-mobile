package javax.microedition.lcdui;

/**
 * Desktop shim for the MIDP {@code Canvas} class.
 *
 * The subclass paints into a fixed size off-screen buffer which the window
 * scales up, so game coordinates never depend on the real window size.
 */
public abstract class Canvas extends Displayable {
    public static final int UP = 1;
    public static final int DOWN = 6;
    public static final int LEFT = 2;
    public static final int RIGHT = 5;
    public static final int FIRE = 8;
    public static final int GAME_A = 9;
    public static final int GAME_B = 10;
    public static final int GAME_C = 11;
    public static final int GAME_D = 12;

    public static final int KEY_NUM0 = 48;
    public static final int KEY_NUM1 = 49;
    public static final int KEY_NUM2 = 50;
    public static final int KEY_NUM3 = 51;
    public static final int KEY_NUM4 = 52;
    public static final int KEY_NUM5 = 53;
    public static final int KEY_NUM6 = 54;
    public static final int KEY_NUM7 = 55;
    public static final int KEY_NUM8 = 56;
    public static final int KEY_NUM9 = 57;
    public static final int KEY_STAR = 42;
    public static final int KEY_POUND = 35;

    public static final int KEY_UP = -1;
    public static final int KEY_DOWN = -2;
    public static final int KEY_LEFT = -3;
    public static final int KEY_RIGHT = -4;
    public static final int KEY_FIRE = -5;
    public static final int KEY_GAME_A = -6;
    public static final int KEY_GAME_B = -7;
    public static final int KEY_GAME_C = -8;
    public static final int KEY_GAME_D = -9;

    private boolean fullScreen;

    protected Canvas() {
    }

    protected abstract void paint(Graphics graphics);

    protected void keyPressed(int keyCode) {
    }

    protected void keyReleased(int keyCode) {
    }

    protected void keyRepeated(int keyCode) {
    }

    public void repaint() {
        Display currentDisplay = display;
        if (currentDisplay != null) {
            currentDisplay.postRepaint(this);
        }
    }

    public void repaint(int x, int y, int width, int height) {
        repaint();
    }

    public void serviceRepaints() {
        Display currentDisplay = display;
        if (currentDisplay != null) {
            currentDisplay.paintNow(this);
        }
    }

    public void setFullScreenMode(boolean enable) {
        this.fullScreen = enable;
    }

    public boolean isFullScreenMode() {
        return fullScreen;
    }

    public int getGameAction(int keyCode) {
        switch (keyCode) {
            case KEY_UP:
            case KEY_NUM2:
                return UP;
            case KEY_DOWN:
            case KEY_NUM8:
                return DOWN;
            case KEY_LEFT:
            case KEY_NUM4:
                return LEFT;
            case KEY_RIGHT:
            case KEY_NUM6:
                return RIGHT;
            case KEY_FIRE:
            case KEY_NUM5:
                return FIRE;
            case KEY_GAME_A:
                return GAME_A;
            case KEY_GAME_B:
                return GAME_B;
            case KEY_GAME_C:
                return GAME_C;
            case KEY_GAME_D:
                return GAME_D;
            default:
                return 0;
        }
    }

    public int getKeyCode(int gameAction) {
        switch (gameAction) {
            case UP:
                return KEY_UP;
            case DOWN:
                return KEY_DOWN;
            case LEFT:
                return KEY_LEFT;
            case RIGHT:
                return KEY_RIGHT;
            case FIRE:
                return KEY_FIRE;
            case GAME_A:
                return KEY_GAME_A;
            case GAME_B:
                return KEY_GAME_B;
            case GAME_C:
                return KEY_GAME_C;
            case GAME_D:
                return KEY_GAME_D;
            default:
                return 0;
        }
    }

    public String getKeyName(int keyCode) {
        if (keyCode >= KEY_NUM0 && keyCode <= KEY_NUM9) {
            return String.valueOf((char) keyCode);
        }
        switch (keyCode) {
            case KEY_UP:
                return "Up";
            case KEY_DOWN:
                return "Down";
            case KEY_LEFT:
                return "Left";
            case KEY_RIGHT:
                return "Right";
            case KEY_FIRE:
                return "Select";
            case KEY_STAR:
                return "*";
            case KEY_POUND:
                return "#";
            default:
                return "Key" + keyCode;
        }
    }

    public boolean hasPointerEvents() {
        return false;
    }

    public boolean hasPointerMotionEvents() {
        return false;
    }

    public boolean hasRepeatEvents() {
        return true;
    }

    public boolean isDoubleBuffered() {
        return true;
    }

    void deliverKeyPressed(int keyCode) {
        keyPressed(keyCode);
    }

    void deliverKeyReleased(int keyCode) {
        keyReleased(keyCode);
    }

    void deliverKeyRepeated(int keyCode) {
        keyRepeated(keyCode);
    }
}
