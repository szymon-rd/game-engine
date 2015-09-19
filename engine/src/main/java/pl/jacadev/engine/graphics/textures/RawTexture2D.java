package pl.jacadev.engine.graphics.textures;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL43;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

/**
 * @author Jaca777
 *         Created 21.12.14 at 13:57
 */
public class RawTexture2D extends Texture{

    protected RawTexture2D(int texture, int width, int height, int internalformat, int format, boolean mipmap) {
        super(GL11.GL_TEXTURE_2D, texture, width, height, internalformat, format, mipmap);
    }

    public RawTexture2D(int width, int height, int internalformat, int format, ByteBuffer data) {
        super(GL11.GL_TEXTURE_2D, Texturing.genTexture2D(internalformat, format, width, height, true, data), width, height, internalformat, format, true);
        enableDefaultParams();
    }
    public RawTexture2D(int width, int height, int internalformat, int format, int[][] params, boolean anisotropy, ByteBuffer data) {
        super(GL11.GL_TEXTURE_2D, Texturing.genTexture2D(internalformat, format, width, height, true, data), width, height, internalformat, format, true);
        enableDefaultParams();
        for(int[] param : params){
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, param[0], param[1]);
        }
        if(anisotropy) Texturing.enableAnisotropy();
    }

    private void enableDefaultParams(){
        GL11.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        GL11.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        GL11.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        GL11.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
    }

    /**
     * Sets the texture.
     * @param data Must be the same size as texture!
     */

    public void set(ByteBuffer data){
        Texturing.setTexture2D(this.width, this.height, this.internalformat, this.format, this.mipmap, data);
    }

    public void copy(RawTexture2D src){
        this.type = src.type;
        this.format = src.format;
        this.internalformat = src.internalformat;
        this.height = src.height;
        this.width = src.width;
        this.mipmap = src.mipmap;
        GL11.glBindTexture(this.type, this.texture);
        GL11.glTexImage2D(this.type, 0, this.getInternalformat(), this.getWidth(), this.getHeight(), 0, this.getFormat(), GL11.GL_UNSIGNED_BYTE, (ByteBuffer) null);
        GL43.glCopyImageSubData(src.getTexture(), src.getType(), 0, 0, 0, 0, this.texture, this.type, 0, 0, 0, 0, src.getWidth(), src.getHeight(), 1);
        GL30.glGenerateMipmap(this.type);
    }

}
