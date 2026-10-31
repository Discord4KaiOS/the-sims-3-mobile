package javax.microedition.lcdui;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

/**
 * Desktop shim for the MIDP {@code Graphics} class, backed by
 * {@link java.awt.Graphics2D}.
 *
 * MIDP coordinates are top-left based and every primitive takes absolute
 * coordinates, so the translate state is applied with AWT transforms/clipping
 * which behave identically for the operations this game uses.
 */
public class Graphics {
    public static final int HCENTER = 1;
    public static final int VCENTER = 2;
    public static final int LEFT = 4;
    public static final int RIGHT = 8;
    public static final int TOP = 16;
    public static final int BOTTOM = 32;
    public static final int BASELINE = 64;

    public static final int SOLID = 0;
    public static final int DOTTED = 1;

    public static final int TRANS_NONE = 0;
    public static final int TRANS_MIRROR_ROT180 = 1;
    public static final int TRANS_MIRROR = 2;
    public static final int TRANS_ROT180 = 3;
    public static final int TRANS_MIRROR_ROT270 = 4;
    public static final int TRANS_ROT90 = 5;
    public static final int TRANS_ROT270 = 6;
    public static final int TRANS_MIRROR_ROT90 = 7;

    private final java.awt.Graphics2D g;
    private final int width;
    private final int height;
    private int color;
    private Font font = Font.getDefaultFont();
    private int strokeStyle = SOLID;

    Graphics(java.awt.Graphics2D g, int width, int height) {
        this.g = g;
        this.width = width;
        this.height = height;
        setColor(0);
        g.setComposite(AlphaComposite.SrcOver);
    }

    public void translate(int x, int y) {
        g.translate(x, y);
    }

    public int getTranslateX() {
        return (int) g.getTransform().getTranslateX();
    }

    public int getTranslateY() {
        return (int) g.getTransform().getTranslateY();
    }

    public void setColor(int rgb) {
        this.color = rgb & 0x00FFFFFF;
        g.setColor(new Color(this.color));
    }

    public void setColor(int red, int green, int blue) {
        setColor((clamp(red) << 16) | (clamp(green) << 8) | clamp(blue));
    }

    public int getColor() {
        return color;
    }

    public void setGrayScale(int value) {
        setColor(value, value, value);
    }

    public int getGrayScale() {
        return (color >> 16) & 0xFF;
    }

    public void setFont(Font font) {
        this.font = font == null ? Font.getDefaultFont() : font;
    }

    public Font getFont() {
        return font;
    }

    public void setStrokeStyle(int style) {
        this.strokeStyle = style;
        g.setStroke(style == DOTTED
                ? new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10f, new float[]{1f, 1f}, 0f)
                : new BasicStroke(1f));
    }

    public int getStrokeStyle() {
        return strokeStyle;
    }

    public void setClip(int x, int y, int w, int h) {
        g.setClip(x, y, w, h);
    }

    public void clipRect(int x, int y, int w, int h) {
        g.clipRect(x, y, w, h);
    }

    public int getClipX() {
        java.awt.Rectangle r = g.getClipBounds();
        return r == null ? 0 : r.x;
    }

    public int getClipY() {
        java.awt.Rectangle r = g.getClipBounds();
        return r == null ? 0 : r.y;
    }

    public int getClipWidth() {
        java.awt.Rectangle r = g.getClipBounds();
        return r == null ? width : r.width;
    }

    public int getClipHeight() {
        java.awt.Rectangle r = g.getClipBounds();
        return r == null ? height : r.height;
    }

    public void fillRect(int x, int y, int w, int h) {
        g.fillRect(x, y, w, h);
    }

    public void drawRect(int x, int y, int w, int h) {
        g.drawRect(x, y, w, h);
    }

    public void drawLine(int x1, int y1, int x2, int y2) {
        g.drawLine(x1, y1, x2, y2);
    }

    public void drawArc(int x, int y, int w, int h, int startAngle, int arcAngle) {
        g.drawArc(x, y, w, h, startAngle, arcAngle);
    }

    public void fillArc(int x, int y, int w, int h, int startAngle, int arcAngle) {
        g.fillArc(x, y, w, h, startAngle, arcAngle);
    }

    public void fillRoundRect(int x, int y, int w, int h, int arcWidth, int arcHeight) {
        g.fillRoundRect(x, y, w, h, arcWidth, arcHeight);
    }

    public void drawRoundRect(int x, int y, int w, int h, int arcWidth, int arcHeight) {
        g.drawRoundRect(x, y, w, h, arcWidth, arcHeight);
    }

    public void fillTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        g.fillPolygon(new int[]{x1, x2, x3}, new int[]{y1, y2, y3}, 3);
    }

    public void drawImage(Image img, int x, int y, int anchor) {
        if (img == null) {
            return;
        }
        int dx = anchorX(x, img.getWidth(), anchor);
        int dy = anchorY(y, img.getHeight(), anchor);
        g.drawImage(img.getBufferedImage(), dx, dy, null);
    }

    public void drawRegion(Image src, int xSrc, int ySrc, int width, int height,
                           int transform, int xDest, int yDest, int anchor) {
        if (src == null) {
            return;
        }
        boolean swap = transform == TRANS_ROT90 || transform == TRANS_ROT270
                || transform == TRANS_MIRROR_ROT90 || transform == TRANS_MIRROR_ROT270;
        int dstW = swap ? height : width;
        int dstH = swap ? width : height;
        int dx = anchorX(xDest, dstW, anchor);
        int dy = anchorY(yDest, dstH, anchor);

        AffineTransform saved = g.getTransform();
        java.awt.Shape savedClip = g.getClip();
        try {
            AffineTransform t = new AffineTransform();
            switch (transform) {
                case TRANS_NONE:
                    break;
                case TRANS_MIRROR:
                    t.translate(width, 0);
                    t.scale(-1, 1);
                    break;
                case TRANS_ROT180:
                    t.translate(width, height);
                    t.scale(-1, -1);
                    break;
                case TRANS_MIRROR_ROT180:
                    t.translate(0, height);
                    t.scale(1, -1);
                    break;
                case TRANS_ROT90:
                    t.translate(height, 0);
                    t.rotate(Math.PI / 2);
                    break;
                case TRANS_ROT270:
                    t.translate(0, width);
                    t.rotate(-Math.PI / 2);
                    break;
                case TRANS_MIRROR_ROT90:
                    t.translate(height, 0);
                    t.rotate(Math.PI / 2);
                    t.translate(width, 0);
                    t.scale(-1, 1);
                    break;
                case TRANS_MIRROR_ROT270:
                    t.translate(0, width);
                    t.rotate(-Math.PI / 2);
                    t.translate(width, 0);
                    t.scale(-1, 1);
                    break;
                default:
                    return;
            }
            t.translate(-xSrc, -ySrc);
            // Restrict drawing to the destination rectangle: the transform maps the
            // source region onto it, so clipping is what selects the region.
            g.clipRect(dx, dy, dstW, dstH);
            g.translate(dx, dy);
            g.drawImage(src.getBufferedImage(), t, null);
        } finally {
            g.setTransform(saved);
            g.setClip(savedClip);
        }
    }

    public void drawRGB(int[] rgbData, int offset, int scanlength, int x, int y, int width, int height,
                        boolean processAlpha) {
        if (rgbData == null || width <= 0 || height <= 0) {
            return;
        }
        int type = processAlpha ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB;
        BufferedImage img = new BufferedImage(width, height, type);
        int[] dst = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
        for (int row = 0; row < height; row++) {
            int srcPos = offset + row * scanlength;
            System.arraycopy(rgbData, srcPos, dst, row * width, width);
        }
        g.drawImage(img, x, y, null);
    }

    public void drawString(String text, int x, int y, int anchor) {
        if (text == null) {
            return;
        }
        java.awt.Font awt = font.getAwtFont();
        g.setFont(awt);
        java.awt.FontMetrics fm = g.getFontMetrics(awt);
        int w = fm.stringWidth(text);
        int h = fm.getHeight();
        int dx = anchorX(x, w, anchor);
        int dy;
        if ((anchor & BASELINE) != 0) {
            dy = y;
        } else if ((anchor & BOTTOM) != 0) {
            dy = y;
        } else if ((anchor & VCENTER) != 0) {
            dy = y + (h - fm.getAscent() - fm.getDescent()) / 2 + fm.getAscent();
        } else {
            dy = y + fm.getAscent();
        }
        g.drawString(text, dx, dy);
    }

    public void drawChar(char character, int x, int y, int anchor) {
        drawString(String.valueOf(character), x, y, anchor);
    }

    public void drawSubstring(String str, int offset, int len, int x, int y, int anchor) {
        if (str == null) {
            return;
        }
        int end = Math.min(str.length(), offset + len);
        if (offset >= end) {
            return;
        }
        drawString(str.substring(offset, end), x, y, anchor);
    }

    public void copyArea(int xSrc, int ySrc, int width, int height, int xDest, int yDest, int anchor) {
        int dx = anchorX(xDest, width, anchor);
        int dy = anchorY(yDest, height, anchor);
        g.copyArea(xSrc, ySrc, width, height, dx - xSrc, dy - ySrc);
    }

    /**
     * Paints a rectangle with a separate alpha value. Not part of MIDP; it is
     * used by the M3G shim for alpha blended sprite rendering.
     */
    public void fillRectWithAlpha(int argb, int x, int y, int w, int h) {
        int alpha = (argb >>> 24) & 0xFF;
        if (alpha >= 255) {
            setColor(argb & 0xFFFFFF);
            fillRect(x, y, w, h);
            return;
        }
        java.awt.Composite saved = g.getComposite();
        Color savedColor = g.getColor();
        try {
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha / 255f));
            g.setColor(new Color(argb & 0xFFFFFF));
            g.fillRect(x, y, w, h);
        } finally {
            g.setComposite(saved);
            g.setColor(savedColor);
        }
    }

    void dispose() {
        g.setTransform(new AffineTransform());
        g.setClip(0, 0, width, height);
        g.setComposite(AlphaComposite.SrcOver);
    }

    private static int anchorX(int x, int w, int anchor) {
        if ((anchor & RIGHT) != 0) {
            return x - w;
        }
        if ((anchor & HCENTER) != 0) {
            return x - (w >> 1);
        }
        return x;
    }

    private static int anchorY(int y, int h, int anchor) {
        if ((anchor & BOTTOM) != 0) {
            return y - h;
        }
        if ((anchor & VCENTER) != 0) {
            return y - (h >> 1);
        }
        return y;
    }

    private static int clamp(int value) {
        return value < 0 ? 0 : Math.min(value, 255);
    }
}
