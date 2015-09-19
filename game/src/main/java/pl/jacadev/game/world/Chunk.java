package pl.jacadev.game.world;

import pl.jacadev.engine.graphics.OGLThread;
import pl.jacadev.engine.graphics.contexts.OGLEngineContext;
import pl.jacadev.engine.graphics.contexts.RenderingContext;
import pl.jacadev.engine.graphics.scene.components.Container;
import pl.jacadev.engine.graphics.shaders.programs.water.WaterSurface;
import pl.jacadev.game.world.entities.staticentities.StaticEntity;
import pl.jacadev.game.world.terrain.Terrain;
import pl.jacadev.engine.graphics.Unloadable;
import pl.jacadev.engine.graphics.shaders.programs.light.LightProgram;
import pl.jacadev.engine.graphics.shaders.programs.light.Lighting;
import pl.jacadev.engine.graphics.shaders.programs.terrain.TerrainProgram;
import pl.jacadev.engine.graphics.utility.math.MatrixStack;

import java.util.*;

/**
 * @author Jaca777
 *         Created 26.12.14 at 20:00
 */
public class Chunk extends Container<ChunkComponent> implements Unloadable {
    public static final int CHUNK_SIZE = 256;
    public static final int TEXTURES = 50;
    protected int x, y;

    protected Terrain terrain;

    /**
     * The chunk's lighting.
     */
    protected Lighting lighting;


    public Chunk(Terrain terrain, int x, int y) {
        this.terrain = terrain;
        this.x = x;
        this.y = y;
    }

    protected static final Comparator<StaticEntity> MODEL_COMPARATOR = new Comparator<StaticEntity>() {
        @Override
        public int compare(StaticEntity o1, StaticEntity o2) {
            return o1.getModel().hashCode() - o2.getModel().hashCode();
        }
    };

    /**
     * Updates all the chunk components.
     */
    @Override
    public void onUpdate(long delta) {
        for(ChunkComponent component : getChildren()){
            component.onUpdate(delta);
        }
        if(terrain != null) terrain.onUpdate(delta);
    }

    /**
     * Draws all the chunk components.
     */
    public void onDraw(OGLEngineContext context) {
        if(context.getRenderingContext().getRenderingState() == RenderingContext.RenderingState.STANDARD
                || context.getRenderingContext().getRenderingState() == RenderingContext.RenderingState.SHADOW_MAP) drawChunk(context);
        else drawWater(context);
    }

    private void drawChunk(OGLEngineContext context){
        MatrixStack modelMatrix = context.getRenderingContext().getCurrentMatrixStack();
        modelMatrix.push();
        modelMatrix.translate(x * CHUNK_SIZE, 0, -y * CHUNK_SIZE);
        terrain.draw(context);

        StaticEntity pEntity = null;
        for (ChunkComponent component : getChildren())
            if (component instanceof StaticEntity) {
                StaticEntity entity = (StaticEntity) component;
                if (pEntity == null || pEntity.getModel() != entity.getModel())
                    entity.getModel().bind(context.getBindings());
                pEntity = entity;
                pEntity.draw(context);
            }
        modelMatrix.pop();
    }

    private void drawWater(OGLEngineContext context) {
        MatrixStack modelMatrix = context.getRenderingContext().getCurrentMatrixStack();
        modelMatrix.push();
        modelMatrix.translate(x * CHUNK_SIZE, 0, -y * CHUNK_SIZE);
        for (ChunkComponent component : getChildren()) {
            if (component instanceof Water){
                WaterSurface waterSurface = ((Water) component).getSurface();
                waterSurface.sethMap(terrain.getHeightmap().getHeightmapTexture());
                component.draw(context);
            }
        }
        modelMatrix.pop();
    }

    /**
     * @return Interpolated height at the given position.
     */
    public float getHeight(float x, float y) {
        return terrain.getHeight(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public Lighting getLighting() {
        return lighting;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    /**
     * Unloads all the chunk components.
     */
    @Override
    public void unload() {
        terrain.unload();
        /*for(ChunkComponent component : getChildren()){
            if(component instanceof Unloadable) ((Unloadable) component).unload();
        }*/ //TODO Change resource management system.
    }
}
