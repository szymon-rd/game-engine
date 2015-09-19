package pl.jacadev.game.resmanagement.loading;

import pl.jacadev.game.resmanagement.Manageable;
import pl.jacadev.game.resmanagement.ResourceDesc;

/**
 * @author Jaca777
 *         Created 20.01.15 at 23:59
 */
public abstract class ResourceLoader {
    public abstract Manageable newManageable(ResourceDesc desc);
    public abstract DataContainer createContainer();
    public abstract void load(DataContainer dest, ResourceDesc desc);


}
