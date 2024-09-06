import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

    // Method to read CSV files
    public List<String[]> readCSV(String filePath) {
        List<String[]> data = new ArrayList<>();

        // Read the file and store the data in a list
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // loop over the lines in the file, split them by the delimiter and store them in the data list
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                data.add(values);
            }
        } catch (IOException e) {
            // Print an error message if the file cannot be read
            System.out.println("Error reading file: " + filePath);
            e.printStackTrace();
        }
        return data;
    }

    // Method to print CSV data (for testing purposes)
    public void printCSV(List<String[]> data) {
        // loop over the rows in the data list and print them
        for (String[] row : data) {
            for (String value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }
}
