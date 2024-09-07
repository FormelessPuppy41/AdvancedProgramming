
/**
 * A stopwatch accumulates time when it is running. You can repeatedly start and stop the stopwatch.
 * You can use a stopwatch to measure the running time of a program in both nanoseconds and milliseconds.
 */
public class StopWatch {
    private long elapsedTime; // Stores elapsed time in nanoseconds
    private long startTime; // Tracks the starting time when the stopwatch is running
    private boolean isRunning; // Tracks whether the stopwatch is currently running

    /**
     * Constructs a stopwatch that is in the stopped state and has no accumulated time.
     */
    public StopWatch() {
        reset();
    }

    /**
     * Starts the stopwatch. If it's already running, nothing happens.
     */
    public void start() {
        if (isRunning) {
            return;
        }
        isRunning = true;
        startTime = System.nanoTime(); // Records start time in nanoseconds
    }

    /**
     * Stops the stopwatch and accumulates the elapsed time. If it's not running, nothing happens.
     */
    public void stop() {
        if (!isRunning) {
            return;
        }
        isRunning = false;
        long endTime = System.nanoTime(); // Records the time when stopped
        elapsedTime += (endTime - startTime); // Accumulates elapsed time
    }

    /**
     * Resets the stopwatch to zero elapsed time and stops it.
     */
    public void reset() {
        elapsedTime = 0;
        isRunning = false;
    }

    /**
     * Returns the total elapsed time in nanoseconds.
     *
     * @return elapsed time in nanoseconds
     */
    public long getElapsedTimeInNano() {
        if (isRunning) {
            long endTime = System.nanoTime();
            return elapsedTime + (endTime - startTime); // Returns the current total time if still running
        } else {
            return elapsedTime;
        }
    }

    /**
     * Returns the total elapsed time in milliseconds.
     *
     * @return elapsed time in milliseconds
     */
    public double getElapsedTimeInMilli() {
        return getElapsedTimeInNano() / 1_000_000.0; // Converts nanoseconds to milliseconds
    }

    /**
     * Outputs the elapsed time in both milliseconds and nanoseconds.
     */
    public void output() {
        System.out.println("Elapsed time: " + getElapsedTimeInMilli() + " milliseconds (" + getElapsedTimeInNano() + " nanoseconds)");
    }

    
}
