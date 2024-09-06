import java.util.List;
import java.util.Map;

public class Assignment1a {

    private StopWatch stopWatch;
    private Sorter sorter;

    public Assignment1a(StopWatch stopWatch, Sorter sorter) {
        this.stopWatch = stopWatch;
        this.sorter = sorter;
    }

    // The method to run the main sorting task
    public void runAssignment1a(boolean warmup, boolean simulate, Map<String, List<String[]>> fileMap, List<Integer> sampleSizes) {

        for (int sampleSize : sampleSizes) {
            System.out.println("Sample size: " + sampleSize + "\n");
            
            // Sort each individual or combined file and print its name
            for (Map.Entry<String, List<String[]>> entry : fileMap.entrySet()) {
                String fileName = entry.getKey();
                List<String[]> fileContent = entry.getValue();

                sortData(fileContent, fileName, warmup, simulate, sampleSize);
            }
            System.out.println( "--------------------------------------------------------------------------------------------------------------------");
        }
    }

    // Method to output data and measure sorting times for a given dataset
    public void sortData(List<String[]> file, String fileName, boolean warmup, boolean simulate, int sampleSize) {
        System.out.println("Sorting " + fileName);
        stopWatch.measureTime(data -> sorter.quickSort(file, 0, 0, sampleSize), file, warmup, simulate);
        System.out.println("Finished sorting " + fileName + "\n");
    }
}
