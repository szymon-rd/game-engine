package pl.jacadev.game.resmanagement.loading;

/**
 * @author Jaca777
 *         Created 15.01.15 at 14:05
 *         TODO Change everything
 */
public class RequestProcessor extends Thread {

    private static final UncaughtExceptionHandler PROCESSOR_UNCAUGHT_EXCEPTION_HANDLER = new UncaughtExceptionHandler() {
        @Override
        public void uncaughtException(Thread t, Throwable e) {
           //TODO Pop out a window or smth.
            e.printStackTrace();
            System.exit(1);
        }
    };

    private RequestQueue queue;
    private boolean processing;

    public RequestProcessor(RequestQueue queue) {
        this.queue = queue;
        this.setUncaughtExceptionHandler(PROCESSOR_UNCAUGHT_EXCEPTION_HANDLER);
        this.start();
    }

    @Override
    public void run() {
        try {
            listen();
        } catch (InterruptedException e) {
            //Do nothing
        }
    }

    @Override
    public synchronized void start() {
        this.processing = true;
        super.start();
    }

    public void interrupt() {
        this.processing = false;
        super.interrupt();
    }


    private void listen() throws InterruptedException {
        while (processing) {
            LoadRequest request = queue.take();
            request.setState(LoadRequest.RequestState.LOADING);
            process(request);
            request.setState(LoadRequest.RequestState.LOADED);
        }
    }


    public void process(LoadRequest request) {
        System.out.println("NEW REQUEST");
        request.getDescriptor().getType().getLoader()
                .load(request.getDest(), request.getDescriptor());
    }

}
