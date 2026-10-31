package javax.microedition.lcdui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * The Swing component that hosts the emulated MIDP screen.
 *
 * The game paints into an off-screen buffer of the logical screen size, which
 * is then scaled (integer nearest-neighbour when possible) into the window.
 */
final class ScreenPanel extends JPanel {
    private final Display display;
    private BufferedImage buffer;
    private java.awt.Graphics2D bufferGraphics;
    private Displayable displayable;
    private boolean repaintPending;
    private final Set<Integer> pressedKeys = new HashSet<>();

    ScreenPanel(Display display) {
        this.display = display;
        setPreferredSize(display.preferredWindowSize());
        setBackground(Color.BLACK);
        setFocusable(true);
        setDoubleBuffered(false);
        installKeyDispatcher();
    }

    void setDisplayable(Displayable displayable) {
        this.displayable = displayable;
        ensureBuffer();
        requestFocusInWindow();
        repaint();
    }

    private void ensureBuffer() {
        int width = Display.logicalWidth();
        int height = Display.logicalHeight();
        if (buffer == null || buffer.getWidth() != width || buffer.getHeight() != height) {
            buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            bufferGraphics = buffer.createGraphics();
            bufferGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
            bufferGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        }
    }

    void postRepaint() {
        if (repaintPending) {
            return;
        }
        repaintPending = true;
        SwingUtilities.invokeLater(() -> {
            repaintPending = false;
            repaint();
        });
    }

    void paintCanvasNow() {
        if (SwingUtilities.isEventDispatchThread()) {
            paintImmediately(0, 0, Math.max(1, getWidth()), Math.max(1, getHeight()));
            return;
        }
        try {
            SwingUtilities.invokeAndWait(() ->
                    paintImmediately(0, 0, Math.max(1, getWidth()), Math.max(1, getHeight())));
        } catch (Exception ignored) {
        }
    }

    private java.awt.Graphics2D gameBufferGraphics() {
        ensureBuffer();
        return bufferGraphics;
    }

    @Override
    protected void paintComponent(java.awt.Graphics awtGraphics) {
        super.paintComponent(awtGraphics);
        java.awt.Graphics2D windowGraphics = (java.awt.Graphics2D) awtGraphics;
        Displayable current = displayable;
        if (current == null) {
            return;
        }
        java.awt.Graphics2D gameBuffer = gameBufferGraphics();
        gameBuffer.setComposite(AlphaComposite.Src);
        gameBuffer.setClip(0, 0, buffer.getWidth(), buffer.getHeight());
        if (current instanceof Canvas) {
            Graphics logicalGraphics = new Graphics(gameBuffer, buffer.getWidth(), buffer.getHeight());
            try {
                ((Canvas) current).paint(logicalGraphics);
            } catch (RuntimeException exception) {
                exception.printStackTrace();
            } finally {
                logicalGraphics.dispose();
            }
        } else if (current instanceof Alert) {
            ((Alert) current).paintTo(new Graphics(gameBuffer, buffer.getWidth(), buffer.getHeight()));
        }

        windowGraphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        double scale = Math.min((double) getWidth() / buffer.getWidth(), (double) getHeight() / buffer.getHeight());
        int destWidth = Math.max(1, (int) Math.round(buffer.getWidth() * scale));
        int destHeight = Math.max(1, (int) Math.round(buffer.getHeight() * scale));
        int destX = (getWidth() - destWidth) / 2;
        int destY = (getHeight() - destHeight) / 2;
        windowGraphics.drawImage(buffer, destX, destY, destWidth, destHeight, null);
    }

    private void installKeyDispatcher() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent event) {
                if (event.getID() == KeyEvent.KEY_PRESSED) {
                    onKeyPressed(event);
                } else if (event.getID() == KeyEvent.KEY_RELEASED) {
                    onKeyReleased(event);
                }
                return false;
            }
        });
    }

    private void onKeyPressed(KeyEvent event) {
        int keyCode = mapKey(event.getKeyCode());
        if (keyCode == Integer.MIN_VALUE) {
            return;
        }
        Displayable current = displayable;
        if (!(current instanceof Canvas)) {
            return;
        }
        Canvas canvas = (Canvas) current;
        synchronized (pressedKeys) {
            if (!pressedKeys.add(event.getKeyCode())) {
                canvas.deliverKeyRepeated(keyCode);
                return;
            }
        }
        canvas.deliverKeyPressed(keyCode);
    }

    private void onKeyReleased(KeyEvent event) {
        int keyCode = mapKey(event.getKeyCode());
        if (keyCode == Integer.MIN_VALUE) {
            return;
        }
        Displayable current = displayable;
        if (!(current instanceof Canvas)) {
            return;
        }
        synchronized (pressedKeys) {
            pressedKeys.remove(event.getKeyCode());
        }
        ((Canvas) current).deliverKeyReleased(keyCode);
    }

    /** Maps an AWT virtual key code to a MIDP key code. */
    private static int mapKey(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_UP:
                return Canvas.KEY_UP;
            case KeyEvent.VK_DOWN:
                return Canvas.KEY_DOWN;
            case KeyEvent.VK_LEFT:
                return Canvas.KEY_LEFT;
            case KeyEvent.VK_RIGHT:
                return Canvas.KEY_RIGHT;
            case KeyEvent.VK_ENTER:
            case KeyEvent.VK_SPACE:
                return Canvas.KEY_FIRE;
            case KeyEvent.VK_F1:
                return Canvas.KEY_GAME_A;
            case KeyEvent.VK_F2:
                return Canvas.KEY_GAME_B;
            case KeyEvent.VK_F3:
            case KeyEvent.VK_BACK_SPACE:
                return Canvas.KEY_GAME_C;
            case KeyEvent.VK_F4:
                return Canvas.KEY_GAME_D;
            case KeyEvent.VK_ASTERISK:
            case KeyEvent.VK_MULTIPLY:
                return Canvas.KEY_STAR;
            case KeyEvent.VK_NUMBER_SIGN:
                return Canvas.KEY_POUND;
            default:
                break;
        }
        if (keyCode >= KeyEvent.VK_0 && keyCode <= KeyEvent.VK_9) {
            return Canvas.KEY_NUM0 + (keyCode - KeyEvent.VK_0);
        }
        if (keyCode >= KeyEvent.VK_NUMPAD0 && keyCode <= KeyEvent.VK_NUMPAD9) {
            return Canvas.KEY_NUM0 + (keyCode - KeyEvent.VK_NUMPAD0);
        }
        return Integer.MIN_VALUE;
    }

    @Override
    public Dimension getPreferredSize() {
        return display.preferredWindowSize();
    }
}
