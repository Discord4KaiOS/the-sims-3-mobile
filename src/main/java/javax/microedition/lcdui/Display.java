package javax.microedition.lcdui;

import java.awt.Dimension;
import javax.microedition.midlet.MIDlet;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Desktop shim for the MIDP {@code Display} class.
 *
 * Owns the Swing window and dispatches painting and key events to the current
 * {@link Displayable}. The emulated screen size can be overridden with the
 * {@code midp.screen.width}, {@code midp.screen.height} and {@code midp.scale}
 * system properties.
 */
public class Display {
    private static Display instance;

    private final MIDlet midlet;
    private final JFrame frame;
    private final ScreenPanel panel;
    private Displayable current;

    private Display(MIDlet midlet) {
        this.midlet = midlet;
        this.panel = new ScreenPanel(this);
        this.frame = new JFrame(midlet.getAppProperty("MIDlet-Name"));
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                Display.this.midlet.notifyDestroyed();
            }
        });
        frame.setContentPane(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setVisible(true);
        SwingUtilities.invokeLater(panel::requestFocusInWindow);
    }

    public static Display getDisplay(MIDlet midlet) {
        if (instance == null) {
            if (midlet == null) {
                throw new NullPointerException("midlet");
            }
            instance = new Display(midlet);
        }
        return instance;
    }

    static int logicalWidth() {
        return Integer.getInteger("midp.screen.width", 240);
    }

    static int logicalHeight() {
        return Integer.getInteger("midp.screen.height", 320);
    }

    static int windowScale() {
        return Integer.getInteger("midp.scale", 2);
    }

    public Displayable getCurrent() {
        return current;
    }

    public void setCurrent(Displayable next) {
        if (next == null) {
            throw new NullPointerException("next");
        }
        Displayable previous = current;
        if (previous != next) {
            current = next;
            next.display = this;
            if (previous != null) {
                previous.notifyHidden();
            }
            SwingUtilities.invokeLater(() -> {
                panel.setDisplayable(next);
                next.notifyShown();
            });
        }
    }

    public boolean isColor() {
        return true;
    }

    public int numColors() {
        return 65536;
    }

    public int numAlphaLevels() {
        return 256;
    }

    public boolean vibrate(int duration) {
        if (duration > 0) {
            java.awt.Toolkit.getDefaultToolkit().beep();
            return true;
        }
        return false;
    }

    public void flashBacklight(int duration) {
    }

    void postRepaint(Canvas canvas) {
        if (current == canvas) {
            panel.postRepaint();
        }
    }

    void paintNow(Canvas canvas) {
        if (current == canvas) {
            panel.paintCanvasNow();
        }
    }

    Dimension preferredWindowSize() {
        return new Dimension(logicalWidth() * windowScale(), logicalHeight() * windowScale());
    }

    /** Called by {@link MIDlet#notifyDestroyed()}. */
    void shutdown() {
        frame.dispose();
    }
}
