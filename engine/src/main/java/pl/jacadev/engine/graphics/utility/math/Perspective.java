package pl.jacadev.engine.graphics.utility.math;

import org.lwjgl.util.vector.Matrix4f;

class Perspective {

    /**
     * @param fovDegrees Field of view in degrees.
     * @param aspectRatio Aspect ratio (Screen width / Screen height).
     * @param znear The nearest Z coordinate that would be rendered.
     * @param zfar The farthest Z coordinate that would be rendered.
     * @return Perspective matrix generated with the given parameters.
     */
    public static Matrix4f getPerspective(float fovDegrees, float aspectRatio,
                                          float znear, float zfar) {
        float ymax, xmax;
        ymax = (float) (znear * Math.tan(fovDegrees * Math.PI / 360.0));
        xmax = ymax * aspectRatio;
        return frustum(-xmax, xmax, -ymax, ymax, znear, zfar);
    }

    private static Matrix4f frustum(float left, float right, float bottom, float top, float near, float far) {
        Matrix4f matrix = new Matrix4f();
        matrix.setIdentity();

        matrix.m00 = 2 * near / (right - left);
        matrix.m11 = 2 * near / (top - bottom);
        matrix.m20 = (right + left) / (right - left);
        matrix.m21 = (top + bottom) / (top - bottom);
        matrix.m22 = -(far + near) / (far - near);
        matrix.m23 = -1;
        matrix.m32 = -2 * far * near / (far - near);
        matrix.m33 = 0;

        return matrix;
    }
}
