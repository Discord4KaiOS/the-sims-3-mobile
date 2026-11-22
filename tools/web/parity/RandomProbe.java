import java.util.Random;

public final class RandomProbe {
    public static void main(String[] a) {
        int[] bounds = {1, 2, 3, 4, 5, 7, 8, 16, 17, 64, 99, 100, 128, 255, 256, 1000, 1024,
                        65536, 1000000, 1073741824, 2147483647};
        StringBuilder out = new StringBuilder();
        for (int bound : bounds) {
            Random random = new Random(42L);
            for (int i = 0; i < 200; i++) {
                out.append("nextInt(").append(bound).append(")=").append(random.nextInt(bound)).append(' ');
            }
            out.append('\n');
            Random r2 = new Random(7L);
            for (int i = 0; i < 20; i++) {
                out.append("next(").append(bound).append(")=").append(r2.nextInt()).append(' ');
            }
            out.append('\n');
        }
        System.out.print(out);
    }
}
