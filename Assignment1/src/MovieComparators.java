import java.util.Comparator;

public class MovieComparators {
    

    // Comparator for sorting by title
    public static Comparator<Movie> byTitle(boolean ascending) {
        return (m1, m2) -> {
            String stringChar1 = Character.toString(m1.getTitle().charAt(0));
            String stringChar2 = Character.toString(m2.getTitle().charAt(0));
            
            int result = stringChar1.compareToIgnoreCase(stringChar2);
            
            return ascending ? result : -result;
        };
    }

    // Comparator for sorting by rating only
    public static Comparator<Movie> byRating(boolean ascending) {
        return (m1, m2) -> {
            int result = Double.compare(m1.getRating(), m2.getRating());
            return ascending ? result : -result;
        };
    }

    // Comparator for sorting by duration
    public static Comparator<Movie> byDuration(boolean ascending) {
        return (m1, m2) -> {
            int result = Integer.compare(m1.getDuration(), m2.getDuration());
            return ascending ? result : -result;
        };
    }

    // Comparator for sorting by start time
    public static Comparator<Movie> byStartTime(boolean ascending) {
        return (m1, m2) -> {
            int result = Integer.compare(m1.getStartTime(), m2.getStartTime());
            return ascending ? result : -result;
        };
    }

    // Comparator for sorting by title and then by rating
    public static Comparator<Movie> byTitleThenRating(boolean ascending) {
        return (m1, m2) -> {
            int result = byTitle(ascending).compare(m1, m2);

            if (result == 0) { // If titles are the same, compare by rating
                result = byRating(ascending).compare(m1, m2);
            }

            return result;
        };
    }
}
