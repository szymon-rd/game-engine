package pl.jacadev.engine.graphics.shaders.programs.identity;

import pl.jacadev.engine.graphics.shaders.Program;

import java.io.InputStream;

/**
 * @author Jaca777
 *         Created 2015-06-25 at 15
 */
public class IdentityProgram extends Program {

    private static InputStream FRAGMENT_SHADER = IdentityProgram.class.getResourceAsStream("frag.glsl");
    private static InputStream VERTEX_SHADER = IdentityProgram.class.getResourceAsStream("vert.glsl");

    private static final String[] OUT_NAMES = new String[]{"fragData"};

    public int aVertex;
    public int aTexCoord;

    public IdentityProgram() {
        super(VERTEX_SHADER, FRAGMENT_SHADER, OUT_NAMES);
        this.aTexCoord = getAttributeLocation("inTexCoord");
        this.aVertex = getAttributeLocation("inVertex");
    }


    @Override
    public void use(){
        super.use();
    }

}
