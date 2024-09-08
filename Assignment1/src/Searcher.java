import java.util.List;

public class Searcher {

    protected final Comparator sortComparator;
    protected final Comparator searchComparator;

    public Searcher(Comparator sortComparator, Comparator searchComparator) {
        this.sortComparator = sortComparator;
        this.searchComparator = searchComparator;
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
    public int binarySearch(List<String[]> data, int low, int high, String[] value, boolean ascending) {
        // check if the data is 'filtered' in ascending order, that is the first element is smaller than the value.
        if (low <= high) {
            int mid = low + (high - low) / 2;

            // check if the value is found at the midpoint
            int comparisonResult = this.sortComparator.compare(data.get(mid), value, ascending);
            if (comparisonResult == 0) {
                System.out.println("Found a matching value at index: " + mid);
                return mid;
            }

            // recursively search the list using the comparator.
            if (comparisonResult < 0) {
                    // search the right side of the list
                return binarySearch(data, mid + 1, high, value, ascending);
            } else {
                    // search the left side of the list
                return binarySearch(data, low, mid - 1, value, ascending);
            }
        } else {
            // return -1 if the value is not found.
            System.out.println("Not found, either not present or not sorted.");
            return -1;
        }
    }

    public int findValueInRange(List<String[]> data, String[] value, boolean ascending) {
        int result = -1;
        int first = -1;
        int last = -1;

        int startIndex = binarySearch(data, 1, data.size() - 1, value, ascending);

        // find the first and last index of the value.
        System.out.println("Starting the search for the first and last index with the value around the starting index: " + startIndex);
        first = findFirstValue(data, startIndex, value); // Not needed as it is a sorted array for the first two columns. However, it is left in for the sake of compleness, because now it also works with a array sorted on only one dimension.
        last = findLastValue(data, startIndex, value);
        
        // find the max value of the range
        if (first == -1 && last == -1) {
            throw new IllegalArgumentException("Value not found in the list.");
        } else if (first == last) {
            result = first;
        } else {
            // if the sort columns are equal to or larger than the search columns, then the best fitting value is the last value since the array is sorted on those columns.
            if (sortComparator.sortColumns.length >= searchComparator.sortColumns.length) {
                result = last;
            } else {
            // set the result to the first value as default. This is allowed since first and last are not equal and both unequal to -1. 
            // So, either first is the largest value or it will be replaced by a larger value.
            result = first; 
            for (int i = first; i <= last; i++) {
                if (this.sortComparator.compare(data.get(i), data.get(result), true) > 0) {
                    result = i;
                }
            }
        }
        }
        System.out.println("Found the best fitting value at index: " + result);
        return result;
    }

    private int findFirstValue(List<String[]> data, int startIndex, String[] value) {
        int result = -1;

        for (int i = startIndex; i >= 0; i--) {
            if (this.searchComparator.compare(data.get(i), value, true) == 0) {
                result = i;
            }
        }

        return result;
    }

    private int findLastValue(List<String[]> data, int startIndex, String[] value) {
        int result = -1;

        for (int i = startIndex; i < data.size(); i++) {
            if (this.searchComparator.compare(data.get(i), value, true) == 0) {
                result = i;
            }
        }

        return result;
    }


}
