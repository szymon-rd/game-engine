package pl.jacadev.game.resmanagement.manageables.containers;

import pl.jacadev.game.resmanagement.loading.DataContainer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * @author Jaca777
 *         Created 21.01.15 at 15:47
 */
public class ModelDataContainer extends DataContainer {

    private boolean loaded;

    private FloatBuffer vertices;
    private FloatBuffer texCoords;
    private FloatBuffer normals;
    private IntBuffer indices;
    private int indicesAmount;

    public void load(FloatBuffer vertices, FloatBuffer texCoords, FloatBuffer normals, IntBuffer indices, int indicesAmount) {
        this.indices = indices;
        this.indicesAmount = indicesAmount;
        this.normals = normals;
        this.texCoords = texCoords;
        this.vertices = vertices;
        this.loaded = true;
    }

    @Override
    public boolean isLoaded() {
        return loaded;
    }

    public IntBuffer getIndices() {
        return indices;
    }

    public int getIndicesAmount() {
        return indicesAmount;
    }

    public FloatBuffer getNormals() {
        return normals;
    }

    public FloatBuffer getTexCoords() {
        return texCoords;
    }

    public FloatBuffer getVertices() {
        return vertices;
    }
}
