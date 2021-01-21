package aleksander73.cheems.core;

import android.app.Activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import aleksander73.cheems.assets.ResourceSystem;
import aleksander73.cheems.input.InputSystem;
import aleksander73.cheems.physics.PhysicsSystem;
import aleksander73.cheems.rendering.RenderingSystem;
import aleksander73.cheems.rendering.SurfaceView;
import aleksander73.cheems.utility.Event;

public class GameEngine {
    private final Event onInitialized = new Event();
    private static final List<System> systems = new ArrayList<>();
    private Game game;
    private Timer gameTimer;

    public void initialize(Activity activity) {
        SurfaceView surfaceView = new SurfaceView(activity);
        activity.setContentView(surfaceView);
        systems.addAll(Arrays.asList(
                new RenderingSystem(this, surfaceView),
                new InputSystem(this, surfaceView),
                new PhysicsSystem(this),
                new ResourceSystem(this, activity.getAssets())
        ));
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(GameEngine.this.isInitialized()) {
                    GameEngine.this.onInitialized.fire();
                    timer.cancel();
                }
            }
        }, 0, 50);
    }

    private boolean isInitialized() {
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

    public static RenderingSystem getRenderingSystem() {
        return (RenderingSystem) systems.get(0);
    }

    public static InputSystem getInputSystem() {
        return (InputSystem) systems.get(1);
    }

    public static PhysicsSystem getPhysicsSystem() {
        return (PhysicsSystem) systems.get(2);
    }

    public static ResourceSystem getResourceSystem() {
        return (ResourceSystem) systems.get(3);
    }

    public Game getGame() {
        return game;
    }
}
