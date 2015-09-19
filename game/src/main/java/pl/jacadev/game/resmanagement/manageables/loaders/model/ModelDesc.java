package pl.jacadev.game.resmanagement.manageables.loaders.model;

import pl.jacadev.game.resmanagement.ResourceDesc;
import pl.jacadev.game.resmanagement.ResourceType;

/**
 * @author Jaca777
 *         Created 19.01.15 at 16:58
 */
public class ModelDesc extends ResourceDesc {
    private String path;

    public ModelDesc(String path) {
        super(ResourceType.MODEL);
        this.path = path;
    }

    @Override
    public int compareTo(ResourceDesc descriptor) {
        if(!(descriptor instanceof ModelDesc)) return descriptor.getType().ordinal() - getType().ordinal();
        else return ((ModelDesc) descriptor).getPath().compareTo(this.path);
    }

    public String getPath() {
        return path;
    }
}
