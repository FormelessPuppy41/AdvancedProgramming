import java.util.List;
import java.util.Map;

public class AlgorithmApplier {

    protected final StopWatch stopWatch = new StopWatch();
    protected final Utilities utilities = new Utilities(stopWatch);

    protected final Sorter sorter;
    protected final Searcher searcher;

    protected final Comparator sortComparator;
    protected final Comparator searchComparator;


    public AlgorithmApplier(int[] sortColumns, int[] searchColumns, boolean ascendingSort) {
        this.sortComparator = new Comparator(sortColumns, ascendingSort);
        this.sorter = new Sorter(this.sortComparator);

        this.searchComparator = new Comparator(searchColumns, ascendingSort);
        this.searcher = new Searcher(this.sorter, this.searchComparator);
    }

    public AlgorithmApplier(int[] sortColumns, boolean ascendingSort) {
        this.sortComparator = new Comparator(sortColumns, ascendingSort);
        this.sorter = new Sorter(this.sortComparator);

        this.searchComparator = null;
        this.searcher = null;
    }

    public void sortDataFileMap(int from, boolean warmup, boolean simulate, boolean ascending, Map<String, List<String[]>> fileMap) {
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
        outputData(file);
    }

    public void searchForValueFileMap(Map<String, List<String[]>> fileMap, String[] value, boolean ascending) {
        // Iterate over the sample sizes
        // Search for the value in each individual or combined file and print its name
        for (Map.Entry<String, List<String[]>> entry : fileMap.entrySet()) {
            String fileName = entry.getKey();
            List<String[]> fileContent = entry.getValue();
            
            searchForValueSingleFile(fileContent, fileName, value, ascending);

            // Print a separator between the different files
            System.out.println( "----------------------------------------------------------------");
        }
    }

    private void searchForValueSingleFile(List<String[]> fileContent, String fileName, String[] value, boolean ascending) {
        // Create a new comparator with the sort columns. This is needed to specify which columns to use as search keys.
    
        System.out.println("Searching for value: " + value[0] + " in " + fileName);
        
        int index = this.searcher.findValueInRange(fileContent, value, ascending);
        
        System.out.println("Index: " + index);
        System.out.println("Found value: " + String.join(", ", fileContent.get(index)));
    }

    public void optimiseMovieScheduleFileMap(Map<String, List<String[]>> fileMap, String startingNode) {
        // Iterate over the sample sizes
        // Optimise the movie schedule for each individual or combined file and print its name
        for (Map.Entry<String, List<String[]>> entry : fileMap.entrySet()) {
            String fileName = entry.getKey();
            List<String[]> fileContent = entry.getValue();
            
            System.out.println("Optimising movie schedule for " + fileName);
            optimiseMovieScheduleSingleFile(fileContent, fileName, startingNode);

            // Print a separator between the different files
            System.out.println( "----------------------------------------------------------------");
        }
    }

    private void optimiseMovieScheduleSingleFile(List<String[]> fileContent, String fileName, String startingNode) {
        // Create a new DirectedGraphInitialiser instance to initialise the graph
        DirectedGraphInitialiser graphInitialiser = new DirectedGraphInitialiser(fileContent, startingNode, this.sorter);
        
        // Create a new Optimiser instance to optimise the movie schedule
        Optimiser optimiser = new Optimiser(fileContent, graphInitialiser.getGraph(), graphInitialiser.getArcs(), startingNode);
        optimiser.optimise();
    }


    // Method to print sorted data
    private void outputData(List<String[]> file) {
        System.out.println("Sorted data for the first " + (file.size() - 1) + " rows:");
        
        // Iterate over the sorted list and print each row
        for (int i = 0; i < file.size() - 1; i++) {
            String[] row = file.get(i);
            
            // Assumes each row is an array of strings just like csv, join the columns with commas
            System.out.println(String.join(", ", row)); 
            // row[1]);
        }
    } 
    
}
