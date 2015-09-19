package pl.jacadev.game.resmanagement.manageables.loaders.texture;

import pl.jacadev.engine.graphics.resources.PNGDecoder;

import java.util.Arrays;

/**
 * @author Jaca777
 *         Created 22.01.15 at 23:31
 */
public class TextureArrayDesc extends TextureDesc {

    private String[] names;

    public TextureArrayDesc(String[] names, PNGDecoder.Format format) {
        super(Arrays.toString(names), TextureType.T_OTHER, format);
        this.names = names;
    }

    public String[] getNames() {
        return names;
    }
}
