package pl.jacadev.game.resmanagement.manageables.containers;

import pl.jacadev.game.resmanagement.loading.DataContainer;

import java.nio.ByteBuffer;

/**
 * @author Jaca777
 *         Created 21.01.15 at 15:45
 */
public class TextureDataContainer extends DataContainer {
    private boolean loaded = false;
    private ByteBuffer[] data;
    private int w,h,d;
    private int format, internalformat;

    public void load(ByteBuffer data, int w, int h, int format, int internalformat){
        load(new ByteBuffer[]{data}, w, h, 1, format, internalformat);
    }


    public void load(ByteBuffer[] data, int w, int h, int d, int format, int internalformat) {
        this.data = data;
        this.h = h;
        this.w = w;
        this.d = d;
        this.loaded = true;
        this.format = format;
        this.internalformat = internalformat;
    }

    public ByteBuffer[] getData() {
        return data;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public int getD() {
        return d;
    }

    public int getFormat() {
        return format;
    }

    public int getInternalformat() {
        return internalformat;
    }

    @Override
    public boolean isLoaded() {
        return loaded;
    }
}
