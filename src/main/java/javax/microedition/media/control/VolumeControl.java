package javax.microedition.media.control;

/** Desktop shim for the MIDP {@code VolumeControl}. */
public interface VolumeControl extends Control {
    int setLevel(int level);

    int getLevel();

    void setMute(boolean mute);

    boolean isMuted();
}
