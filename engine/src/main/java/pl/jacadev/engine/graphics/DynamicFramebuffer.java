package pl.jacadev.engine.graphics;

import org.lwjgl.opengl.GL30;
import pl.jacadev.engine.graphics.textures.RawTexture2D;

import static org.lwjgl.opengl.GL30.GL_DRAW_FRAMEBUFFER;

/**
 * @author Jaca777
 *         Created 2015-08-13 at 13
 */
public class DynamicFramebuffer extends Framebuffer {

    public DynamicFramebuffer() {
        super(null, GL30.glGenFramebuffers());
    }

    @Override
    public void bindDraw() {
        if(this.destTex != null) super.bindDraw();
        else throw new RenderingException("DynamicFramebuffer needs to know it's rendering destination before binding");
    }

    @Override
    public void bindRead() {
        if(this.destTex != null) super.bindRead();
        else throw new RenderingException("DynamicFramebuffer needs to know it's rendering destination before binding");
    }

    public void renderTo(RawTexture2D dest, int attachment) {
        this.destTex = dest;
        GL30.glFramebufferTexture2D(GL_DRAW_FRAMEBUFFER, attachment, dest.getType(), this.destTex.getTexture(), 0);
    }

    public void bindDraw(RawTexture2D dest, int attachment) {
        super.bindDraw();
        renderTo(dest, attachment);
    }
}
