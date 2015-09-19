package pl.jacadev.engine.graphics.shaders.programs.light;

import org.lwjgl.util.vector.Vector3f;
import pl.jacadev.engine.graphics.Unloadable;
import pl.jacadev.engine.graphics.utility.math.Math3D;

/**
 * @author Jaca777
 *         Created 13.12.14 at 17:13
 */
public class SpotLightSource implements Unloadable{
    public static final int SHADOW_MAP_HEIGHT = 1024;
    public static final int SHADOW_MAP_WIDTH = 1024;

    private Vector3f pos, color, amColor;
    private float attenuation, gradient;
    private ShadowMap shadowMap;

    /**
     * @param pos Light source position.
     * @param color Light color.
     * @param amColor Ambient light color.
     * @param attenuation Light attenuation.
     * @param gradient Light attenuation gradient.
     */
    public SpotLightSource(Vector3f pos, Vector3f color, Vector3f amColor, float attenuation, float gradient, boolean shadow) {
        this.pos = pos;
        this.color = color;
        this.amColor = amColor;
        this.attenuation = attenuation;
        this.gradient = gradient;
        this.shadowMap = shadow ? new ShadowMap(SHADOW_MAP_WIDTH, SHADOW_MAP_HEIGHT, null) : null;
    }

    /**
     * @param pos Light source position.
     * @param color Light color.
     * @param amColor Ambient light color.
     * @param amStr Strength of the ambient light.
     * @param attenuation Light attenuation.
     * @param gradient Light attenuation gradient.
     */
    public SpotLightSource(Vector3f pos, Vector3f color, float str, Vector3f amColor, float amStr, float attenuation, float gradient, boolean shadow) {
       this(pos, Math3D.mul(color, str), Math3D.mul(amColor, amStr), attenuation, gradient, shadow);
    }

    @Override
    public void unload() {
        if(isShaded()) this.shadowMap.unload();
    }

    /**
     * @return Light source position.
     */
    public Vector3f getPos() {
        return pos;
    }

    public void setPos(Vector3f pos) {
        this.pos = pos;
    }

    /**
     * @return Color of the light source.
     */
    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }

    /**
     * @return Color of the ambient light.
     */
    public Vector3f getAmColor() {
        return amColor;
    }

    public void setAmColor(Vector3f amColor) {
        this.amColor = amColor;
    }

    /**
     * @return Light attenuation.
     */
    public float getAttenuation() {
        return attenuation;
    }

    public void setAttenuation(float attenuation) {
        this.attenuation = attenuation;
    }

    /**
     * @return Light attenuation gradient.
     */
    public float getGradient() {
        return gradient;
    }

    public void setGradient(float gradient) {
        this.gradient = gradient;
    }

    public boolean isShaded(){
        return shadowMap != null;
    }

    public ShadowMap getShadowMap() {
        return shadowMap;
    }
}
