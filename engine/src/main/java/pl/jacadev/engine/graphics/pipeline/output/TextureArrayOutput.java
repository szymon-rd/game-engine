package pl.jacadev.engine.graphics.pipeline.output;

import pl.jacadev.engine.graphics.pipeline.OGLRendererOutput;
import pl.jacadev.engine.graphics.textures.Texture2DArray;

/**
 * @author Jaca777
 *         Created 2015-07-10 at 13
 */
public class TextureArrayOutput extends OGLRendererOutput<Texture2DArray> {
    public TextureArrayOutput(Texture2DArray output) {
        super(output);
    }
}
