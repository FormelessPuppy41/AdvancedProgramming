import java.util.List;

public class Sorter{

    public void SortingStopWatch() {
        StopWatch stopwatch = new StopWatch();

        stopwatch.start();
    }

    /*
     * Standard Sort method that sorts the data based on the column specified, where column 0 is the first column.
     */
    public void StandardSort(List<String[]> data, int column){
        // should probably consist of a CompareTo method for each row. Where the row is compared to the next row based on the column value.
        for (String[] row : data) {
            System.out.println(row[column]);
        }
    }

}