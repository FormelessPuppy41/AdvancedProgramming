import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

/**
 * Main class to run the assignments for the first assignment of the Minor. This class reads the CSV files and runs the assignments.
 * 
 * @author 611968bq - Berend Quist
 */
public class Main {
    /**
     * Main method to run the assignments for the first assignment of the Minor. This method reads the CSV files and runs the assignments.
     * 
     * @param args
     */
    public static void main(String[] args) {

        // ------------- Read CSV files -------------
        List<Movie> file1 = new ArrayList<>();
        List<Movie> file2 = new ArrayList<>();
        List<Movie> file3 = new ArrayList<>();
        List<Movie> file4 = new ArrayList<>();
        List<Movie> file5 = new ArrayList<>();

        CSVHandler csvReader = new CSVHandler();
        try {
            file1 = csvReader.processData("Assignment1/data/movies_1.csv");
            file2 = csvReader.processData("Assignment1/data/movies_2.csv");
            file3 = csvReader.processData("Assignment1/data/movies_3.csv");
            file4 = csvReader.processData("Assignment1/data/movies_4.csv");
            file5 = csvReader.processData("Assignment1/data/movies_5.csv");
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
        }

        Assignments assignments = new Assignments();

        // ------------- Run Assignment 1a -------------
        System.out.println("\n\n******************************************************************************************************");
        System.out.println("Running Assignment 1a: Sorting movie data based on start time");

        assignments.runAssignment1a(file1, "File1", true);
        assignments.runAssignment1a(file2, "File2", true);
        assignments.runAssignment1a(file3, "File3", true);
        assignments.runAssignment1a(file4, "File4", true);
        assignments.runAssignment1a(file5, "File5", true);

        // ------------- Run Assignment 1b -------------
        System.out.println("\n\n******************************************************************************************************");
        System.out.println("Running Assignment 1b: Searching for a movie based on title and rating");

        // Search for a movie with (un)specified title, rating, duration, and start time
        String searchMovieTitle = "M";
        Double searchRating = null;
        Integer searchDuration = null;
        Integer searchStartTime = null;

        // Search each file for the movie
        assignments.runAssignment1b(file1, "File1",searchMovieTitle, searchRating, searchDuration, searchStartTime);
        assignments.runAssignment1b(file2, "File2",searchMovieTitle, searchRating, searchDuration, searchStartTime);
        assignments.runAssignment1b(file3, "File3",searchMovieTitle, searchRating, searchDuration, searchStartTime);
        assignments.runAssignment1b(file4, "File4",searchMovieTitle, searchRating, searchDuration, searchStartTime);
        assignments.runAssignment1b(file5, "File5",searchMovieTitle, searchRating, searchDuration, searchStartTime);


        // ------------- Run Assignment 1d -------------
        System.out.println("\n\n******************************************************************************************************");
        System.out.println("Running Assignment 1d: Optimising movie schedule");
        int startingNodeIndex = 1;
        assignments.runAssignment1d(file1, "File1", startingNodeIndex);
        assignments.runAssignment1d(file2, "File2", startingNodeIndex);
        assignments.runAssignment1d(file3, "File3", startingNodeIndex);
        assignments.runAssignment1d(file4, "File4", startingNodeIndex);
        assignments.runAssignment1d(file5, "File5", startingNodeIndex);

        // ------------- Run Assignment 1e -------------
        System.out.println("\n\n******************************************************************************************************");
        System.out.println("Running Assignment 1e: Optimising movie schedule with a maximum time spent of 6 hours");
        assignments.runAssignment1e(file1, "File1");
        assignments.runAssignment1e(file2, "File2");
        assignments.runAssignment1e(file3, "File3");
        assignments.runAssignment1e(file4, "File4");
        assignments.runAssignment1e(file5, "File5");

        // ------------- Run Assignment 1f -------------
        System.out.println("\n\n******************************************************************************************************");
        System.out.println("Running Assignment 1f: Finding the minimum number of rooms required to show all movies");
        assignments.runAssignment1f(file1, "File1");
        assignments.runAssignment1f(file2, "File2");
        assignments.runAssignment1f(file3, "File3");
        assignments.runAssignment1f(file4, "File4");
        assignments.runAssignment1f(file5, "File5");
    }
}