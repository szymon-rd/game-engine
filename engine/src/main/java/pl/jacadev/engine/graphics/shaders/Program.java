package pl.jacadev.engine.graphics.shaders;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix3f;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import pl.jacadev.engine.graphics.contexts.Bindings;
import pl.jacadev.engine.graphics.textures.Texture;
import pl.jacadev.engine.graphics.utility.BufferTools;

import java.io.InputStream;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.*;

/**
 * @author Jaca777
 *         Created 13.11.14 at 19:40
 */
public class Program {

    private final int program;

    public Program(InputStream vertexShader, InputStream fragmentShader, String[] outNames) {
        int vS = ShaderUtil.compileShader(GL_VERTEX_SHADER, vertexShader);
        int fS = ShaderUtil.compileShader(GL_FRAGMENT_SHADER, fragmentShader);
        this.program = ShaderUtil.createProgram(vS, fS, outNames);
        glUseProgram(this.program);
    }

    public Program(String vertexSource, String fragmentSource, String[] outNames) {
        int vS = ShaderUtil.compileShader(GL_VERTEX_SHADER, vertexSource);
        int fS = ShaderUtil.compileShader(GL_FRAGMENT_SHADER, fragmentSource);
        this.program = ShaderUtil.createProgram(vS, fS, outNames);
        glUseProgram(this.program);
    }

    public Program(int program) {
        this.program = program;
        glUseProgram(this.program);
    }

    /**
     * Binds the program.
     */
    public void use() {
        glUseProgram(this.program);
    }

    /**
     * Binds the texture to the given sampler.
     * @param texture Texture to bind.
     * @param sampler Sampler offset to bind the texture to.
     */
    public void useTexture(Texture texture, int sampler, Bindings bindings) {
        useTexture(texture.getTexture(), texture.getType(), sampler);
        bindings.setBindedTexture(texture.getType(), texture.getTexture());
    }

    public void useTexture(int texture, int tType, int sampler) {
        if(texture != -1) {
            GL13.glActiveTexture(GL13.GL_TEXTURE0 + sampler);
            GL11.glBindTexture(tType, texture);
        }
    }

    protected void setOutput(String[] outNames) {
        for (int i = 0; i < outNames.length; i++) {
            String name = outNames[i];
            GL30.glBindFragDataLocation(program, i, name);
        }
    }

    /**
     * @return All the program's attributes.
     */
    public String[] getAttributes() {
        int numAttributes = glGetProgrami(program, GL_ACTIVE_ATTRIBUTES);
        int maxAttributeLength = glGetProgrami(program, GL_ACTIVE_ATTRIBUTE_MAX_LENGTH);
        String[] attributes = new String[numAttributes];
        for (int i = 0; i < numAttributes; i++) {
            String name = glGetActiveAttrib(program, i, maxAttributeLength);
            attributes[glGetAttribLocation(program, name)] = name;
        }
        return attributes;
    }

    /**
     * @return All the program's uniforms.
     */
    public String[] getUniforms() {
        int numUniforms = glGetProgrami(program, GL_ACTIVE_UNIFORMS);
        int maxUniformLength = glGetProgrami(program, GL_ACTIVE_UNIFORM_MAX_LENGTH);
        String[] attributes = new String[numUniforms];
        for (int i = 0; i < numUniforms; i++) {
            String name = glGetActiveUniform(program, i, maxUniformLength);
            attributes[glGetUniformLocation(program, name)] = name;
        }
        return attributes;
    }

    /**
     * @param name A Name of an attribute to search for.
     * @return Location of the attribute with the given name.
     */
    public int getAttributeLocation(String name) {
        return glGetAttribLocation(program, name);
    }

    /**
     * @param name A Name of an uniform to search for.
     * @return Location of the uniform with the given name.
     */
    public int getUniformLocation(String name) {
        return glGetUniformLocation(program, name);
    }

    public void setUniformMatrix4(int location, FloatBuffer matrix) {
        glUniformMatrix4(location, false, matrix);
    }

    private FloatBuffer tempBuff4 = BufferUtils.createFloatBuffer(16);
    public void setUniformMatrix4(int location, Matrix4f matrix) {
        BufferTools.storeMat4(matrix, tempBuff4);
        glUniformMatrix4(location, false, tempBuff4);
    }

    public void setUniformMatrix3(int location, FloatBuffer matrix) {
        glUniformMatrix3(location, false, matrix);
    }

    private FloatBuffer tempBuff3 = BufferUtils.createFloatBuffer(9);
    public void setUniformMatrix3(int location, Matrix3f matrix) {
        BufferTools.storeMat3(matrix, tempBuff3);
        glUniformMatrix3(location, false, tempBuff3);
    }

    public void setUniformf(int location, float i) {
        glUniform1f(location, i);
    }

    public void setUniformi(int location, int i) {
        glUniform1i(location, i);
    }

    public void setUniformV4(int location, float v1, float v2, float v3, float v4) {
        glUniform4f(location, v1, v2, v3, v4);
    }

    public void setUniformV2(int location, float v1, float v2) {
        glUniform2f(location, v1, v2);
    }

    public void setUniformV3(int location, float v1, float v2, float v3) {
        glUniform3f(location, v1, v2, v3);
    }

    public void setUniformV4(int location, Vector4f vector) {
        glUniform4f(location, vector.x, vector.y, vector.z, vector.w);
    }

    public void setUniformV3(int location, Vector3f vector) {
        glUniform3f(location, vector.x, vector.y, vector.z);
    }

    public int getProgram() {
        return program;
    }
}
