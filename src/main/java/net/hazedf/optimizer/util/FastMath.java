package net.hazedf.optimizer.util;

public class FastMath {
    private static final int SIN_BITS = 12;
    private static final int SIN_MASK = ~(-1 << SIN_BITS);
    private static final int SIN_COUNT = SIN_MASK + 1;
    private static final float radFull = (float) (Math.PI * 2.0);
    private static final float degFull = (float) (360.0);
    private static final float radToIndex = SIN_COUNT / radFull;
    private static final float[] sinTable = new float[SIN_COUNT];

    static {
        for (int i = 0; i < SIN_COUNT; i++) {
            sinTable[i] = (float) Math.sin((i + 0.5f) / SIN_COUNT * radFull);
        }
        for (int i = 0; i < 360; i += 90) {
            sinTable[(int) (i * radToIndex) & SIN_MASK] = (float) Math.sin(i * Math.PI / 180.0);
        }
    }

    public static float sin(float rad) {
        return sinTable[(int) (rad * radToIndex) & SIN_MASK];
    }

    public static float cos(float rad) {
        return sinTable[(int) ((rad + Math.PI / 2) * radToIndex) & SIN_MASK];
    }
}
