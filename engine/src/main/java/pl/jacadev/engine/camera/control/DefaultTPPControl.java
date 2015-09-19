package pl.jacadev.engine.camera.control;

import pl.jacadev.engine.camera.FPPCamera;
import pl.jacadev.engine.input.MouseInput;

/**
 * @author Jaca777
 *         Created 23.12.14 at 14:48
 */
public class DefaultTPPControl implements ControlInterface {
    private float rotationSpeed;
    private FPPCamera camera;

    @Override
    public void onUpdate(long delta) {
        camera.rotate(MouseInput.getYDelta() * delta * rotationSpeed, MouseInput.getXDelta() * delta * rotationSpeed);
    }
}
