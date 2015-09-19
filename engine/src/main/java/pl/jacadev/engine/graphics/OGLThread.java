package pl.jacadev.engine.graphics;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.GLU;
import pl.jacadev.engine.EngineContext;
import pl.jacadev.engine.EngineThread;
import pl.jacadev.engine.OGLError;
import pl.jacadev.engine.Timer;
import pl.jacadev.engine.graphics.contexts.OGLEngineContext;
import pl.jacadev.engine.graphics.pipeline.OGLRenderingPipeline;

import static org.lwjgl.opengl.GL11.*;

/**
 * @author Jaca777
 *         Created 10.05.15 at 13:22
 */
public class OGLThread extends EngineThread {
    private OGLRenderingPipeline pipeline;
    private DisplayProps displayProps;
    private OGLEngineContext context;
    private final Object contextMutex = new Object();

    public OGLThread(Timer timer, OGLRenderingPipeline pipeline, DisplayProps displayProps) {
        super(timer);
        this.pipeline = pipeline;
        this.displayProps = displayProps;
    }

    @Override
    protected void init() {
        try {
            Display.setDisplayMode(displayProps.displayMode);
            //Display.setFullscreen(true);
            Display.setVSyncEnabled(true);
            Display.setTitle(displayProps.title);
            Display.setResizable(displayProps.resizable);
            Display.create(displayProps.format, displayProps.attribs);
        } catch (LWJGLException e) {
            throw new RuntimeException(e);
        }
        this.context = new OGLEngineContext(this);
        synchronized (contextMutex) {contextMutex.notify();}
        bind(pipeline);
        initOGL();
    }

    /**
     * OpenGL capabilities that are enabled by default.
     */
    private static final int[] DEFAULT_CAPABILITIES = {
            GL_CULL_FACE,
            GL_DEPTH_TEST,
            GL_BLEND,
            GL32.GL_TEXTURE_CUBE_MAP_SEAMLESS
    };

    private void initOGL() {
        for (int cap : DEFAULT_CAPABILITIES) {
            glEnable(cap);
        }
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    @Override
    public EngineContext getContext() {
        return this.context;
    }

    @Override
    protected void onIteration(long delta) {
        if (Display.isCloseRequested()) interrupt();
        if(Display.wasResized()){
            pipeline.resize(Display.getWidth(), Display.getHeight());
            context.getShadingContext().resize(Display.getWidth(), Display.getHeight());
            displayProps.setDisplayMode(Display.getDisplayMode());
        }
        checkerror();
        Display.update();
    }

    public OGLRenderingPipeline getPipeline() {
        return pipeline;
    }

    public DisplayProps getDisplayProps() {
        return displayProps;
    }

    public void awaitContext() {
        synchronized (this.contextMutex){
            try {
                this.contextMutex.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Checks whether OpenGL has thrown any error.
     */
    public static void checkerror() {
        int error = GL11.glGetError();
        if (error != 0) {
            throw new OGLError(error, GLU.gluErrorString(error));
        }
    }

    public static class DisplayProps {
        private DisplayMode displayMode;
        private String title;
        private boolean resizable;
        private PixelFormat format;
        private ContextAttribs attribs;

        public DisplayProps(DisplayMode displayMode, String title, boolean resizable, PixelFormat format, ContextAttribs attribs) {
            this.displayMode = displayMode;
            this.title = title;
            this.resizable = resizable;
            this.format = format;
            this.attribs = attribs;
        }

        public DisplayMode getDisplayMode() {
            return displayMode;
        }

        private DisplayProps setDisplayMode(DisplayMode displayMode) {
            this.displayMode = displayMode;
            return this;
        }

        public String getTitle() {
            return title;
        }

        private DisplayProps setTitle(String title) {
            this.title = title;
            return this;
        }

        public boolean isResizable() {
            return resizable;
        }

        private DisplayProps setResizable(boolean resizable) {
            this.resizable = resizable;
            return this;
        }

        public PixelFormat getFormat() {
            return format;
        }

        private DisplayProps setFormat(PixelFormat format) {
            this.format = format;
            return this;
        }

        public ContextAttribs getAttribs() {
            return attribs;
        }

        private DisplayProps setAttribs(ContextAttribs attribs) {
            this.attribs = attribs;
            return this;
        }
    }
}