package javax.microedition.m3g;

import javax.microedition.lcdui.Graphics;

/**
 * Software implementation of the subset of {@code Graphics3D} that this game
 * uses: a single sprite drawn with a parallel camera.
 *
 * The retail MIDlet only ever binds a 2D {@link Graphics} target and renders one
 * world containing one 1x1 {@link Image2D} sprite that is scaled up to cover the
 * viewport, which produces the colour fades between screens. That is exactly
 * what this rasterizer reproduces, including alpha blending.
 */
public final class Graphics3D extends Object3D {
    public static final int ANTIALIAS = 2;
    public static final int DITHER = 4;
    public static final int TRUE_COLOR = 8;
    public static final int OVERWRITE = 16;

    private static Graphics3D instance;

    private Graphics target;
    private int viewportX;
    private int viewportY;
    private int viewportWidth;
    private int viewportHeight;
    private Camera camera;

    private Graphics3D() {
    }

    public static Graphics3D getInstance() {
        if (instance == null) {
            instance = new Graphics3D();
        }
        return instance;
    }

    public void bindTarget(Object target) {
        bindTarget(target, true);
    }

    public void bindTarget(Object target, boolean depthBuffer) {
        bindTarget(target, depthBuffer, 0);
    }

    public void bindTarget(Object target, boolean depthBuffer, int hints) {
        if (!(target instanceof Graphics)) {
            throw new IllegalArgumentException("only LCDUI Graphics targets are supported");
        }
        this.target = (Graphics) target;
    }

    public void releaseTarget() {
        this.target = null;
    }

    public void setViewport(int x, int y, int width, int height) {
        this.viewportX = x;
        this.viewportY = y;
        this.viewportWidth = width;
        this.viewportHeight = height;
    }

    public int getViewportX() {
        return viewportX;
    }

    public int getViewportY() {
        return viewportY;
    }

    public int getViewportWidth() {
        return viewportWidth;
    }

    public int getViewportHeight() {
        return viewportHeight;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public Camera getCamera() {
        return camera;
    }

    public void clear(Background background) {
        if (target == null || background == null || !background.isColorClearEnabled()) {
            return;
        }
        target.fillRectWithAlpha(background.getColor(), viewportX, viewportY, viewportWidth, viewportHeight);
    }

    public void render(World world) {
        if (target == null || world == null) {
            return;
        }
        Camera active = camera != null ? camera : world.getActiveCamera();
        clear(world.getBackground());
        for (int index = 0; index < world.getChildCount(); index++) {
            renderNode(world.getChild(index), active);
        }
    }

    public void render(Node node) {
        if (target == null || node == null) {
            return;
        }
        renderNode(node, camera);
    }

    private void renderNode(Node node, Camera activeCamera) {
        if (node instanceof Sprite3D) {
            renderSprite((Sprite3D) node, activeCamera);
        } else if (node instanceof Group) {
            Group group = (Group) node;
            for (int index = 0; index < group.getChildCount(); index++) {
                renderNode(group.getChild(index), activeCamera);
            }
        }
    }

    private void renderSprite(Sprite3D sprite, Camera activeCamera) {
        Image2D image = sprite.getImage();
        if (image == null || image.getWidth() < 1 || image.getHeight() < 1) {
            return;
        }
        int argb = image.getPixel(0, 0);
        float[] spriteScale = new float[3];
        sprite.getScale(spriteScale);
        float[] spriteTranslation = new float[3];
        sprite.getTranslation(spriteTranslation);

        // Parallel projection: the viewport spans 2*near*tan(fovy/2) world units
        // vertically, so this many pixels correspond to one world unit.
        float fovy = activeCamera == null ? 90f : activeCamera.getVerticalFieldOfView();
        float near = activeCamera == null ? 1f : activeCamera.near;
        double visibleHeight = 2.0 * near * Math.tan(Math.toRadians(fovy) / 2.0);
        double pixelsPerUnit = viewportHeight / visibleHeight;

        int width = (int) Math.round(Math.abs(spriteScale[0]) * pixelsPerUnit);
        int height = (int) Math.round(Math.abs(spriteScale[1]) * pixelsPerUnit);
        int centerX = viewportX + viewportWidth / 2 + (int) Math.round(spriteTranslation[0] * pixelsPerUnit);
        int centerY = viewportY + viewportHeight / 2 - (int) Math.round(spriteTranslation[1] * pixelsPerUnit);

        int left = Math.max(viewportX, centerX - width / 2);
        int top = Math.max(viewportY, centerY - height / 2);
        int right = Math.min(viewportX + viewportWidth, centerX + width / 2);
        int bottom = Math.min(viewportY + viewportHeight, centerY + height / 2);
        if (right <= left || bottom <= top) {
            return;
        }

        int blending = sprite.getAppearance().getCompositingMode().getBlending();
        int color;
        if (blending == CompositingMode.ALPHA || blending == CompositingMode.ALPHA_ADD) {
            color = argb;
        } else if (blending == CompositingMode.MODULATE || blending == CompositingMode.MODULATE_X2) {
            int alpha = ((argb >>> 24) & 0xFF);
            if (blending == CompositingMode.MODULATE_X2) {
                alpha = Math.min(255, alpha * 2);
            }
            color = (alpha << 24) | (argb & 0xFFFFFF);
        } else {
            color = 0xFF000000 | (argb & 0xFFFFFF);
        }
        target.fillRectWithAlpha(color, left, top, right - left, bottom - top);
    }
}
