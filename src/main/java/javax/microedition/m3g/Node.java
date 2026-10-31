package javax.microedition.m3g;

/** Desktop shim for the JSR-184 {@code Node} base class. */
public abstract class Node extends Transformable {
    public static final int NONE = 0;
    public static final int ORIGIN = 1;
    public static final int X_AXIS = 2;
    public static final int Y_AXIS = 3;
    public static final int Z_AXIS = 4;

    int alignment = NONE;

    public void setAlignment(int zReference, int zTarget) {
        this.alignment = zReference;
    }

    public int getAlignmentZReference() {
        return alignment;
    }

    public void setAlphaFactor(float alphaFactor) {
    }

    public float getAlphaFactor() {
        return 1f;
    }

    public void setPickingEnabled(boolean enabled) {
    }

    public boolean isPickingEnabled() {
        return false;
    }

    public boolean isVisible() {
        return true;
    }
}
