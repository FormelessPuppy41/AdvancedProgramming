import java.util.List;

/*
 * Class that contains sorting algorithms for sorting a list of strings.
 * 
 * @Author: Berend Quist - 611968bq@eur.nl
 * 
 */
public class Sorter{

    /*
     * Quick sort algorithm, which sorts a list of strings based on a specific column.
     * 
     * @param data: the list of strings to be sorted
     * @param column: the column to sort the strings by. Starts at 0.
     * @param from: the starting index of the list
     * @param to: the ending index of the list
     */
    public void quickSort(List<String[]> data, int column, int from, int to) {
       // check if from is smaller than to
       if (from >= to) {return;}
       int part = partition(data, column, from, to);

       // recursively sort the list
       quickSort(data, column, from, part);
       quickSort(data, column, part + 1, to);

        // should probably consist of a CompareTo method for each row. Where the row is compared to the next row based on the column value.
        // for (String[] row : data) {System.out.println(row[column]);}
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
    private static int partition(List<String[]> data, int column, int from, int to) {
        // initialize pivot and indices
        String pivot = data.get(from)[column];
        int i = from - 1;
        int j = to + 1;

        // loop until indices cross
        while (i < j) {
            // FIXME: This only works for integers, not strings. Probably need to implement a compareTo method for strings.
            i++; while (data.get(i)[column].compareTo(pivot) < 0) {i++;}
            j--; while (data.get(j)[column].compareTo(pivot) > 0) {j--;}

            if (i < j) {swap(data, i, j);} // swap elements
            
        }
        // return the partition index
        return j;
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