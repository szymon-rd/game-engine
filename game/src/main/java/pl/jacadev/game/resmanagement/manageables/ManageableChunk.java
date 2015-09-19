package pl.jacadev.game.resmanagement.manageables;

import org.lwjgl.util.vector.Vector3f;
import pl.jacadev.engine.graphics.contexts.OGLEngineContext;
import pl.jacadev.game.resmanagement.ResourceManager;
import pl.jacadev.game.resmanagement.loading.DataContainer;
import pl.jacadev.game.resmanagement.manageables.containers.ChunkDataContainer;
import pl.jacadev.game.resmanagement.manageables.loaders.chunk.ChunkDesc;
import pl.jacadev.game.resmanagement.manageables.loaders.staticentitymodel.local.StaticEntityModelDesc;
import pl.jacadev.game.world.Chunk;
import pl.jacadev.game.world.Water;
import pl.jacadev.game.world.entities.staticentities.StaticEntity;
import pl.jacadev.game.world.entities.staticentities.worldcomponents.StaticEntityModel;
import pl.jacadev.game.world.terrain.Heightmap;
import pl.jacadev.game.world.terrain.MaterialsMap;
import pl.jacadev.game.world.terrain.Terrain;
import pl.jacadev.game.resmanagement.Manageable;
import pl.jacadev.game.resmanagement.ResourceManagementException;
import pl.jacadev.engine.graphics.shaders.programs.water.WaterSurface;

import java.util.Arrays;

/**
 * @author Jaca777
 *         Created 15.01.15 at 14:22
 */
public class ManageableChunk extends Chunk implements Manageable {
    private boolean loaded = false;
    private boolean blocked = false;

    private ChunkDesc desc;

    public ManageableChunk(ChunkDesc desc) {
        super(null, 0, 0);
        this.desc = desc;
    }

    @Override
    public void draw(OGLEngineContext context) {
        if(loaded) super.draw(context);
    }

    public void load(DataContainer data){
        if(!blocked) {
            if (!data.isLoaded()) throw new ResourceManagementException("DataContainer is not loaded");
            ChunkDataContainer chunkContainer = (ChunkDataContainer) data;
            Heightmap hm = new Heightmap(
                    chunkContainer.getTerrain().getVertices(),
                    chunkContainer.getTerrain().getTexCoords(),
                    chunkContainer.getTerrain().getNormals(),
                    chunkContainer.getTerrain().getIndices(),
                    chunkContainer.getTerrain().getIndicesAmount(),
                    chunkContainer.getTerrain().getHarray(),
                    chunkContainer.getTerrain().getQ());
            MaterialsMap materialsMap = new MaterialsMap(
                    chunkContainer.getMaterialMap().getW(),
                    chunkContainer.getMaterialMap().getH(),
                    chunkContainer.getMaterialMap().getData()[0]
            );
            this.terrain = new Terrain(hm, chunkContainer.getMaterialSet(), materialsMap);
            this.x = chunkContainer.getX();
            this.y = chunkContainer.getY();

            StaticEntity[] entities = chunkContainer.getStaticEntities();
            for(StaticEntity entity : entities) if(entity.isCalcY()) entity.setY(entity.getY() + terrain.getHeight(entity.getX(), entity.getZ()));
            StaticEntity[] staticEntities = chunkContainer.getStaticEntities();
            Arrays.sort(staticEntities, MODEL_COMPARATOR);
            getChildren().addAll(staticEntities);

            Water[] waterArray = new Water[chunkContainer.getWtrCoords().length / 4];
            for(int i = 0; i < waterArray.length; i++){
                waterArray[i] = new Water(new WaterSurface(
                        chunkContainer.getWtrCoords()[i * 4],
                        chunkContainer.getWtrCoords()[i * 4 + 1],
                        chunkContainer.getWtrCoords()[i * 4 + 2],
                        chunkContainer.getWtrCoords()[i * 4 + 3]));
            }
            getChildren().addAll(waterArray);
            this.loaded = true;
        }else blocked = false;

/*
       StaticEntityModel mushroom = (StaticEntityModel) ResourceManager.use(new StaticEntityModelDesc("mushroom"));
        final int i = 100;
        for(int x = 0; x < i; x++)
            for(int y = 0; y < i;  y++)
                getChildren().add(new StaticEntity(mushroom, new Vector3f(((float)x/i) * CHUNK_SIZE, getHeight(((float)x/i) * CHUNK_SIZE,((float)y/i) * CHUNK_SIZE), ((float)y/i) * CHUNK_SIZE), false));
*/


    }

    @Override
    public void unload() {
        if(loaded) {
            super.unload();
            ResourceManager.cancelUsage(((ManageableMaterialSet) this.terrain.getMaterials()).getDesc());
            this.loaded = false;
        } else blocked = true;
    }

    @Override
    public boolean isLoaded() {
        return loaded;
    }

    @Override
    public ChunkDesc getDesc() {
        return desc;
    }
}
