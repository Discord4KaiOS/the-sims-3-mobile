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
        int w = Display.logicalWidth();
        int h = Display.logicalHeight();
        if (buffer == null || buffer.getWidth() != w || buffer.getHeight() != h) {
            buffer = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
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
    protected void paintComponent(java.awt.Graphics g0) {
        super.paintComponent(g0);
        java.awt.Graphics2D g = (java.awt.Graphics2D) g0;
        Displayable d = displayable;
        if (d == null) {
            return;
        }
        java.awt.Graphics2D bg = gameBufferGraphics();
        bg.setComposite(AlphaComposite.Src);
        bg.setClip(0, 0, buffer.getWidth(), buffer.getHeight());
        if (d instanceof Canvas) {
            Graphics lg = new Graphics(bg, buffer.getWidth(), buffer.getHeight());
            try {
                ((Canvas) d).paint(lg);
            } catch (RuntimeException e) {
                e.printStackTrace();
            } finally {
                lg.dispose();
            }
        } else if (d instanceof Alert) {
            ((Alert) d).paintTo(new Graphics(bg, buffer.getWidth(), buffer.getHeight()));
        }

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        double scale = Math.min((double) getWidth() / buffer.getWidth(), (double) getHeight() / buffer.getHeight());
        int dw = Math.max(1, (int) Math.round(buffer.getWidth() * scale));
        int dh = Math.max(1, (int) Math.round(buffer.getHeight() * scale));
        int dx = (getWidth() - dw) / 2;
        int dy = (getHeight() - dh) / 2;
        g.drawImage(buffer, dx, dy, dw, dh, null);
    }

    private void installKeyDispatcher() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getID() == KeyEvent.KEY_PRESSED) {
                    onKeyPressed(e);
                } else if (e.getID() == KeyEvent.KEY_RELEASED) {
                    onKeyReleased(e);
                }
                return false;
            }
        });
    }

    private void onKeyPressed(KeyEvent e) {
        int keyCode = mapKey(e.getKeyCode());
        if (keyCode == Integer.MIN_VALUE) {
            return;
        }
        Displayable d = displayable;
        if (!(d instanceof Canvas)) {
            return;
        }
        Canvas canvas = (Canvas) d;
        synchronized (pressedKeys) {
            if (!pressedKeys.add(e.getKeyCode())) {
                canvas.deliverKeyRepeated(keyCode);
                return;
            }
        }
        canvas.deliverKeyPressed(keyCode);
    }

    private void onKeyReleased(KeyEvent e) {
        int keyCode = mapKey(e.getKeyCode());
        if (keyCode == Integer.MIN_VALUE) {
            return;
        }
        Displayable d = displayable;
        if (!(d instanceof Canvas)) {
            return;
        }
        synchronized (pressedKeys) {
            pressedKeys.remove(e.getKeyCode());
        }
        ((Canvas) d).deliverKeyReleased(keyCode);
    }

    /** Maps an AWT virtual key code to a MIDP key code. */
    private static int mapKey(int vk) {
        switch (vk) {
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
        if (vk >= KeyEvent.VK_0 && vk <= KeyEvent.VK_9) {
            return Canvas.KEY_NUM0 + (vk - KeyEvent.VK_0);
        }
        if (vk >= KeyEvent.VK_NUMPAD0 && vk <= KeyEvent.VK_NUMPAD9) {
            return Canvas.KEY_NUM0 + (vk - KeyEvent.VK_NUMPAD0);
        }
        return Integer.MIN_VALUE;
    }

    @Override
    public Dimension getPreferredSize() {
        return display.preferredWindowSize();
    }
}
