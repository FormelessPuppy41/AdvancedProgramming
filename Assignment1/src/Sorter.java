import java.util.List;

/*
 * Class that contains sorting algorithms for sorting a list of strings.
 * 
 * @Author: 
 * 
 */
public class Sorter{

    private Comparator comparator;

    // Constructor
    public Sorter(Comparator comparator ) {
        this.comparator = comparator;
    }

    //public Sorter(int[] sortColumns) {this.comparator = new Comparator(sortColumns);}

    /*
     * Quick sort algorithm, which sorts a list of strings based on a specific column.
     * 
     * @param data: the list of strings to be sorted
     * @param column: the column to sort the strings by. Starts at 0.
     * @param from: the starting index of the list
     * @param to: the ending index of the list
     */
    public void quickSort(List<String[]> data, int from, int to, boolean ascending) {

        // check if 'from' is smaller than 'to'
       if (from < to) {
            int pivot = partition(data, from, to, ascending);

            // recursively sort the list
            quickSort(data, from, pivot - 1, ascending);
            quickSort(data, pivot + 1, to, ascending);
       } else if ( from == to) {}
    }

    /*
     * Partition algorithm, which partitions a list of strings based on a specific column.
     * 
     * @param data: the list of strings to be sorted
     * @param column: the column to sort the strings by. Starts at 0.
     * @param from: the starting index of the list
     * @param to: the ending index of the list
     * @return: the index of the partition
     */
    private int partition(List<String[]> data, int from, int to, boolean ascending) {
        // median-of-three pivot selection for efficiency because you start in the middle instead of the first value.
        int mid = (from + to) / 2;
        String[] pivot = medianOfThree(data, from, mid, to, ascending);

        pivot = data.get(to);

        int i = (from - 1);

        for (int j = from; j <= to - 1; j++) {
            if (this.comparator.compare(data.get(j), pivot, ascending) < 0) {
                i++;
                swap(data, i, j);
            }
        }
        swap(data, i + 1, to);
        // return the partition index
        return i + 1;
    }

    /*
     * Median of three algorithm, which selects the median of three values based on a specific column.
     * 
     * @param data: the list of strings to be sorted
     * @param column: the column to sort the strings by. Starts at 0.
     * @param low: the low index of the list
     * @param mid: the middle index of the list
     * @param high: the high index of the list
     */
    private String[] medianOfThree(List<String[]> data, int low, int mid, int high, boolean ascending) {
        if (this.comparator.compare(data.get(low), data.get(mid), ascending) > 0) {swap(data, low, mid);}
        if (this.comparator.compare(data.get(low), data.get(high), ascending) > 0) {swap(data, low, high);}
        if (this.comparator.compare(data.get(mid), data.get(high), ascending) > 0) {swap(data, mid, high);}
        
        return data.get(mid);  // Use median as the pivot
    }

    /*
     * Standard sort algorithm, which sorts a list of strings based on a specific column.
     * For ascending order, i < j otherwise for descending i > j.
     * 
     * @param data: the list of strings to be sorted
     * @param i: the column/row to sort to swap. Starts at 0. Column if data is one dimensional, row if data is two dimensional.
     * @param j: the column/row to sort to swap. Starts at 0. Column if data is one dimensional, row if data is two dimensional.
     */
    private static void swap(List<String[]> data, int i, int j) {
        // check if data is empty
        if (data.isEmpty()) {return;}
        
        // swap the elements
        String[] temp = data.get(i);
        data.set(i, data.get(j));
        data.set(j, temp);
    }
    
}