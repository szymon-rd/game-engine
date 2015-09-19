package pl.jacadev.game.resmanagement.manageables;

import pl.jacadev.game.resmanagement.ResourceManager;
import pl.jacadev.game.resmanagement.loading.DataContainer;
import pl.jacadev.game.resmanagement.manageables.containers.PhysicalModelDataContainer;
import pl.jacadev.game.resmanagement.manageables.loaders.model.ModelDesc;
import pl.jacadev.game.resmanagement.manageables.loaders.physicalmodel.PhysicalModelDesc;
import pl.jacadev.game.resmanagement.manageables.loaders.texture.TextureArrayDesc;
import pl.jacadev.game.world.entities.TexturedModel;
import pl.jacadev.game.resmanagement.Manageable;
import pl.jacadev.game.resmanagement.ResourceManagementException;

/**
 * @author Jaca777
 *         Created 31.01.15 at 15:35
 */
public class ManageableTexturedModel extends TexturedModel implements Manageable{
    private boolean loaded = false;
    private boolean blocked = false;

    private ModelDesc modelDesc;
    private TextureArrayDesc textureDesc;

    private PhysicalModelDesc desc;

    public ManageableTexturedModel(PhysicalModelDesc desc) {
        super(null, null);
        this.desc = desc;
    }

    @Override
    public void load(DataContainer data) {
        if(!blocked) {
            if (!data.isLoaded()) throw new ResourceManagementException("DataContainer is not loaded");
            PhysicalModelDataContainer entityData = (PhysicalModelDataContainer) data;
            this.textureDesc = entityData.getTextureDesc();
            this.modelDesc = entityData.getModelDesc();
            this.model = entityData.getModel();
            this.texture = entityData.getTexture();
        } else blocked = false;
    }

    @Override
    public void unload() {
        if(loaded) {
            ResourceManager.cancelUsage(textureDesc);
            ResourceManager.cancelUsage(modelDesc);
            this.loaded = false;
        } else blocked = true;
    }

    @Override
    public void draw() {
        if(loaded) super.draw();
    }

    @Override
    public boolean isLoaded() {
        return loaded;
    }

    @Override
    public PhysicalModelDesc getDesc() {
        return desc;
    }
}
