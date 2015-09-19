package pl.jacadev.engine.graphics.contexts;

import pl.jacadev.engine.EngineContext;
import pl.jacadev.engine.graphics.OGLThread;
import pl.jacadev.engine.graphics.scene.Scene;

/**
 * @author Jaca777
 *         Created 2015-07-10 at 16
 */
public class OGLEngineContext implements EngineContext{
    private OGLThread.DisplayProps displayContext;
    private ShadingContext shadingContext;
    private RenderingContext renderingContext;
    private OGLThread oglThreadContext;
    private Scene sceneContext;
    private Bindings bindings;

    public OGLEngineContext(OGLThread oglThread) {
        this.displayContext = oglThread.getDisplayProps();
        this.shadingContext = new ShadingContext();
        this.renderingContext = new RenderingContext();
        this.bindings = new Bindings();
        this.oglThreadContext = oglThread;
    }

    public OGLThread.DisplayProps getDisplayContext() {
        return displayContext;
    }

    public ShadingContext getShadingContext() {
        return shadingContext;
    }

    public RenderingContext getRenderingContext() {
        return renderingContext;
    }

    public OGLThread getOglThreadContext() {
        return oglThreadContext;
    }

    public Scene getSceneContext() {
        return sceneContext;
    }

    public Bindings getBindings() {
        return bindings;
    }

    public void updateSceneContext(Scene sceneContext) {
        this.sceneContext = sceneContext;
    }
}
