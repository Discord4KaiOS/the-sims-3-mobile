package javax.microedition.lcdui;

import java.awt.image.BufferedImage;

/**
 * Desktop shim for the MIDP {@code Font} class.
 *
 * The game renders its own bitmap fonts through the EA SDK text engine, so this
 * implementation only has to provide the metrics the SDK queries.
 */
public class Font {
    public static final int FACE_SYSTEM = 0;
    public static final int FACE_MONOSPACE = 32;
    public static final int FACE_PROPORTIONAL = 64;

    public static final int STYLE_PLAIN = 0;
    public static final int STYLE_BOLD = 1;
    public static final int STYLE_ITALIC = 2;
    public static final int STYLE_UNDERLINED = 4;

    public static final int SIZE_SMALL = 8;
    public static final int SIZE_MEDIUM = 0;
    public static final int SIZE_LARGE = 16;

    private static final Font DEFAULT = new Font(FACE_PROPORTIONAL, STYLE_PLAIN, SIZE_MEDIUM);

    private static java.awt.Graphics2D scratch;

    private final int face;
    private final int style;
    private final int size;
    private final java.awt.Font awtFont;

    private Font(int face, int style, int size) {
        this.face = face;
        this.style = style;
        this.size = size;
        String family = face == FACE_MONOSPACE ? java.awt.Font.MONOSPACED
                : face == FACE_PROPORTIONAL ? java.awt.Font.DIALOG : java.awt.Font.DIALOG;
        int awtStyle = java.awt.Font.PLAIN;
        if ((style & STYLE_BOLD) != 0) {
            awtStyle |= java.awt.Font.BOLD;
        }
        if ((style & STYLE_ITALIC) != 0) {
            awtStyle |= java.awt.Font.ITALIC;
        }
        this.awtFont = new java.awt.Font(family, awtStyle, pixelSize(size));
    }

    private static int pixelSize(int size) {
        switch (size) {
            case SIZE_SMALL:
                return 9;
            case SIZE_LARGE:
                return 16;
            default:
                return 12;
        }
    }

    public static Font getFont(int face, int style, int size) {
        return new Font(face, style, size);
    }

    public static Font getFont(int fontType) {
        return getFont((fontType >> 8) & 0xFF, fontType & 0xFF,
                (fontType & 0x10000) != 0 ? SIZE_LARGE : SIZE_MEDIUM);
    }

    public static Font getDefaultFont() {
        return DEFAULT;
    }

    public int getFace() {
        return face;
    }

    public int getStyle() {
        return style;
    }

    public int getSize() {
        return size;
    }

    public boolean isPlain() {
        return style == STYLE_PLAIN;
    }

    public boolean isBold() {
        return (style & STYLE_BOLD) != 0;
    }

    public boolean isItalic() {
        return (style & STYLE_ITALIC) != 0;
    }

    public boolean isUnderlined() {
        return (style & STYLE_UNDERLINED) != 0;
    }

    public int getHeight() {
        java.awt.FontMetrics fontMetrics = metrics();
        return fontMetrics.getHeight();
    }

    public int getBaselinePosition() {
        return metrics().getAscent();
    }

    public int charWidth(char character) {
        return metrics().charWidth(character);
    }

    public int charsWidth(char[] characters, int offset, int length) {
        return metrics().charsWidth(characters, offset, length);
    }

    public int stringWidth(String str) {
        return str == null ? 0 : metrics().stringWidth(str);
    }

    public int substringWidth(String str, int offset, int length) {
        if (str == null) {
            return 0;
        }
        int end = Math.min(str.length(), offset + length);
        return offset >= end ? 0 : metrics().stringWidth(str.substring(offset, end));
    }

    java.awt.Font getAwtFont() {
        return awtFont;
    }

    java.awt.FontMetrics metrics() {
        java.awt.Graphics2D scratch = scratchGraphics();
        return scratch.getFontMetrics(awtFont);
    }

    private static synchronized java.awt.Graphics2D scratchGraphics() {
        if (scratch == null) {
            scratch = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).createGraphics();
        }
        return scratch;
    }
}
