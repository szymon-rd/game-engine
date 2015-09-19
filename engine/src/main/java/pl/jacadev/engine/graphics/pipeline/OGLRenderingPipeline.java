package pl.jacadev.engine.graphics.pipeline;

import org.lwjgl.opengl.GL11;
import pl.jacadev.engine.EngineContext;
import pl.jacadev.engine.Task;
import pl.jacadev.engine.graphics.contexts.OGLEngineContext;

/**
 * @author Jaca777
 *         Created 2015-06-25 at 14
 *         Dynamic rendering pipeline.
 */
public class OGLRenderingPipeline implements Task {

    private boolean complete = true;
    private OGLEngineContext engineContext;
    private OGLRenderer[] pipeline = new OGLRenderer[0];
    private OGLOnScreenRenderer onScreenRenderer = new OGLOnScreenRenderer();

    /**
     * Adds renderer at the tail of pipeline.
     */
    public void addLast(OGLRenderer renderer) {
        OGLRenderer[] newPipeline = new OGLRenderer[pipeline.length + 1];
        System.arraycopy(this.pipeline, 0, newPipeline, 0, pipeline.length);
        newPipeline[this.pipeline.length] = renderer;
        this.pipeline = newPipeline;
        this.complete = false;
    }

    /**
     * Adds renderer at the head of pipeline.
     */
    public void addFirst(OGLRenderer renderer) {
        OGLRenderer[] newPipeline = new OGLRenderer[pipeline.length + 1];
        System.arraycopy(this.pipeline, 0, newPipeline, 1, pipeline.length);
        newPipeline[0] = renderer;
        this.pipeline = newPipeline;
        this.complete = false;
    }

    public void add(int index, OGLRenderer renderer) {
        if(index > -1 && index < pipeline.length) {
            OGLRenderer[] newPipeline = new OGLRenderer[pipeline.length + 1];
            System.arraycopy(this.pipeline, 0, newPipeline, 0, index - 1);
            newPipeline[index] = renderer;
            System.arraycopy(this.pipeline, index, newPipeline, index + 1, newPipeline.length - index);
            this.complete = false;
        } else throw new IllegalArgumentException("Index is either negative or out of bound of pipeline.");
    }

    public void set(OGLRenderer[] pipeline){
        this.pipeline = pipeline;
    }

    @Override
    public void init(EngineContext context) {
        this.engineContext = (OGLEngineContext) context;
        this.onScreenRenderer.init(this.engineContext);
    }

    private void syncTextures() {
        if(pipeline.length == 0){
            complete = true;
        } else {
            if(!pipeline[0].isInitiated()) pipeline[0].init(this.engineContext);
            OGLRenderer lastElem = pipeline[0];
            for(int i = 1; i < pipeline.length; i++) {
                OGLRenderer elem = pipeline[i];
                if(!elem.isInitiated()) elem.init(this.engineContext);
                elem.setInput(lastElem.getOutput());
                lastElem = elem;
            }
            onScreenRenderer.setInput(lastElem.getOutput());
            complete = true;
        }
    }

    @Override
    public void update(long delta) {
        if(!complete) syncTextures();
        for(OGLRenderer renderer : pipeline) {
            renderer.onRender();
        }
        onScreenRenderer.onRender();
    }

    public void resize(int newX, int newY){
        if(!complete) syncTextures();
        for(OGLRenderer renderer : pipeline) {
            renderer.onResize(newX, newY);
        }
        GL11.glViewport(0, 0, newX, newY);
    }
}
