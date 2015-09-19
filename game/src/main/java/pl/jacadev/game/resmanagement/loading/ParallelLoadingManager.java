package pl.jacadev.game.resmanagement.loading;

import pl.jacadev.game.resmanagement.ResourceManagementException;

/**
 * @author Jaca777
 *         Created 21.01.15 at 15:27
 */
public class ParallelLoadingManager {

    public static final RequestQueue QUEUE = new RequestQueue();
    public static final RequestProcessor[] PROCESSORS = {new RequestProcessor(QUEUE)};

    public static void queue(LoadRequest request){
        try {
            QUEUE.put(request);
        } catch (InterruptedException e) {
            throw new ResourceManagementException("Thread was interrupted while queueing.");
        }
        request.setState(LoadRequest.RequestState.QUEUED);
    }
}
