package aleksander73.vector.physics.collision;

import java.util.HashMap;
import java.util.Map;

import aleksander73.vector.core.Component;
import aleksander73.vector.core.GameObject;
import aleksander73.vector.utility.functional_interface.Condition;
import aleksander73.vector.utility.functional_interface.Consumer;

public class Collider extends Component {
    private Map<Condition<GameObject>, Consumer<GameObject>> customOnCollisionEnter = new HashMap<>();

    public void addCustomOnCollisionEnter(Condition<GameObject> condition, Consumer<GameObject> consumer) {
        customOnCollisionEnter.put(condition, consumer);
    }

    public Map<Condition<GameObject>, Consumer<GameObject>> getCustomOnCollisionEnter() {
        return customOnCollisionEnter;
    }
}
