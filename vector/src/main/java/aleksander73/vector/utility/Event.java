package aleksander73.vector.utility;

import aleksander73.vector.adt.Queue;

public class Event {
    private final Queue<Runnable> runnables = new Queue<>();

    public void fire() {
        for(Runnable runnable : runnables) {
            runnable.run();
        }
    }

    public void queueRunnable(Runnable runnable) {
        runnables.add(runnable);
    }

    public void clear() {
        runnables.clear();
    }
}
