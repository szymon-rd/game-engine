package pl.jacadev.game.world.terrain;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Vector3f;
import pl.jacadev.game.resmanagement.manageables.containers.TerrainDataContainer;
import pl.jacadev.game.world.Chunk;

import java.awt.image.BufferedImage;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * @author Jaca777
 *         Created 29.12.14 at 16:28
 */
public class HeightmapLoader {
    private static final float MAX_COLOR = 0x00FFFFFF;
    private static final int RGB_MASK = 0x00FFFFFF;
    private static Vector3f temp = new Vector3f();

    /**
     * Loads chunk with one extra triangle to connect two chunks.
     */
    public static Heightmap loadChunkHeightmap(float[][] map, int x, int y, int size) {
        float[][] harray = new float[size - 2][size - 2];
        float q = Chunk.CHUNK_SIZE / (float) (size - 3);
        int bSize = (size - 2) * (size - 2);
        FloatBuffer vBuff = BufferUtils.createFloatBuffer(bSize * 4);
        FloatBuffer tBuff = BufferUtils.createFloatBuffer(bSize * 2);
        FloatBuffer nBuff = BufferUtils.createFloatBuffer(bSize * 3);
        int iAmount = 6 * (size - 3) * (size - 3);
        IntBuffer iBuff = BufferUtils.createIntBuffer(iAmount);
        for (int pX = 0; pX < size - 2; pX++)
            for (int pZ = 0; pZ < size - 2; pZ++) {
                harray[pX][pZ] = map[x + pX + 1][y + pZ + 1];
                vBuff.put(pX * q);
                vBuff.put(map[x + pX + 1][y + pZ + 1]);
                vBuff.put(pZ * q);
                vBuff.put(1);
                tBuff.put((pX / (float) (size - 3)) * Chunk.TEXTURES);
                tBuff.put((pZ / (float) (size - 3)) * Chunk.TEXTURES);
                float hU = map[x + pX + 1][y + pZ + 2];
                float hD = map[x + pX + 1][y + pZ];
                float hL = map[x + pX][y + pZ + 1];
                float hR = map[x + pX + 2][y + pZ + 1];
                temp.set(hL - hR, 2f, hD - hU);
                temp.normalise(temp);
                nBuff.put(temp.x);
                nBuff.put(temp.y);
                nBuff.put(temp.z);
                if (pX < size - 3 && pZ < size - 3) {
                    iBuff.put((pZ + 1) * (size - 2) + pX);
                    iBuff.put(pZ * (size - 2) + pX);
                    iBuff.put(pZ * (size - 2) + pX + 1);

                    iBuff.put((pZ + 1) * (size - 2) + pX + 1);
                    iBuff.put((pZ + 1) * (size - 2) + pX);
                    iBuff.put(pZ * (size - 2) + pX + 1);
                }
            }
        vBuff.flip();
        tBuff.flip();
        nBuff.flip();
        iBuff.flip();
        return new Heightmap(vBuff, tBuff, nBuff, iBuff, iAmount, harray, q);
    }

    public static void loadDataContainer(TerrainDataContainer dest, float[][] map, int x, int y, int size) {
        float[][] harray = new float[size - 2][size - 2];
        float q = Chunk.CHUNK_SIZE / (float) (size - 3);
        int bSize = (size - 2) * (size - 2);
        FloatBuffer vBuff = BufferUtils.createFloatBuffer(bSize * 4);
        FloatBuffer tBuff = BufferUtils.createFloatBuffer(bSize * 2);
        FloatBuffer nBuff = BufferUtils.createFloatBuffer(bSize * 3);
        int iAmount = 6 * (size - 3) * (size - 3);
        IntBuffer iBuff = BufferUtils.createIntBuffer(iAmount);
        for (int pX = 0; pX < size - 2; pX++)
            for (int pZ = 0; pZ < size - 2; pZ++) {
                harray[pX][pZ] = map[x + pX + 1][y + pZ + 1];
                vBuff.put(pX * q);
                vBuff.put(map[x + pX + 1][y + pZ + 1]);
                vBuff.put(pZ * q);
                vBuff.put(1);
                tBuff.put((pX / (float) (size - 3)) * Chunk.TEXTURES);
                tBuff.put((pZ / (float) (size - 3)) * Chunk.TEXTURES);
                float hU = map[x + pX + 1][y + pZ + 2];
                float hD = map[x + pX + 1][y + pZ];
                float hL = map[x + pX][y + pZ + 1];
                float hR = map[x + pX + 2][y + pZ + 1];
                temp.set(hL - hR, 2f, hD - hU);
                temp.normalise(temp);
                nBuff.put(temp.x);
                nBuff.put(temp.y);
                nBuff.put(temp.z);
                if (pX < size - 3 && pZ < size - 3) {
                    iBuff.put((pZ + 1) * (size - 2) + pX);
                    iBuff.put(pZ * (size - 2) + pX);
                    iBuff.put(pZ * (size - 2) + pX + 1);

                    iBuff.put((pZ + 1) * (size - 2) + pX + 1);
                    iBuff.put((pZ + 1) * (size - 2) + pX);
                    iBuff.put(pZ * (size - 2) + pX + 1);
                }
            }
        vBuff.flip();
        tBuff.flip();
        nBuff.flip();
        iBuff.flip();
        dest.load(vBuff, tBuff, nBuff, iBuff, iAmount, q, harray);
    }

    public static float[][] toHeightArray(BufferedImage map, float maxH, float minH) {
        int size = map.getWidth();
        float[][] array = new float[size][size];
        float diff = maxH - minH;
        for (int x = 0; x < size; x++)
            for (int y = 0; y < size; y++) array[x][y] = minH + (((map.getRGB(x, y) & RGB_MASK) / MAX_COLOR) * diff);
        return array;
    }
}
