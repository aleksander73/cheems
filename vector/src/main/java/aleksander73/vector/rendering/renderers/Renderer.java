package aleksander73.vector.rendering.renderers;

import aleksander73.vector.core.Component;

public abstract class Renderer extends Component {
    private boolean active = true;

    public abstract void render();

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
