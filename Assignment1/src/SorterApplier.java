import java.util.List;
import java.util.Map;

public class SorterApplier {
    
    private StopWatch stopWatch = new StopWatch();
    private Sorter sorter = new Sorter();
    private Comparator comparator = new Comparator();
    private Utilities utilities = new Utilities(stopWatch);

    public void run(List<Integer> sampleSizes, int column, int from, boolean warmup, boolean simulate, Map<String, List<String[]>> fileMap) {
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
        this.utilities.measureTime(data -> this.sorter.quickSort(file, this.comparator, column, from, sampleSize), file, warmup, simulate);
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
