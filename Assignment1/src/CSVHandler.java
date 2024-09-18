import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A utility class for reading and printing data from CSV files.
 * 
 * The CSVHandler class reads CSV files where values are delimited by semicolons (;).
 * It provides methods to:
 * - Read CSV data from a file into a list of Movie objects.
 * - Print the CSV data to the console (mainly for testing purposes).
 * 
 * @author 611968bq
 */
public class CSVHandler {

    /**
     * Processes the CSV data from the specified file path and returns a list of Movie objects.
     * 
     * @param filePath The path to the CSV file.
     * @return A list of Movie objects created from the CSV data.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public List<Movie> processData(String filePath) throws IOException {
        List<String[]> rawData;
        try {
            rawData = readCSV(filePath);
        } catch (IOException e) {
            throw new IOException("Error reading the CSV file: " + e.getMessage(), e);
        }

        List<Movie> movies = new ArrayList<>();
        for (int i = 1; i < rawData.size(); i++) {
            try {
                String[] row = rawData.get(i);
                Movie movie = parseMovie(row);
                movies.add(movie);
            } catch (IllegalArgumentException e) {
                System.err.println("Skipping invalid row " + i + ": " + e.getMessage());
            }
        }
        return movies;
    }

    /**
     * Reads a CSV file from the specified file path.
     * Assumes values in the CSV file are delimited by semicolons (;).
     * 
     * @param filePath The path to the CSV file.
     * @return A list of string arrays, where each array represents a row of the CSV file.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    private List<String[]> readCSV(String filePath) throws IOException {
        List<String[]> data = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Split each line by the semicolon delimiter and store in the data list
                String[] values = line.split(";");
                data.add(values);
            }
        } catch (IOException e) {
            System.err.println("Error while reading file: " + filePath);
            throw e; // re-throw the exception after logging the error
        }

        return data;
    }

    /**
     * Parses a row of strings into a Movie object.
     * 
     * @param row A string array representing a row from the CSV file.
     * @return A Movie object parsed from the row.
     * @throws IllegalArgumentException if the row format is invalid.
     */
    private Movie parseMovie(String[] row) {
        if (row.length < 4) {
            throw new IllegalArgumentException("Invalid row length: " + row.length);
        }
        try {
            String title = row[0];
            Double rating = Double.parseDouble(row[1]);
            int duration = Integer.parseInt(row[2]);
            int startTime = Integer.parseInt(row[3]);
            return new Movie(title, rating, duration, startTime);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error parsing movie data: " + String.join(", ", row), e);
        }
    }

    /**
     * Prints the list of Movie objects to the console.
     * Each movie is printed on a new line.
     * 
     * @param movies The list of Movie objects to print.
     */
    public void printCSV(List<Movie> movies) {
        for (Movie movie : movies) {
            System.out.println(movie);
        }
    }
}
