public class Comparator {

    protected final int[] sortColumns;

    protected final boolean ascending;

    public Comparator(int[] sortColumns, boolean ascending) {
        this.sortColumns = sortColumns;
        this.ascending = ascending;
    }

    public int compare(String[] row1, String[] row2) {
        // Loop through all specified columns for sorting
        for (int column : sortColumns) {
            // Get the column index & check if it is valid
            //int column = sortColumns[i];
            if (column < 0 || column > 3) {
                throw new IllegalArgumentException("Column index must be between 0 and 3");
            }

            int returnValue = 0;
            String value1 = row1[column];
            String value2 = row2[column];

            if (value2 == null) {
                continue;
            }

            // Compare based on column type
            if (column == 0) { // Compare strings - Title (column 0)
                returnValue = value1.substring(0, 1).toLowerCase().compareTo(value2.substring(0, 1).toLowerCase());
            } else if (column == 1) { // Compare floats - Rating (column 1)
                returnValue = Float.compare(Float.parseFloat(value1), Float.parseFloat(value2));
            } else if (column == 2 || column == 3) { // Compare integers - Duration (column 2) or StartTime (column 3)
                returnValue = Integer.compare(Integer.parseInt(value1), Integer.parseInt(value2));
            } else {
                throw new IllegalArgumentException("Column index must be between 0 and 3");
            }


            // Return comparison result, accounting for ascending/descending order
            if (returnValue != 0) {
                return returnValue;
            }
        }

        // If all columns are equal
        return 0;
    }
}
