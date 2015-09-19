package pl.jacadev.game.resmanagement.manageables.loaders.texture.local;

import org.lwjgl.opengl.GL11;
import pl.jacadev.game.resmanagement.ResourceDesc;
import pl.jacadev.game.resmanagement.ResourceType;
import pl.jacadev.game.resmanagement.loading.DataContainer;
import pl.jacadev.game.resmanagement.manageables.loaders.texture.TextureArrayDesc;
import pl.jacadev.game.resmanagement.manageables.loaders.texture.TextureDesc;
import pl.jacadev.game.resmanagement.Manageable;
import pl.jacadev.game.resmanagement.ResourceManagementException;
import pl.jacadev.game.resmanagement.loading.ResourceLoader;
import pl.jacadev.game.resmanagement.manageables.containers.TextureDataContainer;
import pl.jacadev.game.resmanagement.manageables.textures.*;
import pl.jacadev.engine.graphics.resources.ImageDecoder;
import pl.jacadev.game.resources.Resources;

import java.io.InputStream;

/**
 * @author Jaca777
 *         Created 22.01.15 at 22:28
 */
public class LocalTextureLoader extends ResourceLoader {
    @Override
    public Manageable newManageable(ResourceDesc desc) {
        desc.checkType(ResourceType.TEXTURE);
        switch (((TextureDesc) desc).getTextureType()) {
            case T_2D:
                return new ManageableTexture2D((TextureDesc) desc);
            case T_RAW_2D:
                return new ManageableRawTexture2D((TextureDesc) desc);
            case T_RECT_2D:
                return new ManageableRectTexture2D((TextureDesc) desc);
            case T_CUBEMAP:
                return new ManageableCubemap((TextureDesc) desc);
            case T_OTHER:
                if(desc instanceof TextureArrayDesc)
                    return new ManageableTexture2DArray((TextureArrayDesc) desc);
                else throw new ResourceManagementException("Unknown T_OTHER descriptor");
            default:
                throw new ResourceManagementException("Unsupported texture type");
        }
    }

    @Override
    public DataContainer createContainer() {
        return new TextureDataContainer();
    }

    //TODO deal with non-RGBA textures
    @Override
    public void load(DataContainer dest, ResourceDesc desc) {
        desc.checkType(ResourceType.TEXTURE);
        TextureDesc textureDesc = (TextureDesc) desc;
        TextureDataContainer textureContainer = (TextureDataContainer) dest;
        switch (textureDesc.getTextureType()) {
            case T_2D:
            case T_RAW_2D:
            case T_RECT_2D:
                ImageDecoder.DecodedImage image = ImageDecoder.decodePNG(Resources.getAsStream(textureDesc.getName()), textureDesc.getFormat());
                textureContainer.load(image.getBuffer(), image.getW(), image.getH(), GL11.GL_RGBA, GL11.GL_RGBA);
                break;
            case T_CUBEMAP:
                ImageDecoder.DecodedImagesArray cubemapArray = ImageDecoder.decodePNGs(Resources.getCubemapStreams(textureDesc.getName(), ".png"), textureDesc.getFormat());
                textureContainer.load(cubemapArray.getBuffers(), cubemapArray.getW(), cubemapArray.getH(), 6, GL11.GL_RGBA, GL11.GL_RGBA);
                break;
            case T_OTHER:
                if(desc instanceof TextureArrayDesc) {
                    TextureArrayDesc arrayDesc = (TextureArrayDesc) textureDesc;
                    String[] names = arrayDesc.getNames();
                    InputStream[] streams = new InputStream[names.length];
                    for(int i = 0; i < names.length; i++){
                        streams[i] = Resources.getAsStream(names[i]);
                    }
                    ImageDecoder.DecodedImagesArray imagesArray = ImageDecoder.decodePNGs(streams, textureDesc.getFormat());
                    textureContainer.load(imagesArray.getBuffers(), imagesArray.getW(), imagesArray.getH(), names.length, GL11.GL_RGBA, GL11.GL_RGBA);
                }else throw new ResourceManagementException("Unknown T_OTHER descriptor: " + desc.getClass());
                break;
            default:
                throw new ResourceManagementException("Unsupported texture type");
        }
    }
}
