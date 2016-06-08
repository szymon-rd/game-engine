package pl.jacadev.game.resmanagement.manageables;

import org.lwjgl.util.vector.Vector3f;
import pl.jacadev.game.resmanagement.ResourceManager;
import pl.jacadev.game.world.entities.staticentities.StaticEntity;
import pl.jacadev.game.world.entities.staticentities.worldcomponents.StaticEntityModel;

/**
 * @author Jaca777
 *         Created 05.02.15 at 18:02
 */
public class ManageableStaticEntity extends StaticEntity {
    public ManageableStaticEntity(StaticEntityModel model, Vector3f pos, boolean calcY) {
        super(model, pos, calcY);
    }

    @Override
    public void unload() {
        ResourceManager.cancelUsage(((ManageableStaticEntityModel) model).getDesc());
    }
}
