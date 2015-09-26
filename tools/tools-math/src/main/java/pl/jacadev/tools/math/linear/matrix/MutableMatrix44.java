package pl.jacadev.tools.math.linear.matrix;

import pl.jacadev.tools.math.linear.vector.Vector3;

/**
 * @author Jaca777
 *         Created 2015-09-24 at 19
 */
public class MutableMatrix44 extends Matrix44{
    @Override
    public void setIdentity() {
        super.setIdentity();
    }

    @Override
    public void rotate(float angle, Vector3 axis) {
        super.rotate(angle, axis);
    }

    @Override
    public void translate(Vector3 vector) {
        super.translate(vector);
    }

    @Override
    public void translate(float x, float y, float z) {
        super.translate(x, y, z);
    }

    @Override
    public void add(Matrix44 matrix) {
        super.add(matrix);
    }

    @Override
    public void sub(Matrix44 matrix) {
        super.sub(matrix);
    }

    @Override
    public void mul(Matrix44 matrix) {
        super.mul(matrix);
    }

    @Override
    public void div(Matrix44 matrix) {
        super.div(matrix);
    }

    @Override
    public void set(int x, int y, float val) {
        super.set(x, y, val);
    }
}
