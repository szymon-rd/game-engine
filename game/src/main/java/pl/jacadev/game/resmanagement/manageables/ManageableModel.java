package pl.jacadev.game.resmanagement.manageables;

import pl.jacadev.game.resmanagement.Manageable;
import pl.jacadev.game.resmanagement.ResourceManagementException;
import pl.jacadev.game.resmanagement.loading.DataContainer;
import pl.jacadev.game.resmanagement.manageables.containers.ModelDataContainer;
import pl.jacadev.game.resmanagement.manageables.loaders.model.ModelDesc;
import pl.jacadev.engine.graphics.models.VAOModel;
import pl.jacadev.engine.graphics.shaders.StandardProgram;

/**
 * @author Jaca777
 *         Created 18.01.15 at 19:01
 */
public class ManageableModel extends VAOModel implements Manageable {

    private boolean loaded = false;
    private boolean blocked = false;

    private ModelDesc desc;

    public ManageableModel(ModelDesc desc) {
        super(0, 0, 0, 0, 0, 0, StandardProgram.ATTRIBUTES);
        this.desc = desc;
    }

    @Override
    public void draw() {
        if (loaded) super.draw();
    }

    @Override
    public int getVao() {
        return super.getVao();
    }


    /**
     * Unloads buffers, including VAO buffer.
     */
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
            ModelDataContainer modelContainer = (ModelDataContainer) data;
            this.loadBuffers(modelContainer.getVertices(), modelContainer.getTexCoords(), modelContainer.getNormals(), modelContainer.getIndices());
            this.indices = modelContainer.getIndicesAmount();
            this.createVAO();
            this.loaded = true;
        }else blocked = false;
    }

    @Override
    public boolean isLoaded() {
        return loaded;
    }

    @Override
    public ModelDesc getDesc() {
        return desc;
    }
}
