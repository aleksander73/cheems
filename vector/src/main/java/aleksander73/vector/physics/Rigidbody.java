package aleksander73.vector.physics;

import aleksander73.vector.core.Component;

public class Rigidbody extends Component {
    private boolean gravityApplied = true;
    private float velocity;

    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    public boolean isGravityApplied() {
        return gravityApplied;
    }

    public void setGravityApplied(boolean gravityApplied) {
        this.gravityApplied = gravityApplied;
    }
}
