package aleksander73.cheems.core;

import java.util.Timer;
import java.util.TimerTask;

import aleksander73.cheems.utility.Event;

public class GameEngine {
    private Event onInitialized = new Event();
    private Game game;
    private Timer gameTimer;

    public GameEngine() {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(GameEngine.this.initialized()) {
                    GameEngine.this.onInitialized.fire();
                    timer.cancel();
                }
            }
        }, 0, 50);
    }

    private boolean initialized() {
        return true;
    }

    public void startGame(final Game game) {
        this.game = game;
        gameTimer = new Timer();
        gameTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                game.run();
            }
        }, 0);
    }

    public void shutdown() {
        game.stop();
        gameTimer.cancel();
    }

    public Event getOnInitialized() {
        return onInitialized;
    }

    public Game getGame() {
        return game;
    }
}
