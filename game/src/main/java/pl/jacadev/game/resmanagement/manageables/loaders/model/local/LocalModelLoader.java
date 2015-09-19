package pl.jacadev.game.resmanagement.manageables.loaders.model.local;

import pl.jacadev.game.resmanagement.Manageable;
import pl.jacadev.game.resmanagement.ResourceDesc;
import pl.jacadev.game.resmanagement.ResourceType;
import pl.jacadev.game.resmanagement.loading.DataContainer;
import pl.jacadev.game.resmanagement.manageables.ManageableModel;
import pl.jacadev.game.resmanagement.manageables.loaders.model.ModelDesc;
import pl.jacadev.game.resources.Resources;
import pl.jacadev.game.resmanagement.loading.ResourceLoader;
import pl.jacadev.game.resmanagement.manageables.containers.ModelDataContainer;
import pl.jacadev.game.resmanagement.manageables.loaders.model.ObjLoader;

/**
 * @author Jaca777
 *         Created 08.01.15 at 20:00
 */
public class LocalModelLoader extends ResourceLoader {


    @Override
    public void load(DataContainer dest, ResourceDesc descriptor) {
        descriptor.checkType(ResourceType.MODEL);
        ObjLoader.read(Resources.getAsStream(((ModelDesc) descriptor).getPath())).store((ModelDataContainer) dest);
    }

    @Override
    public Manageable newManageable(ResourceDesc desc) {
        return new ManageableModel((ModelDesc) desc);
    }

    @Override
    public DataContainer createContainer() {
        return new ModelDataContainer();
    }


}
