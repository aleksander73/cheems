package aleksander73.vector.core;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import aleksander73.vector.rendering.renderers.Renderer;
import aleksander73.vector.scene.Scene;
import aleksander73.vector.time.Time;
import aleksander73.vector.time.Timer;
import aleksander73.vector.utility.ListUtility;
import aleksander73.vector.utility.functional_interface.Condition;

public abstract class Game {
    private volatile boolean running = false;
    private final List<Scene> scenes = new ArrayList<>();
    private final Condition<GameObject> isActive = new Condition<GameObject>() {
        @Override
        public boolean test(GameObject element) {
            return element.isActive();
        }
    };

    public Game() {
        this.setupInput();
        Scene scene = this.buildScene();
        scenes.add(scene);
        Scene.setCurrentScene(scene);
    }

    protected abstract Scene buildScene();

    protected abstract void setupInput();

    protected abstract void clearInput();

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
                GameEngine.getPhysicsSystem().gatherInformation(Scene.getCurrentScene());
                this.update();
                this.clearInput();
                Scene.getCurrentScene().onUpdated();
                GameEngine.getPhysicsSystem().simulatePhysics(Scene.getCurrentScene());
                GameEngine.getRenderingSystem().requestRender();
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

    public void render() {
        List<GameObject> gameObjects = Scene.getCurrentScene().getGameObjects();
        List<Renderer> renderers = new ArrayList<>();
        for(int i = 0; i < gameObjects.size(); i++) {
            renderers.addAll(gameObjects.get(i).getComponents(Renderer.class));
        }
        List<Renderer> activeRenderers = ListUtility.filter(renderers, new Condition<Renderer>() {
            @Override
            public boolean test(Renderer renderer) {
                return renderer.isActive();
            }
        });
        for(Renderer renderer : activeRenderers) {
            renderer.render();
        }
    }

    public void stop() {
        running = false;
    }

    public boolean isRunning() {
        return running;
    }
}
