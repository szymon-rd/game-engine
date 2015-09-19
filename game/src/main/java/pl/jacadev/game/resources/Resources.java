package pl.jacadev.game.resources;

import pl.jacadev.engine.graphics.resources.ResSupplier;

import java.io.InputStream;

/**
 * @author Jaca777
 *         Created 18.12.14 at 16:30
 */
public class Resources {

    private static final String RES_PATH = "pl/jacadev/game/resources";
    private static final String[] CUBEMAP_ELEMENTS = new String[]{
            "posx",
            "negx",
            "posy",
            "negy",
            "posz",
            "negz"
    };

    public static InputStream getAsStream(String path) {
        return ResSupplier.getAsStream(RES_PATH + "/" + path);
    }

    public static InputStream[] getCubemapStreams(String cubemapPath, String format){
        InputStream[] streams = new InputStream[6];
        for (int i = 0; i < CUBEMAP_ELEMENTS.length; i++) {
            streams[i] = getAsStream("textures/cubemaps/" + cubemapPath + "/" + CUBEMAP_ELEMENTS[i] + "." + format);
        }
        return streams;
    }
}
