package javax.microedition.m3g;

/** Desktop shim for the JSR-184 {@code Camera} class. */
public class Camera extends Node {
    public static final int GENERIC = 48;
    public static final int PARALLEL = 49;
    public static final int PERSPECTIVE = 50;

    float fovy = 90f;
    float aspectRatio = 1f;
    float near = 1f;
    float far = 1000f;

    public Camera() {
    }

    public void setParallel(float fovy, float aspectRatio, float near, float far) {
        this.fovy = fovy;
        this.aspectRatio = aspectRatio;
        this.near = near;
        this.far = far;
    }

    public void setPerspective(float fovy, float aspectRatio, float near, float far) {
        setParallel(fovy, aspectRatio, near, far);
    }

    public float getVerticalFieldOfView() {
        return fovy;
    }
}
