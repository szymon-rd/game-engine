package pl.jacadev.engine.graphics.pipeline.output;

import pl.jacadev.engine.graphics.pipeline.OGLRendererOutput;
import pl.jacadev.engine.graphics.textures.MultisampleTexture2D;

/**
 * @author Jaca777
 *         Created 2015-07-10 at 13
 */
public class MultisampleTextureOutput extends OGLRendererOutput<MultisampleTexture2D>{
    public MultisampleTextureOutput(MultisampleTexture2D output) {
        super(output);
    }
}
