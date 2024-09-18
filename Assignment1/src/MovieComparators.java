import java.util.Comparator;

/**
 * A utility class containing comparators for sorting Movie objects by different attributes.
 * 
 * Provides static methods to create comparators for sorting movies by:
 * - Title (with optional ascending/descending order)
 * - Rating (with optional ascending/descending order)
 * - Duration (with optional ascending/descending order)
 * - Start time (with optional ascending/descending order)
 * - Title, then by Rating (with optional ascending/descending order)
 * 
 * These comparators can be used with sorting methods like Collections.sort() or Streams.
 * 
 * @author 611968bq - Berend Quist
 */
public class MovieComparators {

    /**
     * Returns a comparator for sorting movies by title.
     * The comparison is case-insensitive and considers only the first character of the title.
     * 
     * @param ascending If true, sorts in ascending order; if false, sorts in descending order.
     * @return A comparator for sorting movies by title.
     */
    public static Comparator<Movie> byTitle(boolean ascending) {
        return (m1, m2) -> {
            // Compare first characters of the movie titles, ignoring case
            char firstChar1 = m1.getTitle().toLowerCase().charAt(0);
            char firstChar2 = m2.getTitle().toLowerCase().charAt(0);
            
            int result = Character.compare(firstChar1, firstChar2);
            
            return ascending ? result : -result;
        };
    }

    /**
     * Returns a comparator for sorting movies by rating.
     * 
     * @param ascending If true, sorts in ascending order; if false, sorts in descending order.
     * @return A comparator for sorting movies by rating.
     */
    public static Comparator<Movie> byRating(boolean ascending) {
        return (m1, m2) -> {
            int result = Double.compare(m1.getRating(), m2.getRating());
            return ascending ? result : -result;
        };
    }

    /**
     * Returns a comparator for sorting movies by duration.
     * 
     * @param ascending If true, sorts in ascending order; if false, sorts in descending order.
     * @return A comparator for sorting movies by duration.
     */
    public static Comparator<Movie> byDuration(boolean ascending) {
        return (m1, m2) -> {
            int result = Integer.compare(m1.getDuration(), m2.getDuration());
            return ascending ? result : -result;
        };
    }

    /**
     * Returns a comparator for sorting movies by start time.
     * 
     * @param ascending If true, sorts in ascending order; if false, sorts in descending order.
     * @return A comparator for sorting movies by start time.
     */
    public static Comparator<Movie> byStartTime(boolean ascending) {
        return (m1, m2) -> {
            int result = Integer.compare(m1.getStartTime(), m2.getStartTime());
            return ascending ? result : -result;
        };
    }

    /**
     * Returns a comparator for sorting movies by title, and then by rating if the titles are the same.
     * The comparison is case-insensitive and considers only the first character of the title.
     * 
     * @param ascending If true, sorts in ascending order; if false, sorts in descending order.
     * @return A comparator for sorting movies by title, then by rating if the titles are the same.
     */
    public static Comparator<Movie> byTitleThenRating(boolean ascending) {
        return (m1, m2) -> {
            // First, compare by title
            int result = byTitle(ascending).compare(m1, m2);

            // If titles are the same, compare by rating
            if (result == 0) {
                result = byRating(ascending).compare(m1, m2);
            }

            return result;
        };
    }
}
