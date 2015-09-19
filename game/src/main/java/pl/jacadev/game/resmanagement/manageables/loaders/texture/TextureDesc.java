package pl.jacadev.game.resmanagement.manageables.loaders.texture;

import pl.jacadev.game.resmanagement.ResourceType;
import pl.jacadev.game.resmanagement.ResourceDesc;
import pl.jacadev.engine.graphics.resources.PNGDecoder;

/**
 * @author Jaca777
 *         Created 22.01.15 at 22:31
 */
public class TextureDesc extends ResourceDesc {

    public static enum TextureType {
        T_2D, T_RAW_2D, T_RECT_2D, T_CUBEMAP, T_OTHER
    }


    private String name;
    private TextureType texType;
    private PNGDecoder.Format format;

    public TextureDesc(String name, TextureType type, PNGDecoder.Format format) {
        super(ResourceType.TEXTURE);
        this.name = name;
        this.texType = type;
        this.format = format;
    }

    @Override
    public int compareTo(ResourceDesc descriptor) {
        if(!(descriptor instanceof TextureDesc)) return descriptor.getType().ordinal() - getType().ordinal();
        else return ((TextureDesc) descriptor).getName().compareTo(this.name) * 6 + texType.ordinal();
    }

    public String getName() {
        return name;
    }

    public TextureType getTextureType() {
        return texType;
    }

    public PNGDecoder.Format getFormat() {
        return format;
    }
}
