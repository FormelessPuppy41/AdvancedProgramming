import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Create a CSVReader instance
        CSVReader csvReader = new CSVReader();
        
        // Read CSV files (you can modify these paths)
        List<String[]> file1 = csvReader.readCSV("Assignment1/data/movies_1.csv");
        List<String[]> file2 = csvReader.readCSV("Assignment1/data/movies_2.csv");
        List<String[]> file3 = csvReader.readCSV("Assignment1/data/movies_3.csv");
        List<String[]> file4 = csvReader.readCSV("Assignment1/data/movies_4.csv");
        List<String[]> file5 = csvReader.readCSV("Assignment1/data/movies_5.csv");

        // Sorting Example
        Sorter sorter = new Sorter();
        sorter.StandardSort(file1, 0);
        

        // Output sorted data (for demonstration purposes)
        // csvReader.printCSV(file1);
    }
}
