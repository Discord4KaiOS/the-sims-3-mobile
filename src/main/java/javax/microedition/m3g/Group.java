package javax.microedition.m3g;

import java.util.ArrayList;
import java.util.List;

/** Desktop shim for the JSR-184 {@code Group} class. */
public class Group extends Node {
    final List<Node> children = new ArrayList<>();

    public Group() {
    }

    public void addChild(Node child) {
        if (child == null) {
            throw new NullPointerException("child");
        }
        if (!children.contains(child)) {
            children.add(child);
        }
    }

    public void removeChild(Node child) {
        children.remove(child);
    }

    public int getChildCount() {
        return children.size();
    }

    public Node getChild(int index) {
        return children.get(index);
    }

    public int getChildIndex(Node child) {
        return children.indexOf(child);
    }

    public boolean pick(int scope, float x, float y, javax.microedition.m3g.Camera camera) {
        return false;
    }
}
