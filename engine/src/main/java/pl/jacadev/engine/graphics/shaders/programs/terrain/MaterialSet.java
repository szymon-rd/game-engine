package pl.jacadev.engine.graphics.shaders.programs.terrain;

import pl.jacadev.engine.graphics.Unloadable;
import pl.jacadev.engine.graphics.textures.Texture2DArray;

import java.nio.ByteBuffer;

/**
 * @author Jaca777
 *         Created 01.01.15 at 15:39
 */
public class MaterialSet implements Unloadable {

    public static final int TEXTURE_SIZE = 512;
    public static final int ARRAY_SIZE = 3;
    protected Texture2DArray textureArray;
    protected Texture2DArray prlxMaps;
    protected float[] shininessArray;

    public MaterialSet(ByteBuffer[] textures, ByteBuffer[] prlxMaps, float... shininessArray) {
        this.textureArray = new Texture2DArray(TEXTURE_SIZE, TEXTURE_SIZE, textures.length, textures);
        this.prlxMaps = new Texture2DArray(TEXTURE_SIZE, TEXTURE_SIZE, textures.length, prlxMaps);
        this.shininessArray = shininessArray;
    }

    public MaterialSet(Texture2DArray textureArray, Texture2DArray prlxMaps, float... shininessArray) {
        this.textureArray = textureArray;
        this.prlxMaps = prlxMaps;
        this.shininessArray = shininessArray;
    }

    /**
     * @param offset Material offset.
     * @param shininess The new material shininess.
     */
    public void setShininess(int offset, float shininess){
        shininessArray[offset] = shininess;
    }

    /**
     * @param offset Material offset.
     * @param texture The new material texture.
     */
    public void setTexture(int offset, ByteBuffer texture){
        textureArray.set(offset, texture);
    }

    /**
     * @return Array consisting of shininess of each material.
     */
    public float[] getShininessArray() {
        return shininessArray;
    }

    /**
     * @return Array consisting of texture of each material.
     */
    public Texture2DArray getTextureArray() {
        return textureArray;
    }

    public Texture2DArray getPrlxMaps() {
        return prlxMaps;
    }

    /**
     * Unloads materials.
     */
    public void unload() {
        textureArray.unload();
    }
}
