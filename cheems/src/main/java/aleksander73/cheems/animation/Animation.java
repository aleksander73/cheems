package aleksander73.cheems.animation;

import aleksander73.cheems.core.Component;
import aleksander73.cheems.time.Timer;

public class Animation extends Component {
    private float duration;
    private Timer timer = new Timer();
    private float t;

    private boolean finished;
    private boolean looped;

    public Animation(float duration, boolean looped) {
        this.duration = duration;
        this.looped = looped;
    }

    public void start() {
        timer.start();
        finished = false;
    }

    public void update() {
        if(!finished) {
            float elapsedTime = timer.elapsedTime();
            if(elapsedTime > duration) {
                this.stop();
                if(looped) {
                    this.start();
                }
            } else {
                t = elapsedTime;
            }
        }
    }

    private void stop() {
        timer.stop();
        finished = true;
    }

    public void reset() {
        if(timer.isRunning()) {
            timer.stop();
        }
        timer.reset();
        t = 0.0f;
    }

    public float getT() {
        return t;
    }

    public boolean hasFinished() {
        return finished;
    }
}
