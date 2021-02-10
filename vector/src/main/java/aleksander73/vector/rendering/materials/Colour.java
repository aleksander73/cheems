package aleksander73.vector.rendering.materials;

import aleksander73.math.linear_algebra.Vector4d;

public class Colour {
    public static final Colour DEFAULT = new Colour(255, 255, 255, 255);
    private final Vector4d rgba;

    public Colour(int red, int green, int blue, int alpha) {
        rgba = new Vector4d(red, green, blue, alpha);
    }

    public Vector4d normalize() {
        return rgba.mul(1.0f / 255.0f).toVector4d();
    }

    public int getRed() {
        return (int) rgba.getW();
    }

    public void setRed(int red) {
        rgba.getValues()[0] = red;
    }

    public int getGreen() {
        return (int) rgba.getX();
    }

    public void setGreen(int green) {
        rgba.getValues()[1] = green;
    }

    public int getBlue() {
        return (int) rgba.getY();
    }

    public void setBlue(int blue) {
        rgba.getValues()[2] = blue;
    }

    public int getAlpha() {
        return (int) rgba.getZ();
    }

    public void setAlpha(int alpha) {
        rgba.getValues()[3] = alpha;
    }
}
