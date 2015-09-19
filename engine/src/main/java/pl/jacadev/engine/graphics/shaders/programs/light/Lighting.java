package pl.jacadev.engine.graphics.shaders.programs.light;

/**
 * @author Jaca777
 *         Created 28.12.14 at 15:59
 */
public class Lighting {
    public static final int MAX_LIGHTS = 10;
    private SpotLightSource[] sources = new SpotLightSource[MAX_LIGHTS];
    private DirectionalLight directionalLight;
    private int numLights = 0;

    public Lighting(SpotLightSource... lightSources) {
        this.numLights = lightSources.length;
        System.arraycopy(lightSources, 0, this.sources, 0, numLights);
    }

    public Lighting() {
    }

    public void merge(Lighting lighting) {
        SpotLightSource[] sources = lighting.getSources();
        System.arraycopy(sources, 0, this.sources, numLights, sources.length);
        this.numLights += sources.length;
    }

    public void clear() {
        this.numLights = 0;
    }

    public void addLightSource(SpotLightSource source) {
        this.sources[this.numLights++] = source;
    }

    public int getNumLights() {
        return numLights;
    }

    public SpotLightSource[] getSources() {
        return sources;
    }

    public SpotLightSource getSource(int i) {
        return this.sources[i];
    }

    public void setDirectionalLight(DirectionalLight directionalLight) {
        this.directionalLight = directionalLight;
    }

    public DirectionalLight getDirectionalLight() {
        return directionalLight;
    }
}
