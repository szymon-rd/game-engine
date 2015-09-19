package pl.jacadev.engine.camera;


import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import pl.jacadev.engine.graphics.shaders.programs.water.WaterSurface;
import pl.jacadev.engine.graphics.utility.math.ProjectionMatrix;

/**
 * @author Jaca777
 *         TODO Rewrite.
 */

public class FPPCamera implements Camera {

    private ProjectionMatrix projection;

    private static float MAX_PITCH = 90;
    private static float MAX_YAW = 360;

    private Vector3f pos = new Vector3f();
    private float radPitch, radYaw;

    private Matrix4f cameraMatrix = new Matrix4f();
    private Vector3f temp = new Vector3f();

    public FPPCamera(ProjectionMatrix projection) {
        this.projection = projection;
    }

    @Override
    public Matrix4f cameraMatrix() {
        cameraMatrix.setIdentity();
        cameraMatrix.rotate(radPitch, X_AXIS);
        cameraMatrix.rotate(radYaw, Y_AXIS);
        pos.negate(temp);
        cameraMatrix.translate(temp);
        return cameraMatrix;
    }

    @Override
    public Matrix4f mirrorCameraMatrix() {
        cameraMatrix.setIdentity();
        cameraMatrix.rotate(-radPitch, X_AXIS);
        cameraMatrix.rotate(radYaw, Y_AXIS);
        float yDiff = getY() - WaterSurface.WATER_LEVEL;
        pos.negate(temp);
        temp.setY(-(WaterSurface.WATER_LEVEL - yDiff));
        cameraMatrix.translate(temp);
        return cameraMatrix;
    }

    private Matrix4f rotationMatrix = new Matrix4f();

    @Override
    public Matrix4f rotationMatrix() { // pos.y -> pos.x -> pos.z
        rotationMatrix.setIdentity();
        rotationMatrix.rotate(radPitch, X_AXIS);
        rotationMatrix.rotate(radYaw, Y_AXIS);
        return rotationMatrix;
    }

    @Override
    public Matrix4f mirrorRotationMatrix() {
        rotationMatrix.setIdentity();
        rotationMatrix.rotate(-radPitch, X_AXIS);
        rotationMatrix.rotate(radYaw, Y_AXIS);
        return rotationMatrix;
    }


    public void setRotation(float radPitch, float radYaw) {
        this.radPitch = radPitch;
        this.radYaw = radYaw;
        checkRotationBounds();
    }

    public void setRadPitch(float radPitch) {
        this.radPitch = radPitch;
    }

    public void setRadYaw(float radYaw) {
        this.radYaw = radYaw;
    }

    public void rotate(float radPitch, float radYaw) {
        this.radPitch += radPitch;
        this.radYaw += radYaw;
        checkRotationBounds();
    }

    public void checkRotationBounds() {
        if (Math.abs(radPitch) > MAX_PITCH)
            radPitch = MAX_PITCH * Math.signum(radPitch);
        if (Math.abs(radYaw) > MAX_YAW)
            radYaw %= MAX_YAW;

    }

    public void moveForward(float delta, boolean yLocked) {
        float xTrans = (float) Math.sin(radYaw) * delta;
        float zTrans = (float) Math.cos(radYaw) * delta;
        if (!yLocked) {
            float yTrans = (float) (Math.sin(radPitch) * delta);
            float pitchCos = (float) Math.cos(radPitch);
            xTrans *= pitchCos;
            zTrans *= pitchCos;
            pos.y -= yTrans;
        }
        pos.x += xTrans;
        pos.z -= zTrans;
    }

    public void moveAside(float delta) {
        pos.x += (float) Math.cos(radYaw) * delta;
        pos.z += (float) Math.sin(radYaw) * delta;
    }


    public float getPitch() {
        return radPitch;
    }

    public float getYaw() {
        return radYaw;
    }

    public float getX() {
        return pos.x;
    }

    public float getY() {
        return pos.y;
    }

    public float getZ() {
        return pos.z;
    }

    @Override
    public Vector3f getPos() {
        return pos;
    }

    public void setPosition(Vector3f pos) {
        this.pos = pos;
    }

    @Override
    public ProjectionMatrix getProjection() {
        return projection;
    }
}
