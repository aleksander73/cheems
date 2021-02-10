package aleksander73.vector.time;

import java.util.Arrays;
import java.util.HashSet;

import aleksander73.vector.adt.StateMachine;

public class Timer {
    private final StateMachine stateMachine;
    private final String STOPPED = "STOPPED";
    private final String RUNNING = "RUNNING";

    private long start;
    private long elapsedTime;

    public Timer() {
        stateMachine = new StateMachine(new HashSet<>(Arrays.asList(STOPPED, RUNNING)), STOPPED);
        stateMachine.enableTransition(STOPPED, RUNNING);
        stateMachine.enableTransition(RUNNING, STOPPED);
        stateMachine.setOnEnter(RUNNING, new Runnable() {
            @Override
            public void run() {
                start = Time.currentTime();
            }
        });
        stateMachine.setOnEnter(STOPPED, new Runnable() {
            @Override
            public void run() {
                elapsedTime = Timer.this.elapsedTimeNano();
            }
        });
    }

    public void start() {
        stateMachine.changeState(RUNNING);
    }

    public long elapsedTimeNano() {
        if(stateMachine.currentState().equals(RUNNING)) {
            return Time.currentTime() - start;
        } else {
            return this.elapsedTime;
        }
    }

    public float elapsedTime() {
        return this.elapsedTimeNano() * 0.000000001f;
    }

    public void stop() {
        stateMachine.changeState(STOPPED);
    }

    public void reset() {
        elapsedTime = 0L;
    }

    public void restart() {
        this.stop();
        this.reset();
        this.start();
    }

    public boolean isRunning() {
        return stateMachine.currentState().equals(RUNNING);
    }
}
