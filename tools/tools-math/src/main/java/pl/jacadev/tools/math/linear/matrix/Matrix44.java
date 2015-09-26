package pl.jacadev.tools.math.linear.matrix;

import pl.jacadev.tools.math.linear.vector.Vector3;

import java.nio.FloatBuffer;

/**
 * @author Jaca777
 *         Created 2015-09-21 at 19
 */
public abstract class Matrix44 extends Matrix {

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

    public float get(int r, int c) {
        return values[r * 4 + c];
    }

    protected void set(int x, int y, float val) {
        //TODO
        throw new UnsupportedOperationException();
    }

    protected void rotate(float angle, Vector3 axis) {
        //TODO
        throw new UnsupportedOperationException();
    }

    protected void translate(Vector3 vector) {
        //TODO
        throw new UnsupportedOperationException();
    }

    protected void translate(float x, float y, float z) {
        //TODO
        throw new UnsupportedOperationException();
    }

    protected void add(Matrix44 matrix) {
        //TODO
        throw new UnsupportedOperationException();
    }

    protected void sub(Matrix44 matrix) {
        //TODO
        throw new UnsupportedOperationException();
    }

    protected void mul(Matrix44 matrix) {
        //TODO
        throw new UnsupportedOperationException();
    }

    protected void div(Matrix44 matrix) {
        //TODO
        throw new UnsupportedOperationException();
    }

    protected void setIdentity() {
        //TODO
        throw new UnsupportedOperationException();
    }

}
