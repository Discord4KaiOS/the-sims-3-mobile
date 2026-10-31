package javax.microedition.lcdui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

/**
 * Desktop shim for the MIDP {@code Image} class.
 *
 * Mutability/immutability is tracked, because MIDP forbids drawing into a
 * loaded (immutable) image and the game relies on {@code getGraphics()} to
 * prepare ARGB masks.
 */
public class Image {
    private final BufferedImage image;
    private final boolean mutable;

    private Image(BufferedImage image, boolean mutable) {
        this.image = image;
        this.mutable = mutable;
    }

    public static Image createImage(int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("bad size " + width + "x" + height);
        }
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        return new Image(img, true);
    }

    public static Image createImage(String name) throws IOException {
        InputStream in = openResource(name);
        if (in == null) {
            throw new IOException("resource not found: " + name);
        }
        try {
            return decode(in, name);
        } finally {
            in.close();
        }
    }

    public static Image createImage(byte[] data, int offset, int length) throws IOException {
        return decode(new ByteArrayInputStream(data, offset, length), "<memory>");
    }

    public static Image createImage(Image source, int x, int y, int width, int height) {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.drawImage(source.image, -x, -y, null);
        g.dispose();
        return new Image(img, false);
    }

    public static Image createImage(InputStream stream) throws IOException {
        return decode(stream, "<stream>");
    }

    private static Image decode(InputStream in, String name) throws IOException {
        BufferedImage img = ImageIO.read(in);
        if (img == null) {
            throw new IOException("unsupported image data: " + name);
        }
        // Normalise to ARGB so alpha blending works the same for every source type.
        BufferedImage argb = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = argb.createGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();
        return new Image(argb, false);
    }

    private static InputStream openResource(String name) {
        String path = name.startsWith("/") ? name : "/" + name;
        InputStream in = Image.class.getResourceAsStream(path);
        if (in == null) {
            in = Thread.currentThread().getContextClassLoader().getResourceAsStream(path.substring(1));
        }
        return in;
    }

    public Graphics getGraphics() {
        if (!mutable) {
            throw new IllegalStateException("image is immutable");
        }
        Graphics2D g = image.createGraphics();
        return new Graphics(g, image.getWidth(), image.getHeight());
    }

    public int getWidth() {
        return image.getWidth();
    }

    public int getHeight() {
        return image.getHeight();
    }

    public boolean isMutable() {
        return mutable;
    }

    public void getRGB(int[] rgbData, int offset, int scanlength, int x, int y, int width, int height) {
        image.getRGB(x, y, width, height, rgbData, offset, scanlength);
    }

    public void setRGB(int[] rgbData, int offset, int scanlength, int x, int y, int width, int height) {
        image.setRGB(x, y, width, height, rgbData, offset, scanlength);
    }

    /** Native image used by the {@link Graphics} shim. */
    public BufferedImage getBufferedImage() {
        return image;
    }
}
