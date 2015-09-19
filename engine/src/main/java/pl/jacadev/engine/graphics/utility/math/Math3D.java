package pl.jacadev.engine.graphics.utility.math;

import org.lwjgl.util.vector.Vector3f;

/**
 * @author Jaca777
 *         Created 25.12.14 at 15:31
 */
public class Math3D {
    /**
     * Preforms a multiplication and returns a result.
     * @param vector Multiplicand.
     * @param mul Multiplier.
     * @return Result of the multiplication.
     */
    public static Vector3f mul(Vector3f vector, float mul){
        return new Vector3f(vector.getX() * mul, vector.getY() * mul, vector.getZ() * mul);
    }

    /**
     * Preforms a multiplication and stores the result in the given vector.
     * @param vector Multiplier, the result is stored in it.
     * @param mul Multiplicand.
     */
    public static void sMul(Vector3f vector, float mul){
        vector.set(vector.getX() * mul, vector.getY() * mul, vector.getZ() * mul);
    }

    /**
     * Preforms a division and returns a result.
     * @param vector Numerator.
     * @param div Denominator.
     * @return Result of the division.
     */
    public static Vector3f div(Vector3f vector, float div){
        return new Vector3f(vector.getX() / div, vector.getY() / div, vector.getZ() / div);
    }

    /**
     * Preforms a division and stores the result in the given vector.
     * @param vector Numerator, the result is stored in in it.
     * @param div Denominator.
     */
    public static void sDiv(Vector3f vector, float div){
        vector.set(vector.getX() / div, vector.getY() / div, vector.getZ() / div);
    }

    /**
     * Preforms an addition and returns a result.
     * @param v1 Component.
     * @param v2 Component.
     * @return Result of the addition.
     */
    public static Vector3f sum(Vector3f v1, Vector3f v2) {
        return new Vector3f(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z);
    }

    /**
     * Preforms an addition and stores the result in the given vector.
     * @param dest Component, the result is stored in in it.
     * @param v Component.
     */
    public static void sSum(Vector3f dest, Vector3f v) {
        dest.set(dest.x + v.x, dest.y + v.y, dest.z + v.z);
    }
}
