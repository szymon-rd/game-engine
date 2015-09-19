package pl.jacadev.engine.input;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.Arrays;

/**
 * @author Jaca777
 *         Created 22.12.14 at 22:51
 */
public class MouseInput {

    private static final byte PRESSED = 1;
    private static final byte RELEASED = 2;
    private static byte[] buttons;
    private static int xDelta, yDelta;
    private static int scrollDelta;
    private static boolean grabbed = true;

    public static void init(){
        try {
            Mouse.create();
            Mouse.setGrabbed(true);
            buttons = new byte[Mouse.getButtonCount()];
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
    }

    public static void update() {
        if (grabbed) {
            if (Mouse.isGrabbed()) {
                if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
                    Mouse.setGrabbed(false);
            } else if (wasButtonPressed(0))
                Mouse.setGrabbed(true);
        }

        xDelta = Mouse.getDX();
        yDelta = Mouse.getDY();
        scrollDelta = Mouse.getDWheel();

        Arrays.fill(buttons, (byte) 0);
        while(Mouse.next()){
            if(Mouse.getEventButton() > -1) buttons[Mouse.getEventButton()] = Mouse.getEventButtonState() ? PRESSED : RELEASED;
        }
    }

    public static void setGrabbed(boolean grabbed) {
        if (grabbed) Mouse.setGrabbed(true);
        else Mouse.setGrabbed(false);
        MouseInput.grabbed = grabbed;
    }

    public static int getXDelta() {
        return xDelta;
    }

    public static int getYDelta() {
        return yDelta;
    }

    public static int getScrollDelta() {
        return scrollDelta;
    }

    public static int getMouseX() {
        return Mouse.getX();
    }

    public static int getMouseY() {
        return Mouse.getY();
    }

    public static boolean isButtonDown(int button){
        return Mouse.isButtonDown(button);
    }

    public static boolean wasButtonPressed(int button){
        return buttons[button] == PRESSED;
    }

    public static boolean wasButtonReleased(int button){
        return buttons[button] == RELEASED;
    }

    public static boolean isGrabbed(){
        return Mouse.isGrabbed();
    }
}
