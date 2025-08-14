package game.util;

import java.util.Random;

public class Rng {
    private static final Random R = new Random();
    public static int range(int min, int max) { return R.nextInt(max - min + 1) + min; }
    public static boolean chance(int pct) { return R.nextInt(100) < pct; }
}