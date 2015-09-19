package pl.jacadev.engine.graphics.postprocessing.shaders.hdr;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL32;
import pl.jacadev.engine.graphics.postprocessing.HDRRenderer;
import pl.jacadev.engine.graphics.shaders.Program;
import pl.jacadev.engine.graphics.textures.MultisampleTexture2D;
import pl.jacadev.engine.graphics.textures.RawTexture2D;

import java.io.InputStream;

/**
 * @author Jaca777
 *         Created 21.02.15 at 18:36
 */
public class HDRProgram extends Program {

    private static InputStream FRAGMENT_SHADER = HDRProgram.class.getResourceAsStream("frag.glsl");
    private static InputStream VERTEX_SHADER = HDRProgram.class.getResourceAsStream("vert.glsl");

    public static final int ATTR_VERTEX = 0;
    public static final int ATTR_TEX_COORD = 1;

    public static final int VIEW_TEXTURE = 1;
    public static final int BRIGHTNESS_MAP = 0;
    public static final int PASSES_BASE = 2;

    /**
     * Level of brightness
     */
    public int uBrightness;

    /**
     * Level of bloom;
     */
    public int uBloomLvl;

    /**
     * Amount of samples per pixel in 'view' texture.
     */
    public int uSamples;

    private MultisampleTexture2D viewTexture;
    private RawTexture2D blurTextures[];
    private RawTexture2D brightnessMap;

    private static final String[] OUT_NAMES = new String[]{"fragColor"};

    public HDRProgram(MultisampleTexture2D viewTexture, RawTexture2D blurTextures[], RawTexture2D brightnessMap) {
        super(VERTEX_SHADER, FRAGMENT_SHADER, OUT_NAMES);
        this.viewTexture = viewTexture;
        this.brightnessMap = brightnessMap;
        this.blurTextures = blurTextures;

        setUniformi(getUniformLocation("view"), VIEW_TEXTURE);
        setUniformi(getUniformLocation("brightnessMap"), BRIGHTNESS_MAP);
        for(int i = 0; i < HDRRenderer.PASSES; i++){
            setUniformi(getUniformLocation("pass" + (i + 1)), PASSES_BASE + i);
        }
        assignLocations();
    }

    private void assignLocations(){
        this.uBrightness = getUniformLocation("exposure");
        this.uBloomLvl = getUniformLocation("bloomLevel");
        this.uSamples = getUniformLocation("samples");
    }

    @Override
    public void use() {
        super.use();
        try {
            setUniformi(this.uSamples, this.viewTexture.getSamples());
            useTexture(this.viewTexture.getTexture(), GL32.GL_TEXTURE_2D_MULTISAMPLE, VIEW_TEXTURE);
            useTexture(this.brightnessMap.getTexture(), GL11.GL_TEXTURE_2D, BRIGHTNESS_MAP);
            for (int i = 0; i < HDRRenderer.PASSES; i++) {
                useTexture(this.blurTextures[i].getTexture(), GL11.GL_TEXTURE_2D, PASSES_BASE + i);
            }
        }catch(NullPointerException e){
            //Do nothing
        }
    }

}
