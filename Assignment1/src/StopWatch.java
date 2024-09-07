import java.util.function.Consumer;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * Measures the time it takes to apply an algorithm to a given dataset.
     *
     * The method can:
     * - Perform warm-up runs (10 runs to stabilize JIT optimizations).
     * - Either run the algorithm once or run it 100 times in simulation mode and calculate the average time.
     *
     * @param algorithm the algorithm to apply
     * @param data the data on which to apply the algorithm
     * @param warmup whether to perform warm-up runs (10 runs)
     * @param simulate whether to run a simulation (100 runs) and calculate the average time
     */
    public <T> void measureTime(Consumer<T> algorithm, T data, boolean warmup, boolean simulate) {
        // Warm-up phase
        if (warmup) {
            warmUpAlgorithm(algorithm, data, 10); // Warm up by running the algorithm 10 times
        }

        // Run simulation or a single run
        if (simulate) {
            runSimulation(algorithm, data, 100); // Simulate 100 runs and calculate average time
        } else {
            runSingle(algorithm, data); // Run the algorithm once
        }
    }

    /**
     * Runs the algorithm 100 times, measures the time, and calculates the average.
     *
     * @param algorithm the algorithm to apply
     * @param data the data to apply the algorithm to
     * @param iterations the number of iterations to run
     */
    private <T> void runSimulation(Consumer<T> algorithm, T data, int iterations) {
        List<Long> runningTimesNano = new ArrayList<>();
        StopWatch stopWatch = new StopWatch();

        // Run the algorithm multiple times and record the running times
        for (int i = 0; i < iterations; i++) {
            T dataCopy = deepCopy(data);
            stopWatch.reset();
            stopWatch.start();
            algorithm.accept(dataCopy); // Apply the algorithm
            stopWatch.stop();
            runningTimesNano.add(stopWatch.getElapsedTimeInNano());
        }

        // Calculate the average times
        double averageTimeNano = calculateAverage(runningTimesNano);
        double averageTimeMilli = averageTimeNano / 1_000_000.0; // Convert to milliseconds

        // Output the average time similar to a single run output
        System.out.println("Average elapsed time over " + iterations + " runs: " 
            + averageTimeMilli + " milliseconds (" + averageTimeNano + " nanoseconds)");
    }

    /**
     * Runs the algorithm once and measures the time.
     *
     * @param algorithm the algorithm to apply
     * @param data the data to apply the algorithm to
     */
    private <T> void runSingle(Consumer<T> algorithm, T data) {
        reset();
        start();
        algorithm.accept(data); // Apply the algorithm
        stop();
        output();
    }

    /**
     * Performs warm-up runs to stabilize JIT optimizations.
     *
     * @param algorithm the algorithm to apply
     * @param data the data to apply the algorithm to
     * @param warmUpIterations number of warm-up runs
     */
    private <T> void warmUpAlgorithm(Consumer<T> algorithm, T data, int warmUpIterations) {
        for (int i = 0; i < warmUpIterations; i++) {
            T dataCopy = deepCopy(data);
            algorithm.accept(dataCopy); // Warm up by applying the algorithm
        }
    }

    /**
     * Deep copies a list or array of strings. This is used to ensure each algorithm run works with a fresh copy.
     *
     * @param data the data to deep copy
     * @return a deep copy of the data
     */
    @SuppressWarnings("unchecked")
    private static <T> T deepCopy(T data) {
        if (data instanceof List) {
            return (T) new ArrayList<>((List<?>) data); // Deep copy for lists
        } else if (data instanceof String[]) {
            return (T) ((String[]) ((String[]) data).clone()); // Deep copy for arrays
        }
        return data; // Return the original data if it's not a list or array
    }

    /**
     * Calculates the average of a list of long values.
     *
     * @param values the list of long values
     * @return the average value
     */
    private static double calculateAverage(List<Long> values) {
        if (values == null || values.isEmpty()) {
            return 0;
        }
        long sum = 0;
        for (long value : values) {
            sum += value;
        }
        return (double) sum / values.size();
    }
}
