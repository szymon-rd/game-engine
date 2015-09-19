package pl.jacadev.engine.graphics.shaders.programs.water;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import pl.jacadev.engine.graphics.Unloadable;
import pl.jacadev.engine.graphics.contexts.OGLEngineContext;
import pl.jacadev.engine.graphics.contexts.RenderingContext;
import pl.jacadev.engine.graphics.scene.components.DrawableComponent;
import pl.jacadev.engine.graphics.utility.BufferTools;
import pl.jacadev.engine.graphics.utility.math.MatrixStack;
import pl.jacadev.engine.graphics.utility.ogl.VAOUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * @author Jaca777
 *         Created 06.02.15 at 18:58
 */
public class WaterSurface extends DrawableComponent implements Unloadable {

    /**
     * Y coordinate of water surface.
     */
    public static final float WATER_LEVEL = 45; //45
    private static final int INDICES = 6;

    private int vBuff;
    private int iBuff;
    private int vao;
    private int hMap;

    private Vector3f pos;

    /**
     * @param v1 The first vertex of quadrangular water surface.
     * @param v2 The second vertex of quadrangular water surface.
     * @param v3 The third vertex of quadrangular water surface.
     * @param v4 The fourth vertex of quadrangular water surface.
     */
    public WaterSurface(Vector2f v1, Vector2f v2, Vector2f v3, Vector2f v4){
        FloatBuffer v = BufferTools.toDirectBuffer(new float[]{v1.x, WATER_LEVEL, v1.y, 1.0f,
                v2.x, WATER_LEVEL, v2.y, 1.0f,
                v3.x, WATER_LEVEL, v3.y, 1.0f,
                v4.x, WATER_LEVEL, v4.y, 1.0f});
        IntBuffer i = BufferTools.toDirectBuffer(new int[]{0, 1, 2, 0, 2, 3});

        this.vBuff = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.vBuff);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, v, GL15.GL_STATIC_DRAW);

        this.iBuff = GL15.glGenBuffers();

        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, this.iBuff);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, i, GL15.GL_STATIC_DRAW);

        this.vao = VAOUtil.createVAO(new int[]{this.vBuff}, this.iBuff, new int[]{WtrSrfProgram.ATTR_VERTEX}, new int[]{4}, new int[]{GL11.GL_FLOAT});

       this.pos = new Vector3f((v1.x + v2.x + v3.x + v4.x) / 4, (v1.y + v2.y + v3.y + v4.y) / 4, WATER_LEVEL);
    }

    /**
     * Draws water surface.
     */
    protected void onDraw(OGLEngineContext context){
        if(context.getRenderingContext().getRenderingState() == RenderingContext.RenderingState.REFLECTIONS) {
            MatrixStack stack = context.getRenderingContext().getCurrentMatrixStack();
            WtrSrfProgram shader = context.getShadingContext().getWaterProgram();
            shader.use();
            shader.useHMap(hMap);
            shader.setUniformMatrix4(shader.uMMatrix, stack.topBuff());
            GL30.glBindVertexArray(vao);
            GL11.glDrawElements(GL11.GL_TRIANGLES, INDICES, GL11.GL_UNSIGNED_INT, 0);
            GL30.glBindVertexArray(0);
        }
    }

    @Override
    public void onUpdate(long delta) {

    }

    /**
     * Unloads buffers.
     */
    @Override
    public void unload() {
        GL15.glDeleteBuffers(vBuff);
        GL15.glDeleteBuffers(iBuff);
        GL30.glDeleteVertexArrays(vao);
    }

    public Vector3f getPos() {
        return pos;
    }

    public void sethMap(int hMap) {
        this.hMap = hMap;
    }
}

