package aleksander73.cheems.input;

import android.view.View;

import aleksander73.cheems.core.GameEngine;
import aleksander73.cheems.core.System;
import aleksander73.cheems.rendering.SurfaceView;
import aleksander73.cheems.rendering.Transformation;
import aleksander73.math.linear_algebra.Matrix;
import aleksander73.math.linear_algebra.Vector2d;
import aleksander73.math.linear_algebra.Vector3d;

public class InputSystem extends System {
    private final SurfaceView surfaceView;
    private Matrix screenToNDCMatrix;

    public InputSystem(GameEngine gameEngine, SurfaceView surfaceView) {
        super(gameEngine);
        this.surfaceView = surfaceView;
        this.setReady(true);
    }

    public void initScreenToNDCMatrix(int width, int height) {
        Matrix center = Transformation.translationMatrix(new Vector3d(-width / 2.0f, -height / 2.0f, 0.0f));
        Matrix flipY = Transformation.scaleMatrix(new Vector3d(1.0f, -1.0f, 1.0f));
        Matrix normalize = Transformation.scaleMatrix(new Vector3d(1.0f / (width / 2.0f), 1.0f / (height / 2.0f), 0.0f));
        screenToNDCMatrix = normalize.mul(flipY.mul(center));
    }

    public Vector2d screenToNDC(Vector2d v) {
        Vector3d vec3d = new Vector3d(v.getX(), v.getY(), 0.0f);
        return vec3d.transform(screenToNDCMatrix).toVector2d();
    }

    public void setOnTouchListener(View.OnTouchListener listener) {
        surfaceView.setOnTouchListener(listener);
    }
}
