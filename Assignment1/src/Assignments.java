import java.util.Comparator;
import java.util.List;

/**
 * The Assignments class contains methods for various movie-related assignments.
 * These include sorting movies, searching for a specific movie, optimizing movie schedules,
 * and finding the minimum number of rooms required to show all movies.
 * 
 * @author 611968bq - Berend Quist
 */
public class Assignments {
    protected final StopWatch stopWatch = new StopWatch();
    protected final Utilities utilities = new Utilities(stopWatch);

    /**
     * Assignment 1a: Sorting movie data based on the start time.
     * 
     * @param file  The file containing the list of movies.
     */
    public void runAssignment1a(List<Movie> file, String keyTitle) {
        System.out.println("************************************");
        System.out.println("Assignment 1a: Sorting movie data based on start time");

        Sorter sorter = new Sorter();
        Comparator<Movie> movieComparatorAscending = MovieComparators.byStartTime(true);

        sorter.sortData(file, movieComparatorAscending, keyTitle);
    }

    /**
     * Assignment 1b: Searching for a movie based on title, rating, duration, and start time.
     * 
     * @param file The list of movies to search in.
     * @param searchTitle   The title of the movie to search.
     * @param searchRating  The rating of the movie to search.
     * @param searchDuration The duration of the movie to search.
     * @param searchStartTime The start time of the movie to search.
     */
    public void runAssignment1b(List<Movie> file, String searchTitle, Double searchRating, Integer searchDuration, Integer searchStartTime) {
        System.out.println("************************************");
        System.out.println("Assignment 1b: Searching for a movie based on title and rating");

        Sorter sorter = new Sorter();
        Comparator<Movie> movieComparatorTitleRatingAscending = MovieComparators.byTitleThenRating(true);

        sorter.sortData(file, movieComparatorTitleRatingAscending, "File5");

        // Search for the movie
        Searcher searcher = new Searcher();
        searcher.findMovie(file, false, false, searchTitle, searchRating, searchDuration, searchStartTime);
    }

    /**
     * Assignment 1d: Optimizing the movie schedule based on the start time.
     * 
     * @param startingNodeIndex The index of the starting movie node.
     * @param file The list of movies to process.
     */
    public void runAssignment1d(List<Movie> file, int startingNodeIndex) {
        System.out.println("************************************");
        System.out.println("Assignment 1d: Optimising movie schedule based on the start time");

        Sorter sorter = new Sorter();
        Comparator<Movie> movieComparatorStartTimeAscending = MovieComparators.byStartTime(true);
        sorter.sortData(file, movieComparatorStartTimeAscending, "File1");

        Movie startingNode = file.get(startingNodeIndex);

        DirectedGraphInitialiser graphInitialiser = new DirectedGraphInitialiser(file, startingNode, sorter);

        // Optimize the movie schedule
        Optimiser optimiser = new Optimiser(file, graphInitialiser.getGraph(), graphInitialiser.getArcs(), startingNode);
        this.utilities.measureTime(data -> optimiser.findPath(false, false), file, false, false);

        List<Movie> sequence = optimiser.getOptimalMovieSequence();
    }

    /**
     * Assignment 1e: Optimizing the movie schedule with a time limit of 6 hours.
     * 
     * @param file The list of movies to process.
     */
    public void runAssignment1e(List<Movie> file) {
        System.out.println("************************************");
        System.out.println("Assignment 1e: Optimising movie schedule based on a time limit (6h)");

        Sorter sorter = new Sorter();
        Comparator<Movie> movieComparatorStartTimeAscending = MovieComparators.byStartTime(true);
        sorter.sortData(file, movieComparatorStartTimeAscending, "File1");

        Movie startingNode = file.get(0);
        System.out.println(startingNode.toString());

        DirectedGraphInitialiser graphInitialiser = new DirectedGraphInitialiser(file, file.get(1), sorter);

        Optimiser optimiser = new Optimiser(file, graphInitialiser.getGraph(), graphInitialiser.getArcs(), startingNode);
        this.utilities.measureTime(data -> optimiser.findPathDisregardStartingIndex(false, true), file, false, false);
    }

    /**
     * Assignment 1f: Finding the minimum number of rooms required to show all movies.
     * 
     * @param file The list of movies to process.
     */
    public void runAssignment1f(List<Movie> file) {
        System.out.println("************************************");
        System.out.println("Assignment 1f: Finding the minimum number of rooms required to show all movies");

        Sorter sorter = new Sorter();
        Comparator<Movie> movieComparatorStartTimeAscending = MovieComparators.byStartTime(true);
        sorter.sortData(file, movieComparatorStartTimeAscending, "File1");

        // Allocate rooms for the movies
        CinemaScheduler cinemaRoomAllocator = new CinemaScheduler(file);
        this.utilities.measureTime(data -> cinemaRoomAllocator.findMinimumRequiredRooms(), file, false, false);
    }
}
