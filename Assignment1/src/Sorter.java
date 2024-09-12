import java.util.List;
import java.util.Comparator;

public class Sorter {

    public Sorter() {
        // Constructor
    }

    // Sort data using a custom comparator
    public void sortData(List<Movie> data, Comparator<Movie> comparator, String keyTitle) {
        System.out.println("-----------------");
        System.out.println("Start sorting data: " + keyTitle);

        if (!isDataSorted(data, comparator)) {
            quickSort(data, 0, data.size() - 1, comparator);
        }
    }

    // Check if data is sorted according to a comparator
    public boolean isDataSorted(List<Movie> data, Comparator<Movie> comparator) {
        System.out.println("Start checking if data is sorted");
        
        // Loop through the data to check if it is sorted
        for (int i = 0; i < data.size() - 1; i++) {
            if (comparator.compare(data.get(i), data.get(i + 1)) > 0) {
                System.out.println("Data is not sorted: -- Value1: " + data.get(i) + " -- Value2: " + data.get(i + 1));
                return false;
            }
        }
        return true;
    }

    // QuickSort algorithm with a comparator
    public void quickSort(List<Movie> data, int from, int to, Comparator<Movie> comparator) {
        if (from < to) {
            int pivot = partition(data, from, to, comparator);

            // Recursively sort the list
            quickSort(data, from, pivot - 1, comparator);
            quickSort(data, pivot + 1, to, comparator);
        }
    }

    // Partition method for quicksort
    private int partition(List<Movie> data, int from, int to, Comparator<Movie> comparator) {
        Movie pivot = data.get(to);
        int i = (from - 1);

        for (int j = from; j <= to - 1; j++) {
            if (comparator.compare(data.get(j), pivot) < 0) {
                i++;
                swap(data, i, j);
            }
        }
        swap(data, i + 1, to);

        // Return the partition index
        return i + 1;
    }

    // Swap two elements in the list
    private void swap(List<Movie> data, int i, int j) {
        Movie temp = data.get(i);
        data.set(i, data.get(j));
        data.set(j, temp);
    }
}
