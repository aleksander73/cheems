package aleksander73.cheems.core;

public class Component {
    private GameObject gameObject;

    public void initialize() {}

    public GameObject getGameObject() {
        return gameObject;
    }

    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }
}
