package pl.jacadev.game.resmanagement.manageables.containers;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * @author Jaca777
 *         Created 21.01.15 at 22:11
 */
public class TerrainDataContainer extends ModelDataContainer{
    private float q;
    private float[][] harray;

    public void load(FloatBuffer vertices, FloatBuffer texCoords, FloatBuffer normals, IntBuffer indices, int indicesAmount, float q, float[][] harray) {
        this.q = q;
        this.harray = harray;
        super.load(vertices, texCoords, normals, indices, indicesAmount);
    }

    @Override
    public void load(FloatBuffer vertices, FloatBuffer texCoords, FloatBuffer normals, IntBuffer indices, int indicesAmount) {
        throw new UnsupportedOperationException("Too few arguments");
    }

    public float[][] getHarray() {
        return harray;
    }

    public float getQ() {
        return q;
    }
}
