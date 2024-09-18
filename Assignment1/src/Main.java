import java.util.ArrayList;
import java.util.List;

import java.io.IOException;

public class Main {
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
        assignments.runAssignment1a(file1, "File1");
        assignments.runAssignment1a(file2, "File2");
        assignments.runAssignment1a(file3, "File3");
        assignments.runAssignment1a(file4, "File4");
        assignments.runAssignment1a(file5, "File5");

        // ------------- Run Assignment 1b -------------
        // Search for a movie with (un)specified title, rating, duration, and start time
        String searchMovieTitle = "M";
        Double searchRating = null;
        Integer searchDuration = null;
        Integer searchStartTime = null;

        // Search each file for the movie
        assignments.runAssignment1b(file1, searchMovieTitle, searchRating, searchDuration, searchStartTime);
        assignments.runAssignment1b(file2, searchMovieTitle, searchRating, searchDuration, searchStartTime);
        assignments.runAssignment1b(file3, searchMovieTitle, searchRating, searchDuration, searchStartTime);
        assignments.runAssignment1b(file4, searchMovieTitle, searchRating, searchDuration, searchStartTime);
        assignments.runAssignment1b(file5, searchMovieTitle, searchRating, searchDuration, searchStartTime);


        // ------------- Run Assignment 1d -------------
        int startingNodeIndex = 1;
        //assignments.runAssignment1d(file1, startingNodeIndex);
        //assignments.runAssignment1d(file2, startingNodeIndex);
        //assignments.runAssignment1d(file3, startingNodeIndex);
        //assignments.runAssignment1d(file4, startingNodeIndex);
        //assignments.runAssignment1d(file5, startingNodeIndex);

        // ------------- Run Assignment 1e -------------
        //assignments.runAssignment1e(file1);
        //assignments.runAssignment1e(file2);
        //assignments.runAssignment1e(file3);
        //assignments.runAssignment1e(file4);
        //assignments.runAssignment1e(file5);

        // ------------- Run Assignment 1f -------------
        //assignments.runAssignment1f(file1);
        //assignments.runAssignment1f(file2);
        //assignments.runAssignment1f(file3);
        //assignments.runAssignment1f(file4);
        //assignments.runAssignment1f(file5);
    }

    // -------------- Helper Functions --------------

    private void outputData(List<Movie> movies) {
        for (Movie movie : movies) {
            System.out.println(movie.toString());
        }
    }
}