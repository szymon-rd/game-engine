package pl.jacadev.game.world.loading;

import pl.jacadev.engine.graphics.contexts.OGLEngineContext;
import pl.jacadev.game.resmanagement.ResourceManager;
import pl.jacadev.game.resmanagement.manageables.loaders.chunk.ChunkDesc;
import pl.jacadev.game.world.Chunk;
import pl.jacadev.game.world.entities.Player;

import java.util.Arrays;

/**
 * @author Jaca777
 *         Created 02.01.15 at 21:12
 */
public class LimitedChunksMap extends ChunksMap {

    /**
     * Maximal size of terrain that can be rendered at once (in chunks).
     */
    public static final int SIZE = 3;

    private Player player;

    /**
     * Shift of chunks coordinates.
     */
    private int x, y;

    /**
     * Array organization:
     * 7 - (x + 0, y + 2) 8 - (x + 1, y + 2) 9 - (x + 2, y + 2)
     * 4 - (x + 0, y + 1) 5 - (x + 1, y + 1) 6 - (x + 2, y + 1)
     * 1 - (x + 0, y + 0) 2 - (x + 1, y + 0) 3 - (x + 2, y + 0)
     * <p/>
     * 5 - Player position.
     */
    private Chunk[] chunks = new Chunk[9];

    public LimitedChunksMap(int centerX, int centerY, Player player) {
        this.x = centerX - 1;
        this.y = centerY - 1;
        this.player = player;
        fill();
    }

    @Override
    public void fill() {
        for (int x = 0; x < SIZE; x++)
            for (int y = 0; y < SIZE; y++) load(x, y);
    }

    private void refill() {
        for (int x = 0; x < SIZE; x++)
            for (int y = 0; y < SIZE; y++)
                if (getValue(x, y) == null) {
                    System.out.println();
                    System.out.println("FILLING " + x + " " + y);
                    load(x, y);
                }
    }


    private void load(int x, int y) {
        if (isInArrayRange(x, y)) {
            setValue(x, y,
                    (Chunk) ResourceManager.use(new ChunkDesc(x + this.x, y + this.y)));
        } else throw new ArrayIndexOutOfBoundsException(String.format("x: %d y: %d", x, y));
    }

    private void unload(int x, int y) {
        if (isInArrayRange(x, y)) {
            if (getValue(x, y) != null) {
                try {
                    ChunkDesc desc = new ChunkDesc(x + this.x, y + this.y);
                    ResourceManager.cancelUsage(desc);
                } catch (Exception e) { //
                    System.out.println("LM Pos:" + this.x + " " + this.y);
                    throw new RuntimeException(e);
                }
            }
        } else throw new ArrayIndexOutOfBoundsException(String.format("x: %d y: %d", x, y));
    }


    @Override
    public void onUpdate(long delta) {
        int xD = (int) ((Math.floor(player.getX() / Chunk.CHUNK_SIZE)) - this.x - 1);
        int yD = (int) ((Math.ceil(-player.getZ() / Chunk.CHUNK_SIZE)) - this.y - 1);
        if (xD != 0 || yD != 0) {
            System.out.println();
            System.out.println("new xD = " + (this.x + xD));
            System.out.println("new yD = " + (this.y + yD));
            System.out.println();
            move(xD, yD);
        }
        for(Chunk chunk : chunks){
            if (chunk != null) chunk.onUpdate(delta);
        }
    }

    private Chunk[] temp = new Chunk[9];

    private void move(int xD, int yD) {
        System.arraycopy(chunks, 0, temp, 0, SIZE * SIZE);
        Arrays.fill(chunks, null);
        for (int x = 0; x < SIZE; x++)
            for (int y = 0; y < SIZE; y++) {
                int newX = x - xD;
                int newY = y - yD;
                if (isInArrayRange(newX, newY))
                    setValue(newX, newY, temp[y * SIZE + x]);
                else if (getValue(x, y) != null) unload(x, y);
            }
        this.x += xD;
        this.y += yD;
        refill();
    }

    @Override
    public void onDraw(OGLEngineContext context) {
        for (Chunk chunk : chunks)
            if (chunk != null) chunk.draw(context);

    }

    public boolean isChunkInRange(int x, int y) {
        return isInArrayRange(x - this.x, y - this.y);
    }

    public boolean isInArrayRange(int x, int y) {
        return (x > -1 && y > -1 && x < SIZE && y < SIZE);
    }

    private Chunk getValue(int x, int y) {
        return chunks[y * SIZE + x];
    }

    private void setValue(int x, int y, Chunk value) {
        System.out.println("Setting " + x + " " + y);
        chunks[y * SIZE + x] = value;
    }

    @Override
    public Chunk getChunk(int x, int y) {
        int sX = x - this.x;
        int sY = y - this.y;
        if (!isChunkInRange(sX, sY)) {
            return null;
        } else {
            return getValue(sX, sY);
        }
    }


    @Override
    public float getHeight(float x, float y) {
        int chunkX = (int) (x / Chunk.CHUNK_SIZE);
        int chunkY = (int) (y / Chunk.CHUNK_SIZE);
        if (isChunkInRange(chunkX, chunkY))
            return getChunk(chunkX, chunkY).getHeight(x % Chunk.CHUNK_SIZE, y % Chunk.CHUNK_SIZE);
        else throw new RuntimeException("Coordinates are out of loaded range.");
    }

}
