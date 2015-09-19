package pl.jacadev.game.resmanagement;

import pl.jacadev.engine.EngineContext;
import pl.jacadev.engine.Task;

import java.util.*;

/**
 * @author Jaca777
 *         Created 20.01.15 at 20:48
 */
public class ResourceManager {

    private static Map<ResourceDesc, ResourceController> resources = new TreeMap<>();
    private static Set<ResourceController> unloadedResources = Collections.synchronizedSet(new HashSet<ResourceController>());

    static final Object mutex = new Object();

    /**
     * Does all finding, loading and controlling stuff, and returns described resource.
     *
     * @param desc Description of the wanted resource.
     * @return Described resource.
     */
    public static Manageable use(ResourceDesc desc) {
        if (isManaged(desc)) return resources.get(desc).newUsage();
        else {
            Manageable m = desc.getType().getLoader().newManageable(desc);
            ResourceController controller = new ResourceController(desc, m);
            controller.newUsage();
            resources.put(desc, controller);
            return m;
        }
    }

    /**
     * Reduces amount of usages of the described resource. If there are no usages, unloads it.
     *
     * @param desc
     */
    public static void cancelUsage(ResourceDesc desc) {
        if (isManaged(desc)) resources.get(desc).cancelUsage();
        else throw new ResourceManagementException("Described resource is not controlled");
    }

    /**
     * Finalizes loading of unloaded resources. Must be called in the OGL thread!
     */
    public static void update() {
        synchronized (mutex) {
            Iterator<ResourceController> iterator = unloadedResources.iterator();
            while (iterator.hasNext()) if (iterator.next().finalizeLoading()) iterator.remove();
        }
    }


    static void addUnloaded(ResourceController cntrl) {
        synchronized (mutex) {
            unloadedResources.add(cntrl);
        }
    }

    private static boolean isManaged(ResourceDesc desc) {
        if(desc == null) return false;
        else return resources.containsKey(desc);
    }

    public static final Task RESOURCE_TASK = new Task() {
        @Override
        public void init(EngineContext context) {
            //
        }

        @Override
        public void update(long delta) {
            ResourceManager.update();
        }
    };

}
