import java.util.List;

public class Searcher {

    public Searcher() {
    }

    private int binarySearch(List<Movie> data, int low, int high, Movie key) {
        int resultIndex = -1; 

        if (low <= high) {
            int mid = low + (high - low) / 2;

            // Check if the value is found at the midpoint
            if (data.get(mid).compareTo(key) == 0) {
                System.out.println("Found a matching value at index: " + mid);
            }

            // Compare the movies
            if (data.get(mid).compareTo(key) < 0) {
                binarySearch(data, mid + 1, high, key); // Continue searching the right side of the list
            } else {
                binarySearch(data, low, mid - 1, key); // Continue searching the left side of the list
            }
        } 
        
        if (resultIndex == -1) {
            System.out.println("Not found, either not present or not sorted.");
        }
        return resultIndex;
    }

    private int binarySearch_findFirstMatchingMovie(List<Movie> data, int low, int high, Movie key) {
        System.out.println("-----------------");
        System.out.println("Start search for first matching movie");
        int resultIndex = -1;

        // Get the title of the key (case insensitive)
        String keyTitle = key.getTitle().toLowerCase();

        while (low <= high) {
            System.out.println();
            int mid = low + (high - low) / 2;

            // Get the title of the movie and the key (case insensitive)
            String title  = data.get(mid).getTitle().toLowerCase(); 
            System.out.println(mid);
            // Compare the titles of the movies (case insensitive)
            if (title.startsWith(keyTitle)) {
                resultIndex = mid; // Save the index of the first matching movie
                high = mid - 1; // Continue searching the left side of the list
            } else if (title.compareTo(keyTitle) < 0) {
                low = mid + 1; // Continue searching the right side of the list
            } else {
                high = mid - 1; // Continue searching the left side of the list
            }
        }

        if (resultIndex == -1) {
            System.out.println("Not found, either not present or not sorted.");
        } else {
            System.out.println("Last matching movie found at index: " + resultIndex + ", The corresponding value: " + data.get(resultIndex).toString());
        }

        return resultIndex;
    }

    private int binarySearch_findLastMatchingMovie(List<Movie> data, int low, int high, Movie key) {
        System.out.println("-----------------");
        System.out.println("Start search for last matching movie");

        int resultIndex = -1;
        String keyTitle = key.getTitle().toLowerCase(); // Case-insensitive comparison

        while (low <= high) {
            int mid = low + (high - low) / 2;
            String title = data.get(mid).getTitle().toLowerCase();

            if (title.startsWith(keyTitle)) {
                resultIndex = mid;
                low = mid + 1; // Continue searching to the right for the last match
            } else if (title.compareTo(keyTitle) < 0) {
                low = mid + 1; // Move right
            } else {
                high = mid - 1; // Move left
            }
        }

        if (resultIndex == -1) {
            System.out.println("No matching movie found.");
        } else {
            System.out.println("Last matching movie found at index: " + resultIndex + ", The corresponding value: " + data.get(resultIndex).toString());
        }

        return resultIndex;
    }

    /**
     * Find a movie in the list of movies. 
     * 
     * Directly search for a movie in the list of movies.
     *
     * @param data : the list of movies
     * @param key : the movie to search for
     * @return : the index of the movie
     */
    public int findMovie(List<Movie> data, Movie key) {
        System.out.println("-----------------");
        System.out.println("Start search for exact matching movie");

        int resultIndex = binarySearch(data, 1, data.size() - 1, key);

        System.out.println("The index found is: " + resultIndex + ", The corresponding value: " + data.get(resultIndex).toString());

        return resultIndex;
    }


    /**
     * Find a movie in the list of movies. 
     * 
     * If exactMatch is true, the method will return the index of the movie if it is found.
     * If exactMatch is false, the method will return the index of the first movie that matches the key.
     * This can be useful if you want to find the first movie that e.g. starts with a certain title.
     * 
     * @param data : the list of movies
     * @param exactMatch : whether to find an exact match or the first matching movie
     * @param title : the title of the movie (or null)
     * @param rating : the rating of the movie (or null)
     * @param duration : the duration of the movie (or null)
     * @param startTime : the start time of the movie (or null)
     * @return : the index of the movie
     */
    public int findMovie(List<Movie> data, boolean exactMatch, boolean firstMatch, String title, Double rating, Integer duration, Integer startTime) {
        // Create a (partial) movie instance of the title for easy comparison.
        Movie partialMovie = Movie.createPartialMovie(title, rating, duration, startTime);
        
        int resultIndex = -1;

        if (exactMatch) {
            resultIndex = findMovie(data, partialMovie);
        } else {
            if (firstMatch) {
                resultIndex = binarySearch_findFirstMatchingMovie(data, 0, data.size() - 1, partialMovie);
            } else {
                resultIndex = binarySearch_findLastMatchingMovie(data, 0, data.size() - 1, partialMovie);
            }
        }

        return resultIndex;
    }

}
