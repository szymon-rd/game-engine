package pl.jacadev.engine.graphics.models;


import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import pl.jacadev.engine.graphics.utility.ogl.VAOUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * @author Jaca777
 *         Created 15.01.15 at 15:28
 */
public class VAOModel extends Model{

    protected int vaoName = -1;

    /**
     * @see Model#Model(int, int, int, int, int, int[])
     */
    public VAOModel(int vertexBuff, int texCoordBuff, int normalBuff, int indexBuff, int indices, int vao, int[] attributes) {
        super(vertexBuff, texCoordBuff, normalBuff, indexBuff, indices, attributes);
        this.vaoName = vao;
    }

    /**
     * @see Model#Model(float[], float[], float[], int[], int[])
     */
    public VAOModel(float[] vertices, float[] texCoords, float[] normals, int[] indices, int[] attributes) {
        super(vertices, texCoords, normals, indices, attributes);
        createVAO();
    }

    /**
     * @see Model#Model(FloatBuffer, FloatBuffer, FloatBuffer, IntBuffer, int, int[])
     */
    public VAOModel(FloatBuffer vertices, FloatBuffer texCoords, FloatBuffer normals, IntBuffer indices, int numElements, int[] attributes) {
        super(vertices, texCoords, normals, indices, numElements, attributes);
        createVAO();
    }

    /**
     * @see Model#Model(int, int, int, int, int, int[])
     */
    public VAOModel(int vertexBuff, int texCoordBuff, int normalBuff, int indexBuff, int indices, int[] attributes, int vaoName) {
        super(vertexBuff, texCoordBuff, normalBuff, indexBuff, indices, attributes);
        this.vaoName = vaoName;
    }

    /**
     * Binds vao and draws model.
     */

    @Override
    public void bind() {
        GL30.glBindVertexArray(vaoName);
    }

    @Override
    public void finalizeRendering() {
        GL30.glBindVertexArray(0);
    }

    /**
     * Creates VAO with vertex, texture coordinate and normal buffer for given attributes locations.
     * @param attributes Array consisting of attributes locations. Organization:
     *                   0 - vertex attribute
     *                   1 - texture coordinate attribute
     *                   2 - normal attribute
     */

    private static final int[] VAO_SIZES = {4,2,3};
    private static final int[] VAO_TYPES = {GL11.GL_FLOAT, GL11.GL_FLOAT, GL11.GL_FLOAT};
    protected void createVAO(){
        this.vaoName = VAOUtil.createVAO(new int[]{this.vertexBuff, this.texCoordBuff, this.normalBuff}, indexBuff, attributes, VAO_SIZES, VAO_TYPES);
    }


    /**
     * @return VAO name.
     */
    public int getVao() {
        return vaoName;
    }

    @Override
    public void unload() {
        super.unload();
        GL30.glDeleteVertexArrays(vaoName);
    }
}
