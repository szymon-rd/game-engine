package pl.jacadev.engine.graphics.utility;

/**
 * @author Jaca777
 *         Created 29.01.15 at 20:52
 *
 * Kind of threadsafe. Even if there is a race condition, number is still random.
 */
public class FastRandom {
    private static int seed;

    static {
        regenerateSeed();
    }

    private static int xorshift32() {
        seed ^= seed >> 12;
        seed ^= seed << 25;
        return seed ^= seed >> 27;
    }

    /**
     * @param start The lowest value that might be randomly generated.
     * @param length Range of draw.
     * @return A randomly generated value.
     */
    public static int fRandom(int start, int length){
        return start + (xorshift32() % length);
    }

    /**
     *
     * @param start The lowest value that might be randomly generated.
     * @param limit The highest value that might be randomly generated.
     * @return A randomly generated value.
     */
    public static int random(int start, int limit){
        return start + (xorshift32() % (limit - start + 1));
    }

    /**
     * Replaces the current seed with given.
     * @param seed Seed to use.
     */
    public static void useSeed(int seed){
        FastRandom.seed = seed;
    }

    /**
     * Regenerates seed using the nano time.
     */
    public static void regenerateSeed(){
        seed = (int) (System.nanoTime() * 0x000000000FFFFFFFF);
    }
}
