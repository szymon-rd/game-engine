package pl.jacadev.game.world.terrain;

import pl.jacadev.engine.graphics.shaders.programs.terrain.MaterialSet;
import pl.jacadev.game.resmanagement.ResourceManager;
import pl.jacadev.game.resmanagement.manageables.loaders.materialset.MaterialSetDesc;

/**
 * @author Jaca777
 *         Created 29.12.14 at 16:31
 *         TODO Change.
 */
public class TerrainResources {
    public static final int UPLANDS = 0;

    private static final MaterialSetDesc[] MATERIAL_SET_DESCS = {
            new MaterialSetDesc(new String[]{"grass_0", "alley_0", "rocks_0"})
    };


    public static MaterialSet getMaterialSet(int set) {
        return (MaterialSet) ResourceManager.use(MATERIAL_SET_DESCS[set]);
    }
}
