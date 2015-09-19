package pl.jacadev.game.world.loading;

import pl.jacadev.engine.graphics.scene.components.DrawableComponent;
import pl.jacadev.game.world.Chunk;

/**
 * @author Jaca777
 *         Created 02.01.15 at 21:09
 *         It's not a Container! Ideologically parent is not resposible for adding and removing componenets.
 *         Moreover chunks have to be encapsulated with #getChunk method to make #ChunksMap more abstract and flexible.
 */
public abstract class ChunksMap extends DrawableComponent {

    /**
     * @return Chunk with the given offset.
     */
    public abstract Chunk getChunk(int x, int y);

    /**
     * Fills chunks map with chunks.
     */
    public abstract void fill();

    /**
     * @return interpolated height at the given point.
     */
    public abstract float getHeight(float x, float y);
}
