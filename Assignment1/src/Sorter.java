import java.util.List;
import java.util.Comparator;

/**
 * The Sorter class provides methods to sort a list of Movie objects using a custom comparator.
 * It includes methods to check if the data is already sorted and implements the QuickSort algorithm
 * to sort the data if necessary.
 * 
 * @author 611968bq - Berend Quist
 */
public class Sorter {

    /**
     * Constructor for the Sorter class.
     */
    public Sorter() {
        // Default constructor
    }

    /**
     * Sorts the data using the provided comparator if the data is not already sorted.
     * 
     * @param data The list of Movie objects to sort.
     * @param comparator The comparator to define the sort order.
     * @param keyTitle A title to display for logging purposes.
     */
    public void sortData(List<Movie> data, Comparator<Movie> comparator, String keyTitle) {
        System.out.println("-----------------");
        System.out.println("Start sorting data: " + keyTitle);

        // Check if the data is already sorted before sorting to avoid unnecessary sorting.
        if (!isDataSorted(data, comparator)) {
            System.out.println("-> Data is not sorted. Sorting data...");
            quickSort(data, 0, data.size() - 1, comparator);
        }
        System.out.println("Data sorted.");
    }

    /**
     * Checks if the data is sorted according to the provided comparator.
     * 
     * @param data The list of Movie objects to check.
     * @param comparator The comparator to compare the order of elements.
     * @return true if the data is sorted, false otherwise.
     */
    public boolean isDataSorted(List<Movie> data, Comparator<Movie> comparator) {
        System.out.println("-> Check if data is sorted.");

        // Loop through the list to verify if it is sorted
        for (int i = 0; i < data.size() - 1; i++) {
            if (comparator.compare(data.get(i), data.get(i + 1)) > 0) {
                System.out.println("--> Data is not sorted: -- Value1: " + data.get(i) + " -- Value2: " + data.get(i + 1));
                return false;
            }
        }
        System.out.println("--> Data is sorted.");
        return true;
    }

    /**
     * Implements the QuickSort algorithm to sort the data using the provided comparator.
     * 
     * @param data The list of Movie objects to sort.
     * @param from The starting index of the list.
     * @param to The ending index of the list.
     * @param comparator The comparator to define the sort order.
     */
    public void quickSort(List<Movie> data, int from, int to, Comparator<Movie> comparator) {
        if (from < to) {
            int pivotIndex = partition(data, from, to, comparator);

            // Recursively sort the left and right partitions
            quickSort(data, from, pivotIndex - 1, comparator);
            quickSort(data, pivotIndex + 1, to, comparator);
        }
    }

    /**
     * Partition method for the QuickSort algorithm.
     * It selects a pivot element and arranges the elements such that those smaller than the pivot
     * come before it, and those greater come after it.
     * 
     * @param data The list of Movie objects to partition.
     * @param from The starting index of the partition.
     * @param to The ending index of the partition.
     * @param comparator The comparator to define the order of elements.
     * @return The index position of the pivot element after partitioning.
     */
    private int partition(List<Movie> data, int from, int to, Comparator<Movie> comparator) {
        Movie pivot = data.get(to);  // Select the last element as the pivot
        int i = from - 1;  // Index of the smaller element

        for (int j = from; j < to; j++) {
            // If current element is smaller than or equal to the pivot, swap it
            if (comparator.compare(data.get(j), pivot) < 0) {
                i++;
                swap(data, i, j);
            }
        }
        // Swap the pivot element to the correct position
        swap(data, i + 1, to);
        return i + 1;  // Return the index of the pivot element
    }

    /**
     * Swaps two elements in the list.
     * 
     * @param data The list of Movie objects.
     * @param i The index of the first element to swap.
     * @param j The index of the second element to swap.
     */
    private void swap(List<Movie> data, int i, int j) {
        Movie temp = data.get(i);
        data.set(i, data.get(j));
        data.set(j, temp);
    }
}
