package pl.jacadev.game.resmanagement.manageables.loaders.physicalmodel;

import pl.jacadev.game.resmanagement.ResourceDesc;
import pl.jacadev.game.resmanagement.ResourceType;

/**
 * @author Jaca777
 *         Created 31.01.15 at 22:16
 */
public class PhysicalModelDesc extends ResourceDesc {
    private String textureName;
    private String modelName;

    public PhysicalModelDesc(String modelName, String textureName) {
        super(ResourceType.PHYSICAL_MODEL);
        this.modelName = modelName;
        this.textureName = textureName;
    }


    @Override
    public int compareTo(ResourceDesc descriptor) {
        if(descriptor.getType() != this.getType())
            return descriptor.getType().ordinal() - this.getType().ordinal();
        PhysicalModelDesc modelDesc = (PhysicalModelDesc) descriptor;
        int tCompResult = modelDesc.getTextureName().compareTo(this.getTextureName());
        int mCompResult = modelDesc.getModelName().compareTo(this.getModelName());
        if(tCompResult == 0 && mCompResult == 0) return 0;
        else return (tCompResult + mCompResult != 0) ? tCompResult + mCompResult : tCompResult * 3 + mCompResult;
    }

    public String getModelName() {
        return modelName;
    }

    public String getTextureName() {
        return textureName;
    }
}
