package pl.jacadev.engine.graphics.shaders.effects;

import pl.jacadev.engine.graphics.textures.Texture;

/**
 * @author Jaca777
 *         Created 2015-08-13 at 12
 */
public interface Filter<T extends Texture> {
    void apply(T texture);
    void apply(T src, T dest);
}
