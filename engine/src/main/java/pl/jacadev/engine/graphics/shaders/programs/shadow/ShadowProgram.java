package pl.jacadev.engine.graphics.shaders.programs.shadow;

import org.lwjgl.util.vector.Matrix4f;
import pl.jacadev.engine.graphics.shaders.Program;

import java.io.InputStream;

/**
 * @author Jaca777
 *         Created 2015-06-25 at 15
 */
public class ShadowProgram extends Program {

    private static InputStream FRAGMENT_SHADER = ShadowProgram.class.getResourceAsStream("frag.glsl");
    private static InputStream VERTEX_SHADER = ShadowProgram.class.getResourceAsStream("vert.glsl");

    private static final String[] OUT_NAMES = new String[]{"fragData"};

    public int aVertex;

    /**
     * Model matrix uniform location.
     */
    public int uMMatrix;

    /**
     * Camera matrix uniform location.
     */
    public int uCameraMatrix;

    /**
     * Perspective matrix uniform location.
     */
    public int uPerspMatrix;

    public ShadowProgram() {
        super(VERTEX_SHADER, FRAGMENT_SHADER, OUT_NAMES);
        this.aVertex = getAttributeLocation("inVertex");
        this.uCameraMatrix = getUniformLocation("cameraMatrix");
        this.uMMatrix = getUniformLocation("mMatrix");
        this.uPerspMatrix = getUniformLocation("perspMatrix");
    }


    @Override
    public void use(){
        super.use();
    }

    @Override
    public void setUniformMatrix4(int location, Matrix4f matrix) {
        super.setUniformMatrix4(location, matrix);
    }
}
