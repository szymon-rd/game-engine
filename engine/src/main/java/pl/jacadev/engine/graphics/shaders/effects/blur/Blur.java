package pl.jacadev.engine.graphics.shaders.effects.blur;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import pl.jacadev.engine.graphics.DynamicFramebuffer;
import pl.jacadev.engine.graphics.shaders.Program;
import pl.jacadev.engine.graphics.shaders.effects.Filter;
import pl.jacadev.engine.graphics.textures.RawTexture2D;
import pl.jacadev.engine.graphics.utility.ogl.Shapes;

import java.io.InputStream;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;

/**
 * @author Jaca777
 *         Created 23.02.15 at 00:21
 */
public class Blur extends Program implements Filter<RawTexture2D> {
    public static final int RGBA = 0;
    public static final int RGB = 1;
    public static final int RG = 2;
    public static final int DEPTH = 3;

    private static final String[] OUT_NAMES_RGBA = new String[]{"fragColorV4", "fragColorV3","fragColorV2","fragColorFloat"};

    private int currentFormat;

    private static InputStream FRAGMENT_SHADER = Blur.class.getResourceAsStream("frag.glsl");
    private static InputStream VERTEX_SHADER = Blur.class.getResourceAsStream("vert.glsl");

    public static final int ATTR_VERTEX = 0;
    public static final int ATTR_TEX_COORD = 1;

    protected static final int TEXTURE = 0;
    private static final int SAMPLES_NUM = 25;

    /**
     * Base of the array containing displacements (expressed in vectors) for the blurring.
     */
    public int[] uDisplacementArray;
    public int uFormat;

    private int rect;
    private RawTexture2D copy;
    private DynamicFramebuffer framebuffer;

    public Blur() {
        super(VERTEX_SHADER, FRAGMENT_SHADER, OUT_NAMES_RGBA);
        setUniformi(getUniformLocation("textureS"), TEXTURE);
        this.rect = Shapes.mkFullRect(ATTR_VERTEX, ATTR_TEX_COORD);
        this.copy = new RawTexture2D(1, 1, GL11.GL_RGBA, GL11.GL_RGBA, null); //Some random values.
        this.framebuffer = new DynamicFramebuffer();
        assignLocations();
    }

    private void assignLocations() {
        this.uFormat = getUniformLocation("format");
        this.uDisplacementArray = new int[SAMPLES_NUM];
        for (int i = 0; i < SAMPLES_NUM; i++) {
            uDisplacementArray[i] = getUniformLocation("blurShifts[" + i + ']');
        }
    }

    @Override
    public void use() {
        super.use();
    }

    /**
     * Starts using the given texture for blurring.
     */
    public void useTexture(int texture) {
        useTexture(texture, GL11.GL_TEXTURE_2D, TEXTURE);
    }


    /**
     * Calculates displacements for new dimensions of texture.
     *
     * @param w Width of the texture.
     * @param h height of the texture.
     */
    public void applyBlurDisplacement(int w, int h) {
        float xInc = 1.0f / (float) w;
        float yInc = 1.0f / (float) h;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                setUniformV2(this.uDisplacementArray[j * 5 + i], (-2.0f * xInc) + ((float) i * xInc), (-2.0f * yInc) + ((float) j * yInc));
            }
        }
    }

    @Override
    public void apply(RawTexture2D texture) {
        use();
        setDest(texture);
        copy.copy(texture);
        useTexture(copy.getTexture());
        GL11.glClear(GL_COLOR_BUFFER_BIT);
        GL30.glBindVertexArray(rect);
        GL11.glViewport(0, 0, texture.getWidth(), texture.getHeight());
        GL11.glDrawElements(GL11.GL_TRIANGLES, Shapes.QUAD_INDICES_AMOUNT, GL11.GL_UNSIGNED_INT, 0);
    }

    public void setDest(RawTexture2D dest) {
        applyBlurDisplacement(dest.getWidth(), dest.getHeight());
        int format = dest.getFormat();
        if (currentFormat != format) switch (format) {
            case GL11.GL_RGBA:
                this.currentFormat = format;
                framebuffer.bindDraw(dest, GL30.GL_COLOR_ATTACHMENT0);
                setUniformi(uFormat, RGBA);
                break;
            case GL11.GL_RGB:
                this.currentFormat = format;
                framebuffer.bindDraw(dest, GL30.GL_COLOR_ATTACHMENT1);
                setUniformi(uFormat, RGB);
                break;
            case GL30.GL_RG:
                this.currentFormat = format;
                framebuffer.bindDraw(dest, GL30.GL_COLOR_ATTACHMENT2);
                setUniformi(uFormat, RG);
                break;
            case GL11.GL_DEPTH_COMPONENT:
                this.currentFormat = format;
                framebuffer.bindDraw(dest, GL30.GL_COLOR_ATTACHMENT3);
                setUniformi(uFormat, DEPTH);
                break;
        }
    }

    @Override
    public void apply(RawTexture2D src, RawTexture2D dest) {
        use();
        setDest(dest);
        useTexture(src.getTexture());
        GL11.glClear(GL_COLOR_BUFFER_BIT);
        GL30.glBindVertexArray(rect);
        GL11.glViewport(0, 0, dest.getWidth(), dest.getHeight());
        GL11.glDrawElements(GL11.GL_TRIANGLES, Shapes.QUAD_INDICES_AMOUNT, GL11.GL_UNSIGNED_INT, 0);
    }
}
