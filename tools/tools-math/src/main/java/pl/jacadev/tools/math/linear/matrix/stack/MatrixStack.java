package pl.jacadev.tools.math.linear.matrix.stack;

import pl.jacadev.tools.math.linear.matrix.Matrix44;
import pl.jacadev.tools.math.linear.matrix.MutableMatrix44;
import pl.jacadev.tools.math.linear.vector.Vector3;

/**
 * @author Jaca777
 *         Created 2015-09-26 at 20
 */
public abstract class MatrixStack extends MutableMatrix44 {

    public abstract void push();

    public abstract void pop();
}
