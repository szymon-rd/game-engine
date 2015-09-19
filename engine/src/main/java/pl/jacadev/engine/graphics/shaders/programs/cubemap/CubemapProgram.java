package pl.jacadev.engine.graphics.shaders.programs.cubemap;

import pl.jacadev.engine.graphics.contexts.Bindings;
import pl.jacadev.engine.graphics.shaders.SceneRenderingProgram;
import pl.jacadev.engine.graphics.textures.Cubemap;

import java.io.InputStream;

/**
 * @author Jaca777
 *         Created 19.12.14 at 23:09
 */
public class CubemapProgram extends SceneRenderingProgram {
    private static InputStream FRAGMENT_SHADER = CubemapProgram.class.getResourceAsStream("frag.glsl");
    private static InputStream VERTEX_SHADER = CubemapProgram.class.getResourceAsStream("vert.glsl");

    public static final int ATTR_VERTEX = 0;

    /**
     * Rotation matrix. Contains only model rotation. Used for rotating normals.
     */
    public int uRotMatrix;

    /**
     * Mirrored camera rotation matrix.
     */
    public int uMRotMatrix;

    /**
     * Perspective matrix.
     */
    public int uPerspMatrix;

    private static final String[] OUT_NAMES = new String[]{"fragData"};

    public CubemapProgram() {
        super(VERTEX_SHADER, FRAGMENT_SHADER, OUT_NAMES);
        uRotMatrix = getUniformLocation("rotMatrix");
        uMRotMatrix = getUniformLocation("mRotMatrix");
        uPerspMatrix = getUniformLocation("perspMatrix");
        super.uRenderState = getUniformLocation("rmirror");
    }

    /**
     *
     * @param cubemap Cubemap to be used.
     */
    public void useCubemap(Cubemap cubemap, Bindings bindings){
        useTexture(cubemap, 0, bindings);
    }
}
