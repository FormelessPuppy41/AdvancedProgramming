import java.util.List;

public class Searcher2 {

    public Searcher2() {

    }

    public void binarySearch(List<Movie> data, int low, int high, Movie key) {
        if (low <= high) {
            int mid = low + (high - low) / 2;

            if (data.get(mid).compareTo(key) == 0) {
                System.out.println("Found a matching value at index: " + mid);
            }

            if (data.get(mid).compareTo(key) < 0) {
                binarySearch(data, mid + 1, high, key);
            } else {
                binarySearch(data, low, mid - 1, key);
            }
        } else {
            System.out.println("Not found, either not present or not sorted.");
        }
        
    }
    
}
