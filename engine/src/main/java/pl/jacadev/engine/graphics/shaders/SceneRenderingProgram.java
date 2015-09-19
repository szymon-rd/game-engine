package pl.jacadev.engine.graphics.shaders;

import pl.jacadev.engine.graphics.shaders.programs.light.DirectionalLight;
import pl.jacadev.engine.graphics.shaders.programs.light.Lighting;
import pl.jacadev.engine.graphics.shaders.programs.light.SpotLightSource;

import java.io.InputStream;

/**
 * @author Jaca777
 *         Created 2015-06-26 at 16
 */
public class SceneRenderingProgram extends StandardProgram {

    protected static final int LIGHTS_NUM = 5;
    protected static final String LIGHTS_NAME = "lights";
    protected static final String[] FIELD_NAMES = {"pos", "color", "amColor", "attenation", "gradient"};

    /**
     * Position of light source.
     */
    public final int STRUCT_POS = 0;
    /**
     * Color of diffuse and specular light. (simplified)
     */
    public final int STRUCT_COLOR = 1;
    /**
     * Color of ambient light.
     */
    public final int STRUCT_AM_COLOR = 2;
    /**
     * Two values below are used to calculate attenuation of light:
     * exp(-pow((distance * attenuation), gradient))
     */
    public final int STRUCT_ATTENUATION = 3;

    public final int STRUCT_GRADIENT = 4;

    public static final int ALL = 0;
    public static final int ABOVE_WATER = 1;
    public int uRenderState;
    public int uBrightness;
    /**
     * Array containing light sources.
     */
    protected int[][] uLightsArray;

    /**
     * Number of lights to use.
     */
    public int uNumLights;

    /**
     * Determines whether the lighting is enabled. If not, fragments are displayed with unmodified texture.
     */
    public int uLightingEnabled;

    public int uDirLightColor;
    public int uDirLightAmColor;
    public int uDirLightDir;

    /**
     * Material shininess.
     */
    public int uShininess;

    public SceneRenderingProgram(InputStream vertexShader, InputStream fragmentShader, String[] outNames) {
        super(vertexShader, fragmentShader, outNames);
    }

    /**
     * Sets current lighting.
     *
     * @param lighting Lighting to use
     */
    public void useLighting(Lighting lighting) {
        DirectionalLight dirLight = lighting.getDirectionalLight();
        if (dirLight != null) {
            setUniformV3(this.uDirLightAmColor, dirLight.getAmColor());
            setUniformV3(this.uDirLightColor, dirLight.getColor());
            setUniformV3(this.uDirLightDir, dirLight.getDirection());
        }
        for (int i = 0; i < lighting.getNumLights(); i++) {
            SpotLightSource source = lighting.getSource(i);
            setUniformV3(getLightsMemberLocation(i, STRUCT_POS), source.getPos());
            setUniformV3(getLightsMemberLocation(i, STRUCT_COLOR), source.getColor());
            setUniformV3(getLightsMemberLocation(i, STRUCT_AM_COLOR), source.getAmColor());
            setUniformf(getLightsMemberLocation(i, STRUCT_ATTENUATION), source.getAttenuation());
            setUniformf(getLightsMemberLocation(i, STRUCT_GRADIENT), source.getGradient());
        }
        setUniformi(uNumLights, lighting.getNumLights());
    }

    /**
     * Sets current lighting. Used for loading chunks lighting.
     *
     * @param lightings Array of lighting
     */
    public void useLighting(Lighting[] lightings) {
        int num = 0;
        for (Lighting lighting : lightings)
            for (int j = 0; j < lighting.getNumLights(); j++) {
                SpotLightSource source = lighting.getSource(j);
                setUniformV3(getLightsMemberLocation(num, STRUCT_POS), source.getPos());
                setUniformV3(getLightsMemberLocation(num, STRUCT_COLOR), source.getColor());
                setUniformV3(getLightsMemberLocation(num, STRUCT_AM_COLOR), source.getAmColor());
                setUniformf(getLightsMemberLocation(num, STRUCT_ATTENUATION), source.getAttenuation());
                setUniformf(getLightsMemberLocation(num, STRUCT_GRADIENT), source.getGradient());
                num++;
            }
        setUniformi(uNumLights, num);
    }

    /**
     * @param arrayOffset  Offset of structure in array
     * @param structOffset Offset of value in structure
     * @return Calculated location of value
     */
    public int getLightsMemberLocation(int arrayOffset, int structOffset) {
        return uLightsArray[arrayOffset][structOffset];
    }
}
