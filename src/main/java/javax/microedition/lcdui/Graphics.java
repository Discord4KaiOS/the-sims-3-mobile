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

    private final java.awt.Graphics2D awtGraphics;
    private final int width;
    private final int height;
    private int color;
    private Font font = Font.getDefaultFont();
    private int strokeStyle = SOLID;

    Graphics(java.awt.Graphics2D awtGraphics, int width, int height) {
        this.awtGraphics = awtGraphics;
        this.width = width;
        this.height = height;
        setColor(0);
        awtGraphics.setComposite(AlphaComposite.SrcOver);
    }

    public void translate(int x, int y) {
        awtGraphics.translate(x, y);
    }

    public int getTranslateX() {
        return (int) awtGraphics.getTransform().getTranslateX();
    }

    public int getTranslateY() {
        return (int) awtGraphics.getTransform().getTranslateY();
    }

    public void setColor(int rgb) {
        this.color = rgb & 0x00FFFFFF;
        awtGraphics.setColor(new Color(this.color));
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
        awtGraphics.setStroke(style == DOTTED
                ? new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10f, new float[]{1f, 1f}, 0f)
                : new BasicStroke(1f));
    }

    public int getStrokeStyle() {
        return strokeStyle;
    }

    public void setClip(int x, int y, int rectWidth, int rectHeight) {
        awtGraphics.setClip(x, y, rectWidth, rectHeight);
    }

    public void clipRect(int x, int y, int rectWidth, int rectHeight) {
        awtGraphics.clipRect(x, y, rectWidth, rectHeight);
    }

    public int getClipX() {
        java.awt.Rectangle clipBounds = awtGraphics.getClipBounds();
        return clipBounds == null ? 0 : clipBounds.x;
    }

    public int getClipY() {
        java.awt.Rectangle clipBounds = awtGraphics.getClipBounds();
        return clipBounds == null ? 0 : clipBounds.y;
    }

    public int getClipWidth() {
        java.awt.Rectangle clipBounds = awtGraphics.getClipBounds();
        return clipBounds == null ? width : clipBounds.width;
    }

    public int getClipHeight() {
        java.awt.Rectangle clipBounds = awtGraphics.getClipBounds();
        return clipBounds == null ? height : clipBounds.height;
    }

    public void fillRect(int x, int y, int rectWidth, int rectHeight) {
        awtGraphics.fillRect(x, y, rectWidth, rectHeight);
    }

    public void drawRect(int x, int y, int rectWidth, int rectHeight) {
        awtGraphics.drawRect(x, y, rectWidth, rectHeight);
    }

    public void drawLine(int x1, int y1, int x2, int y2) {
        awtGraphics.drawLine(x1, y1, x2, y2);
    }

    public void drawArc(int x, int y, int rectWidth, int rectHeight, int startAngle, int arcAngle) {
        awtGraphics.drawArc(x, y, rectWidth, rectHeight, startAngle, arcAngle);
    }

    public void fillArc(int x, int y, int rectWidth, int rectHeight, int startAngle, int arcAngle) {
        awtGraphics.fillArc(x, y, rectWidth, rectHeight, startAngle, arcAngle);
    }

    public void fillRoundRect(int x, int y, int rectWidth, int rectHeight, int arcWidth, int arcHeight) {
        awtGraphics.fillRoundRect(x, y, rectWidth, rectHeight, arcWidth, arcHeight);
    }

    public void drawRoundRect(int x, int y, int rectWidth, int rectHeight, int arcWidth, int arcHeight) {
        awtGraphics.drawRoundRect(x, y, rectWidth, rectHeight, arcWidth, arcHeight);
    }

    public void fillTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        awtGraphics.fillPolygon(new int[]{x1, x2, x3}, new int[]{y1, y2, y3}, 3);
    }

    public void drawImage(Image image, int x, int y, int anchor) {
        if (image == null) {
            return;
        }
        int dx = anchorX(x, image.getWidth(), anchor);
        int dy = anchorY(y, image.getHeight(), anchor);
        awtGraphics.drawImage(image.getBufferedImage(), dx, dy, null);
    }

    /**
     * Copies a region of an image, optionally mirroring/rotating it, into an
     * anchored position.
     *
     * The transform is expressed as the affine map from source coordinates to
     * destination-local coordinates, then drawn clipped to the destination
     * rectangle.
     */
    public void drawRegion(Image src, int xSrc, int ySrc, int width, int height,
                           int transform, int xDest, int yDest, int anchor) {
        if (src == null || width <= 0 || height <= 0) {
            return;
        }
        boolean swap = transform == TRANS_ROT90 || transform == TRANS_ROT270
                || transform == TRANS_MIRROR_ROT90 || transform == TRANS_MIRROR_ROT270;
        int dstW = swap ? height : width;
        int dstH = swap ? width : height;
        int dx = anchorX(xDest, dstW, anchor);
        int dy = anchorY(yDest, dstH, anchor);

        // x' = m00*x + m01*y + m02, y' = m10*x + m11*y + m12, with x,y in image space.
        double m00 = 1, m01 = 0, m02 = -xSrc;
        double m10 = 0, m11 = 1, m12 = -ySrc;
        switch (transform) {
            case TRANS_NONE:
                break;
            case TRANS_MIRROR:
                m00 = -1;
                m02 = xSrc + width;
                break;
            case TRANS_ROT180:
                m00 = -1;
                m02 = xSrc + width;
                m11 = -1;
                m12 = ySrc + height;
                break;
            case TRANS_MIRROR_ROT180:
                m11 = -1;
                m12 = ySrc + height;
                break;
            case TRANS_ROT90:
                m00 = 0;
                m01 = -1;
                m02 = ySrc + height;
                m10 = 1;
                m11 = 0;
                m12 = -xSrc;
                break;
            case TRANS_ROT270:
                m00 = 0;
                m01 = 1;
                m02 = -ySrc;
                m10 = -1;
                m11 = 0;
                m12 = xSrc + width;
                break;
            case TRANS_MIRROR_ROT90:
                m00 = 0;
                m01 = -1;
                m02 = ySrc + height;
                m10 = -1;
                m11 = 0;
                m12 = xSrc + width;
                break;
            case TRANS_MIRROR_ROT270:
                m00 = 0;
                m01 = 1;
                m02 = -ySrc;
                m10 = 1;
                m11 = 0;
                m12 = -xSrc;
                break;
            default:
                return;
        }

        java.awt.Shape savedClip = awtGraphics.getClip();
        AffineTransform saved = awtGraphics.getTransform();
        try {
            awtGraphics.clipRect(dx, dy, dstW, dstH);
            awtGraphics.translate(dx, dy);
            awtGraphics.drawImage(src.getBufferedImage(), new AffineTransform(m00, m10, m01, m11, m02, m12), null);
        } finally {
            awtGraphics.setTransform(saved);
            awtGraphics.setClip(savedClip);
        }
    }

    public void drawRGB(int[] rgbData, int offset, int scanlength, int x, int y, int width, int height,
                        boolean processAlpha) {
        if (rgbData == null || width <= 0 || height <= 0) {
            return;
        }
        int type = processAlpha ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB;
        BufferedImage image = new BufferedImage(width, height, type);
        int[] dst = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        for (int row = 0; row < height; row++) {
            int srcPos = offset + row * scanlength;
            System.arraycopy(rgbData, srcPos, dst, row * width, width);
        }
        awtGraphics.drawImage(image, x, y, null);
    }

    public void drawString(String text, int x, int y, int anchor) {
        if (text == null) {
            return;
        }
        java.awt.Font awt = font.getAwtFont();
        awtGraphics.setFont(awt);
        java.awt.FontMetrics fontMetrics = awtGraphics.getFontMetrics(awt);
        int rectWidth = fontMetrics.stringWidth(text);
        int rectHeight = fontMetrics.getHeight();
        int dx = anchorX(x, rectWidth, anchor);
        int dy;
        if ((anchor & BASELINE) != 0) {
            dy = y;
        } else if ((anchor & BOTTOM) != 0) {
            dy = y;
        } else if ((anchor & VCENTER) != 0) {
            dy = y + (rectHeight - fontMetrics.getAscent() - fontMetrics.getDescent()) / 2 + fontMetrics.getAscent();
        } else {
            dy = y + fontMetrics.getAscent();
        }
        awtGraphics.drawString(text, dx, dy);
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
        awtGraphics.copyArea(xSrc, ySrc, width, height, dx - xSrc, dy - ySrc);
    }

    /**
     * Paints a rectangle with a separate alpha value. Not part of MIDP; it is
     * used by the M3G shim for alpha blended sprite rendering.
     */
    public void fillRectWithAlpha(int argb, int x, int y, int rectWidth, int rectHeight) {
        int alpha = (argb >>> 24) & 0xFF;
        if (alpha >= 255) {
            setColor(argb & 0xFFFFFF);
            fillRect(x, y, rectWidth, rectHeight);
            return;
        }
        java.awt.Composite saved = awtGraphics.getComposite();
        Color savedColor = awtGraphics.getColor();
        try {
            awtGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha / 255f));
            awtGraphics.setColor(new Color(argb & 0xFFFFFF));
            awtGraphics.fillRect(x, y, rectWidth, rectHeight);
        } finally {
            awtGraphics.setComposite(saved);
            awtGraphics.setColor(savedColor);
        }
    }

    void dispose() {
        awtGraphics.setTransform(new AffineTransform());
        awtGraphics.setClip(0, 0, width, height);
        awtGraphics.setComposite(AlphaComposite.SrcOver);
    }

    private static int anchorX(int x, int rectWidth, int anchor) {
        if ((anchor & RIGHT) != 0) {
            return x - rectWidth;
        }
        if ((anchor & HCENTER) != 0) {
            return x - (rectWidth >> 1);
        }
        return x;
    }

    private static int anchorY(int y, int rectHeight, int anchor) {
        if ((anchor & BOTTOM) != 0) {
            return y - rectHeight;
        }
        if ((anchor & VCENTER) != 0) {
            return y - (rectHeight >> 1);
        }
        return y;
    }

    private static int clamp(int value) {
        return value < 0 ? 0 : Math.min(value, 255);
    }
}
