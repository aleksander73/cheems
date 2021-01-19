package aleksander73.cheems.core;

import android.app.Activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import aleksander73.cheems.rendering.RenderingSystem;
import aleksander73.cheems.rendering.SurfaceView;
import aleksander73.cheems.utility.Event;

public class GameEngine {
    private Event onInitialized = new Event();
    private List<System> systems = new ArrayList<>();
    private Game game;
    private Timer gameTimer;

    public GameEngine(Activity activity) {
        SurfaceView surfaceView = new SurfaceView(activity);
        activity.setContentView(surfaceView);
        systems.addAll(Arrays.asList(
            new RenderingSystem(this, surfaceView)
        ));
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
        for(System system : systems) {
            if(!system.isReady()) {
                return false;
            }
        }
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
