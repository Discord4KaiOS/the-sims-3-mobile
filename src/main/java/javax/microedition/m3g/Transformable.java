package javax.microedition.m3g;

/** Desktop shim for the JSR-184 {@code Transformable} base class. */
public abstract class Transformable extends Object3D {
    final float[] translation = new float[3];
    final float[] scale = new float[]{1f, 1f, 1f};
    final float[] orientation = new float[]{0f, 0f, 0f, 1f};

    public void setTranslation(float tx, float ty, float tz) {
        translation[0] = tx;
        translation[1] = ty;
        translation[2] = tz;
    }

    public void getTranslation(float[] translation) {
        if (translation == null || translation.length < 3) {
            throw new IllegalArgumentException("translation must have length 3");
        }
        System.arraycopy(this.translation, 0, translation, 0, 3);
    }

    public void setScale(float sx, float sy, float sz) {
        scale[0] = sx;
        scale[1] = sy;
        scale[2] = sz;
    }

    public void getScale(float[] scale) {
        if (scale == null || scale.length < 3) {
            throw new IllegalArgumentException("scale must have length 3");
        }
        System.arraycopy(this.scale, 0, scale, 0, 3);
    }

    public void setOrientation(float angle, float ax, float ay, float az) {
        float length = (float) Math.sqrt(ax * ax + ay * ay + az * az);
        if (length == 0f) {
            throw new IllegalArgumentException("axis must not be zero length");
        }
        float half = (float) Math.toRadians(angle) / 2f;
        float scale = (float) Math.sin(half) / length;
        orientation[0] = ax * scale;
        orientation[1] = ay * scale;
        orientation[2] = az * scale;
        orientation[3] = (float) Math.cos(half);
    }

    public void getOrientation(float[] angleAxis) {
        if (angleAxis == null || angleAxis.length < 4) {
            throw new IllegalArgumentException("angleAxis must have length 4");
        }
        System.arraycopy(orientation, 0, angleAxis, 0, 4);
    }

    public void preRotate(float angle, float ax, float ay, float az) {
        setOrientation(angle, ax, ay, az);
    }

    public void postRotate(float angle, float ax, float ay, float az) {
        setOrientation(angle, ax, ay, az);
    }

    public void translate(float tx, float ty, float tz) {
        translation[0] += tx;
        translation[1] += ty;
        translation[2] += tz;
    }

    public void scale(float sx, float sy, float sz) {
        scale[0] *= sx;
        scale[1] *= sy;
        scale[2] *= sz;
    }
}
