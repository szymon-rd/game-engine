package pl.jacadev.engine.graphics.shaders.programs.terrain;

import pl.jacadev.engine.graphics.contexts.Bindings;
import pl.jacadev.engine.graphics.shaders.SceneRenderingProgram;
import pl.jacadev.engine.graphics.textures.RawTexture2D;

import java.io.InputStream;

/**
 * @author Jaca777
 *         Created 19.12.14 at 23:10
 */
public class TerrainProgram extends SceneRenderingProgram {

    private static InputStream FRAGMENT_SHADER = TerrainProgram.class.getResourceAsStream("frag.glsl");
    private static InputStream VERTEX_SHADER = TerrainProgram.class.getResourceAsStream("vert.glsl");
    /**
     * sampler2DArray containing terrain textures.
     */
    protected static final int TERRAIN_TEXTURES = 0;
    /**
     * Terrain materials map. sampler2D
     */
    protected static final int MATERIAL_MAP = 1;


    /**
     * Camera position
     */
    public int uCameraPos;

    /**
     * Rotation matrix. Contains only model rotation. Used for rotating normals.
     */
    public int uRotMatrix;

    /**
     * Material shininess.
     */
    public int[] uShininessArray;

    /**
     * Gradient of a fog.
     */
    public int uFogGradient;

    /**
     * Density of a fog.
     */
    public int uFogDensity;

    public int dirLightShadowSampler = 3;
    public int uDirLightPersp;
    public int uDirLightCamera;

    private static final String[] OUT_NAMES = new String[]{"fragData"};

    public TerrainProgram() {
        super(VERTEX_SHADER, FRAGMENT_SHADER, OUT_NAMES);
        setUniformi(getUniformLocation("terrain.materialsMap"), MATERIAL_MAP);
        setUniformi(getUniformLocation("directionalLight.shadowMap"), dirLightShadowSampler);
        assignLocations();
    }

    private void assignLocations() {
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
        uDirLightPersp = getUniformLocation("directionalLight.persp");
        uDirLightCamera = getUniformLocation("directionalLight.camera");
        uNumLights = getUniformLocation("numLights");
        uLightingEnabled = getUniformLocation("lightingEnabled");

        uShininessArray = new int[MaterialSet.ARRAY_SIZE];
        for(int i = 0; i < MaterialSet.ARRAY_SIZE; i++) {
            uShininessArray[i] = getUniformLocation("terrain.shininess[" + i + ']');
        }

        uBrightness = getUniformLocation("brightness");
        uFogDensity = getUniformLocation("fog.density");
        uFogGradient = getUniformLocation("fog.gradient");
        uRenderState = getUniformLocation("rmirror");
        uWaterLvl = getUniformLocation("waterlvl");
    }

    public void useMaterialSet(MaterialSet materials, Bindings bindings) {
        for (int i = 0; i < MaterialSet.ARRAY_SIZE; i++) {
            setUniformf(uShininessArray[i], materials.getShininessArray()[i]);
        }
        useTexture(materials.getTextureArray(), TERRAIN_TEXTURES, bindings);
        //useTexture(materials.getPrlxMaps(), PRLX_MAPS);
    }


    public void useMaterialMap(RawTexture2D map, Bindings bindings) {
        useTexture(map, MATERIAL_MAP, bindings);
    }
}
