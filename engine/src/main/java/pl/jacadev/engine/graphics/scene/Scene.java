package pl.jacadev.engine.graphics.scene;


import pl.jacadev.engine.camera.Camera;
import pl.jacadev.engine.graphics.contexts.OGLEngineContext;
import pl.jacadev.engine.graphics.scene.components.ComponentList;
import pl.jacadev.engine.graphics.scene.components.Container;
import pl.jacadev.engine.graphics.scene.components.DrawableComponent;
import pl.jacadev.engine.graphics.scene.components.global.GlobalComponent;
import pl.jacadev.engine.graphics.shaders.programs.light.Lighting;
import pl.jacadev.engine.graphics.shaders.programs.water.WtrSrfProgram;

/**
 * @author Jaca777
 *         Created 2015-07-13 at 13
 */
public class Scene extends DrawableComponent {

    /**
     * Root component. Contains all other drawable components like world, GUIs.
     */
    private Container<DrawableComponent> root;

    /**
     * Global drawable components are not 'solid' components. Great examples of global drawable components would be fog and graphical filters.
     */
    private ComponentList<GlobalComponent> globalDrawableComponents = new ComponentList<>();

    /**
     * Global components are used mainly for testing. They are updated at each game loop iteration.
     */
    private ComponentList<Component> globalComponents = new ComponentList<>();

    /**
     * Scene is being drawn from this camera perspective.
     */
    private Camera camera;

    /**
     * Global scene lighting.
     */
    private Lighting lighting = new Lighting();

    public Scene(Container<DrawableComponent> root) {
        this.root = root;
    }

    public Container<DrawableComponent> getRoot() {
        return root;
    }

    public ComponentList<GlobalComponent> getGlobalDrawableComponents() {
        return globalDrawableComponents;
    }

    public ComponentList<Component> getGlobalComponents() {
        return globalComponents;
    }

    @Override
    public void onDraw(OGLEngineContext engineContext) {
        WtrSrfProgram waterProgram = engineContext.getShadingContext().getWaterProgram();
        waterProgram.use();
        waterProgram.update(1000L / 60); //TODO CHANGE

        engineContext.getShadingContext().useCamera(this.camera);
        engineContext.getShadingContext().useLighting(this.lighting);

        for (GlobalComponent comp : globalDrawableComponents) {
            comp.draw(engineContext);
        }

        root.draw(engineContext);
    }


    @Override
    public void onUpdate(long delta) {
        for(Component comp : globalComponents){
            comp.onUpdate(delta);
        }
        for (GlobalComponent comp : globalDrawableComponents) {
            comp.onUpdate(delta);
        }
        root.onUpdate(delta);
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public Camera getCamera() {
        return camera;
    }

    public Lighting getLighting() {
        return lighting;
    }
}
