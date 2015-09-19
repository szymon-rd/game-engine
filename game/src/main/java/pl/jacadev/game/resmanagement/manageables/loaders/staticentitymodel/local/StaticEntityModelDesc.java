package pl.jacadev.game.resmanagement.manageables.loaders.staticentitymodel.local;

import pl.jacadev.game.resmanagement.ResourceDesc;
import pl.jacadev.game.resmanagement.ResourceType;

/**
 * @author Jaca777
 *         Created 29.01.15 at 17:40
 */
public class StaticEntityModelDesc extends ResourceDesc {
    private String name;

    public StaticEntityModelDesc(String name) {
        super(ResourceType.STATIC_ENTITY_MODEL);
        this.name = name;
    }

    public String getName() {
        return name;
    }


    @Override
    public int compareTo(ResourceDesc descriptor) {
        if (descriptor.getType() != ResourceType.STATIC_ENTITY_MODEL)
            return descriptor.getType().ordinal() - this.getType().ordinal();
        else return ((StaticEntityModelDesc) descriptor).getName().compareTo(name);
    }
}
