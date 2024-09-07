

public class Comparator {

    private final int[] sortColumns;

    public Comparator(int[] sortColumns) {
        this.sortColumns = sortColumns;
    }


    /*
     * Compare method, which compares two values based on a specific column.
     * 
     * @param value1: the first value to compare
     * @param value2: the second value to compare
     * @param column: the column to sort the strings by. Starts at 0->3. Where 0 is Title, 1 is Rating, 2 is Duration and 3 is StartTime.
     */
    public int compare(String[] row1, String[] row2) {
        // Loop through all specified columns for sorting
        for (int i = sortColumns.length - 1; i >= 0; i--) {
            // Get the column index & check if it is valid
            int column = sortColumns[i];
            if (column < 0 || column > 3) {throw new IllegalArgumentException("Column index must be between 0 and 3");}
        
            int returnValue = 0;

            if (column == 0) { // Compare strings - Title (column 0)
                returnValue = row1[column].substring(0, 1).toLowerCase().compareTo(row2[column].substring(0, 1).toLowerCase());
            } else if (column == 1) { // Compare floats - Rating (column 1)
                returnValue = Float.compare(Float.parseFloat(row1[column]), Float.parseFloat(row2[column]));
            } else if (column == 2 || column == 3) { // Compare integers - Duration (column 2) or StartTime (column 3)
                returnValue = Integer.compare(Integer.parseInt(row1[column]), Integer.parseInt(row2[column]));
            } else {
                throw new IllegalArgumentException("Column index must be between 0 and 3");
            }

            // If the current column comparison gives a non-zero result, return that result.
            if (returnValue != 0) {
                return returnValue;
            }
        }
        
        // If all columns are equal
        return 0;
    }

}
