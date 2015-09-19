package pl.jacadev.engine.graphics.shaders.effects.bloom;

import org.lwjgl.opengl.GL32;
import pl.jacadev.engine.graphics.shaders.Program;
import pl.jacadev.engine.graphics.shaders.effects.Filter;
import pl.jacadev.engine.graphics.textures.MultisampleTexture2D;
import pl.jacadev.engine.graphics.textures.RawTexture2D;

import java.io.InputStream;

/**
 * @author Jaca777
 *         Created 23.02.15 at 00:21
 */
public class BloomDetection extends Program implements Filter<RawTexture2D> {
    private static InputStream FRAGMENT_SHADER = BloomDetection.class.getResourceAsStream("frag.glsl");
    private static InputStream VERTEX_SHADER = BloomDetection.class.getResourceAsStream("vert.glsl");

    public static final int ATTR_VERTEX = 1;
    public static final int ATTR_TEX_COORD = 0;

    protected static final int VIEW_TEXTURE = 0;

    private MultisampleTexture2D viewTexture;

    public int uSamples;

    private static final String[] OUT_NAMES = new String[]{"fragBrightColor"};

    public BloomDetection(MultisampleTexture2D viewTexture) {
        super(VERTEX_SHADER, FRAGMENT_SHADER, OUT_NAMES);
        this.viewTexture = viewTexture;
        this.uSamples = getUniformLocation("samples");
    }

    @Override
    public void use() {
        super.use();
        if (this.viewTexture != null) {
            setUniformi(this.uSamples, this.viewTexture.getSamples());
            useTexture(this.viewTexture.getTexture(), GL32.GL_TEXTURE_2D_MULTISAMPLE, VIEW_TEXTURE);
        }
    }

    @Override
    public void apply(RawTexture2D texture) {

    }

    @Override
    public void apply(RawTexture2D src, RawTexture2D dest) {

    }
}
