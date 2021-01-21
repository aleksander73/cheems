package aleksander73.cheems.physics.collision;

import java.util.HashMap;
import java.util.Map;

import aleksander73.cheems.core.GameObject;
import aleksander73.cheems.utility.functional_interface.BiFunction;

public class CollisionDetector {
    private Map<String, Integer> indices = new HashMap<>();
    private BiFunction<GameObject, Boolean>[][] collisionMatrix;

    /**
     * Both parameters must have a collider attached on them.
     */
    public boolean detectCollision(GameObject gameObject1, GameObject gameObject2) {
        Collider collider1 = gameObject1.getComponent(Collider.class);
        Collider collider2 = gameObject2.getComponent(Collider.class);
        if(collider1 == null || collider2 == null) {
            String message;
            if(collider1 == null && collider2 == null) {
                message = "error: Neither argument has a collider!";
            } else if(collider1 == null) {
                message = "error: First argument doesn't have a collider!";
            } else {
                message = "error: Second argument doesn't have a collider!";
            }
            throw new IllegalArgumentException(message);
        }

        int index1 = indices.get(collider1.getClass().getSimpleName());
        int index2 = indices.get(collider2.getClass().getSimpleName());

        return (collisionMatrix[index1][index2] != null) ? collisionMatrix[index1][index2].accept(gameObject1, gameObject2) : false;
    }

    public Map<String, Integer> getIndices() {
        return indices;
    }

    public BiFunction<GameObject, Boolean>[][] getCollisionMatrix() {
        return collisionMatrix;
    }

    public void setCollisionMatrix(BiFunction<GameObject, Boolean>[][] collisionMatrix) {
        this.collisionMatrix = collisionMatrix;
    }
}
