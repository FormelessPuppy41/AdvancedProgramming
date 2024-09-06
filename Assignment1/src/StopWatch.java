import java.util.function.Consumer;
import java.util.ArrayList;
import java.util.List;

/**
 * A stopwatch accumulates time when it is running. You can
 * repeatedly start and stop the stopwatch. You can use a
 * stopwatch to measure the running time of a program.
 */
public class StopWatch {
    private long elapsedTime;
    private long startTime;
    private boolean isRunning;

    /**
     * Measures the time it takes to apply an algorithm to a given data set.
     * 
     * Method can warmup the algorithm by running it 10 times before measuring the time.
     * 
     * Either runs a simulation of the algorithm 100 times and calculates the average time,
     * Or runs the algorithm once and outputs the time.
     * 
     * @param algorithm the algorithm to apply
     * @param data the data to apply the algorithm to
     * @param warmup whether to perform warm-up runs or not
     * @param simulate whether to run a simulation (n=100) or not
     */
    public <T> void measureTime(Consumer<T> algorithm, T data, boolean warmup, boolean simulate) {
        StopWatch stopWatch = new StopWatch();
        
        if (warmup) {
            // Perform warm-up runs to stabilize JIT optimizations
            for (int i = 0; i < 10; i++) {
                T dataCopy = deepCopy(data);
                algorithm.accept(dataCopy);
            }
        }
        
        if (simulate) {
            List<Long> running_times = new ArrayList<>();

            for (int i = 0; i < 100; i++) {
                // make a deep copy of the data
                T dataCopy = deepCopy(data);

                // start timing
                stopWatch.reset();
                stopWatch.start();

                // apply the algorithm
                algorithm.accept(dataCopy);

                // stop timing and record the time
                stopWatch.stop();
                running_times.add(stopWatch.getElapsedTime());
            }

            double averageTime = calculateAverage(running_times);
            System.out.println("Average time: " + averageTime + " milliseconds");

        } else {
        // Single run
        stopWatch.reset();
        stopWatch.start();

        // apply the algorithm
        algorithm.accept(data); // apply the algorithm

        stopWatch.stop();
        stopWatch.output();
        }
    }


    /**
     * Constructs a stopwatch that is in the stopped state
     * and has no time accumulated.
     */
    public StopWatch() {
        reset();
    }

    /**
     * Starts the stopwatch. Time starts accumulating now.
     */
    public void start() {
        if (isRunning) {
            return;
        }
        isRunning = true;
        startTime = System.currentTimeMillis();
    }

    /**
     * Stops the stopwatch. Time stops accumulating and is
     * added to the elapsed time.
     */
    public void stop() {
        if (!isRunning) {
            return;
        }
        isRunning = false;
        long endTime = System.currentTimeMillis();
        elapsedTime = elapsedTime + endTime - startTime;
    }

    /**
     * Returns the total elapsed time.
     * @return the total elapsed time
     */
    public long getElapsedTime() {
        if (isRunning) {
            long endTime = System.currentTimeMillis();
            return elapsedTime + endTime - startTime;
        } else {
            return elapsedTime;
        }
    }

    /**
     * Stops the watch and resets the elapsed time to 0.
     */
    public void reset() {
        elapsedTime = 0;
        isRunning = false;
    }

    /**
     * Returns a string representation of the stopwatch
     * 
     */
    public void output() {
        System.out.println("Elapsed time: " + getElapsedTime() + " milliseconds");
    }


    // ************* Helper methods *************


    /**
     * Deep copy a list of strings. This method is used to make a copy of the data. 
     * It checks if the data is a list of strings or an array of strings and makes a deep copy of it with the appropriate method.
     * 
     * @param data the list of strings to copy
     * @return a deep copy of the list of strings
     */
    @SuppressWarnings("unchecked")
    private static <T> T deepCopy(T data) {
        if (data instanceof List) {
            return (T) new ArrayList<>((List<?>) data);
        } else if (data instanceof String[]) {
            return (T) ((String[]) ((String[]) data).clone());
        }
        return data;
    }

    /**
     * Calculate the average of a list of long values.
     * 
     * @param values the list of long values
     * @return the average of the long values
     */
    private static double calculateAverage(List<Long> values) {
        if (values == null || values.isEmpty()) {
            return 0;
        }
        System.out.println(values);

        long sum = 0;
        for (long value : values) {
            sum += value;
        }
        return (double) sum / values.size();
    }

}
