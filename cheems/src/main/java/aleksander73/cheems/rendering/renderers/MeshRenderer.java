package aleksander73.cheems.rendering.renderers;

import aleksander73.cheems.rendering.mesh.Mesh;

public class MeshRenderer extends Renderer {
    private Mesh mesh;

    public MeshRenderer(Mesh mesh) {
        this.mesh = mesh;
    }

    @Override
    public void render() {}
}
