package aleksander73.cheems.rendering.mesh;

import aleksander73.math.linear_algebra.Vector2d;
import aleksander73.math.linear_algebra.Vector3d;

public class Vertex {
    public static final int VERTEX_ELEMENTS = 5;

    private final int id;
    private Vector3d position;
    private Vector2d textureCoord;

    public Vertex(int id, Vector3d position) {
        this.id = id;
        this.position = position;
    }

    public Vertex(int id, Vector3d position, Vector2d textureCoord) {
        this(id, position);
        this.textureCoord = textureCoord;
    }

    public int getId() {
        return id;
    }

    public Vector3d getPosition() {
        return position;
    }

    public Vector2d getTextureCoord() {
        return textureCoord;
    }

    public void setTextureCoord(Vector2d textureCoord) {
        this.textureCoord = textureCoord;
    }
}
