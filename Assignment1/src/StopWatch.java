
/**
 * A stopwatch accumulates time when it is running. You can repeatedly start and stop the stopwatch.
 * You can use a stopwatch to measure the running time of a program in both nanoseconds and milliseconds.
 */
public class StopWatch {
    protected long elapsedTime; // Stores elapsed time in nanoseconds
    protected long startTime; // Tracks the starting time when the stopwatch is running
    protected boolean isRunning; // Tracks whether the stopwatch is currently running

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
        if (this.isRunning) {
            return;
        }
        this.isRunning = true;
        this.startTime = System.nanoTime(); // Records start time in nanoseconds
    }

    /**
     * Stops the stopwatch and accumulates the elapsed time. If it's not running, nothing happens.
     */
    public void stop() {
        if (!this.isRunning) {
            return;
        }
        this.isRunning = false;
        long endTime = System.nanoTime(); // Records the time when stopped
        this.elapsedTime += (endTime - this.startTime); // Accumulates elapsed time
    }

    /**
     * Resets the stopwatch to zero elapsed time and stops it.
     */
    public void reset() {
        this.elapsedTime = 0;
        this.isRunning = false;
    }

    /**
     * Returns the total elapsed time in nanoseconds.
     *
     * @return elapsed time in nanoseconds
     */
    public long getElapsedTimeInNano() {
        if (this.isRunning) {
            long endTime = System.nanoTime();
            return this.elapsedTime + (endTime - this.startTime); // Returns the current total time if still running
        } else {
            return this.elapsedTime;
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
