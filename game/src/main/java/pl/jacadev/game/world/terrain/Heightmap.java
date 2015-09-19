package pl.jacadev.game.world.terrain;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import pl.jacadev.engine.graphics.models.Model;
import pl.jacadev.game.world.Chunk;
import pl.jacadev.engine.graphics.models.VAOModel;
import pl.jacadev.engine.graphics.shaders.StandardProgram;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * @author Jaca777
 *         Created 29.12.14 at 16:30
 */
public class Heightmap extends VAOModel {

    private float[][] heightArray;

    /**
     * Chunk size / Heightmap size ratio.
     */
    private float q;

    /**
     * Heightmap as a OGL texture.
     */
    private int heightmapTexture;

    /**
     * Uses standard program attributes.
     */
    public Heightmap(FloatBuffer vertices, FloatBuffer texCoords, FloatBuffer normals, IntBuffer indices, int numElements, float[][] heightArray, float q) {
        super(vertices, texCoords, normals, indices, numElements, StandardProgram.ATTRIBUTES);
        this.heightArray = heightArray;
        this.q = q;

        FloatBuffer data = ByteBuffer.allocateDirect(heightArray.length * heightArray.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        for(int i = 0; i < heightArray.length; i++)
            for(int j = 0; j < heightArray.length; j++)
                data.put(heightArray[j][i]);

        data.flip();
        this.heightmapTexture = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.heightmapTexture);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL30.GL_R32F, heightArray.length, heightArray.length, 0, GL11.GL_RED, GL11.GL_FLOAT, data);
        GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
    }

    /**
     * @return Interpolated height at given position.
     */
    public float getHeight(float x, float y) {
        float pX = x / q;
        float pY = y / q;
        if ((int) pX >= heightArray.length - 1 || (int) pY >= heightArray.length - 1 || x < 0 || y < 0) {
            return 0;
        } else if (pX == Chunk.CHUNK_SIZE) {
            return getHeight(x - 1, y);
        } else if (pY == Chunk.CHUNK_SIZE) {
            return getHeight(x, y - 1);
        } else {
            float interX = pX - (float) Math.floor(pX);
            float interY = pY - (float) Math.floor(pY);
            return heightArray[((int) pX)][(int) pY] * (((1 - interX) + (1 - interY)) / 4)
                    + heightArray[((int) pX + 1)][(int) pY] * (((interX) + (1 - interY)) / 4)
                    + heightArray[((int) pX)][(int) pY + 1] * (((1 - interX) + (interY)) / 4)
                    + heightArray[((int) pX) + 1][(int) pY + 1] * ((interX + interY) / 4);
        }
    }

    public float[][] getHeightArray() {
        return heightArray;
    }

    public int getHeightmapTexture() {
        return heightmapTexture;
    }
}
