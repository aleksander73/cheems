package aleksander73.vector.rendering.renderers;

import android.opengl.GLES20;

import aleksander73.vector.core.GameObject;
import aleksander73.vector.rendering.materials.Material;
import aleksander73.vector.rendering.mesh.Face;
import aleksander73.vector.rendering.mesh.Mesh;
import aleksander73.vector.rendering.shaders.Shader;
import aleksander73.vector.rendering.shaders.ShaderInput;

public class MeshRenderer extends Renderer {
    private final Mesh mesh;

    public MeshRenderer(Mesh mesh) {
        this.mesh = mesh;
    }

    @Override
    public void render() {
        GameObject go = this.getGameObject();

        Shader shader = go.getComponent(Material.class).getShader();
        shader.setShaderInput(new ShaderInput(go));
        shader.use();
        shader.passUniforms();
        shader.enableAttributes();
        shader.passAttributes();

        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mesh.getIBO());
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, mesh.getFaces().length * Face.VERTICES_COUNT, GLES20.GL_UNSIGNED_SHORT, 0);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);

        shader.disableAttributes();
    }
}
