package pl.jacadev.engine.graphics.testing.ogldebug;

import org.objectweb.asm.tree.*;

import java.util.List;
import java.util.regex.Pattern;

import static org.objectweb.asm.Opcodes.INVOKESTATIC;

/**
 * Created by Szymon on 2015-05-30.
 */
public class CallsInjector {


    public static void inject(ClassNode clazz, String path) {
        List<MethodNode> methods = clazz.methods;
        for (MethodNode method : methods) {
            injectCalls(method, path);
        }
    }

    private static final MethodInsnNode TRACER_CALL = new MethodInsnNode(INVOKESTATIC, "pl/jacadev/engine/graphics/opengl/testing/ogldebug/Tracer", "checkError", "(Ljava/lang/String;)V", false);

    private static void injectCalls(MethodNode method, String classPath) {
        AbstractInsnNode[] instructions = method.instructions.toArray();
        InsnList newStack = new InsnList();
        newStack.add(method.instructions);
        for (AbstractInsnNode instruction : instructions) {
            if (instruction.getOpcode() == INVOKESTATIC) {
                MethodInsnNode methodInvoc = (MethodInsnNode) instruction;
                if (isOglInstruction(methodInvoc)) {
                    InsnList tracerCall = new InsnList();
                    tracerCall.add(new LdcInsnNode(methodInvoc.name));
                    tracerCall.add(TRACER_CALL);
                    newStack.insert(instruction, tracerCall);
                }
            }
        }
        method.instructions = newStack;
    }

    private static final Pattern GL_PATTERN = Pattern.compile("org/lwjgl/.+");

    private static boolean isOglInstruction(MethodInsnNode methodInvok) {
        return GL_PATTERN.matcher(methodInvok.owner).matches() && !methodInvok.name.equals("glGetError");
    }

}
