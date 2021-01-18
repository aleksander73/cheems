package aleksander73.cheems.core;

import java.util.Timer;
import java.util.TimerTask;

public class GameEngine {
    private Game game;
    private Timer gameTimer;

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

    public Game getGame() {
        return game;
    }
}
