import java.util.List;
import java.util.Map;

public class AlgorithmApplier {

    private StopWatch stopWatch = new StopWatch();
    private Utilities utilities = new Utilities(stopWatch);
    private Sorter sorter;
    private Comparator comparator;

    public AlgorithmApplier(int[] sortColumns) {
        this.comparator = new Comparator(sortColumns);
        this.sorter = new Sorter(comparator);
    }

    public void sortDataFileMap(int from, boolean warmup, boolean simulate, Map<String, List<String[]>> fileMap) {
        // Iterate over the sample sizes
        // Sort each individual or combined file and print its name
        for (Map.Entry<String, List<String[]>> entry : fileMap.entrySet()) {
            String fileName = entry.getKey();
            List<String[]> fileContent = entry.getValue();
            
            sortDataSingleFile(fileContent, fileName, from, warmup, simulate);

            // Print a separator between the different files
            System.out.println( "----------------------------------------------------------------");
        }
    }

    // Method to output data and measure sorting times for a given dataset
    private void sortDataSingleFile(List<String[]> file, String fileName, int from, boolean warmup, boolean simulate) {
        // check the imputs and raise errors if necessary
        if (from < 1 || from >= file.size()) {throw new IllegalArgumentException("From index must be greater than 1 and smaller than the number of records in the file. Note that if from == 0 then the title is filtered aswell.");}
        
        // Set the sample size to the number of records in the file
        int sampleSize = file.size() - 1;

        // log the sorting process, that includes tracking the time it takes to sort the data (i.e. the stopWatch.measureTime method)
        System.out.println("Sorting " + fileName);
        this.utilities.measureTime(data -> this.sorter.quickSort(file, from, sampleSize), file, warmup, simulate);
        System.out.println("Finished sorting " + fileName + "\n");
        
        // print the sorted data
        outputSortedData(file, sampleSize);
    }

    // Method to print sorted data
    private void outputSortedData(List<String[]> file, int sampleSize) {
        System.out.println("Sorted data for the first " + sampleSize + " rows:");
        
        // Iterate over the sorted list and print each row
        for (int i = 0; i < Math.min(sampleSize, file.size()); i++) {
            String[] row = file.get(i);
            
            // Assumes each row is an array of strings just like csv, join the columns with commas
            System.out.println(String.join(", ", row)); 
            // row[1]);
        }
    } 

    public void searchForValueFileMap(Map<String, List<String[]>> fileMap, String[] value) {
        // Iterate over the sample sizes
        // Search for the value in each individual or combined file and print its name
        for (Map.Entry<String, List<String[]>> entry : fileMap.entrySet()) {
            String fileName = entry.getKey();
            List<String[]> fileContent = entry.getValue();
            
            searchForValueSingleFile(fileContent, fileName, value);

            // Print a separator between the different files
            System.out.println( "----------------------------------------------------------------");
        }
    }

    private void searchForValueSingleFile(List<String[]> fileContent, String fileName, String[] value) {
        System.out.println("Searching for value: " + value[0] + " in " + fileName);
        int index = new Searcher(this.comparator).binarySearch(fileContent, 0, fileContent.size() - 1, value);
        System.out.println("Index: " + index);
        System.out.println("Found value: " + String.join(", ", fileContent.get(index)));
    }
    
}
