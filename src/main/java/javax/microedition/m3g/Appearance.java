package javax.microedition.m3g;

/** Desktop shim for the JSR-184 {@code Appearance} class. */
public class Appearance extends Object3D {
    private CompositingMode compositingMode = new CompositingMode();
    private int layer;

    public Appearance() {
    }

    public void setCompositingMode(CompositingMode compositingMode) {
        this.compositingMode = compositingMode == null ? new CompositingMode() : compositingMode;
    }

    public CompositingMode getCompositingMode() {
        return compositingMode;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public int getLayer() {
        return layer;
    }
}
