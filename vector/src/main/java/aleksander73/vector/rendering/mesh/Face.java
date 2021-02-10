package aleksander73.vector.rendering.mesh;

public class Face {
    public static final int VERTICES_COUNT = 3;
    private final Vertex[] vertices;

    public Face(Vertex[] vertices) {
        this.vertices = vertices;
    }

    public Vertex[] getVertices() {
        return vertices;
    }
}
