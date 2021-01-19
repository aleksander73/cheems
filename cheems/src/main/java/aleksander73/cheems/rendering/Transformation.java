package aleksander73.cheems.rendering;

import aleksander73.math.linear_algebra.Matrix;
import aleksander73.math.linear_algebra.Vector3d;

public class Transformation {
    public static Matrix mvpMatrix(Matrix model, Matrix view, Matrix projection) {
        return projection.mul(view.mul(model));
    }

    public static Matrix translationMatrix(Vector3d translation) {
        float x = translation.getX();
        float y = translation.getY();
        float z = translation.getZ();

        return new Matrix(4, 4, new float[] {
                1.0f,	0.0f,	0.0f,	x,
                0.0f,	1.0f,	0.0f,	y,
                0.0f,	0.0f,	1.0f,	z,
                0.0f,	0.0f,	0.0f,	1.0f
        });
    }

    public static Matrix rotationMatrix(Vector3d rotation) {
        float radX = (float)Math.toRadians(rotation.getX());
        float radY = (float)Math.toRadians(rotation.getY());
        float radZ = (float)Math.toRadians(rotation.getZ());

        float sinX = (float)Math.sin(radX);
        float cosX = (float)Math.cos(radX);
        float sinY = (float)Math.sin(radY);
        float cosY = (float)Math.cos(radY);
        float sinZ = (float)Math.sin(radZ);
        float cosZ = (float)Math.cos(radZ);

        Matrix x = new Matrix(4, 4, new float[] {
                1.0f,	0.0f,	0.0f,	0.0f,
                0.0f,	cosX,  -sinX,	0.0f,
                0.0f,	sinX,	cosX,	0.0f,
                0.0f,	0.0f,	0.0f,	1.0f
        });
        Matrix y = new Matrix(4, 4, new float[] {
                cosY,	0.0f,	sinY,	0.0f,
                0.0f,	1.0f,	0.0f,	0.0f,
                -sinY,	0.0f,	cosY,	0.0f,
                0.0f,	0.0f,	0.0f,	1.0f
        });
        Matrix z = new Matrix(4, 4, new float[] {
                cosZ,  -sinZ,	0.0f,	0.0f,
                sinZ,	cosZ,	0.0f,	0.0f,
                0.0f,	0.0f,	1.0f,	0.0f,
                0.0f,	0.0f,	0.0f,	1.0f
        });

        return z.mul(y.mul(x));
    }

    public static Matrix scaleMatrix(Vector3d scale) {
        float sX = scale.getX();
        float sY = scale.getY();
        float sZ = scale.getZ();

        return new Matrix(4, 4, new float[] {
                sX,		0.0f,	0.0f,	0.0f,
                0.0f,	sY,		0.0f,	0.0f,
                0.0f,	0.0f,	sZ,		0.0f,
                0.0f,	0.0f,	0.0f,	1.0f
        });
    }
}
