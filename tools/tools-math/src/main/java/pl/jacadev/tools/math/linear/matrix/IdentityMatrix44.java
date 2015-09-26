package pl.jacadev.tools.math.linear.matrix;

import pl.jacadev.tools.math.linear.vector.Vector3;

/**
 * @author Jaca777
 *         Created 2015-09-24 at 19
 */
public class IdentityMatrix44 extends Matrix44 {

    private static final float[] MATRIX = {
            1.0f, 0.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 1.0f, 0.0f,
            0.0f, 0.0f, 0.0f, 1.0f
    };

    public IdentityMatrix44() {
        super(MATRIX);
    }

}
