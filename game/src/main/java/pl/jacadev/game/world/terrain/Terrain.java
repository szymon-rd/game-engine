package pl.jacadev.game.world.terrain;

import pl.jacadev.engine.graphics.OGLThread;
import pl.jacadev.engine.graphics.Unloadable;
import pl.jacadev.engine.graphics.contexts.OGLEngineContext;
import pl.jacadev.engine.graphics.contexts.RenderingContext;
import pl.jacadev.engine.graphics.scene.components.DrawableComponent;
import pl.jacadev.engine.graphics.shaders.programs.light.LightProgram;
import pl.jacadev.engine.graphics.shaders.programs.shadow.ShadowProgram;
import pl.jacadev.engine.graphics.shaders.programs.terrain.MaterialSet;
import pl.jacadev.engine.graphics.shaders.programs.terrain.TerrainProgram;
import pl.jacadev.engine.graphics.utility.math.MatrixStack;
import pl.jacadev.game.world.entities.staticentities.StaticEntity;

/**
 * @author Jaca777
 *         Created 28.12.14 at 16:24
 *         Represents quadratic piece of terrain.
 */
public class Terrain extends DrawableComponent implements Unloadable {

    private Heightmap heightmap;

    /**
     * Materials used on this terrain.
     */
    private MaterialSet materials;

    /**
     * Map (texture) of materials.
     */
    private MaterialsMap map;

    public Terrain(Heightmap heightmap, MaterialSet materials, MaterialsMap map) {
        this.heightmap = heightmap;
        this.materials = materials;
        this.map = map;
    }

    @Override
    public void onUpdate(long delta) {

    }

    public void onDraw(OGLEngineContext context) {
        MatrixStack modelMatrix = context.getRenderingContext().getCurrentMatrixStack();
        if(context.getRenderingContext().getRenderingState() == RenderingContext.RenderingState.STANDARD) {
            TerrainProgram tShader = context.getShadingContext().getTerrainProgram();
            tShader.use();
            tShader.setUniformMatrix4(tShader.uMMatrix, modelMatrix.top());
            tShader.setUniformMatrix4(tShader.uRotMatrix, modelMatrix.rot());
            tShader.useMaterialMap(map, context.getBindings());
            tShader.useMaterialSet(materials, context.getBindings());
            heightmap.draw();
        } else if(context.getRenderingContext().getRenderingState() == RenderingContext.RenderingState.SHADOW_MAP){
            ShadowProgram shadowProgram = context.getShadingContext().getShadowProgram();
            shadowProgram.use();
            shadowProgram.setUniformMatrix4(shadowProgram.uMMatrix, modelMatrix.top());
            heightmap.draw();
        }
    }

    public float getHeight(float x, float y) {
        return heightmap.getHeight(x, y);
    }

    @Override
    public void unload() {
        heightmap.unload();
        map.unload();
    }

    public MaterialSet getMaterials() {
        return materials;
    }

    public Heightmap getHeightmap() {
        return heightmap;
    }
}
