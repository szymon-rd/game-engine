package pl.jacadev.engine.graphics.shaders.programs.light;

import pl.jacadev.engine.graphics.contexts.Bindings;
import pl.jacadev.engine.graphics.shaders.SceneRenderingProgram;
import pl.jacadev.engine.graphics.textures.Texture2D;
import pl.jacadev.engine.graphics.textures.Texture2DArray;

import java.io.InputStream;

/**
 * @author Jaca777
 *         Created 18.12.14 at 16:18
 */
public class LightProgram extends SceneRenderingProgram {
    protected static final int TEXTURE = 0;

    private static InputStream FRAGMENT_SHADER = LightProgram.class.getResourceAsStream("frag.glsl");
    private static InputStream VERTEX_SHADER = LightProgram.class.getResourceAsStream("vert.glsl");

    /**
     * Maximal number of lights in 'lights' array
     * @see #uLightsArray
     */
    public static int MAX_LIGHTS = 10;
    /**
     * Selected texture offset in textures array.
     */
    public int uTexOffset;
    /**
     * Camera position
     */
    public int uCameraPos;

    /**
     * Rotation matrix. Contains only model rotation. Used for rotating normals.
     */
    public int uRotMatrix;


    /**
     * Gradient of the fog.
     */
    public int uFogGradient;

    /**
     * Density of the fog.
     */
    public int uFogDensity;

    private static final String[] OUT_NAMES = new String[]{"fragData"};

    public LightProgram() {
        super(VERTEX_SHADER, FRAGMENT_SHADER, OUT_NAMES);
        assignLocations();
    }

    private void assignLocations() {
        //Uniforms
        uCameraPos = getUniformLocation("cameraPos");
        uMMatrix = getUniformLocation("mMatrix");
        uRotMatrix = getUniformLocation("rotMatrix");
        uPerspMatrix = getUniformLocation("perspMatrix");
        uCameraMatrix = getUniformLocation("cameraMatrix");
        uMCameraMatrix = getUniformLocation("mCameraMatrix");

        this.uLightsArray = new int[LIGHTS_NUM][FIELD_NAMES.length];
        for (int i = 0; i < LIGHTS_NUM; i++) {
            for (int f = 0; f < FIELD_NAMES.length; f++) {
                String field = FIELD_NAMES[f];
                uLightsArray[i][f] = getUniformLocation(LIGHTS_NAME + '[' + i + "]." + field);
            }
        }
        uDirLightAmColor = getUniformLocation("directionalLight.amColor");
        uDirLightColor = getUniformLocation("directionalLight.color");
        uDirLightDir = getUniformLocation("directionalLight.dir");

        uNumLights = getUniformLocation("numLights");
        uLightingEnabled = getUniformLocation("lightingEnabled");
        uTexOffset = getUniformLocation("textureOffset");
        uShininess = getUniformLocation("shininess");
        uFogDensity = getUniformLocation("fog.density");
        uFogGradient = getUniformLocation("fog.gradient");
        uBrightness = getUniformLocation("brightness");
        uRenderState = getUniformLocation("rmirror");
        uWaterLvl = getUniformLocation("waterlvl");
    }


    public void useTexture2D(Texture2D texture, Bindings bindings) {
        useTextureArray(texture, TEXTURE, bindings);
        setTextureOffset(0);
    }

    public void useTextureArray(Texture2DArray texture, int offset, Bindings bindings) {
        super.useTexture(texture, TEXTURE, bindings);
        setTextureOffset(offset);
    }

    public void setTextureOffset(int offset) {
        setUniformi(uTexOffset, offset);
    }
}
