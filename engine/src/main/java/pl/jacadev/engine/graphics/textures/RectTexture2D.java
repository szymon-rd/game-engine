package pl.jacadev.engine.graphics.textures;

import org.lwjgl.opengl.GL31;

import java.nio.ByteBuffer;

/**
 * @author Jaca777
 *         Created 01.01.15 at 22:18
 */
public class RectTexture2D extends Texture{

    protected RectTexture2D(int texture, int width, int height, int internalformat, int format, boolean mipmap) {
        super(GL31.GL_TEXTURE_RECTANGLE, texture, width, height, internalformat, format, mipmap);
    }

    public RectTexture2D(int width, int height, int internalformat, int format, ByteBuffer data) {
        super(GL31.GL_TEXTURE_RECTANGLE, Texturing.genTextureRect2D(internalformat, format, width, height, data), width, height, internalformat, format, false);
    }
}
