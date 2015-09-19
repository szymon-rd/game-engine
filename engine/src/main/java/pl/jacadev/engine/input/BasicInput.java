package pl.jacadev.engine.input;

import pl.jacadev.engine.EngineContext;
import pl.jacadev.engine.Task;

/**
 * @author Jaca777
 *         Created 10.05.15 at 01:15
 */
public class BasicInput implements Task {

    @Override
    public void init(EngineContext context) {
        KeyboardInput.init();
        MouseInput.init();
    }

    @Override
    public void update(long delta) {
        KeyboardInput.update();
        MouseInput.update();
    }
}
