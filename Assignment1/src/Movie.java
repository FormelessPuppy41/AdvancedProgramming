
/**
 * A class representing a movie with a title, rating, duration and start time.
 * 
 * The class also contains a method to: 
 * - create a partial movie object with the given values.
 * - compare two movies based on their title, rating, duration and start time.
 * - return a string representation of the movie.
 * 
 * @author 611968bq - Berend Quist
 * 
 */
public class Movie {
    protected final String title;
    protected final Double rating;
    protected final int duration;
    protected final int startTime;

    /**
     * Create a new movie object with the given values.
     * 
     * @param title : the title of the movie
     * @param rating : the rating of the movie
     * @param duration : the duration of the movie
     * @param startTime : the start time of the movie
     */
    public Movie(String title, Double rating, int duration, int startTime) {
        this.title = title;
        this.rating = rating;
        this.duration = duration;
        this.startTime = startTime;
    }

    /**
     * Create a new partial movie object with the given values, not all values need to be filled in. Use 'Null'. If a value is null, use the default value.
     * 
     * @param title : the title of the movie
     * @param rating : the rating of the movie
     * @param duration : the duration of the movie
     * @param startTime : the start time of the movie
     * @return : a new movie object with the given values
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
     * Get the title of the movie.
     * @return : the title of the movie
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Get the rating of the movie.
     * @return : the rating of the movie
     */
    public Double getRating() {
        return this.rating;
    }

    /**
     * Get the duration of the movie.
     * @return : the duration of the movie
     */
    public int getDuration() {
        return this.duration;
    }

    /**
     * Get the start time of the movie.
     * @return : the start time of the movie
     */
    public int getStartTime() {
        return this.startTime;
    }

    /**
     * Compare two movies based on their title, rating, duration and start time.
     * 
     * @param other : the other movie to compare to
     * @return : a negative integer, zero, or a positive integer as this movie is less than, equal to, or greater than the other movie
     */
    public int compareTo(Movie other) {
        // First, compare by title (only use the first character)
        char firstLetterMovie1 = this.title.toLowerCase().charAt(0);
        char firstLetterMovie2 = other.title.toLowerCase().charAt(0);
        
        int result = Character.compare(firstLetterMovie1, firstLetterMovie2);
        
        // If titles are the same, compare by rating
        if (result == 0) {result = Double.compare(this.rating, other.rating);}
        
        // If ratings are the same, compare by duration
        if (result == 0) {result = Integer.compare(this.duration, other.duration);}
    
        // If durations are the same, compare by start time
        if (result == 0) {result = Integer.compare(this.startTime, other.startTime);}
    
        // Return the final comparison result
        return result;
    }

    /**
     * Return a string representation of the movie.
     * 
     * @return : a string representation of the movie
     */
    public String toString() {
        return this.title + ", " + this.rating + ", " + this.duration + ", " + this.startTime;
    }
    
}
