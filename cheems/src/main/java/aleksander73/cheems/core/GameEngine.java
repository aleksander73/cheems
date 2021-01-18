package aleksander73.cheems.core;

import java.util.Timer;
import java.util.TimerTask;

public class GameEngine {
    private Game currentGame;
    private Timer gameTimer;

    public void startGame(final Game game) {
        this.currentGame = game;
        gameTimer = new Timer();
        gameTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                game.run();
            }
        }, 0);
    }

    public void shutdown() {
        currentGame.stop();
        gameTimer.cancel();
    }

    public Game getCurrentGame() {
        return currentGame;
    }
}
