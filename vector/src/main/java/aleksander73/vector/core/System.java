package aleksander73.vector.core;

public abstract class System {
    private GameEngine gameEngine;
    private boolean ready = false;

    public System(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    public void initialize() {}

    public GameEngine getGameEngine() {
        return gameEngine;
    }

    public void setGameEngine(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }
}
