package pl.jacadev.engine.graphics;


import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import pl.jacadev.engine.graphics.contexts.RenderingContext;
import pl.jacadev.engine.graphics.pipeline.OGLRenderer;
import pl.jacadev.engine.graphics.pipeline.output.MultisampleTextureOutput;
import pl.jacadev.engine.graphics.scene.Scene;
import pl.jacadev.engine.graphics.shaders.SceneRenderingProgram;
import pl.jacadev.engine.graphics.shaders.programs.light.DirectionalLight;
import pl.jacadev.engine.graphics.shaders.programs.light.ShadowMap;
import pl.jacadev.engine.graphics.shaders.programs.water.WtrSrfProgram;
import pl.jacadev.engine.graphics.textures.MultisampleTexture2D;
import pl.jacadev.engine.graphics.textures.RawTexture2D;
import pl.jacadev.engine.graphics.utility.math.PerspectiveMatrix;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.GL_MAX_SAMPLES;
import static org.lwjgl.opengl.GL30.GL_RGBA32F;

/**
 * @author Jaca777
 *         Created 13.12.14 at 14:17
 */
public class SceneRenderer extends OGLRenderer {

    /**
     * Window dimensions.
     */
    private int width, height;

    /**
     * Doesn't need a comment, I think. But looks more legible with it.
     */
    private Scene scene;

    /**
     * @param width  The window width.
     * @param height The window height.
     * @see SceneRenderer#width
     * @see SceneRenderer#height
     */

    public SceneRenderer(int width, int height, Scene scene) {
        this.width = width;
        this.height = height;
        this.scene = scene;
    }

    /**
     * Creates window and starts game loop.
     */
    public void onInit() {
        engineContext.updateSceneContext(scene);
        setupFramebuffers();
        WtrSrfProgram wtrSrfShader = engineContext.getShadingContext().getWaterProgram();
        wtrSrfShader.use();
        wtrSrfShader.setBtmTexture(viewTexture);
        wtrSrfShader.setMirrorTexture(mirrorTexture);
    }

    private Framebuffer mirrorFB;
    private RawTexture2D mirrorTexture;

    private MultisampleFramebuffer defFB;
    private MultisampleTexture2D viewTexture;
    private MultisampleTextureOutput output;

    private void setupFramebuffers() {
        System.out.println(GL11.glGetInteger(GL_MAX_SAMPLES));
        this.mirrorTexture = new RawTexture2D(this.width, this.height, GL_RGBA32F, GL_RGBA, new int[][]{{GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE}, {GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE}}, false, null);
        this.mirrorFB = new Framebuffer(this.mirrorTexture);
        ((PerspectiveMatrix) scene.getCamera().getProjection()).setDims(this.width, this.height);
        this.viewTexture = new MultisampleTexture2D(this.width, this.height, GL_RGBA32F, 8); //TODO: Put somewhere else, replace 8 with variable.
        this.defFB = new MultisampleFramebuffer(this.viewTexture);
        this.output = new MultisampleTextureOutput(viewTexture);
    }


    private int i = 0;

    public void onRender() {

        long delta = getDelta();
        if (i++ == 20) { //Only for testing.
            updateFps((int) (1000f / delta));
            i = 0;
        }
        engineContext.getShadingContext().useProjection(scene.getCamera().getProjection());
        scene.onUpdate(delta); //TODO Create physics thread.

        renderShadows();

        //Rendering from mirror perspective.
        this.mirrorFB.bindDraw();
        engineContext.getShadingContext().setRenderingFilter(SceneRenderingProgram.ABOVE_WATER);
        GL11.glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);
        engineContext.getRenderingContext().setRenderingState(RenderingContext.RenderingState.STANDARD);
        scene.draw(engineContext);

        //Rendering from camera perspective.
        this.defFB.bindDraw();
        GL11.glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);
        engineContext.getShadingContext().setRenderingFilter(SceneRenderingProgram.ALL);
        scene.draw(engineContext);
        engineContext.getRenderingContext().setRenderingState(RenderingContext.RenderingState.REFLECTIONS);
        scene.draw(engineContext);
    }

    private void renderShadows() {
        GL11.glDisable(GL11.GL_CULL_FACE);
        engineContext.getRenderingContext().setRenderingState(RenderingContext.RenderingState.SHADOW_MAP);
        DirectionalLight directionalLight = scene.getLighting().getDirectionalLight();
        if(directionalLight != null && directionalLight.isShaded()){
            ShadowMap shadowMap = directionalLight.getShadowMap();
            shadowMap.bind(engineContext);
            scene.getRoot().draw(engineContext);
            engineContext.getShadingContext().getBlurFilter().apply(shadowMap.getShadowMap());
        }
        GL11.glViewport(0, 0, engineContext.getDisplayContext().getDisplayMode().getWidth(), engineContext.getDisplayContext().getDisplayMode().getHeight());
        GL11.glEnable(GL11.GL_CULL_FACE);
    }

    @Override
    public MultisampleTextureOutput getOutput() {
        return output;
    }

    private void updateFps(int fps) { //Only for testing.
        Display.setTitle("FPS: " + fps);
    }

    public void onResize(int newW, int newH) {
        this.width = newW;
        this.height = newH;

        ((PerspectiveMatrix) scene.getCamera().getProjection()).setDims(newW, newH);
        this.viewTexture.bind(engineContext.getBindings());
        this.viewTexture.resize(this.width, this.height);
        this.defFB.resize(this.width, this.height);

        this.mirrorTexture.bind(engineContext.getBindings());
        this.mirrorTexture.resize(this.width, this.height);
        this.mirrorFB.resize(this.width, this.height);
    }

    private long lastUpdate = System.currentTimeMillis();

    /**
     * TODO Pass delta somehow from OGLThread.
     */
    private long getDelta() {
        long delta = System.currentTimeMillis() - lastUpdate;
        lastUpdate = System.currentTimeMillis();
        return delta;
    }


}
