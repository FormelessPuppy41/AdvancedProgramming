import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * A utility class that provides functionality for measuring the time taken by algorithms
 * and for deep copying data structures such as lists and arrays.
 * 
 * @author 611968bq - Berend Quist
 */
public class Utilities {
    protected final StopWatch stopWatch;

    /**
     * Constructs a Utilities object with a given stopwatch for measuring time.
     * 
     * @param stopWatch The stopwatch used to measure execution time.
     */
    public Utilities(StopWatch stopWatch) {
        this.stopWatch = stopWatch;
    }

    /**
     * Measures the time it takes to apply an algorithm to a given dataset.
     * 
     * The method allows for warm-up runs (10 runs to stabilize JIT optimizations)
     * and can either run the algorithm once or simulate multiple runs (100 times) 
     * to calculate the average time.
     *
     * @param algorithm The algorithm to apply.
     * @param data The data to which the algorithm will be applied.
     * @param warmup Whether to perform warm-up runs (10 runs).
     * @param simulate Whether to run the algorithm 100 times and calculate the average time.
     */
    public <T> void measureTime(Consumer<T> algorithm, T data, boolean warmup, boolean simulate) {
        // Perform warm-up runs if requested
        if (warmup) {
            warmUpAlgorithm(algorithm, data, 10); // Warm up by running the algorithm 10 times
        }

        // Run simulation or a single run
        if (simulate) {
            runSimulation(algorithm, data, 100); // Simulate 100 runs and calculate the average time
        } else {
            runSingle(algorithm, data); // Run the algorithm once
        }
    }

    /**
     * Creates a deep copy of a list or array of strings to ensure each algorithm run uses a fresh copy.
     * 
     * @param data The data to deep copy.
     * @return A deep copy of the provided data.
     */
    @SuppressWarnings("unchecked")
    public static <T> T deepCopy(T data) {
        if (data instanceof List) {
            return (T) new ArrayList<>((List<?>) data); // Deep copy for lists
        } else if (data instanceof String[]) {
            return (T) ((String[]) ((String[]) data).clone()); // Deep copy for arrays
        }
        return data; // Return the original data if it's not a list or array
    }

    // ------------------------------ Private Helper Methods ------------------------------

    /**
     * Runs the algorithm multiple times, measures the time for each run, and calculates the average time.
     *
     * @param algorithm The algorithm to apply.
     * @param data The data to apply the algorithm to.
     * @param iterations The number of iterations to run.
     */
    private <T> void runSimulation(Consumer<T> algorithm, T data, int iterations) {
        List<Long> runningTimesNano = new ArrayList<>();

        // Run the algorithm multiple times and record the running times
        for (int i = 0; i < iterations; i++) {
            T dataCopy = deepCopy(data);
            
            stopWatch.reset();
            stopWatch.start();
            algorithm.accept(dataCopy); // Apply the algorithm
            stopWatch.stop();
            runningTimesNano.add(stopWatch.getElapsedTimeInNano());
        }

        // Calculate the average time
        double averageTimeNano = calculateAverage(runningTimesNano);
        double averageTimeMilli = averageTimeNano / 1_000_000.0; // Convert to milliseconds

        // Output the average time
        System.out.println("Average elapsed time over " + iterations + " runs: " 
            + averageTimeMilli + " milliseconds (" + averageTimeNano + " nanoseconds)");
    }

    /**
     * Runs the algorithm once and measures the time.
     *
     * @param algorithm The algorithm to apply.
     * @param data The data to apply the algorithm to.
     */
    private <T> void runSingle(Consumer<T> algorithm, T data) {
        stopWatch.reset();
        stopWatch.start();
        algorithm.accept(data); // Apply the algorithm
        stopWatch.stop();
        stopWatch.output();
    }

    /**
     * Performs warm-up runs to stabilize JIT optimizations before measuring the time.
     *
     * @param algorithm The algorithm to apply.
     * @param data The data to apply the algorithm to.
     * @param warmUpIterations Number of warm-up runs.
     */
    private <T> void warmUpAlgorithm(Consumer<T> algorithm, T data, int warmUpIterations) {
        for (int i = 0; i < warmUpIterations; i++) {
            T dataCopy = deepCopy(data);
            algorithm.accept(dataCopy); // Warm up by applying the algorithm
        }
    }

    /**
     * Calculates the average of a list of long values.
     *
     * @param values The list of long values representing the measured times.
     * @return The average value of the list.
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
