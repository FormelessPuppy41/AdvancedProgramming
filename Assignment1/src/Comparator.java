

public class Comparator {

    //private final int[] sortColumns;

    //public Comparator(int[] sortColumns) {this.sortColumns = sortColumns;}


    /*
     * Compare method, which compares two values based on a specific column.
     * 
     * @param value1: the first value to compare
     * @param value2: the second value to compare
     * @param column: the column to sort the strings by. Starts at 0->3. Where 0 is Title, 1 is Rating, 2 is Duration and 3 is StartTime.
     */
    public int compare(String value1, String value2, int column) {
        if (column == 0) { // compare strings - Title
            // compare the lowercases to make it case insensitive
            return value1.substring(0, 1).toLowerCase().compareTo(value2.substring(0, 1).toLowerCase());
        } 
        else if (column == 1) { // compare floats - Rating
            return Float.compare(Float.parseFloat(value1), Float.parseFloat(value2));
        } 
        else if (column == 2 || column == 3) { // compare integers - Duration and StartTime
            return Integer.compare(Integer.parseInt(value1), Integer.parseInt(value2));
        } 
        else { // throw an error if the column is not between 0 and 3
            throw new IllegalArgumentException("Column index must be between 0 and 3");
        }
    }

    
    // Custom comparator that handles sorting by title (first letter) then by rating
    public int compareByTitleAndRating(String[] row1, String[] row2) {
        // Compare by first letter of Title (column 0)
        int titleComparison = compare(row1[0], row2[0], 0);
        
        if (titleComparison != 0) {
            return titleComparison; // If the first letter is different, return that comparison
        } else {
            // If the first letter is the same, compare by rating (column 1)
            return compare(row1[1], row2[1], 1);    
        }
    }
}
