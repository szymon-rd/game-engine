package pl.jacadev.game.world.entities;

import org.lwjgl.util.vector.Vector3f;
import pl.jacadev.engine.graphics.scene.components.threedim.Component3D;

/**
 * @author Jaca777
 *         Created 30.12.14 at 23:29
 */
public abstract class PhysicalEntity extends Component3D {
    
    public PhysicalEntity(Vector3f pos) {
        super(pos);
    }

    @Override
    public void onUpdate(long delta) {
        //TODO
    }
}
