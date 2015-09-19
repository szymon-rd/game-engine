package pl.jacadev.game.resmanagement;

/**
 * @author Jaca777
 *         Created 17.01.15 at 20:27
 */
public abstract class ResourceDesc implements Comparable<ResourceDesc> {
    private ResourceType type;

    public ResourceDesc(ResourceType type) {
        this.type = type;
    }

    public ResourceType getType() {
        return type;
    }

    @Override
    public abstract int compareTo( ResourceDesc descriptor);

    public void checkType(ResourceType type){
        if(type != this.type) throw new ResourceManagementException("Wrong descriptor type. Expected: " + type.name() + " Given: " + this.type.name());
    }

}
