package javax.microedition.m3g;

/** Desktop shim for the JSR-184 {@code Sprite3D} class. */
public class Sprite3D extends Node {
    public static final int SPRITE = 32;

    private final boolean scaled;
    private final Image2D image;
    private final Appearance appearance;

    public Sprite3D(boolean scaled, Image2D image, Appearance appearance) {
        this.scaled = scaled;
        this.image = image;
        this.appearance = appearance == null ? new Appearance() : appearance;
    }

    public boolean isScaled() {
        return scaled;
    }

    public Image2D getImage() {
        return image;
    }

    public Appearance getAppearance() {
        return appearance;
    }

    public void setAppearance(Appearance appearance) {
    }

    public boolean pick(int scope, float x, float y, Camera camera) {
        return false;
    }
}
