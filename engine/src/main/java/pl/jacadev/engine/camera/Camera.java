package pl.jacadev.engine.camera;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import pl.jacadev.engine.graphics.utility.math.ProjectionMatrix;

/**
 * @author Jaca777
 *         Created 22.12.14 at 22:34
 *         TODO Rewrite whole camera architecture.
 */
public interface Camera {
    Vector3f X_AXIS = new Vector3f(1, 0, 0);
    Vector3f Y_AXIS = new Vector3f(0, 1, 0);
    Vector3f Z_AXIS = new Vector3f(0, 0, 1);

    /**
     * @return Camera rotation and translation matrix.
     */
    Matrix4f cameraMatrix();
    Matrix4f mirrorCameraMatrix();

    /**
     * @return Camera rotation matrix.
     */
    Matrix4f rotationMatrix();
    Matrix4f mirrorRotationMatrix();

    /**
     * @return Camera position.
     */
    Vector3f getPos();

    float getPitch();
    float getYaw();

    ProjectionMatrix getProjection();
}
