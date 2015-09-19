package pl.jacadev.game.world;

import pl.jacadev.engine.graphics.shaders.programs.water.WaterSurface;
import pl.jacadev.engine.graphics.contexts.OGLEngineContext;

/**
 * @author Jaca777
 *         Created 06.02.15 at 19:46
 */
public class Water extends ChunkComponent{
    private WaterSurface surface;

    public Water(WaterSurface surface) {
        super(surface.getPos());
        this.surface = surface;
    }

    public void onDraw(OGLEngineContext context) {
        surface.draw(context);
    }

    public void unload(){
        surface.unload();
    }

    @Override
    public void onUpdate(long delta) {
        surface.onUpdate(delta);
    }

    public WaterSurface getSurface() {
        return surface;
    }
}
