package pl.jacadev.game.resmanagement.manageables;

import pl.jacadev.engine.graphics.contexts.Bindings;
import pl.jacadev.game.resmanagement.ResourceDesc;
import pl.jacadev.game.resmanagement.loading.DataContainer;
import pl.jacadev.game.resmanagement.manageables.containers.MaterialSetDataContainer;
import pl.jacadev.game.resmanagement.Manageable;
import pl.jacadev.game.resmanagement.ResourceManagementException;
import pl.jacadev.game.resmanagement.manageables.loaders.materialset.MaterialSetDesc;
import pl.jacadev.engine.graphics.shaders.programs.terrain.MaterialSet;
import pl.jacadev.engine.graphics.textures.Texture2DArray;

import java.nio.ByteBuffer;

/**
 * @author Jaca777
 *         Created 20.02.15 at 17:51
 */
public class ManageableMaterialSet extends MaterialSet implements Manageable{

    private static final Texture2DArray UNBINDABLE_TEXTURES_ARRAY = new Texture2DArray(-1,-1,-1,-1,-1,-1,false){
        @Override
        public void bind(Bindings bindings) {
            //Do nothing
        }
        @Override
        public void set(int offset, ByteBuffer data) {
            //Do nothing
        }
    };
    private static final float[] DEFAULT_SHININESS_ARRAY = new float[]{0.0f,0.0f,0.0f};

    private boolean loaded = false;
    private boolean blocked = false;

    private MaterialSetDesc desc;

    public ManageableMaterialSet(MaterialSetDesc desc) {
        super(UNBINDABLE_TEXTURES_ARRAY, UNBINDABLE_TEXTURES_ARRAY, DEFAULT_SHININESS_ARRAY);
        this.desc = desc;
    }

    @Override
    public void unload() {
        if(loaded) {
            super.unload();
            this.loaded = false;
        } else blocked = true;
    }

    @Override
    public void load(DataContainer data) {
        if(!blocked) {
            if (!data.isLoaded()) throw new ResourceManagementException("DataContainer is not loaded");
            MaterialSetDataContainer materialSetContainer = (MaterialSetDataContainer) data;
            this.textureArray = new Texture2DArray(TEXTURE_SIZE, TEXTURE_SIZE, materialSetContainer.getSize(), materialSetContainer.getTextures());
            this.prlxMaps = new Texture2DArray(TEXTURE_SIZE, TEXTURE_SIZE, materialSetContainer.getSize(), materialSetContainer.getPrlxMaps());
            this.shininessArray = materialSetContainer.getShininess();
        }else blocked = false;
    }

    @Override
    public boolean isLoaded() {
        return loaded;
    }

    @Override
    public ResourceDesc getDesc() {
        return desc;
    }

}
