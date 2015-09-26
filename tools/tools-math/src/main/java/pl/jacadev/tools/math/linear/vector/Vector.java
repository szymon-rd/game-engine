package pl.jacadev.tools.math.linear.vector;

import java.nio.FloatBuffer;

/**
 * @author Jaca777
 *         Created 2015-09-21 at 18
 */
public abstract class Vector {
    private int size;

    public Vector(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public abstract void store(FloatBuffer buffer);

}
