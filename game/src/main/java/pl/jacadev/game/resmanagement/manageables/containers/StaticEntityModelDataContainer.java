package pl.jacadev.game.resmanagement.manageables.containers;

import pl.jacadev.game.resmanagement.loading.DataContainer;
import pl.jacadev.game.resmanagement.manageables.ManageableModel;
import pl.jacadev.game.resmanagement.manageables.loaders.model.ModelDesc;
import pl.jacadev.game.resmanagement.manageables.textures.ManageableTexture2DArray;

/**
 * @author Jaca777
 *         Created 29.01.15 at 17:49
 */
public class StaticEntityModelDataContainer extends DataContainer {

    private int texOff;
    private int texOffLimit;
    private float lvl;
    private boolean randomizeTex = false;

    private ManageableModel model;
    private ManageableTexture2DArray texture;
    private ModelDesc modelDesc;

    private boolean loaded = false;

    public void load(ManageableModel model, ManageableTexture2DArray texture, int texOff, float lvl) {
        this.texOff = texOff;
        this.lvl = lvl;
        this.model = model;
        this.texture = texture;
        this.loaded = true;
    }

    public void load(ManageableModel model, ManageableTexture2DArray texture, int texOff, int texOffLimit, float lvl) {
        this.texOffLimit = texOffLimit;
        this.randomizeTex = true;
        load(model, texture, texOff, lvl);
    }

    @Override
    public boolean isLoaded() {
        return loaded && model.isLoaded() && texture.isLoaded();
    }

    public float getLvl() {
        return lvl;
    }

    public ManageableModel getModel() {
        return model;
    }


    public boolean isRandomizeTex() {
        return randomizeTex;
    }

    public int getTexOff() {
        return texOff;
    }

    public int getTexOffLimit() {
        return texOffLimit;
    }

    public ManageableTexture2DArray getTexture() {
        return texture;
    }
}
