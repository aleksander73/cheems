package aleksander73.cheems.scene;

import java.util.ArrayList;
import java.util.List;

import aleksander73.cheems.core.GameObject;
import aleksander73.cheems.utility.ListUtility;
import aleksander73.cheems.utility.functional_interface.Condition;

public class Scene {
    private List<GameObject> gameObjects = new ArrayList<>();
    private static Scene currentScene;

    public Scene(List<GameObject> gameObjects) {
        for(GameObject gameObject : gameObjects) {
            this.addGameObject(gameObject);
        }
    }

    public GameObject find(final String gameObjectName) {
        return ListUtility.first(gameObjects, new Condition<GameObject>() {
            @Override
            public boolean test(GameObject o) {
                return o.getName().equals(gameObjectName);
            }
        });
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
        gameObject.setScene(this);
    }

    public void removeGameObject(GameObject gameObject) {
        gameObjects.remove(gameObject);
    }

    public static Scene getCurrentScene() {
        return currentScene;
    }

    public static void setCurrentScene(Scene currentScene) {
        Scene.currentScene = currentScene;
    }
}
