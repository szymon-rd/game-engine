package pl.jacadev.engine.graphics.pipeline;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import pl.jacadev.engine.graphics.pipeline.output.RawTextureOutput;
import pl.jacadev.engine.graphics.shaders.programs.identity.IdentityProgram;
import pl.jacadev.engine.graphics.textures.Texture;
import pl.jacadev.engine.graphics.utility.ogl.Shapes;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.GL_DRAW_FRAMEBUFFER;

/**
 * @author Jaca777
 *         Created 2015-06-25 at 14
 */
public class OGLOnScreenRenderer extends OGLRenderer {
    private int srcTexture;
    private IdentityProgram identityProgram;
    private int rectVAO;

    @Override
    protected void onInit() {
        identityProgram = new IdentityProgram();
        rectVAO = Shapes.mkFullRect(identityProgram.aVertex, identityProgram.aTexCoord);
    }

    @Override
    public void onRender() {
        identityProgram.use();
        identityProgram.useTexture(srcTexture, GL_TEXTURE_2D, 0);
        GL30.glBindFramebuffer(GL_DRAW_FRAMEBUFFER, 0);
        GL11.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        GL11.glDisable(GL_DEPTH_TEST);
        GL30.glBindVertexArray(rectVAO);
        GL11.glDrawElements(GL11.GL_TRIANGLES, Shapes.QUAD_INDICES_AMOUNT, GL11.GL_UNSIGNED_INT, 0);
        GL30.glBindVertexArray(0);
        GL11.glEnable(GL_DEPTH_TEST);
    }

    @Override
    public void setInput(OGLRendererOutput<? extends Texture> out) {
        if(out instanceof RawTextureOutput) this.srcTexture = out.getOutput().getTexture();
        else throw new IllegalArgumentException("Wrong type of given input. RawTexture2D expected.");
    }

    @Override
    public OGLRendererOutput<? extends Texture> getOutput() {
        return null;
    }

    @Override
    public void onResize(int newW, int newH) {
        //do nothing
    }
}
