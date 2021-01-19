package aleksander73.cheems.rendering.shaders;

import aleksander73.cheems.core.GameObject;

public class ShaderInput {
    private final GameObject gameObject;

    public ShaderInput(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public GameObject getGameObject() {
        return gameObject;
    }
}
