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
        
        // Create a StopWatch instance
        StopWatch stopWatch = new StopWatch();

        // Question 1: Sorting Algorithm for sorting the scheduled movies by increasing start time.
        Sorter sorter = new Sorter();
        // sorter.quickSort(file1, 0, 0, file1.size() - 1);
        
        stopWatch.measureTime(data -> sorter.quickSort(file2, 0, 0, file2.size() - 1), file2);

        // Output sorted data (for demonstration purposes)
        // csvReader.printCSV(file1);
    }
}
