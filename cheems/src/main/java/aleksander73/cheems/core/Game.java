package aleksander73.cheems.core;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import aleksander73.cheems.scene.Scene;
import aleksander73.cheems.time.Time;
import aleksander73.cheems.time.Timer;
import aleksander73.cheems.utility.ListUtility;
import aleksander73.cheems.utility.functional_interface.Condition;

public abstract class Game {
    private volatile boolean running = false;
    private List<Scene> scenes = new ArrayList<>();
    private final Condition<GameObject> isActive = new Condition<GameObject>() {
        @Override
        public boolean test(GameObject element) {
            return element.isActive();
        }
    };

    public Game() {
        Scene scene = this.buildScene();
        scenes.add(scene);
        Scene.setCurrentScene(scene);
    }

    protected abstract Scene buildScene();

    public void run() {
        final int FPS = 60;
        long frames = 0L;
        Timer timer = new Timer();
        Timer framesTimer = new Timer();

        running = true;
        this.start();
        timer.start();
        framesTimer.start();
        while(running) {
            long elapsedTime = timer.elapsedTimeNano();
            if(elapsedTime > 1000000000L / FPS) {
                this.update();
                Scene.getCurrentScene().onUpdated();
                Time.setDeltaTime(elapsedTime);
                timer.restart();
                frames++;
            }
            if(framesTimer.elapsedTime() > 1.0f) {
                Log.d("game_engine", "FPS: " + frames);
                frames = 0;
                framesTimer.restart();
            }
        }
        timer.stop();
        framesTimer.stop();
    }

    private void start() {
        for(GameObject gameObject : ListUtility.filter(Scene.getCurrentScene().getGameObjects(), isActive)) {
            gameObject.start();
        }
    }

    private void update() {
        for(GameObject gameObject : ListUtility.filter(Scene.getCurrentScene().getGameObjects(), isActive)) {
            gameObject.update();
        }
    }

    public void stop() {
        running = false;
    }

    public boolean isRunning() {
        return running;
    }
}
