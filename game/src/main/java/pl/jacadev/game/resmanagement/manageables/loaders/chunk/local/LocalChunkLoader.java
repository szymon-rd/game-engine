package pl.jacadev.game.resmanagement.manageables.loaders.chunk.local;

import org.lwjgl.opengl.GL11;
import pl.jacadev.game.resmanagement.ResourceDesc;
import pl.jacadev.game.resmanagement.ResourceType;
import pl.jacadev.game.resmanagement.loading.DataContainer;
import pl.jacadev.game.resmanagement.manageables.containers.TerrainDataContainer;
import pl.jacadev.game.resmanagement.manageables.containers.TextureDataContainer;
import pl.jacadev.game.resmanagement.manageables.loaders.chunk.ChunkDesc;
import pl.jacadev.game.resources.Resources;
import pl.jacadev.game.world.Chunk;
import pl.jacadev.game.world.terrain.HeightmapLoader;
import pl.jacadev.game.resmanagement.Manageable;
import pl.jacadev.game.resmanagement.loading.ResourceLoader;
import pl.jacadev.game.resmanagement.manageables.ManageableChunk;
import pl.jacadev.game.resmanagement.manageables.containers.ChunkDataContainer;
import pl.jacadev.engine.graphics.resources.ImageDecoder;
import pl.jacadev.engine.graphics.resources.PNGDecoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.ParseException;

/**
 * @author Jaca777
 *         Created 30.12.14 at 23:13
 */
public class LocalChunkLoader extends ResourceLoader {
    protected static final String CHUNKS_PATH = "world/chunks/";
    protected static final String CHUNK_FILE = "chunk.dt";
    protected static final String HEIGHTMAP_FILE = "hm.bmp";
    protected static final String TEX_MAP_FILE = "mm.png";

    public void load(DataContainer dest, ResourceDesc descriptor) {
        descriptor.checkType(ResourceType.CHUNK);
        ChunkDesc chunkDesc = (ChunkDesc) descriptor;
        int x = chunkDesc.getX();
        int y = chunkDesc.getY();
        try {
            String chunkPath = CHUNKS_PATH + getChunkPack(x, y);
            ChunkProperties chunkProperties = new ChunkProperties(Resources.getAsStream(chunkPath + CHUNK_FILE));
            //Loads terrain data container.
            TerrainDataContainer terrain = new TerrainDataContainer();
            BufferedImage hmImage = ImageIO.read(Resources.getAsStream(chunkPath + HEIGHTMAP_FILE));
            HeightmapLoader.loadDataContainer(terrain, HeightmapLoader.toHeightArray(hmImage, chunkProperties.maxHeight, chunkProperties.minHeight), 0, 0, hmImage.getWidth());

            //Loads materials map.
            TextureDataContainer materialMap = new TextureDataContainer();
            ImageDecoder.DecodedImage texMap = ImageDecoder.decodePNG(Resources.getAsStream(chunkPath + TEX_MAP_FILE), PNGDecoder.Format.RGBA);
            materialMap.load(texMap.getBuffer(), Chunk.CHUNK_SIZE + 1, Chunk.CHUNK_SIZE + 1, GL11.GL_RGBA, GL11.GL_RGBA);

            ((ChunkDataContainer) dest).load(x, y, materialMap, terrain, chunkProperties.materialSet, chunkProperties.lighting, chunkProperties.staticEntities, chunkProperties.waterCoords, terrain.getQ());

        } catch (IOException e) {
            System.out.println("CHUNK NOT FOUND " + x + " " + y);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getChunkPack(int x, int y) {
        return String.format("c%d_%d/", x, y);
    }

    @Override
    public Manageable newManageable(ResourceDesc desc) {
        return new ManageableChunk((ChunkDesc) desc);
    }

    @Override
    public DataContainer createContainer() {
        return new ChunkDataContainer();
    }
}
