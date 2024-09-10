import java.util.ArrayList;
import java.util.List;

public class Main {
    
    private StopWatch stopWatch = new StopWatch();
    private Utilities utilities = new Utilities(this.stopWatch);

    protected List<Movie> file1;
    protected List<Movie> file2;
    protected List<Movie> file3;
    protected List<Movie> file4;
    protected List<Movie> file5;

    public static void main(String[] args) {
        Main main = new Main();

        main.processData();

        // ------------- Run Assignment 1a -------------
        main.runAssignment1a();

        // ------------- Run Assignment 1b -------------
        // main.runAssignment1b();

        // ------------- Run Assignment 1d -------------
        // main.runAssignment1d();
    }

    //TODO: Add these methods in a separate class: Assignments.java class Same for the processData method

    // ------------- Assignment 1a -------------
    private void runAssignment1a() {
        Sorter2 sorter = new Sorter2();

        sorter.quickSort(this.file1, 1, this.file1.size() - 1);
        sorter.quickSort(this.file2, 1, this.file2.size() - 1);
        sorter.quickSort(this.file3, 1, this.file3.size() - 1);
        sorter.quickSort(this.file4, 1, this.file4.size() - 1);
        sorter.quickSort(this.file5, 1, this.file5.size() - 1);

        //utilities.measureTime(data -> sorter.quickSort(this.file1, 1, this.file1.size() - 1), this.file1, false, false);
        //utilities.measureTime(data -> sorter.quickSort(this.file2, 1, this.file2.size() - 1), this.file2, false, false);
        //utilities.measureTime(data -> sorter.quickSort(this.file3, 1, this.file3.size() - 1), this.file3, false, false);
        // utilities.measureTime(data -> sorter.quickSort(this.file4, 1, this.file4.size() - 1), this.file4, false, false);
        // utilities.measureTime(data -> sorter.quickSort(this.file5, 1, this.file5.size() - 1), this.file5, false, false);

        //outputData(this.file1);
    }

    // ------------- Assignment 1b -------------
    private void runAssignment1b() {
        Sorter2 sorter = new Sorter2();
        sorter.quickSort(file5, 1, file5.size() - 1);


    }

    // ------------- Assignment 1d -------------
    private void runAssignment1d() {
        Sorter2 sorter = new Sorter2();
    }

    public void processData() {

        // ------------- Read CSV files -------------
        // Create a CSVReader instance
        CSVReader csvReader = new CSVReader();
        // Read the CSV files
        this.file1 = processData(csvReader.readCSV("Assignment1/data/movies_1.csv"));
        this.file2 = processData(csvReader.readCSV("Assignment1/data/movies_2.csv"));
        this.file3 = processData(csvReader.readCSV("Assignment1/data/movies_3.csv"));
        this.file4 = processData(csvReader.readCSV("Assignment1/data/movies_4.csv"));
        this.file5 = processData(csvReader.readCSV("Assignment1/data/movies_5.csv"));
    }

    // -------------- Helper Functions --------------

    private static List<Movie> processData(List<String[]> file) {
        List<Movie> movies = new ArrayList<>();
        for (int i = 1; i < file.size(); i++) {
            String[] row = file.get(i);
            Movie movie = new Movie(row[0], Double.parseDouble(row[1]), Integer.parseInt(row[2]), Integer.parseInt(row[3]));
            movies.add(movie);
        }
        return movies;
    }

    private void outputData(List<Movie> movies) {
        for (Movie movie : movies) {
            System.out.println(movie.toString());
        }
    }
}