package pl.jacadev.engine.graphics.shaders.programs.water;

import org.lwjgl.opengl.GL11;
import pl.jacadev.engine.graphics.resources.ImageDecoder;
import pl.jacadev.engine.graphics.resources.PNGDecoder;
import pl.jacadev.engine.graphics.resources.ResSupplier;
import pl.jacadev.engine.graphics.shaders.SceneRenderingProgram;
import pl.jacadev.engine.graphics.textures.MultisampleTexture2D;
import pl.jacadev.engine.graphics.textures.RawTexture2D;

import java.io.InputStream;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

/**
 * @author Jaca777
 *         Created 19.12.14 at 23:10
 */
public class WtrSrfProgram extends SceneRenderingProgram {

    public static final int ATTR_VERTEX = 0;

    private static InputStream FRAGMENT_SHADER = WtrSrfProgram.class.getResourceAsStream("frag.glsl");
    private static InputStream VERTEX_SHADER = WtrSrfProgram.class.getResourceAsStream("vert.glsl");

    private static final int MIRROR_TEXTURE_POS = 0;
    private static final int DUDV_TEXTURE_POS = 1;
    private static final int NORMAL_TEXTURE_POS = 2;
    private static final int H_MAP_POS = 3;

    private MultisampleTexture2D btmTexture;
    private RawTexture2D mirrorTexture;

    private int dudvMap;
    private int normalMap;

    /**
     * Screen width.
     */
    public int uScreenW;

    /**
     * Screen height.
     */
    public int uScreenH;

    /**
     * Coefficient of displacement of the texel being sampled. (Multiplied by the value on the du/dv map)
     */
    public int uDisplacement;

    /**
     * Position of the camera. (3D vector)
     */
    public int uCamPos;

    /**
     * Perspective matrix uniform location.
     */
    public int uPerspMatrix;

    /**
     * Model matrix uniform location.
     */
    public int uMMatrix;

    /**
     * Camera matrix uniform location.
     */
    public int uCameraMatrix;

    public int uFogGradient;
    public int uFogDensity;


    /**
     * Names of the output variables of fragment shader.
     */
    private static final String[] OUT_NAMES = new String[]{"fragData"};

    public WtrSrfProgram() {
        super(VERTEX_SHADER, FRAGMENT_SHADER, OUT_NAMES);
        assignLocations();
        use();
        setUniformi(getUniformLocation("mirrorTex"), MIRROR_TEXTURE_POS);
        setUniformi(getUniformLocation("dudvTex"), DUDV_TEXTURE_POS);
        setUniformi(getUniformLocation("normalTex"), NORMAL_TEXTURE_POS);
        setUniformi(getUniformLocation("hMap"), H_MAP_POS);
        loadRes();
    }

    private static final String DUDV_MAP_PATH = "pl/jacadev/engine/res/du_dv.png";
    private static final String NORMAL_MAP_PATH = "pl/jacadev/engine/res/normalmap.png";

    private void loadRes() {
        ImageDecoder.DecodedImage dudvImage = ImageDecoder.decodePNG(ResSupplier.getAsStream(DUDV_MAP_PATH), PNGDecoder.Format.RGB);
        this.dudvMap = GL11.glGenTextures();
        glBindTexture(GL_TEXTURE_2D, dudvMap);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB8, dudvImage.getW(), dudvImage.getH(), 0,
                GL_RGB, GL_UNSIGNED_BYTE, dudvImage.getBuffer());
        glGenerateMipmap(GL_TEXTURE_2D);

        ImageDecoder.DecodedImage nrmImage = ImageDecoder.decodePNG(ResSupplier.getAsStream(NORMAL_MAP_PATH), PNGDecoder.Format.RGB);
        this.normalMap = GL11.glGenTextures();
        glBindTexture(GL_TEXTURE_2D, normalMap);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB8, nrmImage.getW(), nrmImage.getH(), 0,
                GL_RGB, GL_UNSIGNED_BYTE, nrmImage.getBuffer());
        glGenerateMipmap(GL_TEXTURE_2D);
    }


    private void assignLocations() {
        this.uPerspMatrix = getUniformLocation("perspMatrix");
        this.uMMatrix = getUniformLocation("mMatrix");
        this.uCameraMatrix = getUniformLocation("cameraMatrix");
        this.uScreenW = getUniformLocation("screenW");
        this.uScreenH = getUniformLocation("screenH");
        this.uCamPos = getUniformLocation("camPos");
        this.uDisplacement = getUniformLocation("displacement");
        this.uNumLights = getUniformLocation("numLights");
        this.uFogGradient = getUniformLocation("fog.gradient");
        this.uFogDensity = getUniformLocation("fog.density");

        super.uLightsArray = new int[LIGHTS_NUM][FIELD_NAMES.length];
        for (int i = 0; i < LIGHTS_NUM; i++) {
            for (int f = 0; f < FIELD_NAMES.length; f++) {
                String field = FIELD_NAMES[f];
                super.uLightsArray[i][f] = getUniformLocation(LIGHTS_NAME + '[' + i + "]." + field);
            }
        }
        super.uDirLightAmColor = getUniformLocation("directionalLight.amColor");
        super.uDirLightColor = getUniformLocation("directionalLight.color");
        super.uDirLightDir = getUniformLocation("directionalLight.dir");

        this.uBrightness = getUniformLocation("brightness");
        super.uRenderState = getUniformLocation("rmirror");
    }

    public void setBtmTexture(MultisampleTexture2D btmTexture) {
        this.btmTexture = btmTexture;
    }

    public void setMirrorTexture(RawTexture2D mirrorTexture) {
        this.mirrorTexture = mirrorTexture;
    }

    @Override
    public void use() {
        super.use();
        if(this.btmTexture != null) {
            useTexture(this.mirrorTexture.getTexture(), GL11.GL_TEXTURE_2D, MIRROR_TEXTURE_POS);
            useTexture(this.dudvMap, GL11.GL_TEXTURE_2D, DUDV_TEXTURE_POS);
            useTexture(this.normalMap, GL11.GL_TEXTURE_2D, NORMAL_TEXTURE_POS);
        }
    }

    public void useHMap(int hMap){
        useTexture(hMap, GL11.GL_TEXTURE_2D, H_MAP_POS);
    }

    private static final float WAVES_SPEED = 0.00001f;

    private float displacement;

    /**
     * Moves 'waves'.
     */
    public void update(long delta) {
        setUniformf(uDisplacement, displacement = displacement + (delta * WAVES_SPEED) % 1f);
    }

    public int getLightsMemberLocation(int arrayOffset, int structOffset) {
        return uLightsArray[arrayOffset][structOffset];
    }
}
