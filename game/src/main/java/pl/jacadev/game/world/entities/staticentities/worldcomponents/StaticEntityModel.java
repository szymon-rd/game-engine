package pl.jacadev.game.world.entities.staticentities.worldcomponents;

import pl.jacadev.game.world.entities.TexturedModel;
import pl.jacadev.engine.graphics.models.VAOModel;
import pl.jacadev.engine.graphics.textures.Texture2DArray;
import pl.jacadev.engine.graphics.utility.FastRandom;

/**
 * @author Jaca777
 *         Created 31.01.15 at 15:47
 */
public class StaticEntityModel extends TexturedModel {
    protected float lvl;
    protected int textureOffset;

    public StaticEntityModel(VAOModel model, Texture2DArray texture, float lvl, int textureOffset) {
        super(model, texture);
        this.lvl = lvl;
        this.textureOffset = textureOffset;
    }

    public StaticEntityModel(VAOModel model, Texture2DArray texture, int lvl, int firstOff, int lastOff) {
        super(model, texture);
        this.lvl = lvl;
        this.textureOffset = FastRandom.random(firstOff, lastOff);
    }

    protected void randomizeTextureOff(int firstOff, int lastOff) {
        this.textureOffset = FastRandom.random(firstOff, lastOff);
    }

    public float getLvl() {
        return lvl;
    }
}
