package pl.jacadev.game;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.vector.Vector3f;
import pl.jacadev.engine.Application;
import pl.jacadev.engine.Stage;
import pl.jacadev.engine.camera.Camera;
import pl.jacadev.engine.camera.FPPCamera;
import pl.jacadev.engine.camera.control.DefaultFPPControl;
import pl.jacadev.engine.graphics.models.VAOModel;
import pl.jacadev.engine.graphics.OGLThread;
import pl.jacadev.engine.graphics.resources.ImageDecoder;
import pl.jacadev.engine.graphics.resources.PNGDecoder;
import pl.jacadev.engine.graphics.scene.Component;
import pl.jacadev.engine.graphics.scene.Scene;
import pl.jacadev.engine.graphics.scene.components.DrawableComponent;
import pl.jacadev.engine.graphics.scene.components.SimpleContainer;
import pl.jacadev.engine.graphics.scene.components.global.Fog;
import pl.jacadev.engine.graphics.scene.components.global.Skybox;
import pl.jacadev.engine.graphics.shaders.programs.light.DirectionalLight;
import pl.jacadev.engine.graphics.shaders.programs.light.SpotLightSource;
import pl.jacadev.engine.graphics.testing.ModelsFactory;
import pl.jacadev.engine.graphics.testing.ogldebug.DebugClassLoader;
import pl.jacadev.engine.graphics.textures.Cubemap;
import pl.jacadev.engine.graphics.textures.Texture2D;
import pl.jacadev.engine.graphics.utility.math.PerspectiveMatrix;
import pl.jacadev.engine.input.KeyboardInput;
import pl.jacadev.game.resmanagement.ResourceManager;
import pl.jacadev.game.resmanagement.manageables.loaders.model.ModelDesc;
import pl.jacadev.game.resources.Resources;
import pl.jacadev.game.world.World;
import pl.jacadev.game.world.entities.Player;
import pl.jacadev.game.world.entities.staticentities.StaticEntity;
import pl.jacadev.game.world.entities.staticentities.worldcomponents.StaticEntityModel;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Jaca777
 *         Created 13.12.14 at 14:59
 *         TEMPORARY CODE, USED FOR DEBUGGING.
 */
public class Main extends Application {

    public static final int WIDTH = 1400;
    public static final int HEIGHT = 900;
    public static final float RUN_SPEED = 0.0037f;
    public static final float PLANE_SPEED = 0.1f;
    public static final float ROTATION_SPEED = (float) Math.toRadians(0.01);
    private static final PixelFormat DEFAULT_PIXEL_FORMAT = new PixelFormat(8, 8, 0, 8);
    private static final ContextAttribs DEFAULT_CONTEXT_ATTRIBS = new ContextAttribs(4, 4).withForwardCompatible(true).withProfileCore(true);
    private static final String GL_ERRORS_DEBUG = "-gldebug";
    private static final float Z_NEAR = 0.001f, Z_FAR = 2000;
    private static final float FOV = 65;

    private static final float FOG_DENSITY = 0.004f;
    private static final float FLOG_GRADIENT = 20f;


    public static void main(String... args) throws Exception {
        Set<String> arguments = new HashSet<>(Arrays.asList(args));
        if (arguments.contains(GL_ERRORS_DEBUG)) {
            DebugClassLoader loader = new DebugClassLoader();
            Class clazz = loader.loadClass("pl.jacadev.game.Main");
            clazz.getMethod("main", String[].class).invoke(null);
        } else {
            new Main().launch();
        }
    }

    /**
     * TEMPORARY CODE, USED FOR DEBUGGING.
     */
    @Override
    public void start(Stage stage) {
        SimpleContainer root = new SimpleContainer();
        final Scene scene = new Scene(root);
        final Camera camera = new FPPCamera(new PerspectiveMatrix(FOV, Z_NEAR, Z_FAR, WIDTH, HEIGHT));
        scene.setCamera(camera);
        stage.setScene(scene);

        stage.setDisplayProps(new OGLThread.DisplayProps(new DisplayMode(WIDTH, HEIGHT), "Discworld", true, DEFAULT_PIXEL_FORMAT,
                DEFAULT_CONTEXT_ATTRIBS));
        stage.addTask(ResourceManager.RESOURCE_TASK);
        stage.show();

        stage.getEngineContext().getOglThreadContext().runLater(new Runnable() {
            @Override
            public void run() {
                final SpotLightSource source = new SpotLightSource(new Vector3f(1, 2, 1), new Vector3f(0.9f, 0.9f, 0.7f), 2f, new Vector3f(0.1f, 0.1f, 0.06f), 2f, 0.002f, 0f, false);
                //scene.getLighting().addLightSource(source);
                final DirectionalLight directionalLight = new DirectionalLight(new Vector3f(0.6f, 0.6f, 0.4f), new Vector3f(0.4f, 0.4f, 0.30f), (Vector3f) new Vector3f(1.0f,0.5f, 0.0f).normalise(), true);
                scene.getLighting().setDirectionalLight(directionalLight);

                ImageDecoder.DecodedImage joypadImg = ImageDecoder.decodePNG(Resources.getAsStream("textures/joypad_tex.png"), PNGDecoder.Format.RGBA);
                Texture2D joypadTex = new Texture2D(joypadImg.getW(), joypadImg.getH(), joypadImg.getBuffer());
                VAOModel joypadModel = (VAOModel) ResourceManager.use(new ModelDesc("joypad.obj"));
                StaticEntity joypad = new StaticEntity(new StaticEntityModel(joypadModel, joypadTex, 0, 0), new Vector3f(10, 0, 0), false);

                ImageDecoder.DecodedImage sunImage = ImageDecoder.decodePNG(Resources.getAsStream("textures/sun.png"), PNGDecoder.Format.RGBA);
                Texture2D sunTexture = new Texture2D(sunImage.getH(), sunImage.getW(), sunImage.getBuffer());
                VAOModel sunModel = ModelsFactory.makeSphere(1f, 80, 80).buildVAOModel();
                StaticEntity sun = new StaticEntity(new StaticEntityModel(sunModel, sunTexture, 0, 0), source.getPos(), false);
                sun.setPos(189f, 100f, 80f);
                sun.getEffects().setBrightness(60f);
                sun.getEffects().getTransformation().scale(new Vector3f(10f, 10f, 10f));

                final Player player = new Player(camera.getPos());
                DrawableComponent world = new World(player);

                ImageDecoder.DecodedImagesArray cubemapArray = ImageDecoder.decodePNGs(Resources.getCubemapStreams("clouds", "png"), PNGDecoder.Format.RGBA);
                Cubemap cubemap = new Cubemap(cubemapArray.getW(), cubemapArray.getH(), cubemapArray.getBuffers());
                Skybox skybox = new Skybox(cubemap);

                Fog fog = new Fog(FOG_DENSITY, FLOG_GRADIENT);
                scene.getGlobalDrawableComponents().addAll(skybox, fog);
                scene.getRoot().getChildren().addAll(joypad, sun, world);

                final DefaultFPPControl control = new DefaultFPPControl((FPPCamera) scene.getCamera(), RUN_SPEED, ROTATION_SPEED);
                scene.getGlobalComponents().addAll(new Component() {

                    @Override
                    public void onUpdate(long delta) {
                        directionalLight.onUpdate(delta);
                        if (KeyboardInput.isKeyDown(Keyboard.KEY_UP)) {
                            source.getPos().translate(0, 0, 1f);
                        }
                        if (KeyboardInput.isKeyDown(Keyboard.KEY_DOWN)) {
                            source.getPos().translate(0, 0, -1f);
                        }
                        if (KeyboardInput.isKeyDown(Keyboard.KEY_LEFT)) {
                            source.getPos().translate(1f, 0, 0);
                        }
                        if (KeyboardInput.isKeyDown(Keyboard.KEY_RIGHT)) {
                            source.getPos().translate(-1f, 0, 0);
                        }
                        if (KeyboardInput.isKeyDown(Keyboard.KEY_RSHIFT)) {
                            source.getPos().translate(0, 1f, 0);
                        }
                        if (KeyboardInput.isKeyDown(Keyboard.KEY_RCONTROL)) {
                            source.getPos().translate(0, -1f, 0);
                        }
                        if (KeyboardInput.wasKeyPressed(Keyboard.KEY_M)) {
                            if (control.getSpeed() == RUN_SPEED) control.setSpeed(PLANE_SPEED);
                            else control.setSpeed(RUN_SPEED);
                        }
                    }
                }, control);
            }
        });
    }
}
