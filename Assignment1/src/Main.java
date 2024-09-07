import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    private StopWatch stopWatch = new StopWatch();
    private Utilities utilities = new Utilities(this.stopWatch);

    public static void main(String[] args) {

        // ------------- Read CSV files -------------

        // Create a CSVReader instance
        CSVReader csvReader = new CSVReader();
        // Read the CSV files
        List<String[]> file1 = csvReader.readCSV("Assignment1/data/movies_1.csv");
        List<String[]> file2 = csvReader.readCSV("Assignment1/data/movies_2.csv");
        List<String[]> file3 = csvReader.readCSV("Assignment1/data/movies_3.csv");
        List<String[]> file4 = csvReader.readCSV("Assignment1/data/movies_4.csv");
        List<String[]> file5 = csvReader.readCSV("Assignment1/data/movies_5.csv");
        
        // ------------- Create helper stores -------------
        
        // Map for individual files with their names
        Map<String, List<String[]>> fileMap = new HashMap<>();
        // Configure the columns to be sorted by
        int[] sortColumns = new int[]{}; // 0 title (string), 1 rating (float), 2 duration (int), 3 startTime (int)

        //
        // Question 1a: Sorting movie data based on the start time
        //

        // ------------ Configure model ------------
        // Add the files to the map
        fileMap.put("file1", file1);
        fileMap.put("file2", file2);
        fileMap.put("file3", file3);
        fileMap.put("file4", file4);
        fileMap.put("file5", file5);

        // add the column to sort by
        sortColumns = new int[]{3}; // 0 title (string), 1 rating (float), 2 duration (int), 3 startTime (int)

        // configure inputs
        int from = 1; // 1 to skip the header, 0 to include the header. Must be smaller than the number of records in the file, otherwise an error is raised.
        boolean warmup = false;
        boolean simulate = false;

        // ---------- Run the model ------------
        //runAssignment1a(sortColumns, from, warmup, simulate, fileMap, sample_sizes);
        
        //
        // Question 1b: Sorting movie data based on their Title first and then by their Rating.
        //

        // ----------- Configure model ------------
        // Add the files to the map
        fileMap = new HashMap<>(); // Clear the file map
        fileMap.put("file1", file1);

        // Add the columns to sort by
        sortColumns = new int[]{0, 1}; // sort by title first, then by rating

        // Value to search for
        String[] value = new String[]{
            "M", // Title - String
            null, // Rating - Float
            null, // Duration - Int
            null // StartTime - Int
        };
        
        // ---------- Run the model ------------
        runAssignment1b(sortColumns, from, warmup, simulate, fileMap, value);

    }


    // TODO: Probably remove the assignment files and just add methods here. Also, make the stopwatch etc a class variable.

    private static void runAssignment1a(int[] sortColumns, int from, boolean warmup, boolean simulate, Map<String, List<String[]>> fileMap, List<Integer> sampleSizes) {
        // Print the header
        System.out.println("**********************************************************\n");
        System.out.println("Question 1a: Sorting movie data based on the start time");
        System.out.println("**********************************************************\n");
        
        // Create a AlgorithmApplier instance and run the sorting with names
        AlgorithmApplier algorithmApplier = new AlgorithmApplier(sortColumns);
        algorithmApplier.sortDataFileMap(from, warmup, simulate, fileMap);
    } 

    // NOTE: All movies are asked, so only do the search for the last file containing all movies. 
    private static void runAssignment1b(int[] sortColumns, int from, boolean warmup, boolean simulate, Map<String, List<String[]>> fileMap, String[] value) {
        // Print the header
        System.out.println("**********************************************************\n");
        System.out.println("Question 1b: Sorting movie data based on their Title first and then by their Rating.");
        System.out.println("**********************************************************\n");
    
        // Create an instance of AlgorithmApplier to perform the sorting/searching
        AlgorithmApplier algorithmApplier = new AlgorithmApplier(sortColumns);
        algorithmApplier.sortDataFileMap(from, warmup, simulate, fileMap);

        // Search for the value in the last file
        int[] searchColumns = new int[]{0, 1}; // search by title
        AlgorithmApplier algorithmApplier2 = new AlgorithmApplier(searchColumns);
        algorithmApplier2.searchForValueFileMap(fileMap, value);

        
    }
    


    // Method to combine two movie lists
    private static List<String[]> _combineFiles(List<String[]> file1, List<String[]> file2) {
        List<String[]> combinedFiles = new ArrayList<>(file1);
        combinedFiles.addAll(file2);
        return combinedFiles;
    }
}
