package pl.jacadev.engine.graphics.scene;

import pl.jacadev.engine.graphics.contexts.OGLEngineContext;

/**
 * @author Jaca777
 *         Created 2015-07-13 at 13
 */
public interface Drawable {
    /**
     * It's not always used to draw. Sometimes it just preforms changes on context.
     */
    void draw(OGLEngineContext engineContext);
}
