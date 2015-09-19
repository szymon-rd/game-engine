package pl.jacadev.game.resmanagement.manageables.loaders.materialset.local;

import pl.jacadev.game.resmanagement.ResourceDesc;
import pl.jacadev.game.resmanagement.loading.DataContainer;
import pl.jacadev.game.resources.Resources;
import pl.jacadev.game.resmanagement.Manageable;
import pl.jacadev.game.resmanagement.loading.ResourceLoader;
import pl.jacadev.game.resmanagement.manageables.ManageableMaterialSet;
import pl.jacadev.game.resmanagement.manageables.containers.MaterialSetDataContainer;
import pl.jacadev.game.resmanagement.manageables.loaders.materialset.MaterialSetDesc;
import pl.jacadev.engine.graphics.resources.ImageDecoder;
import pl.jacadev.engine.graphics.resources.PNGDecoder;

import java.nio.ByteBuffer;

/**
 * @author Jaca777
 *         Created 20.02.15 at 17:52
 */
public class MaterialSetLoader extends ResourceLoader{

    @Override
    public Manageable newManageable(ResourceDesc desc) {
        return new ManageableMaterialSet((MaterialSetDesc) desc);
    }

    @Override
    public DataContainer createContainer() {
        return new MaterialSetDataContainer();
    }

    private static final String MATERIALS_PATH = "materials/";
    private static final String TEX_FILE = "/tex.png";
    private static final String PRLX_MAP_FILE = "/prlx.png";


    @Override
    public void load(DataContainer dest, ResourceDesc desc) {
        MaterialSetDataContainer materialSetContainer = (MaterialSetDataContainer) dest;
        MaterialSetDesc materialSetDesc = (MaterialSetDesc) desc;

        ByteBuffer[] textures = new ByteBuffer[materialSetDesc.getAmount()];
        ByteBuffer[] prlxMaps = new ByteBuffer[materialSetDesc.getAmount()];

        for(int i = 0; i < materialSetDesc.getAmount(); i++){
            textures[i] = ImageDecoder.decodePNG(Resources.getAsStream(MATERIALS_PATH + materialSetDesc.getMaterials()[i] + TEX_FILE), PNGDecoder.Format.RGBA).getBuffer();
            prlxMaps[i] = ImageDecoder.decodePNG(Resources.getAsStream(MATERIALS_PATH + materialSetDesc.getMaterials()[i] + PRLX_MAP_FILE), PNGDecoder.Format.RGBA).getBuffer();
        }

        materialSetContainer.load(textures, prlxMaps, new float[]{0.0f,0.0f,0.0f}); //TODO Reading shininess from file
    }
}
