package pl.jacadev.tools.math.linear;

import java.nio.FloatBuffer;

/**
 * @author Jaca777
 *         Created 2015-09-21 at 19
 */
public class Vector2 extends Vector {

    private float x, y;

    public Vector2(float x, float y) {
        super(2);
        this.x = x;
        this.y = y;
    }

    public Vector2() {
        super(2);
    }

    public Vector2(Vector2 vector) {
        super(2);
        this.x = vector.getX();
        this.y = vector.getY();
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
}
