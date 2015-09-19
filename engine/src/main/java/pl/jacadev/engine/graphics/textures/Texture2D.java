package pl.jacadev.engine.graphics.textures;

import java.nio.ByteBuffer;

/**
 * @author Jaca777
 *         Created 21.12.14 at 00:08
 */
public class Texture2D extends Texture2DArray {
    protected Texture2D(int texture, int width, int height, int internalformat, int format, boolean mipmap) {
        super(texture, width, height, 1, internalformat, format, mipmap);
    }

    public Texture2D(int width, int height, ByteBuffer data) {
        super(width, height, 1, new ByteBuffer[]{data});
    }

    public Texture2D(int width, int height, ByteBuffer data, int internalformat, int format) {
        super(width, height, 1, new ByteBuffer[]{data}, internalformat, format);
    }

    /**
     * Sets the texture.
     * @param data Must be the same size as texture!
     */
    public void set(ByteBuffer data) {
        Texturing.setTexture2D(this.width, this.height, this.internalformat, this.format, this.mipmap, data);
    }
}
