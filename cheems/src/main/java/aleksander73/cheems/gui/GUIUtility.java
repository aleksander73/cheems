package aleksander73.cheems.gui;

import aleksander73.cheems.rendering.Camera;
import aleksander73.math.linear_algebra.Vector2d;

public class GUIUtility {
    public static Vector2d screenToNDC(Vector2d dimensions) {
        float ar = Camera.getActiveCamera().getWidth() / Camera.getActiveCamera().getHeight();
        return new Vector2d(dimensions.getX(), dimensions.getY() * ar);
    }
}
