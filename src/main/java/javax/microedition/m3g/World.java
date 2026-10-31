package javax.microedition.m3g;

/** Desktop shim for the JSR-184 {@code World} class. */
public class World extends Group {
    private Camera activeCamera;
    private Background background;

    public World() {
    }

    public void setActiveCamera(Camera camera) {
        this.activeCamera = camera;
    }

    public Camera getActiveCamera() {
        return activeCamera;
    }

    public void setBackground(Background background) {
        this.background = background;
    }

    public Background getBackground() {
        return background;
    }
}
