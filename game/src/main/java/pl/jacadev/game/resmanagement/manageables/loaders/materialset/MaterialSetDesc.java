package pl.jacadev.game.resmanagement.manageables.loaders.materialset;

import pl.jacadev.game.resmanagement.ResourceDesc;
import pl.jacadev.game.resmanagement.ResourceType;

import java.util.Arrays;

/**
 * @author Jaca777
 *         Created 20.02.15 at 17:52
 */
public class MaterialSetDesc extends ResourceDesc {

    private String[] materials;
    private int amount;

    public MaterialSetDesc(String[] materials) {
        super(ResourceType.MATERIAL_SET);
        this.materials = materials;
        this.amount = materials.length;
    }

    @Override
    public int compareTo(ResourceDesc descriptor) {
        if(descriptor.getType() != getType())
            return descriptor.getType().ordinal() - getType().ordinal();
        return Arrays.deepHashCode(materials) - Arrays.deepHashCode(((MaterialSetDesc)descriptor).getMaterials());
    }

    public int getAmount() {
        return amount;
    }

    public String[] getMaterials() {
        return materials;
    }
}
