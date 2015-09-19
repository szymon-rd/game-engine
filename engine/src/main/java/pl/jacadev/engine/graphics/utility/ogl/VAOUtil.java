package pl.jacadev.engine.graphics.utility.ogl;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 * @author Jaca777
 *         Created 12.01.15 at 22:21
 */
public class VAOUtil {
    /**
     * Creates new VAO.
     * @param buffers Buffers to add to VAO.
     * @param attributes Attributes locations.
     * @param indexbuffer Indices.
     * @param sizes Attributes size.
     * @param types Attributes types.
     * @return New VAO name.
     */
    public static int createVAO(int[] buffers, int indexbuffer, int[] attributes, int[] sizes, int[] types){
        int vao = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vao);
        for(int i = 0; i < buffers.length; i++) {
            GL20.glEnableVertexAttribArray(attributes[i]);
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, buffers[i]);
            GL20.glVertexAttribPointer(attributes[i], sizes[i], types[i], false, 0, 0);
        }
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indexbuffer);
        GL30.glBindVertexArray(0);
        return vao;
    }
}
