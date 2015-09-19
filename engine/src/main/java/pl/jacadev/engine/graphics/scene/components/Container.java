package pl.jacadev.engine.graphics.scene.components;

import pl.jacadev.engine.graphics.contexts.OGLEngineContext;

/**
 * @author Jaca777
 *         Created 2015-07-13 at 13
 */
public abstract class Container<T extends DrawableComponent> extends DrawableComponent {
    private ComponentList<T> children = new ComponentList<>();

    @Override
    public void onUpdate(long delta) {
        parentUpdate(delta);
        for(T component : children) {
            component.onUpdate(delta);
        }
    }

    @Override
    public void onDraw(OGLEngineContext engineContext) {
        for(T component : children) {
            component.draw(engineContext);
        }
    }

    public ComponentList<T> getChildren() {
        return children;
    }

    protected void parentUpdate(long delta){}
}
