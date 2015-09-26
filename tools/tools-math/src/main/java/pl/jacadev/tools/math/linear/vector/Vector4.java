package pl.jacadev.tools.math.linear.vector;

import java.nio.FloatBuffer;

/**
 * @author Jaca777
 *         Created 2015-09-21 at 19
 */
public class Vector4 extends Vector{
    private float x, y, z, w;

    public Vector4(float x, float y, float z, float w) {
        super(4);
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vector4() {
        super(4);
    }

    public Vector4(Vector4 vector) {
        super(4);
        this.x = vector.getX();
        this.y = vector.getY();
        this.z = vector.getZ();
        this.w = vector.getW();
    }

    public void dot(Vector2 vector){
        //TODO
        throw new UnsupportedOperationException();
    }

    public void cross(Vector2 vector){
        //TODO
        throw new UnsupportedOperationException();
    }

    public void multiply(Vector2 vector){
        //TODO
        throw new UnsupportedOperationException();
    }

    public void divide(Vector2 vector){
        //TODO
        throw new UnsupportedOperationException();
    }

    public void add(Vector2 vector){
        //TODO
        throw new UnsupportedOperationException();
    }

    public void sub(Vector2 vector){
        //TODO
        throw new UnsupportedOperationException();
    }

    public void negate(){
        //TODO
        throw new UnsupportedOperationException();
    }

    public void normalize(){
        //TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public void store(FloatBuffer buffer) {
        //TODO
        throw new UnsupportedOperationException();
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public float getW() {
        return w;
    }

    public void setW(float w) {
        this.w = w;
    }
}
