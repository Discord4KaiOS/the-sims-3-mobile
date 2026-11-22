import com.ea.sims3.FixedMath;

public final class FixedMathProbe {
    static long seed = 123456789L;

    static int next() {
        seed = seed * 6364136223846793005L + 1442695040888963407L;
        return (int) (seed >>> 33);
    }

    static int[] edge = {0, 1, -1, 2, -2, 65536, -65536, 65535, -65535, 32768, -32768, 32767,
        -32767, 205887, -205887, 20861, -20861, 102944, -102944, 411775, -411775, 65537,
        -65537, 131072, 46661, -46661, 4194304, -4194304, 8388608, 65200, 65800, 262143,
        1000000, -1000000, 16777216, 46341, 1000, 999, -999, 250000};

    public static void main(String[] a) {
        for (int l : edge) {
            for (int r : edge) {
                System.out.println("mul|" + l + "|" + r + "|" + FixedMath.mul(l, r));
            }
        }
        for (int l : edge) {
            for (int r : edge) {
                if (r == 0) {
                    continue;
                }
                System.out.println("div|" + l + "|" + r + "|" + FixedMath.div(l, r));
            }
        }
        for (int l : edge) {
            for (int r : edge) {
                System.out.println("length|" + l + "|" + r + "|" + FixedMath.length(l, r));
            }
        }
        for (int v = -70000; v <= 300000; v += 61) {
            System.out.println("sqrt|" + v + "|" + FixedMath.sqrt(v));
            System.out.println("sin|" + v + "|" + FixedMath.sin(v));
            System.out.println("cos|" + v + "|" + FixedMath.cos(v));
            System.out.println("sinShifted|" + v + "|" + FixedMath.sinShifted(v));
            System.out.println("wrapAngle|" + v + "|" + FixedMath.wrapAngle(v));
        }
        for (int i = 0; i < 40000; i++) {
            int v = next() % 900000;
            System.out.println("sqrt|" + v + "|" + FixedMath.sqrt(v));
            System.out.println("sin|" + v + "|" + FixedMath.sin(v));
            System.out.println("cos|" + v + "|" + FixedMath.cos(v));
        }
        for (int y = -400; y <= 400; y += 7) {
            for (int x = -400; x <= 400; x += 7) {
                System.out.println("atan2|" + y + "|" + x + "|" + FixedMath.atan2(y, x));
            }
        }
        for (int y : edge) {
            for (int x : edge) {
                System.out.println("atan2|" + y + "|" + x + "|" + FixedMath.atan2(y, x));
                System.out.println("smoothStep|" + x + "|" + y + "|" + FixedMath.smoothStep(x, y, (x + y) / 2));
            }
        }
        for (int i = 0; i < 20000; i++) {
            int e0 = next() % 200000 - 100000;
            int e1 = e0 + next() % 50000;
            int x = next() % 250000 - 100000;
            System.out.println("smoothStep|" + e0 + "|" + e1 + "|" + x + "|" + FixedMath.smoothStep(e0, e1, x));
        }
    }
}
