/**
 * A class representing a movie with a title, rating, duration, and start time.
 * 
 * The class provides methods to:
 * - Create a partial movie object with the given values, using default values where necessary.
 * - Compare two movies based on their title, rating, duration, and start time.
 * - Return a string representation of the movie.
 * 
 * @author 611968bq - Berend Quist
 */
public class Movie {
    protected final String title;
    protected final Double rating;
    protected final int duration;
    protected final int startTime;

    /**
     * Constructs a new movie object with the given values.
     * 
     * @param title    The title of the movie.
     * @param rating   The rating of the movie.
     * @param duration The duration of the movie in minutes.
     * @param startTime The start time of the movie in minutes (from 00:00).
     */
    public Movie(String title, Double rating, int duration, int startTime) {
        this.title = title;
        this.rating = rating;
        this.duration = duration;
        this.startTime = startTime;
    }

    /**
     * Creates a new partial movie object with the given values.
     * If any value is null, it uses a default value.
     * 
     * @param title     The title of the movie (default: empty string).
     * @param rating    The rating of the movie (default: 0.0).
     * @param duration  The duration of the movie in minutes (default: 0).
     * @param startTime The start time of the movie in minutes (default: 0).
     * @return A new movie object with the provided or default values.
     */
    public static Movie createPartialMovie(String title, Double rating, Integer duration, Integer startTime) {
        // Use default values if null is passed
        String defaultTitle = title != null ? title : "";
        Double defaultRating = rating != null ? rating : 0.0;
        int defaultDuration = duration != null ? duration : 0;
        int defaultStartTime = startTime != null ? startTime : 0;

        // Return a new movie object with these values
        return new Movie(defaultTitle, defaultRating, defaultDuration, defaultStartTime);
    }

    /**
     * Gets the title of the movie.
     * 
     * @return The title of the movie.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Gets the rating of the movie.
     * 
     * @return The rating of the movie.
     */
    public Double getRating() {
        return this.rating;
    }

    /**
     * Gets the duration of the movie.
     * 
     * @return The duration of the movie in minutes.
     */
    public int getDuration() {
        return this.duration;
    }

    /**
     * Gets the start time of the movie.
     * 
     * @return The start time of the movie in minutes.
     */
    public int getStartTime() {
        return this.startTime;
    }

    /**
     * Compares two movies based on their title (first character), rating, duration, and start time.
     * If the titles are the same, it will compare by rating. If the ratings are the same,
     * it will compare by duration, and if durations are the same, it will compare by start time.
     * 
     * @param other The other movie to compare to.
     * @return A negative integer, zero, or a positive integer as this movie is less than, equal to,
     *         or greater than the other movie.
     */
    public int compareTo(Movie other) {
        int result = compareByTitle(other);
        
        if (result == 0) result = Double.compare(this.rating, other.rating);
        if (result == 0) result = Integer.compare(this.duration, other.duration);
        if (result == 0) result = Integer.compare(this.startTime, other.startTime);
        
        return result;
    }

    /**
     * Compares two movies based on their titles' first characters.
     * 
     * @param other The other movie to compare.
     * @return A negative integer, zero, or a positive integer based on the title comparison.
     */
    private int compareByTitle(Movie other) {
        char firstLetterMovie1 = this.title.toLowerCase().charAt(0);
        char firstLetterMovie2 = other.title.toLowerCase().charAt(0);
        return Character.compare(firstLetterMovie1, firstLetterMovie2);
    }

    /**
     * Returns a string representation of the movie.
     * 
     * @return A string containing the title, rating, duration, and start time of the movie.
     */
    @Override
    public String toString() {
        return String.format("%s, Rating: %.1f, Duration: %d min, Start Time: %d min", 
                              this.title, this.rating, this.duration, this.startTime);
    }
}
