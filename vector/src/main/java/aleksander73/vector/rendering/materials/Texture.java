package aleksander73.vector.rendering.materials;

import android.opengl.GLES20;

public class Texture {
    private final int id;

    public Texture(int id) {
        this.id = id;
    }

    public void bind() {
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, id);
    }
}
