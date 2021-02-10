package aleksander73.vector.gui;

import aleksander73.vector.core.GameObject;
import aleksander73.vector.core.Transform;
import aleksander73.vector.rendering.Shaders;
import aleksander73.vector.rendering.materials.Colour;
import aleksander73.vector.rendering.materials.Material;
import aleksander73.vector.rendering.materials.Texture;
import aleksander73.vector.rendering.mesh.Mesh;
import aleksander73.vector.rendering.renderers.MeshRenderer;
import aleksander73.math.linear_algebra.Vector2d;
import aleksander73.math.linear_algebra.Vector3d;

public class GUIElement extends GameObject {
    private float width;
    private float height;
    private int layer;

    public GUIElement(String name, Vector2d position, Vector2d dimensions, Colour colour, Texture texture) {
        this(name, position, dimensions, 0, colour, texture);
    }

    public GUIElement(String name, Vector2d position, Vector2d dimensions, int layer, Colour colour, Texture texture) {
        super(name);
        width = dimensions.getX();
        height = dimensions.getY();
        this.layer = layer;
        Transform transform = new Transform(new Vector3d(position.getX(), position.getY(), 0.0f));
        Mesh mesh = Mesh.generateGUITexture(width, height);
        Material material = new Material(colour, texture, Shaders.getGuiShader());
        MeshRenderer meshRenderer = new MeshRenderer(mesh);
        this.addComponents(transform, mesh, material, meshRenderer);
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public int getLayer() {
        return layer;
    }
}
