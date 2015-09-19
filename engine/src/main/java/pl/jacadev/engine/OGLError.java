package pl.jacadev.engine;

/**
 * @author Jaca777
 *         Created 2015-08-13 at 13
 */
public class OGLError extends Error{
    private int errorCode;
    private String errorMessage;

    public OGLError(int errorCode, String errorMessage) {
        super(String.format("OpenGL error has occurred. Code: %d Error: %s", errorCode, errorMessage));
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
