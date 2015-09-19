package pl.jacadev.engine.camera.control;

import org.lwjgl.input.Keyboard;
import pl.jacadev.engine.camera.*;
import pl.jacadev.engine.input.KeyboardInput;
import pl.jacadev.engine.input.MouseInput;

import java.util.Objects;

/**
 * @author Jaca777
 *         Created 22.12.14 at 22:33
 */
public class DefaultFPPControl implements ControlInterface {
    private int keyForward = Keyboard.KEY_W;
    private int keyBack = Keyboard.KEY_S;
    private int keyLeft = Keyboard.KEY_A;
    private int keyRight = Keyboard.KEY_D;
    private FPPCamera camera;
    private float speed;
    private float rotationSpeed;
    private boolean zLocked;

    public DefaultFPPControl(FPPCamera camera, float speed, float rotationSpeed) {
        Objects.requireNonNull(camera);
        this.speed = speed;
        this.rotationSpeed = rotationSpeed;
        this.camera = camera;
    }

    @Override
    public void onUpdate(long delta) {
        if (KeyboardInput.isKeyDown(keyForward)) {
           camera.moveForward(delta * speed, zLocked);
        }
        if (KeyboardInput.isKeyDown(keyBack)) {
            camera.moveForward(-delta * speed, zLocked);
        }
        if (KeyboardInput.isKeyDown(keyLeft)) {
            camera.moveAside(-delta * speed);
        }
        if (KeyboardInput.isKeyDown(keyRight)) {
            camera.moveAside(delta * speed);
        }

        camera.rotate(-MouseInput.getYDelta() * delta * rotationSpeed, MouseInput.getXDelta() * delta * rotationSpeed);
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getRotationSpeed() {
        return rotationSpeed;
    }

    public void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public void setKeyForward(int keyForward) {
        this.keyForward = keyForward;
    }

    public void setKeyBack(int keyBack) {
        this.keyBack = keyBack;
    }

    public void setKeyLeft(int keyLeft) {
        this.keyLeft = keyLeft;
    }

    public void setKeyRight(int keyRight) {
        this.keyRight = keyRight;
    }

    public boolean isZLocked() {
        return zLocked;
    }

    public void setZLocked(boolean zLocked) {
        this.zLocked = zLocked;
    }
}
