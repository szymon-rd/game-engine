package pl.jacadev.game.world;

import pl.jacadev.engine.graphics.contexts.OGLEngineContext;
import pl.jacadev.engine.graphics.scene.components.DrawableComponent;
import pl.jacadev.game.world.loading.ChunksMap;
import pl.jacadev.game.world.entities.Player;
import pl.jacadev.game.world.loading.LimitedChunksMap;

/**
 * @author Jaca777
 *         Created 26.12.14 at 20:00
 *         Contains all chunks, player, entities, etc. World component is not used for drawing GUI. Do not use as a root component - not a Container.
 */
public class World extends DrawableComponent {

    private ChunksMap chunksMap;
    private Player player;

    public World(Player player) {
        this.player = player;
        this.chunksMap = new LimitedChunksMap(0, 0, player);
    }

    /**
     * Updates world.
     */
    @Override
    public void onUpdate(long delta) {
        player.onUpdate(delta);
        chunksMap.onUpdate(delta);
    }

    /**
     * Draws the world.
     */
    public void onDraw(OGLEngineContext context) {
        player.draw(context);
        chunksMap.draw(context);
    }

    /**
     * @return Interpolated height at the given point.
     */
    public float getHeight(float x, float z){
        return chunksMap.getHeight(x,z);
    }

    /**
     * @return Map of chunks.
     */
    public ChunksMap getChunksMap() {
        return chunksMap;
    }
}
