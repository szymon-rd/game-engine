package pl.jacadev.engine.graphics.scene.components;

import pl.jacadev.engine.graphics.contexts.OGLEngineContext;
import pl.jacadev.engine.graphics.scene.Component;
import pl.jacadev.engine.graphics.scene.Drawable;

/**
 * @author Jaca777
 *         Created 21.12.14 at 18:39
 */
public abstract class DrawableComponent implements Drawable, Component {
    /**
     *  Calls abstract onDraw. Exists for overriding.
     */
    @Override
    public void draw(OGLEngineContext engineContext) {
        onDraw(engineContext);
    }

    /**
     *
     */
    protected abstract void onDraw(OGLEngineContext engineContext);
}
