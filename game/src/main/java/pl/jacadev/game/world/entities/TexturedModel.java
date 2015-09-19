package pl.jacadev.game.world.entities;

import pl.jacadev.engine.graphics.Bindable;
import pl.jacadev.engine.graphics.Unloadable;
import pl.jacadev.engine.graphics.contexts.Bindings;
import pl.jacadev.engine.graphics.models.VAOModel;
import pl.jacadev.engine.graphics.textures.Texture2DArray;

/**
 * @author Jaca777
 *         Created 31.01.15 at 15:05
 */
public class TexturedModel implements Unloadable, Bindable{
    protected Texture2DArray texture;
    protected VAOModel model;

    public TexturedModel(VAOModel model, Texture2DArray texture) {
        this.model = model;
        this.texture = texture;
    }

    public void draw(){
        render();
        finalizeRendering();
    }

    @Override
    public void unload() {
        texture.unload();
        model.unload();
    }

    public void finalizeRendering() {
        model.finalizeRendering();
    }

    @Override
    public void bind(Bindings bindings) {
        texture.bind(bindings);
        model.bind();
    }

    public void render() {
        model.render();
    }
}
