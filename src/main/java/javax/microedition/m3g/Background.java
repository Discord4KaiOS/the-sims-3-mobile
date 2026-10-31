package javax.microedition.m3g;

/** Desktop shim for the JSR-184 {@code Background} class. */
public class Background extends Object3D {
    private boolean colorClearEnabled = true;
    private boolean depthClearEnabled = true;
    private int color;
    private int cropX;
    private int cropY;
    private int cropWidth;
    private int cropHeight;

    public Background() {
    }

    public void setColor(int ARGB) {
        this.color = ARGB;
    }

    public int getColor() {
        return color;
    }

    public void setColorClearEnable(boolean enable) {
        this.colorClearEnabled = enable;
    }

    public boolean isColorClearEnabled() {
        return colorClearEnabled;
    }

    public void setDepthClearEnable(boolean enable) {
        this.depthClearEnabled = enable;
    }

    public boolean isDepthClearEnabled() {
        return depthClearEnabled;
    }

    public void setCrop(int x, int y, int width, int height) {
        this.cropX = x;
        this.cropY = y;
        this.cropWidth = width;
        this.cropHeight = height;
    }
}
