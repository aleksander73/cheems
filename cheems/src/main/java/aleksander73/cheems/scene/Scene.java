package aleksander73.cheems.scene;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aleksander73.cheems.core.GameObject;
import aleksander73.cheems.utility.Event;
import aleksander73.cheems.utility.ListUtility;
import aleksander73.cheems.utility.functional_interface.Condition;

public class Scene {
    private final List<GameObject> gameObjects = new ArrayList<>();
    private final Event onUpdated = new Event();
    private static Scene currentScene;

    public Scene(List<GameObject> gameObjects) {
        for(GameObject gameObject : gameObjects) {
            this.addGameObject(gameObject);
        }
    }

    /**
     * @param conditions - must be mutually exclusive
     */
    public void sortByGrouping(List<Condition<GameObject>> conditions, List<Comparator<GameObject>> comparators) {
        Map<Condition<GameObject>, List<GameObject>> mapConditionToSublist = new HashMap<>();
        Map<Condition<GameObject>, Comparator<GameObject>> mapConditionToComparator = new HashMap<>();
        for(int i = 0; i < conditions.size(); i++) {
            mapConditionToSublist.put(conditions.get(i), new ArrayList<GameObject>());
            mapConditionToComparator.put(conditions.get(i),comparators.get(i));
        }

        for(Condition<GameObject> condition : conditions) {
            List<GameObject> filtered = ListUtility.filter(gameObjects, condition);
            mapConditionToSublist.get(condition).addAll(filtered);
        }

        gameObjects.clear();
        for(Condition<GameObject> condition : conditions) {
            List<GameObject> sublist = mapConditionToSublist.get(condition);
            Collections.sort(sublist, mapConditionToComparator.get(condition));
            gameObjects.addAll(sublist);
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

    public Event getOnUpdated() {
        return onUpdated;
    }

    public void onUpdated() {
        onUpdated.fire();
        onUpdated.clear();
    }

    public static Scene getCurrentScene() {
        return currentScene;
    }

    public static void setCurrentScene(Scene currentScene) {
        Scene.currentScene = currentScene;
    }
}
