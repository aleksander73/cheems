package aleksander73.cheems.assets;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aleksander73.cheems.rendering.RenderingSystem;
import aleksander73.cheems.rendering.materials.Texture;
import aleksander73.cheems.rendering.mesh.Face;
import aleksander73.cheems.rendering.mesh.Mesh;
import aleksander73.cheems.rendering.mesh.Vertex;
import aleksander73.math.linear_algebra.Vector2d;
import aleksander73.math.linear_algebra.Vector3d;

public class ResourceManager {
    private static ResourceManager instance;
    private AssetManager assetManager;
    private static MediaPlayer mediaPlayer = new MediaPlayer();;

    private Map<String, String> shaders = new HashMap<>();
    private Map<String, Mesh> meshes = new HashMap<>();
    private Map<String, Texture> textures = new HashMap<>();

    private ResourceManager() {}

    public static ResourceManager getInstance() {
        if(instance == null) {
            instance = new ResourceManager();
        }
        return instance;
    }

    public void initialize(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public String loadShader(final String filepath) {
        String text = shaders.get(filepath);
        if(text != null) {
            return text;
        }
        StringBuilder builder = new StringBuilder();
        try {
            InputStreamReader input = new InputStreamReader(assetManager.open("shaders/" + filepath));
            BufferedReader reader = new BufferedReader(input);

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch(IOException e) {
            Log.d("game_engine", "error: Could not load resource \"" + filepath + "\"");
            e.printStackTrace();
        }

        text = builder.toString();
        shaders.put(filepath, text);

        return text;
    }

    public Texture getTexture(String filepath) {
        Texture texture;

        Texture stored = textures.get(filepath);
        if(stored != null) {
            texture = stored;
        } else {
            Texture loadedTexture = this.loadTexture(filepath);
            if(loadedTexture != null) {
                textures.put(filepath, loadedTexture);
                texture = loadedTexture;
            } else {
                texture = this.getTexture("white1x1.bmp");
            }
        }

        return texture;
    }

    public Texture getDefaultTex() {
        return this.getTexture("white1x1.bmp");
    }

    private Texture loadTexture(final String filepath) {
        Texture texture = null;

        final int[] handles = new int[1];

        final boolean[] readyFlag = new boolean[1];
        final boolean[] loadedFlag = new boolean[1];
        RenderingSystem.runOnOpenGLThread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = null;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inScaled = false;

                try {
                    InputStream input = assetManager.open("textures/" + filepath);
                    bitmap = BitmapFactory.decodeStream(input, new Rect(), options);
                } catch(IOException e) {
                    Log.d("game_engine", "error: Could not load resource \"" + filepath + "\"");
                    e.printStackTrace();
                }

                if(bitmap != null) {
                    GLES20.glGenTextures(1, handles, 0);
                    GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, handles[0]);
                    GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
                    GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
                    GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
                    bitmap.recycle();

                    loadedFlag[0] = true;
                }

                readyFlag[0] = true;
            }
        });

        try {
            Thread.sleep(50);
            while(!readyFlag[0]) {}	// Make sure the runnable from OpenGL thread has finished
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        if(loadedFlag[0]) {
            texture = new Texture(handles[0]);
        }

        return texture;
    }

    public Mesh loadMesh(final String filepath ) {
        Mesh mesh = meshes.get(filepath);
        if(mesh != null) {
            return mesh;
        }

        // --------------------------------------------------

        List<Vertex> vertices = new ArrayList<>();
        List<Face> faces = new ArrayList<>();

        try {
            InputStreamReader input = new InputStreamReader(assetManager.open("meshes/" + filepath));
            BufferedReader reader = new BufferedReader(input);

            String line;
            int vertexIndex = 0;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                switch(parts[0]) {
                    case "v" : {
                        float x = Float.parseFloat(parts[1]);
                        float y = Float.parseFloat(parts[2]);
                        float z = Float.parseFloat(parts[3]);
                        vertices.add(new Vertex(vertexIndex, new Vector3d(x, y, z)));
                        vertexIndex++;
                        break;
                    }
                    case "f" : {
                        int vi1 = Integer.parseInt(parts[1]) - 1;
                        int vi2 = Integer.parseInt(parts[2]) - 1;
                        int vi3 = Integer.parseInt(parts[3]) - 1;
                        faces.add(new Face(new Vertex[] {
                                vertices.get(vi1),
                                vertices.get(vi2),
                                vertices.get(vi3)
                        }));
                        break;
                    }
                    case "tc" : {
                        int vi = Integer.parseInt(parts[1]) - 1;
                        float tc_x = Float.parseFloat(parts[2]);
                        float tc_y = Float.parseFloat(parts[3]);
                        vertices.get(vi).setTextureCoord(new Vector2d(tc_x, tc_y));
                        break;
                    }
                }
            }
        } catch(IOException e) {
            Log.d("game_engine", "error: Could not load resource \"" + filepath + "\"");
            e.printStackTrace();
        }

        // --------------------------------------------------

        Vertex[] vertexArray = new Vertex[vertices.size()];
        for(int i = 0; i < vertices.size(); i++) {
            vertexArray[i] = vertices.get(i);
        }

        Face[] faceArray = new Face[faces.size()];
        for(int i = 0; i < faces.size(); i++) {
            faceArray[i] = faces.get(i);
        }

        mesh = new Mesh(vertexArray, faceArray);
        meshes.put(filepath, mesh);

        return mesh;
    }

    public void playSound(final String filepath, boolean loop) {
        mediaPlayer = new MediaPlayer();
        try {
            AssetFileDescriptor afd = assetManager.openFd("sounds/" + filepath);
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            mediaPlayer.prepare();
            mediaPlayer.setLooping(loop);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
    }

    public static MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
}
