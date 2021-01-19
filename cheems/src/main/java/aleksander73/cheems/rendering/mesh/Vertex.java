package aleksander73.cheems.rendering.mesh;

import aleksander73.math.linear_algebra.Vector3d;

public class Vertex {
    public static final int VERTEX_ELEMENTS = 5;

    private final int id;
    private Vector3d position;

    public Vertex(int id, Vector3d position) {
        this.id = id;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public Vector3d getPosition() {
        return position;
    }
}
