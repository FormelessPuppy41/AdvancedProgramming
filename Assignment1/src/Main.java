import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

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
        //main.runAssignment1a();

        // ------------- Run Assignment 1b -------------
        String searchMovieTitle = "M";
        Double searchRating = null;
        Integer searchDuration = null;
        Integer searchStartTime = null;
        //main.runAssignment1b(searchMovieTitle, searchRating, searchDuration, searchStartTime);

        // ------------- Run Assignment 1d -------------
        int startingNodeIndex = 1;
        main.runAssignment1d(startingNodeIndex);
    }

    //TODO: Add these methods in a separate class: Assignments.java class Same for the processData method

    // ------------- Assignment 1a -------------
    private void runAssignment1a() {
        System.out.println("************************************");
        System.out.println("Assignment 1a: Sorting movie data based on start time");
        
        Sorter sorter = new Sorter();
        Comparator<Movie> movieComparatorAscending = MovieComparators.byStartTime(true);

        sorter.sortData(this.file1, movieComparatorAscending, "File1");
        sorter.sortData(this.file2, movieComparatorAscending, "File2");
        sorter.sortData(this.file3, movieComparatorAscending, "File3");
        sorter.sortData(this.file4, movieComparatorAscending, "File4");
        sorter.sortData(this.file5, movieComparatorAscending, "File5");

        // utilities.measureTime(data -> sorter.sortData(this.file1, "File1"), this.file1, false, false);
        // utilities.measureTime(data -> sorter.sortData(this.file2, "File2"), this.file2, false, false);
        // utilities.measureTime(data -> sorter.sortData(this.file3, "File3"), this.file3, false, false);
        // utilities.measureTime(data -> sorter.sortData(this.file4, "File4"), this.file4, false, false);
        // utilities.measureTime(data -> sorter.sortData(this.file5, "File5"), this.file5, false, false);

        //outputData(this.file1);
    }

    // ------------- Assignment 1b -------------
    private void runAssignment1b(String searchTitle, Double searchRating, Integer searchDuration, Integer searchStartTime) {
        System.out.println("************************************");
        System.out.println("Assignment 1b: Searching for a movie based on title and rating");

        List<Movie> currentFile = this.file1;

        Sorter sorter = new Sorter();
        Comparator<Movie> movieComparatorTitleRatingAscending = MovieComparators.byTitleThenRating(true);
        
        sorter.sortData(currentFile, movieComparatorTitleRatingAscending, "File5");
        // outputData(currentFile);

        // Create a searcher instance and search for the movie title.
        Searcher searcher = new Searcher();
        searcher.findMovie(currentFile, false, false, searchTitle, searchRating, searchDuration, searchStartTime);
        
    }

    // ------------- Assignment 1d -------------
    private void runAssignment1d(int startingNodeIndex) {
        System.out.println("************************************");
        System.out.println("Assignment 1d: Optimising movie schedule based on the start time");

        List<Movie> currentFile = this.file1;

        Sorter sorter = new Sorter();
        Comparator<Movie> movieComparatorStartTimeAscending = MovieComparators.byStartTime(true);
        sorter.sortData(currentFile, movieComparatorStartTimeAscending, "File1");

        Movie startingNode = currentFile.get(startingNodeIndex); // Must be from the sorted data.
        
        DirectedGraphInitialiser graphInitialiser = new DirectedGraphInitialiser(currentFile, startingNode, sorter);
        
        // Create a new Optimiser instance to optimise the movie schedule
        Optimiser optimiser = new Optimiser(currentFile, graphInitialiser.getGraph(), graphInitialiser.getArcs(), startingNode);
        //Double[] path = optimiser.optimise(true);
        
        utilities.measureTime(data -> optimiser.optimise(false), currentFile, false, false);
        
        List<Movie> sequence = optimiser.getOptimalMovieSequence(true, 1499);

        //FIXME: fix the output of the sequence.
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