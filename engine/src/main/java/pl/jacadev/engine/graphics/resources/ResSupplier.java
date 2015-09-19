package pl.jacadev.engine.graphics.resources;

import java.io.InputStream;
import java.net.URL;

/**
 * @author Jaca777
 *         Created 25.04.15 at 23:10
 */
public class ResSupplier {
    private static final ClassLoader CLASS_LOADER = ResSupplier.class.getClassLoader();

    public static InputStream getAsStream(String resourcePath){
        return CLASS_LOADER.getResourceAsStream(resourcePath);
    }

    public static URL getURL(String resourcePath){
        return CLASS_LOADER.getResource(resourcePath);
    }
}
