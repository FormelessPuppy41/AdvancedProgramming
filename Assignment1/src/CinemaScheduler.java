import java.util.List;
import java.util.HashMap;

/**
 * The CinemaScheduler class is responsible for managing and allocating cinema rooms
 * based on the schedule of movies. It tracks the time slots occupied by movies
 * and calculates the minimum number of rooms required to avoid scheduling conflicts.
 * 
 * @author 611968bq - Berend Quist 
 */
public class CinemaScheduler {

    private static final int MIN_TIME = 0;
    private static final int MAX_TIME = 1440; // Time in minutes (from 00:00 to 24:00)

    private List<Movie> movies;  // List of movies to be scheduled
    private HashMap<Integer, Integer> timeSlotTracker;  // Tracks movie count per minute

    /**
     * Constructs a CinemaScheduler with a list of movies.
     * Initializes the time slot tracker to count the number of movies scheduled at each minute.
     *
     * @param movies List of movies to be scheduled.
     */
    public CinemaScheduler(List<Movie> movies) {
        this.movies = movies;
        this.timeSlotTracker = new HashMap<>();
        initializeTimeSlotTracker();
    }

    /**
     * Initializes the time slot tracker by setting each time slot from MIN_TIME to MAX_TIME to 0.
     * This prepares the tracker for counting the number of movies in each time slot.
     */
    private void initializeTimeSlotTracker() {
        for (int time = MIN_TIME; time <= MAX_TIME; time++) {
            timeSlotTracker.put(time, 0);
        }
    }

    /**
     * Updates the time slot tracker for all movies by incrementing the count of movies
     * for every minute each movie occupies.
     */
    private void updateTimeSlotTracker() {
        for (Movie movie : movies) {
            allocateMovieTimeSlots(movie);
        }
    }

    /**
     * Allocates time slots for a single movie by incrementing the count for each time slot
     * the movie occupies, based on its start time and duration.
     *
     * @param movie The movie to allocate in the time slot tracker.
     */
    private void allocateMovieTimeSlots(Movie movie) {
        int movieStartTime = movie.getStartTime();
        int movieEndTime = movieStartTime + movie.getDuration();

        for (int time = movieStartTime; time < movieEndTime; time++) {
            if (isValidTimeSlot(time)) {
                timeSlotTracker.put(time, timeSlotTracker.get(time) + 1);
            } else {
                throw new IllegalArgumentException("Invalid time slot: " + time);
            }
        }
    }

    /**
     * Checks whether a given time slot is valid (between MIN_TIME and MAX_TIME).
     *
     * @param time The time slot to be validated.
     * @return True if the time slot is valid, false otherwise.
     */
    private boolean isValidTimeSlot(int time) {
        return time >= MIN_TIME && time <= MAX_TIME;
    }

    /**
     * Calculates the minimum number of rooms required by finding the time slot
     * with the maximum number of overlapping movies. This determines the room capacity
     * needed to handle the busiest time slots.
     *
     * @return The minimum number of rooms required to prevent scheduling conflicts.
     */
    public int findMinimumRequiredRooms() {
        System.out.println("************************************");
        System.out.println("Calculating minimum number of rooms required...");
        
        updateTimeSlotTracker();  // Updates time slot tracker based on movie times

        return getMaxOccurringMoviesInTimeSlot();
    }

    /**
     * Finds the maximum number of movies occurring in any given time slot.
     * This determines the maximum overlap and represents the minimum number of rooms required.
     *
     * @return The maximum number of overlapping movies, which equals the number of rooms required.
     */
    private int getMaxOccurringMoviesInTimeSlot() {
        int maxOccurrences = 0;
        for (int occurrences : timeSlotTracker.values()) {
            if (occurrences > maxOccurrences) {
                maxOccurrences = occurrences;
            }
        }
        
        System.out.println("Minimum number of rooms required: " + maxOccurrences);
        return maxOccurrences;
    }
}
