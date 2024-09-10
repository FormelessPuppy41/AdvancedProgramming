import java.util.List;

public class Sorter2 {

    public Sorter2() {
        // Constructor
    }

    public boolean isDataSorted(List<Movie> data) {
        // loop through the data to check if it is sorted, if not set dataSorted to false
        for (int i = 1; i < data.size() - 1; i++) {
            if (data.get(i).compareTo(data.get(i + 1)) != 0) {
                System.out.println("Value1: " + data.get(i) + " Value2: " + data.get(i + 1));
                return false;
            }
        }
        return true;
    }

    public void quickSort(List<Movie> data, int from, int to) {
        // check if 'from' is smaller than 'to'
        if (from < to) {
            int pivot = partition(data, from, to);

            // recursively sort the list
            quickSort(data, from, pivot - 1);
            quickSort(data, pivot + 1, to);
        }
    }

    private int partition(List<Movie> data, int from, int to) {
        // partition algorithm
        Movie pivot = data.get(to);

        int i = (from - 1);

        for (int j = from; j <= to - 1; j++) {
            if (data.get(j).compareTo(pivot) < 0) {
                i++;
                swap(data, i, j);
            }
        }
        swap(data, i + 1, to);
        
        // return the partition index
        return i + 1;
    }

    private void swap(List<Movie> data, int i, int j) {

        if (data.isEmpty()) {
            throw new IllegalArgumentException("Data is empty.");
        }

        // swap algorithm
        Movie temp = data.get(i);
        data.set(i, data.get(j));
        data.set(j, temp);
    }
}
