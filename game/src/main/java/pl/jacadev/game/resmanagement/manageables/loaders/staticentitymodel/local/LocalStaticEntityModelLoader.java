package pl.jacadev.game.resmanagement.manageables.loaders.staticentitymodel.local;

import pl.jacadev.game.resmanagement.Manageable;
import pl.jacadev.game.resmanagement.ResourceDesc;
import pl.jacadev.game.resmanagement.ResourceManager;
import pl.jacadev.game.resmanagement.ResourceType;
import pl.jacadev.game.resmanagement.manageables.ManageableModel;
import pl.jacadev.game.resmanagement.manageables.loaders.texture.TextureArrayDesc;
import pl.jacadev.game.resmanagement.loading.DataContainer;
import pl.jacadev.game.resmanagement.loading.ResourceLoader;
import pl.jacadev.game.resmanagement.manageables.ManageableStaticEntityModel;
import pl.jacadev.game.resmanagement.manageables.containers.StaticEntityModelDataContainer;
import pl.jacadev.game.resmanagement.manageables.loaders.model.ModelDesc;
import pl.jacadev.game.resmanagement.manageables.loaders.staticentitymodel.StaticEntityModelProperties;
import pl.jacadev.game.resmanagement.manageables.textures.ManageableTexture2DArray;
import pl.jacadev.game.resources.Resources;
import pl.jacadev.engine.graphics.resources.PNGDecoder;

import java.io.IOException;
import java.text.ParseException;

/**
 * @author Jaca777
 *         Created 29.01.15 at 17:41
 */
public class LocalStaticEntityModelLoader extends ResourceLoader{
    private static final String DT_FILE = "se.dt";
    private static final String STATIC_ENTITIES_PATH = "world/staticentities/";

    @Override
    public Manageable newManageable(ResourceDesc desc) {
        return new ManageableStaticEntityModel((StaticEntityModelDesc) desc);
    }

    @Override
    public DataContainer createContainer() {
        return new StaticEntityModelDataContainer();
    }

    @Override
    public void load(DataContainer dest, ResourceDesc desc) {
        try {
            desc.checkType(ResourceType.STATIC_ENTITY_MODEL);
            StaticEntityModelDesc entityDesc = (StaticEntityModelDesc) desc;
            String entityPath = STATIC_ENTITIES_PATH + entityDesc.getName() + "/";

            StaticEntityModelProperties entityProperties = new StaticEntityModelProperties(Resources.getAsStream(entityPath + DT_FILE));
            ManageableModel model = (ManageableModel) ResourceManager.use(new ModelDesc(entityPath + "model.obj"));
            ManageableTexture2DArray texture = (ManageableTexture2DArray) ResourceManager.use(new TextureArrayDesc(new String[]{entityPath + "tex0.png"}, PNGDecoder.Format.RGBA)); //TODO Moar textures, moar.
            ((StaticEntityModelDataContainer) dest).load(model, texture, entityProperties.getTexOff(), entityProperties.getLevel());
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

    }
}
