package pl.jacadev.engine.input;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;

import java.util.Arrays;

/**
 * @author Jaca777
 *         Created 22.12.14 at 22:01
 */
public class KeyboardInput {

    //TODO Do something with it ffs.

    public static void init(){
        try {
            Keyboard.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
    }

    private static final byte PRESSED = 1;
    private static final byte RELEASED = 2;
    private static final byte[] keys = new byte[Keyboard.KEYBOARD_SIZE];

    public static void update(){
        Arrays.fill(keys, (byte) 0);
        while(Keyboard.next()){
            keys[Keyboard.getEventKey()] = Keyboard.getEventKeyState() ? PRESSED : RELEASED;
        }
    }

    /**
     * @return Whether the key is down.
     */
    public static boolean isKeyDown(int key){
        return Keyboard.isKeyDown(key);
    }

    /**
     * @return Whether the key was pressed.
     */
    public static boolean wasKeyPressed(int key){
        return keys[key] == PRESSED;
    }

    /**
     * @return Whether the key was released.
     */
    public static boolean wasKeyReleased(int key){
        return keys[key] == RELEASED;
    }
}
