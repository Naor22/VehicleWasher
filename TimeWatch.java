// Naor Ben Azra - 318544939 && Osher Ben Hamo - 209264076
import java.util.concurrent.TimeUnit;

public class TimeWatch { // TimeWatch class to calculate elapsed time
    long starts;

    public static TimeWatch start() {
        return new TimeWatch();
    }

    private TimeWatch() {
        reset();
    }

    public TimeWatch reset() {
        starts = System.currentTimeMillis();
        return this;
    }

    public long time() {
        long ends = System.currentTimeMillis();
        return ends - starts;
    }

    public long time(TimeUnit unit) {
        return unit.convert(time(), TimeUnit.MILLISECONDS);
    }
}