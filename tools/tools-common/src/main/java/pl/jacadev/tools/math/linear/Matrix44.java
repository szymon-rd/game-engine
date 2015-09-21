package pl.jacadev.tools.math.linear;

import java.nio.FloatBuffer;

/**
 * @author Jaca777
 *         Created 2015-09-21 at 19
 */
public class Matrix44 extends Matrix {

    //TODO tests and methods

    private float[] values = new float[4 * 4];

    public Matrix44(float[] values) {
        super(4, 4);
        System.arraycopy(values, 0, this.values, 0, 4 * 4);
    }

    public Matrix44() {
        super(4, 4);
    }

    @Override
    public void store(FloatBuffer buffer) {
        //TODO
        throw new UnsupportedOperationException();
    }

    public float get(int r, int c){
        return values[r * 4 + c];
    }
}
