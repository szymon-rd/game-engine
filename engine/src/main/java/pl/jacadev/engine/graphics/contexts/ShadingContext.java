package pl.jacadev.engine.graphics.contexts;

import pl.jacadev.engine.camera.Camera;
import pl.jacadev.engine.graphics.shaders.effects.blur.Blur;
import pl.jacadev.engine.graphics.shaders.programs.cubemap.CubemapProgram;
import pl.jacadev.engine.graphics.shaders.programs.light.LightProgram;
import pl.jacadev.engine.graphics.shaders.programs.light.Lighting;
import pl.jacadev.engine.graphics.shaders.programs.light.ShadowMap;
import pl.jacadev.engine.graphics.shaders.programs.shadow.ShadowProgram;
import pl.jacadev.engine.graphics.shaders.programs.terrain.TerrainProgram;
import pl.jacadev.engine.graphics.shaders.programs.water.WaterSurface;
import pl.jacadev.engine.graphics.shaders.programs.water.WtrSrfProgram;
import pl.jacadev.engine.graphics.textures.RawTexture2D;
import pl.jacadev.engine.graphics.utility.math.ProjectionMatrix;

/**
 * @author Jaca777
 *         Created 2015-07-10 at 16
 */
public class ShadingContext {

    private WtrSrfProgram waterProgram;
    private LightProgram lightProgram;
    private TerrainProgram terrainProgram;
    private CubemapProgram cubemapProgram;
    private Blur blurFilter;

    private ShadowProgram shadowProgram;

    public ShadingContext() {
        this.waterProgram = new WtrSrfProgram();
        this.lightProgram = new LightProgram();
        lightProgram.setUniformf(lightProgram.uWaterLvl, WaterSurface.WATER_LEVEL);
        this.terrainProgram = new TerrainProgram();
        terrainProgram.setUniformf(terrainProgram.uWaterLvl, WaterSurface.WATER_LEVEL);
        this.cubemapProgram = new CubemapProgram();
        this.blurFilter = new Blur();
        this.shadowProgram = new ShadowProgram();
    }

    public WtrSrfProgram getWaterProgram() {
        return waterProgram;
    }

    public LightProgram getLightProgram() {
        return lightProgram;
    }

    public TerrainProgram getTerrainProgram() {
        return terrainProgram;
    }

    public CubemapProgram getCubemapProgram() {
        return cubemapProgram;
    }

    public ShadowProgram getShadowProgram() {
        return shadowProgram;
    }

    public Blur getBlurFilter() {
        return blurFilter;
    }

    public void setRenderingFilter(int filter){
        lightProgram.use();
        lightProgram.setUniformi(lightProgram.uRenderState, filter);
        cubemapProgram.use();
        cubemapProgram.setUniformi(cubemapProgram.uRenderState, filter);
        terrainProgram.use();
        terrainProgram.setUniformi(terrainProgram.uRenderState, filter);
        waterProgram.use();
        waterProgram.setUniformi(waterProgram.uRenderState, filter);
    }

    /**
     * Updates the shaders uniforms.
     */

    public void syncPrograms(long delta) {

    }

    public void resize(int width, int height) {
        waterProgram.use();
        waterProgram.setUniformi(waterProgram.uScreenW, width);
        waterProgram.setUniformi(waterProgram.uScreenH, height);
    }

    /**
     * Sets the shader perspective matrix uniform.
     */
    public void useProjection(ProjectionMatrix projectionMatrix) {
        lightProgram.use();
        lightProgram.setUniformMatrix4(lightProgram.uPerspMatrix, projectionMatrix.getMatrix());
        terrainProgram.use();
        terrainProgram.setUniformMatrix4(terrainProgram.uPerspMatrix, projectionMatrix.getMatrix());
        cubemapProgram.use();
        cubemapProgram.setUniformMatrix4(cubemapProgram.uPerspMatrix, projectionMatrix.getMatrix());
        waterProgram.use();
        waterProgram.setUniformMatrix4(waterProgram.uPerspMatrix, projectionMatrix.getMatrix());
    }

    public void useCamera(Camera camera) {

        waterProgram.use();
        waterProgram.setUniformV3(waterProgram.uCamPos, camera.getPos());
        waterProgram.setUniformMatrix4(waterProgram.uCameraMatrix, camera.cameraMatrix());

        lightProgram.use();
        lightProgram.setUniformV3(lightProgram.uCameraPos, camera.getPos());
        lightProgram.setUniformMatrix4(lightProgram.uCameraMatrix, camera.cameraMatrix());
        lightProgram.setUniformMatrix4(lightProgram.uMCameraMatrix, camera.mirrorCameraMatrix());

        terrainProgram.use();
        terrainProgram.setUniformV3(terrainProgram.uCameraPos, camera.getPos());
        terrainProgram.setUniformMatrix4(terrainProgram.uCameraMatrix, camera.cameraMatrix());
        terrainProgram.setUniformMatrix4(terrainProgram.uMCameraMatrix, camera.mirrorCameraMatrix());

        cubemapProgram.use();
        cubemapProgram.setUniformMatrix4(cubemapProgram.uRotMatrix, camera.rotationMatrix());
        cubemapProgram.setUniformMatrix4(cubemapProgram.uMRotMatrix, camera.mirrorRotationMatrix());
    }

    public void useLighting(Lighting lighting){
        waterProgram.use();
        waterProgram.useLighting(lighting);

        lightProgram.use();
        lightProgram.useLighting(lighting);

        terrainProgram.use();
        terrainProgram.useLighting(lighting);
    }

    public void useShadowMap(ShadowMap shadowMap) {
        terrainProgram.use();
        terrainProgram.setUniformMatrix4(terrainProgram.uDirLightPersp, shadowMap.getCamera().getProjection().getMatrix());
        terrainProgram.setUniformMatrix4(terrainProgram.uDirLightCamera, shadowMap.getCamera().cameraMatrix());
        RawTexture2D map = shadowMap.getShadowMap();
        terrainProgram.useTexture(map.getTexture(), map.getType(), terrainProgram.dirLightShadowSampler);
    }
}
