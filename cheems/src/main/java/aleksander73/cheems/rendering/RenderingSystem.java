package aleksander73.cheems.rendering;

import android.opengl.GLES20;

import aleksander73.cheems.core.GameEngine;
import aleksander73.cheems.core.System;

public class RenderingSystem extends System {
    private SurfaceView surfaceView;

    public RenderingSystem(GameEngine gameEngine, SurfaceView surfaceView) {
        super(gameEngine);
        this.surfaceView = surfaceView;
        this.surfaceView.setRenderingSystem(this);
    }

    @Override
    public void initialize() {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glDepthFunc(GLES20.GL_LEQUAL);
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        this.setReady(true);
    }

    public void setViewport(int width, int height) {
        GLES20.glViewport(0, 0, width, height);

    }

    public void requestRender() {
        surfaceView.requestRender();
    }

    public void clearScreen() {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
    }

    public void runOnOpenGLThread(Runnable runnable) {
        surfaceView.queueEvent(runnable);
    }
}
