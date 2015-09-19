package pl.jacadev.engine.graphics.contexts;

import pl.jacadev.engine.graphics.utility.math.MatrixStack;

/**
 * @author Jaca777
 *         Created 2015-07-12 at 19
 */
public class RenderingContext {
    public enum RenderingState {
        STANDARD, REFLECTIONS, SHADOW_MAP
    }

    private RenderingState renderingState;
    private MatrixStack matrixStack;

    public RenderingContext() {
        this.matrixStack = new MatrixStack(10);
    }

    public MatrixStack getCurrentMatrixStack() {
        return matrixStack;
    }

    public RenderingState getRenderingState() {
        return renderingState;
    }

    public void setRenderingState(RenderingState renderingState) {
        this.renderingState = renderingState;
    }
}
