package aleksander73.cheems.physics.collision;

import aleksander73.cheems.core.GameObject;

public class Collision {
    private GameObject gameObject1;
    private GameObject gameObject2;

    public Collision(GameObject gameObject1, GameObject gameObject2) {
        this.gameObject1 = gameObject1;
        this.gameObject2 = gameObject2;
    }

    public GameObject getGameObject1() {
        return gameObject1;
    }

    public GameObject getGameObject2() {
        return gameObject2;
    }
}
