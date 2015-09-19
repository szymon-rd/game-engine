package pl.jacadev.engine.graphics.contexts;

import pl.jacadev.engine.EngineContext;
import pl.jacadev.engine.Task;

/**
 * @author Jaca777
 *         Created 2015-07-12 at 20
 */
public class OGLContextTask implements Task{
    private OGLEngineContext context;

    @Override
    public void init(EngineContext context) {
        this.context = (OGLEngineContext) context;
    }

    @Override
    public void update(long delta) {
        context.getShadingContext().syncPrograms(delta);
    }
}
