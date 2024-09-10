
public class Movie {
    protected final String title;
    protected final Double rating;
    protected final int duration;
    protected final int startTime;

    public Movie(String title, Double rating, int duration, int startTime) {
        this.title = title;
        this.rating = rating;
        this.duration = duration;
        this.startTime = startTime;
    }

    public String getTitle() {
        return this.title;
    }

    public Double getRating() {
        return this.rating;
    }

    public int getDuration() {
        return this.duration;
    }

    public int getStartTime() {
        return this.startTime;
    }

    public int compareTo(Movie other) {
        // First, compare by title (lexicographical order)
        int result = this.title.compareTo(other.title);
        
        // If titles are the same, compare by rating
        if (result == 0) {result = this.rating.compareTo(other.rating);}
        
        // If ratings are the same, compare by duration
        if (result == 0) {result = Integer.compare(this.duration, other.duration);}
    
        // If durations are the same, compare by start time
        if (result == 0) {result = Integer.compare(this.startTime, other.startTime);}
    
        // Return the final comparison result
        return result;
    }

    public String toString() {
        return this.title + ", " + this.rating + ", " + this.duration + ", " + this.startTime;
    }
    
}
