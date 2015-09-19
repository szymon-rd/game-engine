package pl.jacadev.engine.graphics.textures;

/**
 * @author Jaca777
 * Created 20.12.14 at 15:13
 */

import org.lwjgl.opengl.EXTTextureFilterAnisotropic;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GL32;
import org.lwjgl.opengl.GLContext;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.glTexImage3D;
import static org.lwjgl.opengl.GL12.glTexSubImage3D;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X;
import static org.lwjgl.opengl.GL30.GL_TEXTURE_2D_ARRAY;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

public class Texturing {


    /**
     * Generates new 2D texture name and assigns data to it.
     */
    public static int genTexture2D(int internalformat, int format, int width, int height, boolean mipmap, ByteBuffer data) {
        int texture = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texture);
        glTexImage2D(GL_TEXTURE_2D, 0, internalformat, width, height, 0, format, GL_UNSIGNED_BYTE, data);
        glGenerateMipmap(GL_TEXTURE_2D);
        return texture;
    }

    /**
     * Generates new 2D array texture name and assigns data to it.
     */
    public static int genTexture2DArray(int internalformat, int format, int width, int height, int size, ByteBuffer[] data) {
        int texture = glGenTextures();
        glBindTexture(GL_TEXTURE_2D_ARRAY, texture);
        glTexImage3D(GL_TEXTURE_2D_ARRAY, 0, internalformat, width, height, size, 0, format, GL_UNSIGNED_BYTE, (ByteBuffer) null);
        for (int i = 0; i < size; i++) {
            glTexSubImage3D(GL_TEXTURE_2D_ARRAY, 0, 0, 0, i, width, height, 1, format, GL_UNSIGNED_BYTE, data[i]);
        }
        glGenerateMipmap(GL_TEXTURE_2D_ARRAY);
        return texture;
    }

    /**
     * Generates new cubemap texture name and assigns data to it.
     * @param data 1. GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X
     *             2. GL13.GL_TEXTURE_CUBE_MAP_NEGATIVE_X
     *             3. GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_Y
     *             4. GL13.GL_TEXTURE_CUBE_MAP_NEGATIVE_Y
     *             5. GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_Z
     *             6. GL13.GL_TEXTURE_CUBE_MAP_NEGATIVE_Z
     */

    public static int genCubemap(int internalformat, int format, int width, int height, ByteBuffer[] data) {
        int texture = glGenTextures();
        glBindTexture(GL_TEXTURE_CUBE_MAP, texture);
        for (int i = 0; i < 6; i++) {
            glTexImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, internalformat, width, height, 0,
                    format, GL_UNSIGNED_BYTE, data[i]);
        }
        glGenerateMipmap(GL_TEXTURE_CUBE_MAP);
        return texture;
    }

    /**
     * Sets binded 2D texture to data.
     */
    static void setTexture2D(int w, int h, int internalformat, int format, boolean mipmap, ByteBuffer data) {
        glTexImage2D(GL_TEXTURE_2D, 0, internalformat, w, h, 0, format, GL_UNSIGNED_BYTE, data);
        if (mipmap) glGenerateMipmap(GL_TEXTURE_2D);
    }

    /**
     * Sets selected fragment of binded 2D texture to data.
     */
    static void setSubTexture2D(int x, int y, int w, int h, int format, boolean mipmap, ByteBuffer data) {
        glTexSubImage2D(GL_TEXTURE_2D, 0, x, y, w, h, format, GL_UNSIGNED_BYTE, data);
        if (mipmap) glGenerateMipmap(GL_TEXTURE_2D);
    }

    /**
     * Sets selected z offset in binded 3D texture to data.
     */
    static void setTexture3D(int w, int h, int offset, int format, boolean mipmap, ByteBuffer data) {
        glTexSubImage3D(GL_TEXTURE_2D_ARRAY, 0, 0, 0, offset, w, h, 1, format, GL_UNSIGNED_BYTE, data);
        if (mipmap) glGenerateMipmap(GL_TEXTURE_2D_ARRAY);
    }

    /**
     * Sets selected fragment of binded 3D texture to data.
     */
    static void setSubTexture3D(int x, int y, int w, int h, int offset, int format, boolean mipmap, ByteBuffer data) {
        glTexSubImage3D(GL_TEXTURE_2D_ARRAY, 0, x, y, offset, w, h, 1, format, GL_UNSIGNED_BYTE, data);
        if (mipmap) glGenerateMipmap(GL_TEXTURE_2D_ARRAY);
    }

    private static final int DEFAULT_ANISOTROPY = 8;


    /**
     * Enables anisotropy with default parameters.
     */
    public static void enableAnisotropy() {
        if (isAnisotropySupported()) {
            glTexParameteri(GL_TEXTURE_2D_ARRAY, EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT, DEFAULT_ANISOTROPY);
            glTexParameteri(GL_TEXTURE_2D, EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT, DEFAULT_ANISOTROPY);
            glTexParameteri(GL_TEXTURE_CUBE_MAP, EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT, DEFAULT_ANISOTROPY);
        } else throw new RuntimeException("Anisotropic filtering is not supported.");
    }

    /**
     * Disables anisotropy.
     */
    public static void disableAnisotropy() {
        if (isAnisotropySupported()) {
            glTexParameteri(GL_TEXTURE_2D_ARRAY, EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT, 0);
            glTexParameteri(GL_TEXTURE_2D, EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT, 0);
            glTexParameteri(GL_TEXTURE_CUBE_MAP, EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT, 0);
        } else throw new RuntimeException("Anisotropic filtering is not supported.");
    }

    /**
     * @return The result of check.
     */
    public static boolean isAnisotropySupported() {
        return GLContext.getCapabilities().GL_EXT_texture_filter_anisotropic;
    }

    /**
     * Creates new rectangle texture.
     * @return Texture name.
     */
    public static int genTextureRect2D(int internalformat, int format, int width, int height, ByteBuffer data) {
        int texture = glGenTextures();
        glBindTexture(GL31.GL_TEXTURE_RECTANGLE, texture);
        glTexImage2D(GL31.GL_TEXTURE_RECTANGLE, 0, internalformat, width, height, 0, format,
                GL_UNSIGNED_BYTE, data);
        return texture;
    }

    /**
     * Creates new rectangle texture.
     * @return Texture name.
     */
    public static int genTextureMultisample2D(int width, int height, int samples, int internalformat) {
        int texture = glGenTextures();
        glBindTexture(GL32.GL_TEXTURE_2D_MULTISAMPLE, texture);
        GL32.glTexImage2DMultisample(GL32.GL_TEXTURE_2D_MULTISAMPLE, samples, internalformat, width, height, true);
        return texture;
    }

}
