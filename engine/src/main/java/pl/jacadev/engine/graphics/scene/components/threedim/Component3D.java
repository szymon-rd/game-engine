package pl.jacadev.engine.graphics.scene.components.threedim;

import org.lwjgl.util.vector.Vector3f;
import pl.jacadev.engine.graphics.contexts.OGLEngineContext;
import pl.jacadev.engine.graphics.scene.components.DrawableComponent;

/**
 * @author Jaca777
 *         Created 2015-07-13 at 13
 */
public abstract class Component3D extends DrawableComponent {
    protected Vector3f pos;
    protected Effects3D effects;
    public Component3D(Vector3f pos) {
        this.pos = pos;
        this.effects = new Effects3D();
    }

    @Override
    public void draw(OGLEngineContext engineContext) {
        super.draw(engineContext);
    }

    public Vector3f getPos() {
        return pos;
    }

    public void setPos(float x, float y, float z) {
        this.pos.set(x, y, z);
    }

    public float getX() {
        return pos.getX();
    }

    public float getY() {
        return pos.getY();
    }

    public void setX(float x) {
        pos.setX(x);
    }

    public void setY(float y) {
        pos.setY(y);
    }

    public void setZ(float z) {
        pos.setZ(z);
    }

    public float getZ() {
        return pos.getZ();
    }

    public Effects3D getEffects() {
        return effects;
    }
}
