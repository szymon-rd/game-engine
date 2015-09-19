package pl.jacadev.engine.graphics.shaders;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import pl.jacadev.engine.graphics.utility.Streams;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.lwjgl.opengl.GL20.*;

/**
 * @author Jaca777
 *         Created 12.11.14 at 20:42
 */
public class ShaderUtil {

    /**
     * @param type Type of the shader. (OpenGL constant).
     * @param file Location of the shader.
     * @return The shader's name.
     * @throws IOException
     */
    public static int compileShader(int type, File file) throws IOException {
        return compileShader(type, new FileInputStream(file));
    }

    /**
     * @param type   Type of the shader. (OpenGL constant).
     * @param stream Stream with a shader code.
     * @return The shader's name.
     */
    public static int compileShader(int type, InputStream stream) {
        String code = Streams.streamToString(stream);
        return compileShader(type, code);
    }

    public static final String SUCCESS_LOG = "Success!";

    /**
     * @param type       Type of the shader.
     * @param shaderCode Code of the shader.
     * @return The shader's name.
     */
    public static int compileShader(int type, String shaderCode) {
        int shader = glCreateShader(type);
        glShaderSource(shader, shaderCode);
        glCompileShader(shader);

        String log = glGetShaderInfoLog(shader, 65536); //Can either be empty, or contain a message (even if operation was successful).
        if (log.isEmpty()) log = SUCCESS_LOG;
        System.out.println("Shader name: " + shader + " - " + log);
        return shader;
    }

    /**
     * Links shaders into a program.
     *
     * @param vertexShader   Vertex shader's name.
     * @param fragmentShader Fragment shader's name.
     * @return The program's name.
     */
    public static int createProgram(int vertexShader, int fragmentShader, String[] outNames) {
        int program = glCreateProgram();
        glAttachShader(program, vertexShader);
        glAttachShader(program, fragmentShader);
        for (int i = 0; i < outNames.length; i++) {
            String name = outNames[i];
            GL30.glBindFragDataLocation(program, i, name);
        }
        glLinkProgram(program);
        String log = glGetProgramInfoLog(program, glGetProgrami(program, GL_INFO_LOG_LENGTH));
        if (log.isEmpty()) log = SUCCESS_LOG;
        System.out.println("Program name: " + program + " - " + log);
        return program;
    }

    /**
     * @param program
     * @return All the attributes' names of the given program.
     */
    public static String[] getAttributes(int program) {
        int numAttributes = glGetProgrami(program, GL20.GL_ACTIVE_ATTRIBUTES);
        int maxAttributeLength = glGetProgrami(program, GL20.GL_ACTIVE_ATTRIBUTE_MAX_LENGTH);

        String[] attributes = new String[numAttributes];
        for (int i = 0; i < numAttributes; i++) {
            String name = GL20.glGetActiveAttrib(program, i, maxAttributeLength);
            attributes[i] = name;
        }
        return attributes;
    }

    /**
     * @param program
     * @return All the uniforms' names of the given program.
     */
    public static String[] getUniforms(int program) {
        int numUniforms = glGetProgrami(program, GL20.GL_ACTIVE_UNIFORMS);
        int maxUniformLength = glGetProgrami(program, GL20.GL_ACTIVE_UNIFORM_MAX_LENGTH);

        String[] uniforms = new String[numUniforms];
        for (int i = 0; i < numUniforms; i++) {
            String name = GL20.glGetActiveAttrib(program, i, maxUniformLength);
            uniforms[i] = name;
        }
        return uniforms;
    }

    /**
     * @param program
     * @param name    Name of the uniform to search for.
     * @return Location of the given uniform.
     */
    public static int getUniformLoc(int program, String name) {
        return glGetUniformLocation(program, name);
    }
}
