package pl.jacadev.engine.graphics.utility.math;

import org.lwjgl.util.vector.Matrix4f;

/**
 * @author Jaca777
 *         Created 26.04.15 at 13:01
 */
public class PerspectiveMatrix extends ProjectionMatrix {
    private float fov;
    private float zNear, zFar;
    private float ratio;
    private Matrix4f matrix;

    public PerspectiveMatrix(float fov, float zNear, float zFar, int width, int height) {
        this.fov = fov;
        this.ratio = ((float) width) / height;
        this.zFar = zFar;
        this.zNear = zNear;
        recalcMatrix();
    }

    private void recalcMatrix() {
        this.matrix = Perspective.getPerspective(fov, ratio, zNear, zFar);
    }

    public float getFov() {
        return fov;
    }

    public void setFov(float fov) {
        this.fov = fov;
        recalcMatrix();
    }

    public float getzFar() {
        return zFar;
    }

    public void setzFar(float zFar) {
        this.zFar = zFar;
        recalcMatrix();
    }

    public float getzNear() {
        return zNear;
    }

    public void setzNear(float zNear) {
        this.zNear = zNear;
        recalcMatrix();
    }

    public void setDims(int w, int h) {
        this.ratio = ((float) w) / h;
        recalcMatrix();
    }

    public float getWHRatio() {
        return ratio;
    }

    public Matrix4f getMatrix() {
        return matrix;
    }
}
