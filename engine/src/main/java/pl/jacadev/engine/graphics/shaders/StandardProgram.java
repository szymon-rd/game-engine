package pl.jacadev.engine.graphics.shaders;

import java.io.InputStream;

/**
 * @author Jaca777
 *         Created 21.12.14 at 00:00
 */
public class StandardProgram extends Program {
    public static final int ATTR_VERTEX = 0;
    public static final int ATTR_TEX_COORD = 1;
    public static final int ATTR_NORMAL = 2;
    public static final int[] ATTRIBUTES = {ATTR_VERTEX, ATTR_TEX_COORD, ATTR_NORMAL};

    /**
     * Model matrix uniform location.
     */
    public int uMMatrix;
    /**
     * Camera matrix uniform location.
     */
    public int uCameraMatrix;

    /**
     * Level of the water.
     */
    public int uWaterLvl;

    /**
     * Mirrored camera matrix uniform location.
     */
    public int uMCameraMatrix;

    /**
     * Perspective matrix uniform location.
     */
    public int uPerspMatrix;

    public StandardProgram(InputStream vertexShader, InputStream fragmentShader, String[] outNames) {
        super(vertexShader, fragmentShader, outNames);
    }

    public  StandardProgram(String vertexSource, String fragmentSource, String[] outNames) {
        super(vertexSource, fragmentSource, outNames);
    }

    public StandardProgram(int program) {
        super(program);
    }
}
