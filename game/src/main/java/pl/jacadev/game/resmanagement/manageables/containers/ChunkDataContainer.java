package pl.jacadev.game.resmanagement.manageables.containers;

import org.lwjgl.util.vector.Vector2f;
import pl.jacadev.game.resmanagement.loading.DataContainer;
import pl.jacadev.game.world.entities.staticentities.StaticEntity;
import pl.jacadev.engine.graphics.shaders.programs.light.Lighting;
import pl.jacadev.engine.graphics.shaders.programs.terrain.MaterialSet;

/**
 * @author Jaca777
 *         Created 21.01.15 at 15:44
 */
public class ChunkDataContainer extends DataContainer {

    private int x, y;
    private TextureDataContainer materialMap;
    private TerrainDataContainer terrain;
    private MaterialSet materialSet;
    private Lighting lighting;
    private StaticEntity[] staticEntities;
    private Vector2f[] wtrCoords;
    private float q;

    public void load(int x, int y, TextureDataContainer materialMap, TerrainDataContainer terrain, MaterialSet materialSet, Lighting lighting, StaticEntity[] staticEntities, Vector2f[] wtrCoords, float q) {
        this.x = x;
        this.y = y;
        this.materialMap = materialMap;
        this.terrain = terrain;
        this.materialSet = materialSet;
        this.lighting = lighting;
        this.staticEntities = staticEntities;
        this.wtrCoords = wtrCoords;
        this.q = q;
    }


    @Override
    public boolean isLoaded() {
        return (materialMap != null && terrain != null) && materialMap.isLoaded() && terrain.isLoaded();
    }

    public TextureDataContainer getMaterialMap() {
        return materialMap;
    }

    public TerrainDataContainer getTerrain() {
        return terrain;
    }

    public Lighting getLighting() {
        return lighting;
    }

    public MaterialSet getMaterialSet() {
        return materialSet;
    }

    public StaticEntity[] getStaticEntities() {
        return staticEntities;
    }

    public Vector2f[] getWtrCoords() {
        return wtrCoords;
    }

    public float getQ() {
        return q;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
