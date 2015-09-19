package pl.jacadev.engine;

/**
 * @author Jaca777
 *         Created 10.05.15 at 01:13
 */
public interface Task {
    void init(EngineContext context);
    void update(long delta);
}
