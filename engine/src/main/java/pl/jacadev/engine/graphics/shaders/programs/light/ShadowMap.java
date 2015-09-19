package pl.jacadev.engine.graphics.shaders.programs.light;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import pl.jacadev.engine.camera.FPPCamera;
import pl.jacadev.engine.graphics.Framebuffer;
import pl.jacadev.engine.graphics.Unloadable;
import pl.jacadev.engine.graphics.contexts.OGLEngineContext;
import pl.jacadev.engine.graphics.shaders.programs.shadow.ShadowProgram;
import pl.jacadev.engine.graphics.textures.RawTexture2D;

/**
 * @author Jaca777
 *         Created 2015-08-01 at 18
 */
public class ShadowMap implements Unloadable {
    private RawTexture2D shadowMap;
    private Framebuffer framebuffer;
    private FPPCamera camera;

    public ShadowMap(int width, int height, FPPCamera camera) {
        this.shadowMap = new RawTexture2D(width, height, GL30.GL_RG32F, GL30.GL_RG, null);
        this.framebuffer = new Framebuffer(shadowMap);
        this.camera = camera;
    }

    public void bind(OGLEngineContext engineContext) {
        this.framebuffer.bindDraw();
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        ShadowProgram shadowProgram = engineContext.getShadingContext().getShadowProgram();
        shadowProgram.use();
        camera.setPosition(engineContext.getSceneContext().getCamera().getPos());
        shadowProgram.setUniformMatrix4(shadowProgram.uPerspMatrix, camera.getProjection().getMatrix());
        shadowProgram.setUniformMatrix4(shadowProgram.uCameraMatrix, camera.cameraMatrix());
        engineContext.getShadingContext().useShadowMap(this);
        GL11.glViewport(0, 0, shadowMap.getWidth(), shadowMap.getHeight());
    }

    @Override
    public void unload() {
        shadowMap.unload();
        framebuffer.unload();
    }

    public FPPCamera getCamera() {
        return camera;
    }

    public RawTexture2D getShadowMap() {
        return shadowMap;
    }


}
