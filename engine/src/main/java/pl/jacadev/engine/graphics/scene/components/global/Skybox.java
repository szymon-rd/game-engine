package pl.jacadev.engine.graphics.scene.components.global;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import pl.jacadev.engine.graphics.Unloadable;
import pl.jacadev.engine.graphics.contexts.OGLEngineContext;
import pl.jacadev.engine.graphics.contexts.RenderingContext;
import pl.jacadev.engine.graphics.shaders.programs.cubemap.CubemapProgram;
import pl.jacadev.engine.graphics.textures.Cubemap;
import pl.jacadev.engine.graphics.utility.BufferTools;

import static org.lwjgl.opengl.GL11.*;

/**
 * @author Jaca777
 *         Created 21.12.14 at 18:39
 */
public class Skybox extends GlobalComponent implements Unloadable {

    private Cubemap cubemap;

    public Skybox(Cubemap cubemap) {
        this.cubemap = cubemap;
    }

    /**
     * Updates the skybox.
     *
     * @param delta
     */
    @Override
    public void onUpdate(long delta) {
    }

    /**
     * Draws the skybox.
     */
    public void onDraw(OGLEngineContext engineContext) {
        if(engineContext.getRenderingContext().getRenderingState() == RenderingContext.RenderingState.STANDARD) {
            glDisable(GL_DEPTH_TEST);
            glCullFace(GL_FRONT);
            CubemapProgram program = engineContext.getShadingContext().getCubemapProgram();
            program.use();
            program.setUniformMatrix4(program.uRotMatrix, engineContext.getSceneContext().getCamera().rotationMatrix());
            program.useCubemap(cubemap, engineContext.getBindings());
            GL30.glBindVertexArray(CUBE_VAO);
            GL11.glDrawElements(GL11.GL_TRIANGLES, iAmount, GL11.GL_UNSIGNED_INT, 0);
            GL30.glBindVertexArray(0);
            glEnable(GL_DEPTH_TEST);
            glCullFace(GL_BACK);
        }
    }

    private static float[] vertices = {
            -1, 1, -1, 1.0f,
            -1, -1, -1, 1.0f,
            1, -1, -1, 1.0f,
            1, 1, -1, 1.0f,

            1, 1, -1, 1.0f,
            1, -1, -1, 1.0f,
            1, -1, 1, 1.0f,
            1, 1, 1, 1.0f,

            -1, 1, 1, 1.0f,
            -1, -1, 1, 1.0f,
            1, -1, 1, 1.0f,
            1, 1, 1, 1.0f,

            -1, 1, -1, 1.0f,
            -1, -1, -1, 1.0f,
            -1, 1, 1, 1.0f,
            -1, -1, 1, 1.0f,

            -1, 1, -1, 1.0f,
            1, 1, -1, 1.0f,
            -1, 1, 1, 1.0f,
            1, 1, 1, 1.0f,

            -1, -1, -1, 1.0f,
            1, -1, -1, 1.0f,
            -1, -1, 1, 1.0f,
            1, -1, 1, 1.0f
    };

    private static int[] indices = new int[]{
            2, 1, 0,
            3, 2, 0,

            6, 5, 4,
            7, 6, 4,

            9, 10, 11,
            8, 9, 11,

            13, 15, 14,
            12, 13, 14,

            17, 16, 18,
            19, 17, 18,

            21, 23, 22,
            20, 21, 22
    };

    private static final int iAmount = indices.length;

    private static final int CUBE_VAO = createSkybox();

    private static int createSkybox() {
        int vBuff = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vBuff);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, BufferTools.toDirectBuffer(vertices), GL15.GL_STATIC_DRAW);

        int iBuff = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, iBuff);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, BufferTools.toDirectBuffer(indices), GL15.GL_STATIC_DRAW);

        int vao = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vao);

        GL20.glEnableVertexAttribArray(CubemapProgram.ATTR_VERTEX);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vBuff);
        GL20.glVertexAttribPointer(CubemapProgram.ATTR_VERTEX, 4, GL11.GL_FLOAT, false, 0, 0);

        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, iBuff);

        GL30.glBindVertexArray(0);
        return vao;
    }

    @Override
    public void unload() {
        cubemap.unload();
        GL30.glDeleteVertexArrays(CUBE_VAO);
    }
}
