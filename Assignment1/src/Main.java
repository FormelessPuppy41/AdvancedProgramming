import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
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
        StopWatch stopWatch = new StopWatch();
        Sorter sorter = new Sorter();

        //
        // Question 1a: Sorting movie data based on the start time
        //

        // Create an Assignment1a instance and run the sorting with names
        Assignment1a assignment1a = new Assignment1a(stopWatch, sorter);

        // Configure model
        int column = 3; // 0 title (string), 1 rating (float), 2 duration (int), 3 startTime (int)
        int from = 1; // 1 to skip the header, 0 to include the header. Must be smaller than the number of records in the file, otherwise an error is raised.
        boolean warmup = true;
        boolean simulate = false;
        
        // Configure sample sizes for testing running times. [1,1500]
        List<Integer> sample_sizes = new ArrayList<>();
        sample_sizes.add(250);
        sample_sizes.add(500);
        sample_sizes.add(1000);
        sample_sizes.add(1500);

        assignment1a.runAssignment1a(column, from, warmup, simulate, fileMap, sample_sizes);
    }



    // Method to combine two movie lists
    public static List<String[]> combineFiles(List<String[]> file1, List<String[]> file2) {
        List<String[]> combinedFiles = new ArrayList<>(file1);
        combinedFiles.addAll(file2);
        return combinedFiles;
    }

    
}
