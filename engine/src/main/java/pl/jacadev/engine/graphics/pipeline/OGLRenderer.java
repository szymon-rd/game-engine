package pl.jacadev.engine.graphics.pipeline;

import pl.jacadev.engine.graphics.contexts.OGLEngineContext;
import pl.jacadev.engine.graphics.textures.Texture;

/**
 * @author Jaca777
 *         Created 17.05.15 at 20:11
 */
public abstract class OGLRenderer {

    private boolean initiated;
    protected OGLEngineContext engineContext;

    public void init(OGLEngineContext context){
        this.engineContext = context;
        onInit();
        this.initiated = true;
    }
    protected abstract void onInit();
    public abstract void onRender();
    public abstract OGLRendererOutput<? extends Texture> getOutput();
    public abstract void onResize(int newW, int newH);

    /**
     * No need to implement everywhere - Renderer called first doesn't have source texture.
     */
    public void setInput(OGLRendererOutput<? extends Texture> texture){}

    public boolean isInitiated() {
        return initiated;
    }
}
