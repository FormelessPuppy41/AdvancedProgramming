import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Utilities {
    protected final StopWatch stopWatch;

    public Utilities(StopWatch stopWatch) {
        this.stopWatch = stopWatch;
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
            _warmUpAlgorithm(algorithm, data, 10); // Warm up by running the algorithm 10 times
        }

        // Run simulation or a single run
        if (simulate) {
            _runSimulation(algorithm, data, 100); // Simulate 100 runs and calculate average time
        } else {
            _runSingle(algorithm, data); // Run the algorithm once
        }
    }

    /**
     * Deep copies a list or array of strings. This is used to ensure each algorithm run works with a fresh copy.
     *
     * @param data the data to deep copy
     * @return a deep copy of the data
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


    // ------------------------------ Helper methods ------------------------------


    /**
     * Runs the algorithm 100 times, measures the time, and calculates the average.
     *
     * @param algorithm the algorithm to apply
     * @param data the data to apply the algorithm to
     * @param iterations the number of iterations to run
     */
    private <T> void _runSimulation(Consumer<T> algorithm, T data, int iterations) {
        List<Long> runningTimesNano = new ArrayList<>();

        // Run the algorithm multiple times and record the running times
        for (int i = 0; i < iterations; i++) {
            T dataCopy = deepCopy(data);
            
            this.stopWatch.reset();
            this.stopWatch.start();
            algorithm.accept(dataCopy); // Apply the algorithm
            this.stopWatch.stop();
            runningTimesNano.add(stopWatch.getElapsedTimeInNano());
        }

        // Calculate the average times
        double averageTimeNano = _calculateAverage(runningTimesNano);
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
    private <T> void _runSingle(Consumer<T> algorithm, T data) {

        this.stopWatch.reset();
        this.stopWatch.start();
        algorithm.accept(data); // Apply the algorithm
        this.stopWatch.stop();
        this.stopWatch.output();
    }

    /**
     * Performs warm-up runs to stabilize JIT optimizations.
     *
     * @param algorithm the algorithm to apply
     * @param data the data to apply the algorithm to
     * @param warmUpIterations number of warm-up runs
     */
    private <T> void _warmUpAlgorithm(Consumer<T> algorithm, T data, int warmUpIterations) {
        for (int i = 0; i < warmUpIterations; i++) {
            T dataCopy = deepCopy(data);
            algorithm.accept(dataCopy); // Warm up by applying the algorithm
        }
    }

    /**
     * Calculates the average of a list of long values.
     *
     * @param values the list of long values
     * @return the average value
     */
    private static double _calculateAverage(List<Long> values) {
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
