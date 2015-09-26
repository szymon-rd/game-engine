package pl.jacadev.tools.math.linear.matrix;

import java.nio.FloatBuffer;

/**
 * @author Jaca777
 *         Created 2015-09-21 at 19
 */
public class Matrix33 extends Matrix{

    //TODO tests and methods

    private float[] values = new float[3 * 3];

    public Matrix33(float[] values) {
        super(3, 3);
        System.arraycopy(values, 0, this.values, 0, 3 * 3);
    }

    public Matrix33() {
        super(3, 3);
    }

    @Override
    public void store(FloatBuffer buffer) {
        //TODO
        throw new UnsupportedOperationException();
    }

    public float get(int r, int c){
        return values[r * 3 + c];
    }
}
