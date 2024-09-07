import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    private StopWatch stopWatch = new StopWatch();
    private Sorter sorter = new Sorter();
    private Comparator comparator = new Comparator();
    private Utilities utilities = new Utilities(this.stopWatch);

    public static void main(String[] args) {

        // Create a Main instance
        Main main = new Main();

        // Create a CSVReader instance
        CSVReader csvReader = new CSVReader();
        
        // Read CSV files
        List<String[]> file1 = csvReader.readCSV("Assignment1/data/movies_1.csv");
        List<String[]> file2 = csvReader.readCSV("Assignment1/data/movies_2.csv");
        List<String[]> file3 = csvReader.readCSV("Assignment1/data/movies_3.csv");
        List<String[]> file4 = csvReader.readCSV("Assignment1/data/movies_4.csv");
        List<String[]> file5 = csvReader.readCSV("Assignment1/data/movies_5.csv");
        
        // Map for individual files with their names
        Map<String, List<String[]>> fileMap = new HashMap<>();
        fileMap.put("file1", file1);
        fileMap.put("file2", file2);
        fileMap.put("file3", file3);
        fileMap.put("file4", file4);
        fileMap.put("file5", file5);

        // Add combined files to the map -- Not implemented in the assignment because we chose to use subsets of provided data for the time complexity analysis.
            // fileMap.put("file1 + file2", combineFiles(file1, file2));
            // fileMap.put("file3 + file4", combineFiles(file3, file4));
            // fileMap.put("file1 + file2 + file3 + file4", combineFiles(combineFiles(file1, file2), combineFiles(file3, file4)));

        // Create StopWatch and Sorter instances
            // StopWatch stopWatch = new StopWatch();
            // Sorter sorter = new Sorter();
            // Comparator comparator = new Comparator();
            // Utilities utilities = new Utilities(stopWatch);
        
        // Configure model
        int column = 0; // 0 title (string), 1 rating (float), 2 duration (int), 3 startTime (int)
        int from = 1; // 1 to skip the header, 0 to include the header. Must be smaller than the number of records in the file, otherwise an error is raised.
        boolean warmup = false;
        boolean simulate = false;
        
        // Configure sample sizes for testing running times. [1,1500]
        List<Integer> sample_sizes = new ArrayList<>();
        sample_sizes.add(250);
        sample_sizes.add(500);
        sample_sizes.add(750);
        sample_sizes.add(1000);
        sample_sizes.add(1500);
        
        //
        // Question 1a: Sorting movie data based on the start time
        //
        runAssignment1a(column, from, warmup, simulate, fileMap, sample_sizes);
    
        
        //
        // Question 1b: Sorting movie data based on their Title first and then by their Rating.
        //

        // Configure model
        sample_sizes = new ArrayList<>();
        sample_sizes.add(250);

        int column1 = 0; // 0 title (string), 1 rating (float), 2 duration (int), 3 startTime (int)
        int column2 = 1; // 0 title (string), 1 rating (float), 2 duration (int), 3 startTime (int)

        runAssignment1b(column1, column2, from, warmup, simulate, fileMap, sample_sizes);

    }


    // TODO: Probably remove the assignment files and just add methods here. Also, make the stopwatch etc a class variable.

    private static void runAssignment1a(int column, int from, boolean warmup, boolean simulate, Map<String, List<String[]>> fileMap, List<Integer> sampleSizes) {
        // Print the header
        System.out.println("**********************************************************\n");
        System.out.println("Question 1a: Sorting movie data based on the start time");
        System.out.println("**********************************************************\n");

        // Create a SorterApplier instance and run the sorting with names
        SorterApplier sorterApplier = new SorterApplier();
        sorterApplier.run(sampleSizes, column, from, warmup, simulate, fileMap);
    } 

    private static void runAssignment1b(int column1, int column2, int from, boolean warmup, boolean simulate, Map<String, List<String[]>> fileMap, List<Integer> sampleSizes) {
        // Print the header
        System.out.println("**********************************************************\n");
        System.out.println("Question 1b: Sorting movie data based on their Title first and then by their Rating.");
        System.out.println("**********************************************************\n");

        // Create an Assignment1b instance and run the sorting with names
        
        // first sort by the second column, then by the first. As the first sorting will be preserved, the second sorting will be the primary sorting.
    }


    // Method to combine two movie lists
    private static List<String[]> _combineFiles(List<String[]> file1, List<String[]> file2) {
        List<String[]> combinedFiles = new ArrayList<>(file1);
        combinedFiles.addAll(file2);
        return combinedFiles;
    }
}
