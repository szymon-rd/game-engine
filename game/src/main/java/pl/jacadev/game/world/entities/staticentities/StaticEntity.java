package pl.jacadev.game.world.entities.staticentities;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;
import pl.jacadev.engine.graphics.contexts.OGLEngineContext;
import pl.jacadev.engine.graphics.contexts.RenderingContext;
import pl.jacadev.engine.graphics.shaders.programs.shadow.ShadowProgram;
import pl.jacadev.game.world.ChunkComponent;
import pl.jacadev.game.world.entities.staticentities.worldcomponents.StaticEntityModel;
import pl.jacadev.engine.graphics.Unloadable;
import pl.jacadev.engine.graphics.shaders.programs.light.LightProgram;
import pl.jacadev.engine.graphics.utility.math.MatrixStack;

/**
 * @author Jaca777
 *         Created 04.01.15 at 21:00
 */
public class StaticEntity extends ChunkComponent implements Unloadable {
    protected StaticEntityModel model;
    protected boolean calcY;

    public StaticEntity(StaticEntityModel model, Vector3f pos, boolean calcY) {
        super(pos);
        this.model = model;
        this.pos = pos;
        this.calcY = calcY;
    }

    /**
     * Draws the static entity.
     */
    public void onDraw(OGLEngineContext context) {
        RenderingContext.RenderingState rendeeringState = context.getRenderingContext().getRenderingState();
        if (rendeeringState == RenderingContext.RenderingState.STANDARD) {
            MatrixStack matrixStack = context.getRenderingContext().getCurrentMatrixStack();
            matrixStack.push();
            matrixStack.mul(effects.getTransformation().getMatrix());
            LightProgram lShader = context.getShadingContext().getLightProgram();
            lShader.use();
            lShader.setUniformf(lShader.uBrightness, effects.getBrightness());
            lShader.setUniformMatrix4(lShader.uMMatrix, matrixStack.top());
            lShader.setUniformMatrix4(lShader.uRotMatrix, matrixStack.rot());
            model.bind(context.getBindings());
            model.draw();
            matrixStack.pop();
        } else if(rendeeringState == RenderingContext.RenderingState.SHADOW_MAP){
            MatrixStack matrixStack = context.getRenderingContext().getCurrentMatrixStack();
            matrixStack.push();
            matrixStack.mul(effects.getTransformation().getMatrix());
            ShadowProgram shadowProgram = context.getShadingContext().getShadowProgram();
            shadowProgram.use();
            shadowProgram.setUniformMatrix4(shadowProgram.uMMatrix, matrixStack.top());
            model.bind(context.getBindings());
            model.draw();
            matrixStack.pop();
        }
    }


    /**
     * Updates the static entity.
     */
    @Override
    public void onUpdate(long delta) {
        getEffects().getTransformation().setTranslation(this.pos);
    }


    public StaticEntityModel getModel() {
        return model;
    }

    /**
     * Unloads the static entity.
     */
    @Override
    public void unload() {
        model.unload();
    }

    public boolean isCalcY() {
        return calcY;
    }
}
