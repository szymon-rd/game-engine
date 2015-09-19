package pl.jacadev.engine.graphics.testing.ogldebug;


import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Szymon on 2015-05-30.
 */
public class Tracer {
    private final static Logger GL_ERROR_LOGGER = Logger.getLogger("OpenGL Error Logger");
    public static void checkError(String insn){
        int error = GL11.glGetError();//A jak to zrobiæ? xd no usun caly kodd z javy  A czemu tak? Tj
        //czysciej a jak cos zjebiesz to se revertniesz Ale jak toz robiæ? xD Tj. TO bym musia³ teraz wszystko przekopiowaæ i pousuwaæ
        //dobra chuj to dodaj swoj kod ahac sec bo nie pamieta
        if (error != 0) {
            GL_ERROR_LOGGER.log(Level.WARNING, String.format("OpenGL Error: %d. Message: %s \n Occured while executing: %s", error, GLU.gluErrorString(error), insn));
            Thread.dumpStack();
        }
    }
}
