package pl.jacadev.game.world.terrain;

import pl.jacadev.engine.graphics.contexts.OGLEngineContext;

/**
 * @author Jaca777
 *         Created 02.01.15 at 21:03
 *         Empty terrain.
 */
public class VoidTerrain extends Terrain {
    public static Terrain VOID_TERRAIN = new VoidTerrain();

    private VoidTerrain() {
        super(null, null, null);
    }

    @Override
    public void draw(OGLEngineContext context) {
        //Do nothing
    }

    @Override
    public float getHeight(float x, float y) {
        return 0f;
    }

    @Override
    public void onUpdate(long delta) {
        //Do nothing.
    }
}
