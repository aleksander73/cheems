package aleksander73.cheems.core;

import android.util.Log;

import aleksander73.cheems.time.Time;
import aleksander73.cheems.time.Timer;

public class Game {
    private volatile boolean running = false;

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

    public void start() {}

    public void update() {}

    public void stop() {
        running = false;
    }

    public boolean isRunning() {
        return running;
    }
}
