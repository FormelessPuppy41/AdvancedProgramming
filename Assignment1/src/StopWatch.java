/**
 * A StopWatch accumulates time when it is running. You can start and stop it multiple times to 
 * measure the total elapsed time. The stopwatch measures time in both nanoseconds and milliseconds.
 * 
 * @author 611968bq - Berend Quist
 */
public class StopWatch {

    protected long elapsedTime; // Accumulated elapsed time in nanoseconds
    protected long startTime; // The time when the stopwatch was last started
    protected boolean isRunning; // True if the stopwatch is currently running

    /**
     * Constructs a StopWatch that is initially stopped with zero accumulated time.
     */
    public StopWatch() {
        reset(); // Initialize in a stopped state with zero elapsed time
    }

    /**
     * Starts the stopwatch if it is not already running. If the stopwatch is running, no action is taken.
     */
    public void start() {
        if (!isRunning) {
            this.isRunning = true;
            this.startTime = System.nanoTime(); // Record the current start time in nanoseconds
        }
    }

    /**
     * Stops the stopwatch and accumulates the time since it was last started. 
     * If the stopwatch is not running, no action is taken.
     */
    public void stop() {
        if (isRunning) {
            this.isRunning = false;
            long endTime = System.nanoTime(); // Get the current time
            this.elapsedTime += (endTime - this.startTime); // Add the time passed since the last start
        }
    }

    /**
     * Resets the stopwatch to zero elapsed time and stops it if it's running.
     */
    public void reset() {
        this.elapsedTime = 0; // Reset elapsed time to zero
        this.isRunning = false; // Ensure the stopwatch is stopped
    }

    /**
     * Returns the total elapsed time in nanoseconds. If the stopwatch is running, it includes the time since it was last started.
     *
     * @return The total elapsed time in nanoseconds.
     */
    public long getElapsedTimeInNano() {
        if (isRunning) {
            long currentTime = System.nanoTime(); // Get the current time
            return this.elapsedTime + (currentTime - this.startTime); // Include the time since the last start
        }
        return this.elapsedTime; // Return the accumulated time
    }

    /**
     * Returns the total elapsed time in milliseconds, converting from nanoseconds.
     *
     * @return The total elapsed time in milliseconds.
     */
    public double getElapsedTimeInMilli() {
        return getElapsedTimeInNano() / 1_000_000.0; // Convert nanoseconds to milliseconds
    }

    /**
     * Prints the total elapsed time in both milliseconds and nanoseconds.
     */
    public void output() {
        System.out.println("\nFinished running the Method: \n -> Elapsed time: " + getElapsedTimeInMilli() + " milliseconds (" + getElapsedTimeInNano() + " nanoseconds)");
    }

}
