package aleksander73.cheems.core;

import java.util.ArrayList;
import java.util.List;

import aleksander73.math.linear_algebra.Quaternion;
import aleksander73.math.linear_algebra.Vector3d;

public class Transform extends Component {
    private static final Transform worldTransform = new Transform();
    private Transform parent = worldTransform;
    private List<Transform> children = new ArrayList<>();

    private Vector3d position = Vector3d.zeroVector;
    private Vector3d rotation = Vector3d.zeroVector;	// Euler angles
    private Vector3d scale = new Vector3d(1.0f, 1.0f, 1.0f);

    private Vector3d right = Vector3d.xUnitVector;
    private Vector3d up = Vector3d.yUnitVector;
    private Vector3d back = Vector3d.zUnitVector;

    public Transform() {}

    public Transform(Vector3d position) {
        this.position = position;
    }

    public Transform(Vector3d position, Vector3d rotation) {
        this(position);
        this.rotate(Vector3d.xUnitVector, rotation.getX());
        this.rotate(Vector3d.yUnitVector, rotation.getY());
        this.rotate(Vector3d.zUnitVector, rotation.getZ());
    }

    public Transform(Vector3d position, Vector3d rotation, Vector3d scale) {
        this(position, rotation);
        this.scale = scale;
    }

    public void translate(Vector3d v) {
        this.translate(worldTransform, v);
    }

    public void translate(Transform reference, Vector3d v) {
        position = position
                .add(reference.right.mul(v.getX()))
                .add(reference.up.mul(v.getY()))
                .add(reference.back.mul(v.getZ()))
                .toVector3d();

        for(Transform child : children) {
            child.translate(reference, v);
        }
    }

    public void rotate(Vector3d axis, float angle) {
        this.rotate(position, axis, angle);
    }

    public void rotate(Vector3d point, Vector3d axis, float angle) {
        Vector3d r = position.sub(point).toVector3d();
        position = point.add(r.rotate(axis, angle)).toVector3d();

        Vector3d delta = Quaternion.toRotationQuaternion(axis, angle).toEulerAngles();
        rotation = rotation.add(delta).toVector3d();
        this.rotateNormals(axis, angle);

        for(Transform child : children) {
            child.rotate(point, axis, angle);
        }
    }

    public void lookAt(Vector3d target) {
        Vector3d forward = this.getForward();
        Vector3d r = target.sub(position).normalize().toVector3d();

        float angle = forward.angle(r);
        Vector3d crossProduct = forward.crossProduct(r).normalize().toVector3d();
        if(angle == 0.0f || crossProduct.magnitude() == 0.0f) {
            return;
        }

        Vector3d axis = (angle != 180.0f) ? crossProduct : up;
        this.rotate(axis, angle);
    }

    private void rotateNormals(Vector3d axis, float angle) {
        right = right.rotate(axis, angle);
        up = up.rotate(axis, angle);
        back = right.crossProduct(up);
    }

    public void scale(Vector3d v) {
        scale = scale.hadamardProduct(v).toVector3d();
        for(Transform child : children) {
            child.scale(v);
        }
    }

    public void setParent(Transform parent) {
        this.parent = parent;
        parent.children.add(this);
    }

    public void removeChild(Transform child) {
        children.remove(child);
        child.parent = worldTransform;
    }

    public Transform copy() {
        Vector3d position = this.position.copy().toVector3d();
        Vector3d rotation = this.rotation.copy().toVector3d();
        Vector3d scale = this.scale.copy().toVector3d();
        return new Transform(position, rotation, scale);
    }

    public Vector3d getPosition() {
        return position;
    }

    public void setPosition(Vector3d position) {
        Vector3d r = position.sub(this.position).toVector3d();
        this.position = position;
        for(Transform child : children) {
            child.translate(r);
        }
    }

    public Vector3d getRotation() {
        return rotation;
    }

    public Vector3d getScale() {
        return scale;
    }

    public Vector3d getLeft() {
        return right.negate().toVector3d();
    }

    public Vector3d getRight() {
        return right;
    }

    public Vector3d getUp() {
        return up;
    }

    public Vector3d getDown() {
        return up.negate().toVector3d();
    }

    public Vector3d getForward() {
        return back.negate().toVector3d();
    }

    public Vector3d getBack() {
        return back;
    }
}
