import java.util.List;

public class Searcher {

    private Comparator comparator;

    public Searcher(Comparator comparator) {
        this.comparator = comparator;
    }
    
    /*
     * Binary search algorithm, which searches for a specific value in a list of strings based on a specific column.
     * 
     * @param data: the list of strings to be searched
     * @param column: the column to search the strings by. Starts at 0.
     * @param low: the starting index of the list
     * @param high: the ending index of the list
     * @param value: the value to search for
     */
    public int binarySearch(List<String[]> data, int low, int high, String[] value) {
        // check if the data is 'filtered' in ascending order, that is the first element is smaller than the value.
        if (low <= high) {
            int mid = low + (high - low) / 2;

            // check if the value is found at the mid point
            
            if (this.comparator.compare(data.get(mid), value) == 0) {
                System.out.println("Found at index: " + mid);
                return mid;
            }
            
            // recursively search the list using the comparator.
            if (comparator.compare(data.get(mid), value) < 0) {
                // search the right side of the list
                return binarySearch(data, mid + 1, high, value);
            } else {
                // search the left side of the list
                return binarySearch(data, low, mid - 1, value);
            }
        } else {
            // return -1 if the value is not found.
            System.out.println("Not found, either not present or not sorted.");
            return -1;
        }
    }


}
