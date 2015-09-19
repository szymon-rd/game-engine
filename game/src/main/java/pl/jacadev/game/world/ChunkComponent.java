package pl.jacadev.game.world;

import org.lwjgl.util.vector.Vector3f;
import pl.jacadev.game.world.entities.PhysicalEntity;

/**
 * @author Jaca777
 *         Created 2015-07-14 at 19
 */
public abstract class ChunkComponent extends PhysicalEntity{
    public ChunkComponent(Vector3f pos) {
        super(pos);
    }
}
