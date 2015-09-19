package pl.jacadev.engine.graphics;

import org.lwjgl.opengl.GL30;
import pl.jacadev.engine.graphics.textures.Texture;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL30.*;

/**
 * @author Jaca777
 *         Created 2015-08-03 at 16
 */
public class DepthFramebuffer extends Framebuffer {
    public DepthFramebuffer(Texture depthTexture) {
        super(depthTexture, GL30.glGenFramebuffers());
        GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, this.name);
        GL30.glFramebufferTexture2D(GL_DRAW_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_TEXTURE_2D, this.destTex.getTexture(), 0);

        int status = glCheckFramebufferStatus(GL_DRAW_FRAMEBUFFER);
        if(status != GL30.GL_FRAMEBUFFER_COMPLETE) throw new RuntimeException("Incomplete framebuffer: " + status);
    }
}
