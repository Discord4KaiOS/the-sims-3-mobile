package javax.microedition.m3g;

/** Desktop shim for the JSR-184 {@code CompositingMode} class. */
public class CompositingMode extends Object3D {
    public static final int ALPHA = 64;
    public static final int ALPHA_ADD = 65;
    public static final int MODULATE = 66;
    public static final int MODULATE_X2 = 67;
    public static final int REPLACE = 68;

    private int blending = REPLACE;
    private boolean depthTestEnabled = true;
    private boolean depthWriteEnabled = true;
    private float alphaThreshold;
    private float threshold;

    public CompositingMode() {
    }

    public void setBlending(int mode) {
        this.blending = mode;
    }

    public int getBlending() {
        return blending;
    }

    public void setAlphaThreshold(float threshold) {
        this.alphaThreshold = threshold;
    }

    public float getAlphaThreshold() {
        return alphaThreshold;
    }

    public void setAlphaWriteEnable(boolean enable) {
    }

    public boolean isAlphaWriteEnabled() {
        return true;
    }

    public void setColorWriteEnable(boolean enable) {
    }

    public boolean isColorWriteEnabled() {
        return true;
    }

    public void setDepthWriteEnable(boolean enable) {
        this.depthWriteEnabled = enable;
    }

    public boolean isDepthWriteEnabled() {
        return depthWriteEnabled;
    }

    public void setDepthTestEnable(boolean enable) {
        this.depthTestEnabled = enable;
    }

    public boolean isDepthTestEnabled() {
        return depthTestEnabled;
    }

    public void setThreshold(float threshold) {
        this.threshold = threshold;
    }

    public float getThreshold() {
        return threshold;
    }
}
