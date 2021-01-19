package aleksander73.cheems.rendering;

import android.content.Context;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import aleksander73.cheems.core.Game;

public class SurfaceView extends GLSurfaceView {
    private RenderingSystem renderingSystem;

    public SurfaceView(Context context) {
        super(context);
        this.setEGLContextClientVersion(2);
        this.setRenderer(new GLRenderer());
        this.setRenderMode(RENDERMODE_WHEN_DIRTY);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    private class GLRenderer implements GLSurfaceView.Renderer {
        @Override
        public void onSurfaceCreated(GL10 glUnused, EGLConfig eglConfig) {
            renderingSystem.initialize();
        }

        @Override
        public void onSurfaceChanged(GL10 glUnused, int width, int height) {
            renderingSystem.setViewport(width, height);
        }

        @Override
        public void onDrawFrame(GL10 glUnused) {
            Game game = renderingSystem.getGameEngine().getGame();
            if(game == null || !game.isRunning()) {
                return;
            }
            renderingSystem.clearScreen();
            game.render();
        }
    }

    public void setRenderingSystem(RenderingSystem renderingSystem) {
        this.renderingSystem = renderingSystem;
    }
}
