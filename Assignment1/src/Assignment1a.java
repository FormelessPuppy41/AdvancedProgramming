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
    public void runAssignment1a(int column, int from, boolean warmup, boolean simulate, Map<String, List<String[]>> fileMap, List<Integer> sampleSizes) {

        // Print the header
        System.out.println("**********************************************************\n");
        System.out.println("Question 1a: Sorting movie data based on the start time");
        System.out.println("**********************************************************\n");

        // Iterate over the sample sizes
        for (int sampleSize : sampleSizes) {
            System.out.println("Sample size: " + sampleSize + "\n");
            
            // Sort each individual or combined file and print its name
            for (Map.Entry<String, List<String[]>> entry : fileMap.entrySet()) {
                String fileName = entry.getKey();
                List<String[]> fileContent = entry.getValue();

                sortData(fileContent, fileName, column, from, warmup, simulate, sampleSize);
            }
            // indicate next sample size
            System.out.println( "----------------------------------------------------------------");
        }
    }

    // Method to output data and measure sorting times for a given dataset
    public void sortData(List<String[]> file, String fileName, int column, int from, boolean warmup, boolean simulate, int sampleSize) {
        // check the imputs and raise errors if necessary
        if (column < 0 || column > 3) {throw new IllegalArgumentException("Column index must be between 0 and 3");}
        if (from < 1 || from >= file.size()) {throw new IllegalArgumentException("From index must be greater than 1 and smaller than the number of records in the file. Note that if from == 0 then the title is filtered aswell.");}

        // log the sorting process, that includes tracking the time it takes to sort the data (i.e. the stopWatch.measureTime method)
        System.out.println("Sorting " + fileName);
        stopWatch.measureTime(data -> sorter.quickSort(file, column, from, sampleSize), file, warmup, simulate);
        System.out.println("Finished sorting " + fileName + "\n");
        
        // print the sorted data
        // outputSortedData(file, sampleSize);
    }

    // Method to print sorted data
    public void outputSortedData(List<String[]> file, int sampleSize) {
        System.out.println("Sorted data for the first " + sampleSize + " rows:");
        
        // Iterate over the sorted list and print each row
        for (int i = 0; i < Math.min(sampleSize, file.size()); i++) {
            String[] row = file.get(i);
            
            // Assumes each row is an array of strings just like csv, join the columns with commas
            System.out.println(String.join(", ", row));
        }
    }
}
