import java.util.List;

/**
 * The Searcher class provides methods to perform binary searches on a list of Movie objects.
 * It allows searching for an exact match or partial matches based on movie titles.
 * 
 * The class supports:
 * - Finding an exact matching movie.
 * - Finding the first or last matching movie based on the movie title.
 * 
 * @author 611968bq - Berend Quist
 */
public class Searcher {

    /**
     * Constructor for the Searcher class.
     */
    public Searcher() {
        // Default constructor
    }

    /**
     * Performs a recursive binary search to find an exact match of a Movie object.
     *
     * @param data The list of movies to search through.
     * @param low The starting index of the search range.
     * @param high The ending index of the search range.
     * @param key The Movie object to search for.
     * @return The index of the movie if found, or -1 if not found.
     */
    private int binarySearch(List<Movie> data, int low, int high, Movie key) {
        if (low <= high) {
            int mid = low + (high - low) / 2;

            // Check if the movie at midpoint matches the key
            if (data.get(mid).compareTo(key) == 0) {
                return mid;
            }

            // Recursively search the left or right side
            if (data.get(mid).compareTo(key) < 0) {
                return binarySearch(data, mid + 1, high, key); // Search the right side
            } else {
                return binarySearch(data, low, mid - 1, key); // Search the left side
            }
        }
        return -1; // Return -1 if not found
    }

    /**
     * Performs a binary search to find the first movie that matches the partial Movie object based on the title.
     *
     * @param data The list of movies to search through.
     * @param low The starting index of the search range.
     * @param high The ending index of the search range.
     * @param key The Movie object to search for (typically partial title).
     * @return The index of the first matching movie, or -1 if not found.
     */
    private int binarySearchFindFirst(List<Movie> data, int low, int high, Movie key) {
        int resultIndex = -1;
        String keyTitle = key.getTitle().toLowerCase();

        while (low <= high) {
            int mid = low + (high - low) / 2;
            String title = data.get(mid).getTitle().toLowerCase();

            if (title.startsWith(keyTitle)) {
                resultIndex = mid; // Save the index and continue searching to the left for the first match
                high = mid - 1;
            } else if (title.compareTo(keyTitle) < 0) {
                low = mid + 1; // Move right
            } else {
                high = mid - 1; // Move left
            }
        }

        return resultIndex;
    }

    /**
     * Performs a binary search to find the last movie that matches the partial Movie object based on the title.
     *
     * @param data The list of movies to search through.
     * @param low The starting index of the search range.
     * @param high The ending index of the search range.
     * @param key The Movie object to search for (typically partial title).
     * @return The index of the last matching movie, or -1 if not found.
     */
    private int binarySearchFindLast(List<Movie> data, int low, int high, Movie key) {
        int resultIndex = -1;
        String keyTitle = key.getTitle().toLowerCase();

        while (low <= high) {
            int mid = low + (high - low) / 2;
            String title = data.get(mid).getTitle().toLowerCase();

            if (title.startsWith(keyTitle)) {
                resultIndex = mid; // Save the index and continue searching to the right for the last match
                low = mid + 1;
            } else if (title.compareTo(keyTitle) < 0) {
                low = mid + 1; // Move right
            } else {
                high = mid - 1; // Move left
            }
        }

        return resultIndex;
    }

    /**
     * Finds a movie in the list of movies by performing an exact match search.
     *
     * @param data The list of movies to search.
     * @param key The movie to search for (exact match).
     * @return The index of the movie if found, or -1 if not found.
     */
    public int findMovie(List<Movie> data, Movie key) {
        return binarySearch(data, 0, data.size() - 1, key);
    }

    /**
     * Finds a movie in the list of movies based on an exact match or partial match.
     * 
     * - If exactMatch is true, it finds an exact match of the movie.
     * - If exactMatch is false and firstMatch is true, it finds the first movie that matches the key.
     * - If exactMatch is false and firstMatch is false, it finds the last movie that matches the key.
     *
     * @param data The list of movies to search.
     * @param exactMatch If true, searches for an exact match.
     * @param firstMatch If true, finds the first matching movie. If false, finds the last matching movie.
     * @param title The title of the movie to search for (can be partial).
     * @param rating The rating of the movie (optional).
     * @param duration The duration of the movie (optional).
     * @param startTime The start time of the movie (optional).
     * @return The index of the movie if found, or -1 if not found.
     */
    public int findMovie(List<Movie> data, boolean exactMatch, boolean firstMatch, String title, Double rating, Integer duration, Integer startTime) {
        Movie partialMovie = Movie.createPartialMovie(title, rating, duration, startTime);
        int resultIndex;

        if (exactMatch) {
            resultIndex = findMovie(data, partialMovie); // Perform exact match search
        } else {
            if (firstMatch) {
                resultIndex = binarySearchFindFirst(data, 0, data.size() - 1, partialMovie);
            } else {
                resultIndex = binarySearchFindLast(data, 0, data.size() - 1, partialMovie);
            }
        }

        return resultIndex;
    }
}
