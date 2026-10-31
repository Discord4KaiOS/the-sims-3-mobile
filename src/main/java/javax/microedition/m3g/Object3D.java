package javax.microedition.m3g;

/** Desktop shim for the JSR-184 {@code Object3D} base class. */
public abstract class Object3D {
    private int userID;
    private Object userObject;

    private boolean animationEnabled = true;
    private int[] animationTracks;

    public final int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public final Object getUserObject() {
        return userObject;
    }

    public final void setUserObject(Object userObject) {
        this.userObject = userObject;
    }

    public final void setAnimationEnabled(boolean enable) {
        this.animationEnabled = enable;
    }

    public final boolean isAnimationEnabled() {
        return animationEnabled;
    }

    public int animate(int time) {
        return 0;
    }

    public void addAnimationTrack(int animationTrack) {
    }

    public void removeAnimationTrack(int animationTrack) {
    }

    public int getAnimationTrackCount() {
        return animationTracks == null ? 0 : animationTracks.length;
    }

    Object3D duplicate() {
        return this;
    }
}
