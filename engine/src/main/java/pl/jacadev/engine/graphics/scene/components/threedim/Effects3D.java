package pl.jacadev.engine.graphics.scene.components.threedim;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

/**
 * @author Jaca777
 *         Created 2015-07-19 at 19
 *         Graphical state of Component.
 */
public class Effects3D {

    /**
     * Transformations applied to model matrix.
     */
    private Transformation transformation;

    /**
     * Brightness of Component.
     * Color eq: outColor = fragColor * brightness
     */
    private float brightness;

    public Effects3D() {
        this.transformation = new Transformation();
        this.brightness = 1;
    }


    public void setBrightness(float brightness) {
        this.brightness = brightness;
    }

    public float getBrightness() {
        return brightness;
    }

    public Transformation getTransformation() {
        return transformation;
    }

    public class Transformation {
        private Matrix4f matrix = new Matrix4f();
        private Matrix4f scaleMatrix = new Matrix4f();
        private Matrix4f rotationMatrix = new Matrix4f();
        private Vector3f translation = new Vector3f();


        public void scale(Vector3f vec) {
            scaleMatrix.scale(vec);
        }

        public void translate(Vector3f vec) {
            Vector3f.add(translation, vec, translation);
        }


        public void rotate(float angle, Vector3f axis) {
            rotationMatrix.rotate(angle, axis);
        }

        public void setScale(Vector3f scale) {
            this.scaleMatrix.setIdentity();
            this.scaleMatrix.scale(scale);
        }

        public void setRotation(float angle, Vector3f axis) {
            this.rotationMatrix.setIdentity();
            this.rotationMatrix.rotate(angle, axis);
        }

        public void setTranslation(Vector3f translation) {
            this.translation.set(translation);
        }

        public void reset(){
            scaleMatrix.setIdentity();
            rotationMatrix.setIdentity();
            translation.set(0,0,0);
        }

        public Matrix4f getMatrix() {
            matrix.setIdentity();
            matrix.translate(translation);
            Matrix4f.mul(matrix, rotationMatrix, matrix);
            Matrix4f.mul(matrix, scaleMatrix, matrix);
            return matrix;
        }
    }


}
