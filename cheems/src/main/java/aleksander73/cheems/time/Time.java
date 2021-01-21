package aleksander73.cheems.time;

public class Time {
    private static long deltaTime;

    public static long currentTime() {
        return System.nanoTime();
    }

    /**
     * Returns time of the previous frame in seconds.
     */
    public static float getDeltaTime() {
        return deltaTime * 0.000000001f;
    }

    public static void setDeltaTime(long deltaTime) {
        Time.deltaTime = deltaTime;
    }
}
