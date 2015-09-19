package pl.jacadev.game.resmanagement.loading;

import pl.jacadev.game.resmanagement.ResourceDesc;

/**
 * @author Jaca777
 *         Created 20.01.15 at 13:45
 */
public class LoadRequest {

    public static enum RequestState{
        UNLOADED,QUEUED,LOADING,LOADED
    }

    private ResourceDesc descriptor;
    private DataContainer dest;
    private RequestState state;

    public LoadRequest(ResourceDesc descriptor, DataContainer dest) {
        this.descriptor = descriptor;
        this.dest = dest;
        this.state = RequestState.UNLOADED;
    }

    public RequestState getState() {
        return state;
    }

   public ResourceDesc getDescriptor() {
        return descriptor;
    }

    public DataContainer getDest() {
        return dest;
    }

    void setState(RequestState state) {
        this.state = state;
    }

    public static LoadRequest createAndQueue(ResourceDesc desc){
        DataContainer dest = desc.getType().getLoader().createContainer();
        LoadRequest request = new LoadRequest(desc, dest);
        ParallelLoadingManager.queue(request);
        return request;
    }
}
