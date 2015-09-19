package pl.jacadev.engine;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author Jaca777
 *         Created 10.05.15 at 01:17
 */
public abstract class EngineThread extends Thread {

    /**
     * Tasks running in this thread.
     */
    protected List<Task> tasks = new LinkedList<>();

    /**
     * Queue of tasks to be run in this thread.
     */
    protected Queue<Runnable> runnableQueue = new ConcurrentLinkedQueue<>();

    /**
     * Determines whether the loop is running.
     */
    protected volatile boolean running = false;

    /**
     * Schedules loop iterations.
     */
    protected Timer timer;


    public EngineThread(Timer timer) {
        this.timer = timer;
    }

    @Override
    public void run() {
        init();
        initModules();
        this.running = true;
        loop();
    }

    protected abstract void init();

    protected abstract EngineContext getContext();

    protected void initModules() {
        EngineContext engineContext = getContext();
        for (Task task : tasks) task.init(engineContext);
    }

    protected void loop() {
        while (running) {
            long delta = timer.getDelta();
            onIteration(delta);
            for (Task task : tasks) task.update(delta);
            while (!runnableQueue.isEmpty()) { //TODO optimize
                runnableQueue.remove().run();
            }
            timer.await();
        }
    }

    protected abstract void onIteration(long delta);

    @Override
    public void interrupt() {
        super.interrupt();
        this.running = false;
    }

    /**
     * Binds a task to thread. Ordering of binding is guaranteed to be equal to the ordering of calling.
     */
    public void bind(Task task) {
        if (!running) tasks.add(task);
        else throw new IllegalStateException("Engine thread is running");
    }

    /**
     * Queues a runnable to execute with the engine thread.
     */
    public void runLater(Runnable runnable) {
        runnableQueue.add(runnable);
    }

}
