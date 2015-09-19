package pl.jacadev.engine.camera;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import pl.jacadev.engine.graphics.utility.math.PerspectiveMatrix;
import pl.jacadev.engine.graphics.utility.math.ProjectionMatrix;

/**
 * @author Jaca777
 *         Created 22.12.14 at 22:33
 *         TODO Rewrite.
 */
public class TPPCamera implements Camera {

    private ProjectionMatrix projection;

    private float maxPitch = (float) (Math.PI/2);
    private float maxYaw = (float) (2 * Math.PI);

    private Vector3f center = new Vector3f();
    private float radPitch, radYaw;
    private float distance = 7;

    private Matrix4f cameraMatrix = new Matrix4f();
    private Vector3f temp = new Vector3f();

    public TPPCamera(PerspectiveMatrix projection) {
        this.projection = projection;
    }

    @Override
    public Matrix4f cameraMatrix() {
        cameraMatrix.setIdentity();
        temp.set(0,0,-distance);
        cameraMatrix.translate(temp);
        cameraMatrix.rotate(radPitch, X_AXIS);
        cameraMatrix.rotate(radYaw, Y_AXIS);
        cameraMatrix.translate(center);
        return cameraMatrix;
    }

    @Override
    public Matrix4f mirrorCameraMatrix() {
        //TODO
        throw new UnsupportedOperationException();
    }

    private Matrix4f rotationMatrix = new Matrix4f();

    @Override
    public Matrix4f rotationMatrix() {
        rotationMatrix.setIdentity();
        rotationMatrix.rotate(radPitch, X_AXIS);
        rotationMatrix.rotate(radYaw, Y_AXIS);
        return rotationMatrix;
    }

    @Override
    public Matrix4f mirrorRotationMatrix() {
        //TODO
        throw new UnsupportedOperationException();
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
        if (Math.abs(radPitch) > maxPitch)
            radPitch = maxPitch * Math.signum(radPitch);
        if (Math.abs(radYaw) > maxYaw)
            radYaw %= maxYaw;
    }


    public float getPitch() {
        return radPitch;
    }

    public float getYaw() {
        return radYaw;
    }

    public float getX() {
        return center.x;
    }

    public float getY() {
        return center.y;
    }

    public float getZ() {
        return center.z;
    }

    public void setZ(float z) {
        center.setZ(z);
    }

    public void setY(float y) {
        center.setY(y);
    }

    public void setX(float x) {
        center.setX(x);
    }

    @Override
    public Vector3f getPos() {
        return center;
    }


    public Vector3f getCenter() {
        return center;
    }

    public void setCenter(Vector3f pos){
        this.center = pos;
    }
    public void setCenter(float x, float y, float z){
        this.center.setX(x);
        this.center.setY(y);
        this.center.setZ(z);
    }

    public void setDistance(float distance) {
        this.distance = Math.max(1f, distance);
    }

    public float getDistance() {
        return distance;
    }

    public void setMaxYaw(float maxYaw) {
        this.maxYaw = maxYaw;
    }

    public void setMaxPitch(float maxPitch) {
        this.maxPitch = maxPitch;
    }

    @Override
    public ProjectionMatrix getProjection() {
        return projection;
    }

}
