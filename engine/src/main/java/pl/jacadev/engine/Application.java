package pl.jacadev.engine;

/**
 * @author Jaca777
 *         Created 2015-07-14 at 15
 */
public abstract class Application {
    public abstract void start(Stage stage);
    public void launch(){
        start(new Stage());
    }
}
