package aleksander73.cheems.utility;

import aleksander73.cheems.adt.Queue;

public class Event {
    private Queue<Runnable> runnables = new Queue<>();

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
