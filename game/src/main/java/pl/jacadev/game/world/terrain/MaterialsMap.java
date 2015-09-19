package pl.jacadev.game.world.terrain;

import org.lwjgl.opengl.GL11;
import pl.jacadev.engine.graphics.textures.RawTexture2D;

import java.nio.ByteBuffer;

/**
 * @author Jaca777
 *         Created 01.01.15 at 20:02
 */
public class MaterialsMap extends RawTexture2D {
    public MaterialsMap(int width, int height, ByteBuffer data) {
        super(width, height, GL11.GL_RGBA, GL11.GL_RGBA, data);
    }
}
