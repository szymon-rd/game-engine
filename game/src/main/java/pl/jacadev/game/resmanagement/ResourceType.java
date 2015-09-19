package pl.jacadev.game.resmanagement;

import pl.jacadev.game.resmanagement.loading.ResourceLoader;
import pl.jacadev.game.resmanagement.manageables.loaders.chunk.local.LocalChunkLoader;
import pl.jacadev.game.resmanagement.manageables.loaders.materialset.local.MaterialSetLoader;
import pl.jacadev.game.resmanagement.manageables.loaders.model.local.LocalModelLoader;
import pl.jacadev.game.resmanagement.manageables.loaders.physicalmodel.local.PhysicalModelLoader;
import pl.jacadev.game.resmanagement.manageables.loaders.staticentitymodel.local.LocalStaticEntityModelLoader;
import pl.jacadev.game.resmanagement.manageables.loaders.texture.local.LocalTextureLoader;

/**
 * @author Jaca777
 *         Created 17.01.15 at 23:35
 */
public enum ResourceType {
    CHUNK(new LocalChunkLoader()), MODEL(new LocalModelLoader()), TEXTURE(new LocalTextureLoader()), PHYSICAL_MODEL(new PhysicalModelLoader()),
    STATIC_ENTITY_MODEL(new LocalStaticEntityModelLoader()), MATERIAL_SET(new MaterialSetLoader());

    private ResourceLoader loader;

    private ResourceType(ResourceLoader loader) {
        this.loader = loader;
    }

    /**
     * @return A loader of resource.
     */
    public ResourceLoader getLoader() {
        return loader;
    }
}
