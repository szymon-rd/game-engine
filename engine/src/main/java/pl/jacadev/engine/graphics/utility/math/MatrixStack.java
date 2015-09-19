package pl.jacadev.engine.graphics.utility.math;

/**
 * @author Jaca777
 *         Created 17.11.14 at 18:26
 */

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import pl.jacadev.engine.graphics.utility.BufferTools;

import java.nio.FloatBuffer;

public class MatrixStack {

    private static final int MATRIX = 0;
    private static final int R_MATRIX = 1;

    private static final int DEFAULT_DEPTH = 32;

    private int depth;
    private Matrix4f[][] matrixStack;
    private int top = 0;

    public MatrixStack(int depth) {
        this.depth = depth;
        this.matrixStack = new Matrix4f[depth][2];
        this.matrixStack[top][MATRIX] = new Matrix4f();
        this.matrixStack[top][R_MATRIX] = new Matrix4f();
        fill();
    }

    public MatrixStack() {
        this(DEFAULT_DEPTH);
    }

    private void fill(){
       for(int i = 0; i < this.depth; i++) {
           matrixStack[i][MATRIX] = new Matrix4f();
           matrixStack[i][R_MATRIX] = new Matrix4f();
       }
    }

    /**
     * Pushes a new matrix onto stack.
     */
    public void push() {
        matrixStack[++top][MATRIX].load(matrixStack[top - 1][MATRIX]);
        matrixStack[top][R_MATRIX].load(matrixStack[top - 1][R_MATRIX]);
    }

    /**
     * Pops the value from the stack.
     */
    public void pop() {
        top--;
    }

    private Vector3f temp = new Vector3f();

    /**
     * Translates the top matrix of the stack.
     * @param x
     * @param y
     * @param z
     */
    public void translate(float x, float y, float z) {
        temp.set(x,y,z);
        matrixStack[top][MATRIX].translate(temp);
    }

    /**
     * Translates the top matrix of the stack.
     * @param vector
     */
    public void translate(Vector3f vector) {
        translate(vector.x, vector.y, vector.z);
    }

    /**
     * Rotates the top matrix of the stack.
     * @param rad Rotation angle in radians.
     * @param x
     * @param y
     * @param z
     */
    public void rotate(float rad, float x, float y, float z) {
        temp.set(x,y,z);
        matrixStack[top][MATRIX].rotate(rad, temp);
        matrixStack[top][R_MATRIX].rotate(rad, temp);
    }

    /**
     * Scales the top matrix of the stack.
     * @param x X scale.
     * @param y Y scale.
     * @param z Z scale.
     */
    public void scale(float x, float y, float z) {
        temp.set(x, y, z);
        matrixStack[top][MATRIX].scale(temp);
    }

    public void mul(Matrix4f matrix){
        Matrix4f.mul(matrixStack[top][MATRIX], matrix, matrixStack[top][MATRIX]);
    }
    /**
     * @return A direct FloatBuffer containing the top matrix.
     */
    public FloatBuffer topBuff() {
        return BufferTools.toDirectBuffer(top());
    }

    /**
     * @return The top matrix.
     */
    public Matrix4f top() {
        return matrixStack[top][MATRIX];
    }

    /**
     * @return A direct FloatBuffer containing a rotation matrix of the top matrix.
     */
    public FloatBuffer rotBuff() {
        return BufferTools.toDirectBuffer(rot());
    }

    /**
     * @return A rotation matrix of the top matrix.
     */
    public Matrix4f rot(){
        return matrixStack[top][R_MATRIX];
    }

    /**
     * Immutable MatrixStack containing only one, identity matrix.
     */
    private static final MatrixStack IDENTITY_STACK = new MatrixStack(1){
        @Override
        public void push() {
            throw new UnsupportedOperationException("MatrixStack.IDENTITY_STACK.push()");
        }

        @Override
        public void pop() {
            throw new UnsupportedOperationException("MatrixStack.IDENTITY_STACK.pop()");
        }

        @Override
        public void translate(float x, float y, float z) {
            throw new UnsupportedOperationException("MatrixStack.IDENTITY_STACK.translate(x,y,z)");
        }

        @Override
        public void translate(Vector3f vector) {
            throw new UnsupportedOperationException("MatrixStack.IDENTITY_STACK.translate(vector)");
        }

        @Override
        public void scale(float x, float y, float z) {
            throw new UnsupportedOperationException("MatrixStack.IDENTITY_STACK.scale(x,y,z)");
        }

        @Override
        public void rotate(float rad, float x, float y, float z) {
            throw new UnsupportedOperationException("MatrixStack.IDENTITY_STACK.rotate(x,y,z)");
        }

        @Override
        public void mul(Matrix4f matrix) {
            throw new UnsupportedOperationException("MatrixStack.IDENTITY_STACK.rotate(matrix)");
        }
    };

}

