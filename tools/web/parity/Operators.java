import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/** Prints every OpMatrix result for a deterministic operand set. */
public final class Operators {
    static long seed = 987654321L;

    static long nextLong() {
        seed = seed * 6364136223846793005L + 1442695040888963407L;
        return seed;
    }

    static long[] values(Class<?> type) {
        if (type == byte.class) {
            return new long[]{0, 1, -1, 127, -128, 100, -100};
        }
        if (type == short.class) {
            return new long[]{0, 1, -1, 32767, -32768, 12345, -12345};
        }
        if (type == char.class) {
            return new long[]{0, 1, 65, 65535, 32768, 40000};
        }
        if (type == int.class) {
            return new long[]{0, 1, -1, 2, -2, 7, -7, 65535, 65536, -65536, 46341, -46341,
                2147483647, -2147483648, 1073741824, -1073741824, 1234567891, -1234567891,
                1000000007, 3, 10};
        }
        return new long[]{0, 1, -1, 2, -2, 3, 7, 65536, 4294967296L, -4294967296L,
            2147483647L, -2147483648L,
            1234567891234567L, -1234567891234567L, 1000003L, 10L};
    }

    static Object convert(long value, Class<?> type) {
        if (type == byte.class) {
            return (byte) value;
        }
        if (type == short.class) {
            return (short) value;
        }
        if (type == char.class) {
            return (char) value;
        }
        if (type == int.class) {
            return (int) value;
        }
        return value;
    }

    public static void main(String[] args) throws Exception {
        Class<?> matrix = Class.forName("com.ea.sims3.OpMatrix");
        List<Method> methods = new ArrayList<>(List.of(matrix.getDeclaredMethods()));
        methods.sort((a, b) -> a.getName().compareTo(b.getName()));
        StringBuilder out = new StringBuilder();
        for (Method method : methods) {
            Class<?>[] parameters = method.getParameterTypes();
            long[] lefts = values(parameters[0]);
            long[] rights = parameters.length > 1 ? values(parameters[1]) : new long[]{0};
            for (long left : lefts) {
                for (long right : rights) {
                    if (parameters.length > 1 && right == 0
                            && (method.getName().contains("divide") || method.getName().contains("remainder"))) {
                        continue;
                    }
                    Object result = parameters.length > 1
                            ? method.invoke(null, convert(left, parameters[0]), convert(right, parameters[1]))
                            : method.invoke(null, convert(left, parameters[0]));
                    out.append(method.getName()).append('|').append(left).append('|').append(right)
                            .append('|').append(result).append('\n');
                }
            }
        }
        System.out.print(out);
    }
}
