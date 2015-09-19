package pl.jacadev.game.resmanagement.manageables.containers;

import pl.jacadev.game.resmanagement.loading.DataContainer;
import pl.jacadev.game.resmanagement.manageables.ManageableModel;
import pl.jacadev.game.resmanagement.manageables.loaders.texture.TextureArrayDesc;
import pl.jacadev.game.resmanagement.manageables.loaders.model.ModelDesc;
import pl.jacadev.game.resmanagement.manageables.textures.ManageableTexture2DArray;

/**
 * @author Jaca777
 *         Created 31.01.15 at 16:07
 */
public class PhysicalModelDataContainer extends DataContainer {
    private boolean loaded;

    private ManageableModel model;
    private ManageableTexture2DArray texture;
    private ModelDesc modelDesc;
    private TextureArrayDesc textureDesc;

    public void load(ManageableModel model, ManageableTexture2DArray texture) {
        this.model = model;
        this.texture = texture;
        this.loaded = true;
    }

    public ManageableModel getModel() {
        return model;
    }

    public ManageableTexture2DArray getTexture() {
        return texture;
    }

    @Override
    public boolean isLoaded() {
        return loaded && model.isLoaded() && texture.isLoaded();
    }

    public ModelDesc getModelDesc() {
        return modelDesc;
    }

    public TextureArrayDesc getTextureDesc() {
        return textureDesc;
    }
}
