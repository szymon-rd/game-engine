package pl.jacadev.game.resmanagement;

import pl.jacadev.game.resmanagement.loading.DataContainer;
import pl.jacadev.engine.graphics.Unloadable;

/**
 * @author Jaca777
 *         Created 20.01.15 at 20:53
 */
public interface Manageable extends Unloadable{
    /**
     * Loads data to graphics card memory.
     */
    public void load(DataContainer data);

    /**
     * @return Whether the manageable resource in stored on graphics card memory and/or is ready to use.
     */
    public boolean isLoaded();

    /**
     * @return Description of manageable resource.
     */
    public ResourceDesc getDesc();
}
