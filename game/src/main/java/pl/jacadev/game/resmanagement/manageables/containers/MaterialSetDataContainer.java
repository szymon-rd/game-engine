package pl.jacadev.game.resmanagement.manageables.containers;

import pl.jacadev.game.resmanagement.loading.DataContainer;

import java.nio.ByteBuffer;

/**
 * @author Jaca777
 *         Created 20.02.15 at 17:53
 */
public class MaterialSetDataContainer extends DataContainer {
    private boolean loaded = false;

    private int size;
    private ByteBuffer[] textures;
    private ByteBuffer[] prlxMaps;
    private float[] shininess;

    public void load(ByteBuffer[] textures, ByteBuffer[] prlxMaps, float[] shininess){
        this.size = textures.length;
        this.textures = textures;
        this.prlxMaps = prlxMaps;
        this.shininess = shininess;
        this.loaded = true;
    }

    public ByteBuffer[] getPrlxMaps() {
        return prlxMaps;
    }

    public int getSize() {
        return size;
    }

    public ByteBuffer[] getTextures() {
        return textures;
    }

    @Override
    public boolean isLoaded() {
        return loaded;
    }

    public float[] getShininess() {
        return shininess;
    }
}
