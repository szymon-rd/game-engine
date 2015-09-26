package pl.jacadev.tools.math.linear.matrix;

import java.nio.FloatBuffer;

/**
 * @author Jaca777
 *         Created 2015-09-21 at 18
 */
public abstract class Matrix {
    private int columns, rows;

    public Matrix(int columns, int rows) {
        this.columns = columns;
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public abstract void store(FloatBuffer buffer);

}
