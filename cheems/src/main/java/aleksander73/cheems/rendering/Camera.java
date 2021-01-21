package aleksander73.cheems.rendering;

import aleksander73.cheems.core.Component;
import aleksander73.cheems.core.Transform;
import aleksander73.math.linear_algebra.Matrix;
import aleksander73.math.linear_algebra.Vector3d;

public class Camera extends Component {
    private float width;
    private float height;
    private float fov;
    private float zNear;
    private float zFar;

    private static Camera activeCamera;

    public Camera(float width, float height, float fov, float zNear, float zFar) {
        this.width = width;
        this.height = height;
        this.fov = fov;
        this.zNear = zNear;
        this.zFar = zFar;
    }

    public Matrix viewMatrix() {
        Matrix center = this.centerViewMatrix();
        Matrix pointOfReference = this.rotateViewMatrix();

        return pointOfReference.mul(center);
    }

    public Matrix centerViewMatrix() {
        Transform transform = this.getGameObject().getComponent(Transform.class);
        Vector3d p = transform.getPosition();

        return new Matrix(4, 4, new float[] {
            1.0f,	0.0f,	0.0f,	-p.getX(),
            0.0f,	1.0f,	0.0f,	-p.getY(),
            0.0f,	0.0f,	1.0f,	-p.getZ(),
            0.0f,	0.0f,	0.0f,	1.0f
        });
    }

    public Matrix rotateViewMatrix() {
        Transform transform = this.getGameObject().getComponent(Transform.class);
        Vector3d r = transform.getRight();
        Vector3d u = transform.getUp();
        Vector3d f = transform.getForward();

        return new Matrix(4, 4, new float[] {
            r.getX(),	r.getY(),	r.getZ(),	0.0f,
            u.getX(),	u.getY(),	u.getZ(),	0.0f,
            f.getX(),	f.getY(),	f.getZ(),	0.0f,
            0.0f,		0.0f,		0.0f,		1.0f
        });
    }

    public Matrix projectionMatrix() {
        float r = width / height;
        float h_tanFov = (float)Math.tan(Math.toRadians(fov / 2.0f));

        float m00 = 1.0f / (r * h_tanFov);
        float m11 = 1.0f / h_tanFov;
        float m22 = -(zNear + zFar) / (zNear - zFar);
        float m23 = 2.0f * zNear * zFar / (zNear - zFar);
        float m32 = 1.0f;

        return new Matrix(4, 4, new float[] {
            m00,	0.0f,	0.0f,	0.0f,
            0.0f,	m11,	0.0f,	0.0f,
            0.0f,	0.0f,	m22,	m23,
            0.0f,	0.0f,	m32,	0.0f
        });
    }

    public void setViewport(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void setFov(float fov) {
        this.fov = fov;
    }

    public static Camera getActiveCamera() {
        return activeCamera;
    }

    public static void setActiveCamera(Camera activeCamera) {
        Camera.activeCamera = activeCamera;
    }
}
