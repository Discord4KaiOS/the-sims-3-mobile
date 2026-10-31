package javax.microedition.m3g;

/** Desktop shim for the JSR-184 {@code Image2D} class. */
public class Image2D extends Object3D {
    public static final int ALPHA = 96;
    public static final int LUMINANCE = 97;
    public static final int LUMINANCE_ALPHA = 98;
    public static final int RGB = 99;
    public static final int RGBA = 100;

    private final int format;
    private final int width;
    private final int height;
    private int[] pixels;

    public Image2D(int format, int width, int height) {
        this.format = format;
        this.width = width;
        this.height = height;
        this.pixels = new int[Math.max(1, width * height)];
    }

    public void set(int x, int y, int width, int height, byte[] data) {
        if (data == null) {
            throw new NullPointerException("data");
        }
        int bytesPerPixel = format == RGBA ? 4 : format == RGB ? 3
                : format == LUMINANCE_ALPHA ? 2 : 1;
        int pos = 0;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                int r;
                int g;
                int b;
                int a = 0xFF;
                switch (bytesPerPixel) {
                    case 4:
                        r = data[pos] & 0xFF;
                        g = data[pos + 1] & 0xFF;
                        b = data[pos + 2] & 0xFF;
                        a = data[pos + 3] & 0xFF;
                        break;
                    case 3:
                        r = data[pos] & 0xFF;
                        g = data[pos + 1] & 0xFF;
                        b = data[pos + 2] & 0xFF;
                        break;
                    case 2:
                        r = g = b = data[pos] & 0xFF;
                        a = data[pos + 1] & 0xFF;
                        break;
                    default:
                        r = g = b = data[pos] & 0xFF;
                        break;
                }
                pos += bytesPerPixel;
                int dstX = x + col;
                int dstY = y + row;
                if (dstX >= 0 && dstX < this.width && dstY >= 0 && dstY < this.height) {
                    pixels[dstY * this.width + dstX] = (a << 24) | (r << 16) | (g << 8) | b;
                }
            }
        }
    }

    public int getFormat() {
        return format;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /** ARGB pixel at the given position; used by the software rasterizer. */
    public int getPixel(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height) {
            return 0;
        }
        return pixels[y * width + x];
    }
}
