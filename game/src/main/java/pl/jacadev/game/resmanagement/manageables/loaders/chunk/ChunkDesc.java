package pl.jacadev.game.resmanagement.manageables.loaders.chunk;

import pl.jacadev.game.resmanagement.ResourceDesc;
import pl.jacadev.game.resmanagement.ResourceType;

/**
 * @author Jaca777
 *         Created 18.01.15 at 14:55
 */
public class ChunkDesc extends ResourceDesc {
    private int x, y;

    public ChunkDesc(int x, int y) {
        super(ResourceType.CHUNK);
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(ResourceDesc descriptor) {
        if(descriptor.getType() != ResourceType.CHUNK) return ResourceType.CHUNK.ordinal() - descriptor.getType().ordinal();
        else{
            ChunkDesc chunkDesc = (ChunkDesc) descriptor;
            int xComp = this.x - chunkDesc.getX();
            int yComp = this.y - chunkDesc.getY();
            if(xComp == 0 && yComp == 0) return 0;
            else return (xComp + yComp != 0) ? xComp + yComp : xComp + yComp * 3;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
