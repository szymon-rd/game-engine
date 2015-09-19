package pl.jacadev.game.resmanagement;

import pl.jacadev.game.resmanagement.loading.DataContainer;
import pl.jacadev.game.resmanagement.loading.LoadRequest;

/**
 * @author Jaca777
 *         Created 14.01.15 at 18:42
 */
public class ResourceController {

    /**
     * Amount of uses of controlled resource.
     */
    protected volatile int uses = 0;

    private ResourceDesc descriptor;
    private Manageable resource;
    private DataContainer data;

    public ResourceController(ResourceDesc descriptor, Manageable resource) {
        this.descriptor = descriptor;
        this.resource = resource;
    }

    /**
     * Increases amount of usages, and, if <code>uses</code> is equal to zero, loads resource.
     *
     * @return Controlled manageable.
     */
    public Manageable newUsage() {
        if (uses++ == 0) {
            this.data = LoadRequest.createAndQueue(descriptor).getDest();
            ResourceManager.addUnloaded(this);
        }
        return resource;
    }

    /**
     * Decreases amount of usages, and, if <code>uses</code> is equal to zero, unloads resource.
     */
    public synchronized void cancelUsage() {
        if (uses == 0) throw new ResourceManagementException("Resource is not loaded");
        else if (uses-- == 1) {
            resource.unload();
        }
    }

    boolean finalizeLoading() {
        if (data == null || !data.isLoaded()) return false;
        else {
            resource.load(data);
            this.data = null; //Leaves data for gc.
            return true;
        }
    }
}
