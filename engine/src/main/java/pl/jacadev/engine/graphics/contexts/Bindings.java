package pl.jacadev.engine.graphics.contexts;

import org.lwjgl.opengl.*;

/**
 * @author Jaca777
 *         Created 2015-07-31 at 15
 */
public class Bindings {

    //Sorted with the frequency of using (very, very micro optimization, but always better)
    private BindingsMap textureBindings = new BindingsMap(GL30.GL_TEXTURE_2D_ARRAY, GL11.GL_TEXTURE_2D, GL31.GL_TEXTURE_RECTANGLE, GL32.GL_TEXTURE_2D_MULTISAMPLE, GL13.GL_TEXTURE_CUBE_MAP,
            GL12.GL_TEXTURE_3D, GL11.GL_TEXTURE_1D, GL30.GL_TEXTURE_1D_ARRAY);

    public void bindTexture(int target, int texture) {
        if (!textureBindings.checkAndUpdate(target, texture)) GL11.glBindTexture(target, texture);
    }

    public void setBindedTexture(int target, int texture) {
        textureBindings.set(target, texture);
    }

    private int bindedVAO = 0;

    public void bindVAO(int vao) {
        if (bindedVAO != vao) {
            GL30.glBindVertexArray(vao);
            bindedVAO = vao;
        }
    }

    /**
     * Very simple implementation of map.
     */
    private class BindingsMap {
        private int[] indicesArray;
        private int[] valuesArray;

        public BindingsMap(int... indicesArray) {
            this.indicesArray = indicesArray;
            this.valuesArray = new int[indicesArray.length];
        }

        private int findIndex(int oglInt) {
            for (int i = 0; i < indicesArray.length; i++) {
                if (indicesArray[i] == oglInt) return i;
            }
            return -1;
        }

        public int get(int oglInt) {
            return valuesArray[findIndex(oglInt)];
        }

        public void set(int oglInt, int val) {
            valuesArray[findIndex(oglInt)] = val;
        }

        public boolean checkAndUpdate(int oglInt, int val) {
            if (get(oglInt) != val) {
                set(oglInt, val);
                return false;
            } else return true;
        }
    }
}
