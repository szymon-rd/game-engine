package pl.jacadev.engine.graphics.postprocessing;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import pl.jacadev.engine.graphics.Framebuffer;
import pl.jacadev.engine.graphics.pipeline.OGLRenderer;
import pl.jacadev.engine.graphics.pipeline.OGLRendererOutput;
import pl.jacadev.engine.graphics.pipeline.output.RawTextureOutput;
import pl.jacadev.engine.graphics.postprocessing.shaders.hdr.HDRProgram;
import pl.jacadev.engine.graphics.shaders.effects.bloom.BloomDetection;
import pl.jacadev.engine.graphics.shaders.effects.blur.Blur;
import pl.jacadev.engine.graphics.textures.*;
import pl.jacadev.engine.graphics.utility.ogl.Shapes;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.GL_RGBA32F;

/**
 * @author Jaca777
 *         Created 23.02.15 at 00:18
 */
public class HDRRenderer extends OGLRenderer{

    private HDRProgram hdrShader;
    private BloomDetection bloomShader;
    private Blur blurShader;

    private Framebuffer bloomFramebuffer;
    private RawTexture2D bloomDest;

    private Framebuffer resultFramebuffer;
    private RawTextureOutput output;

    private int w, h;

    public static final int PASSES = 6;
    public static final float[][] PASSES_RES = {
            {1/2f, 1/2f},
            {1/2f, 1/2f},
            {1/2f, 1/2f},
            {1/2f, 1/2f},
            {1/2f, 1/2f},
            {1/2f, 1/2f}
    };
    private Framebuffer blurFBs[] = new Framebuffer[PASSES];
    private RawTexture2D passes[] = new RawTexture2D[PASSES];

    private int screenrectVAO;


    public HDRRenderer(int width, int height) {
        this.w = width;
        this.h = height;
    }


    @Override
    public void setInput(OGLRendererOutput<? extends Texture> output) {
        output.assertType(MultisampleTexture2D.class);
        MultisampleTexture2D srcTexture = (MultisampleTexture2D) output.getOutput();
        this.hdrShader = new HDRProgram(srcTexture, this.passes, this.bloomDest);
        this.bloomShader = new BloomDetection(srcTexture);
    }

    @Override
    public RawTextureOutput getOutput() {
        return output;
    }

    @Override
    protected void onInit() {
        setup(this.w, this.h);
    }

    /**
     * Sets up all the framebuffers, textures, etc. with given parameters.
     *
     * @param width  Viewport width.
     * @param height Viewport height.
     */
    private void setup(int width, int height) {

        this.bloomDest = new RawTexture2D(width, height, GL_RGBA32F, GL_RGBA, null);
        this.bloomFramebuffer = new Framebuffer(bloomDest);

        for (int i = 0; i < PASSES; i++) {
            this.passes[i] = new RawTexture2D((int) (width * PASSES_RES[i][0]), (int) (height * PASSES_RES[i][1]), GL_RGBA, GL_RGBA, null);
            this.blurFBs[i] = new Framebuffer(this.passes[i]);
        }

        //Creating screen quad
        screenrectVAO = Shapes.mkFullRect(HDRProgram.ATTR_VERTEX, HDRProgram.ATTR_TEX_COORD);

        RawTexture2D resultTexture = new RawTexture2D(width, height, GL_RGBA, GL_RGBA, null);
        this.output = new RawTextureOutput(resultTexture);
        this.resultFramebuffer = new Framebuffer(resultTexture);
        this.blurShader = engineContext.getShadingContext().getBlurFilter();
        blurShader.use();
        blurShader.applyBlurDisplacement(width, height);

    }

    /**
     * Preforms HDR convertion, applies bloom, etc., and puts everything in ogl's back left buffer.
     */
    @Override
    public void onRender() {
        glDisable(GL_DEPTH_TEST);

        //Finding bloom sources.
        this.bloomFramebuffer.bindDraw();
        this.bloomShader.use();
        GL11.glClear(GL_COLOR_BUFFER_BIT);
        GL30.glBindVertexArray(screenrectVAO);
        GL11.glDrawElements(GL11.GL_TRIANGLES, Shapes.QUAD_INDICES_AMOUNT, GL11.GL_UNSIGNED_INT, 0);

        //Applying blur effect to bloom.
        blurShader.use();
        blurShader.setUniformi(blurShader.uFormat, Blur.RGBA);
        blurShader.useTexture(bloomDest.getTexture());
        for (int i = 0; i < PASSES; i++) {
            this.blurFBs[i].bindDraw();
            glClear(GL_COLOR_BUFFER_BIT);
            GL30.glBindVertexArray(screenrectVAO);
            GL11.glViewport(0,0, (int) (w*PASSES_RES[i][0]), (int) (h*PASSES_RES[i][1]));
            GL11.glDrawElements(GL11.GL_TRIANGLES, Shapes.QUAD_INDICES_AMOUNT, GL11.GL_UNSIGNED_INT, 0);
            blurShader.useTexture(passes[i].getTexture());
        }

        //Drawing everything on screen.
        hdrShader.use();
        resultFramebuffer.bindDraw();
        glViewport(0, 0, this.w, this.h);
        GL30.glBindVertexArray(screenrectVAO);
        GL11.glDrawElements(GL11.GL_TRIANGLES, Shapes.QUAD_INDICES_AMOUNT, GL11.GL_UNSIGNED_INT, 0);

        GL30.glBindVertexArray(0);
        glEnable(GL_DEPTH_TEST);
    }


    @Override
    public void onResize(int newWidth, int newHeight) {
        this.w = newWidth;
        this.h = newHeight;

        this.output.getOutput().bind(engineContext.getBindings());
        this.output.getOutput().resize(newWidth, newHeight);
        this.resultFramebuffer.resize(newWidth, newHeight);
        this.bloomFramebuffer.resize(newWidth, newHeight);
        this.bloomDest.bind(engineContext.getBindings());
        this.bloomDest.resize(newWidth, newHeight);
        for (int i = 0; i < PASSES; i++) {
            this.blurFBs[i].resize(newWidth, newHeight);
            this.passes[i].bind(engineContext.getBindings());
            this.passes[i].resize(newWidth, newHeight);
        }
    }

    public void setBloomLevel(float lvl) {
        hdrShader.use();
        hdrShader.setUniformf(hdrShader.uBloomLvl, lvl);
    }

    public void setBrightness(float lvl) {
        hdrShader.use();
        hdrShader.setUniformf(hdrShader.uBrightness, lvl);
    }


}
