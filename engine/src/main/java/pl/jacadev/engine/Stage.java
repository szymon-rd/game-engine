package pl.jacadev.engine;

import pl.jacadev.engine.graphics.OGLThread;
import pl.jacadev.engine.graphics.SceneRenderer;
import pl.jacadev.engine.graphics.contexts.OGLEngineContext;
import pl.jacadev.engine.graphics.pipeline.OGLRenderingPipeline;
import pl.jacadev.engine.graphics.postprocessing.HDRRenderer;
import pl.jacadev.engine.graphics.scene.Scene;
import pl.jacadev.engine.input.BasicInput;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jaca777
 *         Created 2015-07-14 at 15
 */
public class Stage {

    private OGLThread.DisplayProps displayProps;
    private Scene scene;
    private Timer timer = new Timer(60);
    private OGLEngineContext engineContext;
    private List<Task> tasks = new ArrayList<>();

    Stage() {
    }

    public void show(){
        if(timer == null) throw new IllegalStateException("Timer is not set.");
        if(scene == null) throw new IllegalStateException("Scene is not set.");
        if(displayProps == null) throw new IllegalStateException("Display properties are not set.");

        OGLRenderingPipeline pipeline = new OGLRenderingPipeline();
        SceneRenderer sceneRenderer = new SceneRenderer(displayProps.getDisplayMode().getWidth(), displayProps.getDisplayMode().getHeight(), scene);
        pipeline.addLast(sceneRenderer);
        pipeline.addLast(new HDRRenderer(displayProps.getDisplayMode().getWidth(), displayProps.getDisplayMode().getHeight()));
        OGLThread thread = new OGLThread(timer, pipeline, displayProps);
        for(Task task : tasks){
            thread.bind(task);
        }
        thread.bind(new BasicInput());
        thread.start();
        thread.awaitContext();
        this.engineContext = (OGLEngineContext) thread.getContext();
    }

    public void setDisplayProps(OGLThread.DisplayProps displayProps) {
        this.displayProps = displayProps;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public void addTask(Task task){
        tasks.add(task);
    }

    public OGLThread.DisplayProps getDisplayProps() {
        return displayProps;
    }

    public Scene getScene() {
        return scene;
    }

    public Timer getTimer() {
        return timer;
    }

    public OGLEngineContext getEngineContext() {
        return engineContext;
    }

}
