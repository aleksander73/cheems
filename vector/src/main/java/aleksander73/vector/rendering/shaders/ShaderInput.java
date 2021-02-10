package aleksander73.vector.rendering.shaders;

import aleksander73.vector.core.GameObject;

public class ShaderInput {
    private final GameObject gameObject;

    public ShaderInput(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public GameObject getGameObject() {
        return gameObject;
    }
}
