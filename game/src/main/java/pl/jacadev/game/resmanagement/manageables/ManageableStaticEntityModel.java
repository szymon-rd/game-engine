package pl.jacadev.game.resmanagement.manageables;

import pl.jacadev.engine.graphics.contexts.Bindings;//ale jebaæ tox d
import pl.jacadev.game.resmanagement.ResourceDesc;
import pl.jacadev.game.resmanagement.ResourceManager;
import pl.jacadev.game.resmanagement.loading.DataContainer;
import pl.jacadev.game.resmanagement.manageables.containers.StaticEntityModelDataContainer;
import pl.jacadev.game.resmanagement.manageables.loaders.model.ModelDesc;
import pl.jacadev.game.resmanagement.manageables.loaders.staticentitymodel.local.StaticEntityModelDesc;
import pl.jacadev.game.resmanagement.manageables.loaders.texture.TextureArrayDesc;
import pl.jacadev.game.world.entities.staticentities.worldcomponents.StaticEntityModel;
import pl.jacadev.game.resmanagement.Manageable;
import pl.jacadev.game.resmanagement.ResourceManagementException;

/**
 * @author Jaca777
 *         Created 29.01.15 at 17:42
 */
public class ManageableStaticEntityModel extends StaticEntityModel implements Manageable{

    private boolean loaded = false;
    private boolean blocked = false;//zg³upai³em xd

    private ModelDesc modelDesc;
    private TextureArrayDesc textureDesc;
    private StaticEntityModelDesc desc;

    public ManageableStaticEntityModel(StaticEntityModelDesc desc) {
        super(null, null, -1, -1);
        this.desc = desc;
    }

    @Override
    public void load(DataContainer data) {
        if(!blocked) {
            if (!data.isLoaded()) throw new ResourceManagementException("DataContainer is not loaded");
            StaticEntityModelDataContainer entityData = (StaticEntityModelDataContainer) data;
            this.model = entityData.getModel();
            this.texture = entityData.getTexture();
            this.textureOffset = entityData.getTexOff();
            this.modelDesc = entityData.getModel().getDesc();
            this.textureDesc = entityData.getTexture().getDesc();
            this.loaded = true;
        } else blocked = false;
    }
    @Override
    public void unload() {
        if(loaded) {
            ResourceManager.cancelUsage(modelDesc);
            ResourceManager.cancelUsage(textureDesc);
            this.loaded = false;
        } else blocked = true;
    }

    @Override
    public void draw() {
        if(loaded) super.draw();
    }

    @Override
    public void bind(Bindings bindings) {
        if(loaded) super.bind(bindings);
    }

    @Override
    public void finalizeRendering() {
        if(loaded) super.finalizeRendering();
    }

    @Override
    public void render() {
        if(loaded) super.render();
    }

    @Override
    public boolean isLoaded() {
        return loaded;
    }

    @Override
    public ResourceDesc getDesc() {
        return desc;
    }
}
