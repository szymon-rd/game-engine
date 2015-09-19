package pl.jacadev.game.world.entities;


import org.lwjgl.util.vector.Vector3f;
import pl.jacadev.engine.graphics.contexts.OGLEngineContext;
import pl.jacadev.engine.graphics.contexts.RenderingContext;

/**
 * @author Jaca777
 *         Created 30.12.14 at 23:27
 */
public class Player extends PhysicalEntity {

    public Player(Vector3f pos) {
        super(pos);
    }

    @Override
    public void onUpdate(long delta) {

    }



    @Override
    public void onDraw(OGLEngineContext engineContext) {
        if(engineContext.getRenderingContext().getRenderingState() == RenderingContext.RenderingState.STANDARD) {
            //Do nothing
        }
    }
}
