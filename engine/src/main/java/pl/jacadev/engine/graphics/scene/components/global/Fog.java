package pl.jacadev.engine.graphics.scene.components.global;

import pl.jacadev.engine.graphics.contexts.OGLEngineContext;
import pl.jacadev.engine.graphics.shaders.programs.light.LightProgram;
import pl.jacadev.engine.graphics.shaders.programs.terrain.TerrainProgram;

/**
 * @author Jaca777
 *         Created 2015-07-19 at 16
 */
public class Fog extends GlobalComponent {

    private float density;
    private float gradient;

    public Fog(float density, float gradient) {
        this.density = density;
        this.gradient = gradient;
    }

    @Override
    public void onUpdate(long delta) {

    }

    @Override
    public void onDraw(OGLEngineContext engineContext) {
        LightProgram lightShader = engineContext.getShadingContext().getLightProgram();
        lightShader.use();
        lightShader.setUniformf(lightShader.uFogDensity, density);
        lightShader.setUniformf(lightShader.uFogGradient, gradient);

        TerrainProgram terrainShader = engineContext.getShadingContext().getTerrainProgram();
        terrainShader.use();
        terrainShader.setUniformf(terrainShader.uFogDensity, density);
        terrainShader.setUniformf(terrainShader.uFogGradient, gradient);

/*        WtrSrfProgram wtrSrfProgram = engineContext.getShadingContext().getWaterProgram();
        wtrSrfProgram.use();
        wtrSrfProgram.setUniformf(wtrSrfProgram.uFogDensity, density);
        wtrSrfProgram.setUniformf(wtrSrfProgram.uFogGradient, gradient);*/
    }

    public void setDensity(float density) {
        this.density = density;
    }

    public void setGradient(float gradient) {
        this.gradient = gradient;
    }

    public float getDensity() {
        return density;
    }

    public float getGradient() {
        return gradient;
    }
}
