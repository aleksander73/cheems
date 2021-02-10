package aleksander73.vector.rendering.mesh;

import android.opengl.GLES20;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import aleksander73.vector.core.Component;
import aleksander73.vector.core.GameEngine;
import aleksander73.vector.rendering.RenderingUtility;
import aleksander73.math.linear_algebra.Vector2d;
import aleksander73.math.linear_algebra.Vector3d;

public class Mesh extends Component {
    private final Vertex[] vertices;
    private final Face[] faces;

    private int vbo;
    private int ibo;

    public Mesh(Vertex[] vertices, Face[] faces) {
        this.vertices = vertices;
        this.faces = faces;

        this.bufferOnGPU(vertices, faces);
    }

    private void bufferOnGPU(Vertex[] vertices, Face[] faces) {
        float[] vertexData = new float[vertices.length * Vertex.VERTEX_ELEMENTS];
        int i = 0;
        for(Vertex vertex : vertices) {
            Vector3d position = vertex.getPosition();
            Vector2d textureCoord = vertex.getTextureCoord();

            vertexData[i++] = position.getX();
            vertexData[i++] = position.getY();
            vertexData[i++] = position.getZ();
            vertexData[i++] = textureCoord.getX();
            vertexData[i++] = textureCoord.getY();
        }

        short[] indicesData = new short[faces.length * Face.VERTICES_COUNT];
        i = 0;
        for(Face face : faces) {
            for(Vertex vertex : face.getVertices()) {
                indicesData[i++] = (short)vertex.getId();
            }
        }

        final FloatBuffer vertexBuffer = RenderingUtility.asFloatBuffer(vertexData);
        final ShortBuffer indicesBuffer = RenderingUtility.asShortBuffer(indicesData);

        GameEngine.getRenderingSystem().runOnOpenGLThread(new Runnable() {
            @Override
            public void run() {
                int[] buffers = new int[1];
                GLES20.glGenBuffers(1, buffers, 0);
                vbo = buffers[0];

                GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbo);
                GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, vertexBuffer.capacity() * RenderingUtility.BYTES_PER_FLOAT, vertexBuffer, GLES20.GL_STATIC_DRAW);
                GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);

                int[] buffers2 = new int[1];
                GLES20.glGenBuffers(1, buffers2, 0);
                ibo = buffers2[0];

                GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, ibo);
                GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer.capacity() * RenderingUtility.BYTES_PER_SHORT, indicesBuffer, GLES20.GL_STATIC_DRAW);
                GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
            }
        });
    }

    public static Mesh generateGUITexture(float width, float height) {
        float w = width / 2.0f;
        float h = height / 2.0f;
        Vertex[] vertices = new Vertex[] {
                new Vertex(0, new Vector3d(-w, -h, 0.0f), new Vector2d(0.0f, 1.0f)),
                new Vertex(1, new Vector3d(-w, h, 0.0f), new Vector2d(0.0f, 0.0f)),
                new Vertex(2, new Vector3d(w, h, 0.0f), new Vector2d(1.0f, 0.0f)),
                new Vertex(3, new Vector3d(w, -h, 0.0f), new Vector2d(1.0f, 1.0f))
        };
        Face[] faces = new Face[] {
                new Face(new Vertex[] {vertices[0], vertices[1], vertices[2]}),
                new Face(new Vertex[] {vertices[0], vertices[2], vertices[3]})
        };
        return new Mesh(vertices, faces);
    }

    public Vector3d dimensions() {
        float minX = 0.0f, maxX = 0.0f, minY = 0.0f, maxY = 0.0f, minZ = 0.0f, maxZ = 0.0f;
        for(Vertex v : vertices) {
            Vector3d position = v.getPosition();
            float x = position.getX();
            float y = position.getY();
            float z = position.getZ();

            if(x < minX) {
                minX = x;
            } else if(x > maxX) {
                maxX = x;
            }
            if(y < minY) {
                minY = y;
            } else if(y > maxY) {
                maxY = y;
            }
            if(z < minZ) {
                minZ = z;
            } else if(z > maxZ) {
                maxZ = z;
            }
        }
        return new Vector3d(maxX - minX, maxY - minY, maxZ - minZ);
    }

    public Face[] getFaces() {
        return faces;
    }

    public int getVBO() {
        return vbo;
    }

    public int getIBO() {
        return ibo;
    }
}
