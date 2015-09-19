package pl.jacadev.engine.graphics.shaders.programs.light;

import org.lwjgl.util.vector.Vector3f;
import pl.jacadev.engine.camera.FPPCamera;
import pl.jacadev.engine.graphics.scene.Component;
import pl.jacadev.engine.graphics.utility.math.OrthoMatrix;
import pl.jacadev.engine.graphics.utility.math.ProjectionMatrix;

/**
 * @author Jaca777
 *         Created 2015-08-01 at 18
 */
public class DirectionalLight implements Component {
    public static final int SHADOW_MAP_HEIGHT = 4096;
    public static final int SHADOW_MAP_WIDTH = 4096;

    private Vector3f color, amColor;
    private Vector3f direction;
    private FPPCamera camera;
    private ShadowMap shadowMap;

    public DirectionalLight(Vector3f color, Vector3f amColor, Vector3f direction, boolean shadow) {
        this.color = color;
        this.amColor = amColor;
        this.direction = direction;
        if (shadow) {
            ProjectionMatrix projectionMatrix = new OrthoMatrix(130f, -130f, 130f, -130f, 130f, -130f);
            this.camera = new FPPCamera(projectionMatrix);
            camera.setRotation((float) -Math.atan2(direction.y, Math.sqrt(direction.x * direction.x + direction.z * direction.z)), (float) (Math.atan2(direction.x, direction.z)));
            this.shadowMap = new ShadowMap(SHADOW_MAP_WIDTH, SHADOW_MAP_HEIGHT, camera);
        }
    }

    @Override
    public void onUpdate(long delta) {
        //camera.rotate(0, 0.0005f * delta);
    }

    public boolean isShaded() {
        return shadowMap != null;
    }

    public ShadowMap getShadowMap() {
        return shadowMap;
    }

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }

    public Vector3f getAmColor() {
        return amColor;
    }

    public void setAmColor(Vector3f amColor) {
        this.amColor = amColor;
    }

    public Vector3f getDirection() {
        return direction;
    }

    public void setDirection(Vector3f direction) {
        this.direction = direction;
    }

}
