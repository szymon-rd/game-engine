package pl.jacadev.engine.graphics.textures;

import org.lwjgl.opengl.GL32;

/**
 * @author Jaca777
 *         Created 07.04.15 at 13:39
 */
public class MultisampleTexture2D extends Texture {
    private int samples;

    public MultisampleTexture2D(int width, int height, int internalformat, int samples) {
        super(GL32.GL_TEXTURE_2D_MULTISAMPLE, Texturing.genTextureMultisample2D(width, height, samples, internalformat), width, height, internalformat, -1, false);
        this.samples = samples;
    }

    @Override
    public void resize(int w, int h) {
        this.width = w;
        this.height = h;
        GL32.glTexImage2DMultisample(GL32.GL_TEXTURE_2D_MULTISAMPLE, samples, internalformat, width, height, true);
    }

    public int getSamples() {
        return samples;
    }

    public void setSamples(int samples) {
        this.samples = samples;
        GL32.glTexImage2DMultisample(GL32.GL_TEXTURE_2D_MULTISAMPLE, samples, internalformat, width, height, true);
    }
}
