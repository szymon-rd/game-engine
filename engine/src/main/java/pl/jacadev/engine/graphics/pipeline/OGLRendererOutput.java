package pl.jacadev.engine.graphics.pipeline;

import pl.jacadev.engine.graphics.textures.Texture;

/**
 * @author Jaca777
 *         Created 2015-07-10 at 13
 */
public abstract class OGLRendererOutput<T extends Texture> {
    private T output;

    public OGLRendererOutput(T output) {
        this.output = output;
    }

    public T getOutput() {
        return output;
    }

    public <A extends Texture> OGLRendererOutput<A> assertType(Class<A> type) {
        if(!type.isAssignableFrom(output.getClass())) throw new IllegalArgumentException("Wrong type of renderer output. Expected: " + type + " Given: " + output.getClass());
        else return (OGLRendererOutput<A>) this;
    }
}
