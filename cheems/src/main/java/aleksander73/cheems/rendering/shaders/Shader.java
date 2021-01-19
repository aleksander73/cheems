package aleksander73.cheems.rendering.shaders;

import android.opengl.GLES20;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import aleksander73.math.linear_algebra.Matrix;
import aleksander73.math.linear_algebra.Vector4d;

public abstract class Shader {
    private final int program;
    private Map<String, Integer> uniforms = new HashMap<>();
    private Map<String, Integer> attributes = new HashMap<>();
    private ShaderInput shaderInput;

    public Shader(String vertexSrc, String fragmentSrc, String[] uniforms, String[] attributes) {
        int vertexShader = this.loadShader(GLES20.GL_VERTEX_SHADER, vertexSrc);
        int fragmentShader = this.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentSrc);

        program = GLES20.glCreateProgram();
        if(program == 0) {
            Log.d("game_engine", "error: Program has not been created");
        }

        GLES20.glAttachShader(program, vertexShader);
        GLES20.glAttachShader(program, fragmentShader);

        for(int i = 0; i < attributes.length; i++) {
            GLES20.glBindAttribLocation(program, i, attributes[i]);
        }

        GLES20.glLinkProgram(program);

        final int[] linkStatus = new int[1];
        GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, 0);
        if (linkStatus[0] == 0) {
            Log.d("game_engine", "error: Could not link the program");
        }

        // --------------------------------------------------

        for(String uniform : uniforms) {
            this.uniforms.put(uniform, GLES20.glGetUniformLocation(program, uniform));
        }

        for(String attribute : attributes) {
            this.attributes.put(attribute, GLES20.glGetAttribLocation(program, attribute));
        }
    }

    private int loadShader(int type, String src) {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, src);
        GLES20.glCompileShader(shader);

        final int[] compileStatus = new int[1];
        GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compileStatus, 0);
        if (compileStatus[0] == 0) {
            Log.d("game_engine", "error: Could not compile the " + ((type == GLES20.GL_VERTEX_SHADER) ? "vertex" : "fragment") + " shader");
        }

        return shader;
    }

    public void enableAttributes() {
        for(String attribute : attributes.keySet()) {
            int handle = attributes.get(attribute);
            GLES20.glEnableVertexAttribArray(handle);
        }
    }

    public void disableAttributes() {
        for(String attribute : attributes.keySet()) {
            int handle = attributes.get(attribute);
            GLES20.glDisableVertexAttribArray(handle);
        }
    }

    public void use() {
        GLES20.glUseProgram(program);
    }

    public abstract void passUniforms();

    public abstract void passAttributes();

    protected int getAttribute(String attribute) {
        return attributes.get(attribute);
    }

    // --------------------------------------------------

    protected void setInteger(String uniform, int n) {
        GLES20.glUniform1i(uniforms.get(uniform), n);
    }

    protected void setVector4d(String uniform, Vector4d v) {
        GLES20.glUniform4fv(uniforms.get(uniform), 1, v.getValues(), 0);
    }

    protected void setMatrix(String uniform, Matrix matrix) {
        // By default OpenGL ES 2.0 uses column-ordering with matrices while I use row-ordering
        GLES20.glUniformMatrix4fv(uniforms.get(uniform), 1, false, matrix.transpose().getValues(), 0);
    }

    // --------------------------------------------------

    protected ShaderInput getShaderInput() {
        return shaderInput;
    }

    public void setShaderInput(ShaderInput shaderInput) {
        this.shaderInput = shaderInput;
    }
}
