package aleksander73.vector.rendering.shaders;

import android.opengl.GLES20;

import aleksander73.vector.core.GameEngine;
import aleksander73.vector.core.GameObject;
import aleksander73.vector.core.Transform;
import aleksander73.vector.rendering.RenderingUtility;
import aleksander73.vector.rendering.materials.Colour;
import aleksander73.vector.rendering.materials.Material;
import aleksander73.vector.rendering.mesh.Mesh;
import aleksander73.vector.rendering.mesh.Vertex;
import aleksander73.math.linear_algebra.Matrix;

public class GUIShader extends Shader {
    private static final String MODEL_MATRIX = "u_model";
    private static final String COLOUR = "u_colour";
    private static final String TEXTURE = "u_texture";

    private static final String POSITION = "a_position";
    private static final String TEXTURE_XY = "a_texture_xy";

    public GUIShader() {
        super(
            GameEngine.getResourceSystem().loadShader("gui.vert"),
            GameEngine.getResourceSystem().loadShader("gui.frag"),
            new String[] { MODEL_MATRIX, COLOUR, TEXTURE },
            new String[] { POSITION, TEXTURE_XY }
        );
    }

    @Override
    public void passUniforms() {
        GameObject go = this.getShaderInput().getGameObject();
        Transform transform = go.getComponent(Transform.class);
        Material material = go.getComponent(Material.class);

        Matrix modelMatrix = transform.modelMatrix();
        this.setMatrix(MODEL_MATRIX, modelMatrix);

        Colour colour = material.getColour();
        this.setVector4d(COLOUR, colour.normalize());

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        material.getTexture().bind();
        this.setInteger(TEXTURE, 0);
    }

    @Override
    public void passAttributes() {
        int vbo = this.getShaderInput().getGameObject().getComponent(Mesh.class).getVBO();
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbo);
        GLES20.glVertexAttribPointer(this.getAttribute(POSITION), 3, GLES20.GL_FLOAT, false, Vertex.VERTEX_ELEMENTS * RenderingUtility.BYTES_PER_FLOAT, 0);
        GLES20.glVertexAttribPointer(this.getAttribute(TEXTURE_XY), 2, GLES20.GL_FLOAT, false, Vertex.VERTEX_ELEMENTS * RenderingUtility.BYTES_PER_FLOAT, 12);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
    }
}
