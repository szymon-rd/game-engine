package pl.jacadev.engine.graphics.textures;

import org.lwjgl.opengl.GL11;
import pl.jacadev.engine.graphics.Bindable;
import pl.jacadev.engine.graphics.Unloadable;
import pl.jacadev.engine.graphics.contexts.Bindings;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

/**
 * @author Jaca777
 *         Created 20.12.14 at 15:10
 */
public abstract class Texture implements Unloadable, Bindable {
    protected int type,
        texture,
        internalformat,
        format;
    protected int width,
        height;
    protected boolean mipmap;

    protected Texture(int type, int texture, int width, int height, int internalformat, int format, boolean mipmap) {
        this.type = type;
        this.texture = texture;
        this.width = width;
        this.height = height;
        this.internalformat = internalformat;
        this.format = format;
        this.mipmap = mipmap;
    }

    /**
     * Binds the texture.
     */
    @Override
    public void bind(Bindings bindings){
        bindings.bindTexture(this.type, this.texture);
    }

    /**
     * Resizes texture.
     * @param w new width
     * @param h new height
     */
    public void resize(int w, int h) {
        this.width = w;
        this.height = h;
        glTexImage2D(this.type, 0, this.internalformat, w, h, 0,
                this.format, GL11.GL_BYTE, (ByteBuffer) null);
        if(mipmap) genMipmap();
    }

    public void genMipmap(){
        glGenerateMipmap(this.type);
    }

    /**
     * @return Texture's type.
     */
    public int getType() {
        return type;
    }

    /**
     * @return Texture's name.
     */
    public int getTexture() {
        return texture;
    }

    /**
     * @return Internal format of the texture. e.g. RGBA16
     */
    public int getInternalformat() {
        return internalformat;
    }

    /**
     * @return Format of the texture. e.g. RGBA
     */
    public int getFormat() {
        return format;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /**
     * @return Whether the texture is mipmaped.
     */
    public boolean isMipmaped() {
        return mipmap;
    }

    /**
     * Unloads the texture.
     */
    public void unload() {
        GL11.glDeleteTextures(texture);
    }

}
