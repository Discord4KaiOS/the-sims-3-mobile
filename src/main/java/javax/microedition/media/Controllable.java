package javax.microedition.media;

import javax.microedition.media.control.Control;

/** Desktop shim for the MIDP {@code Controllable} interface. */
public interface Controllable {
    Control getControl(String controlType);

    Control[] getControls();
}
