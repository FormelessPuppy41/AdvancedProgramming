

public class Comparator {
    /*
     * Compare method, which compares two values based on a specific column.
     * 
     * @param value1: the first value to compare
     * @param value2: the second value to compare
     * @param column: the column to sort the strings by. Starts at 0->3. Where 0 is Title, 1 is Rating, 2 is Duration and 3 is StartTime.
     */
    public int compare(String value1, String value2, int column) {
        if (column == 0) { // compare strings - Title
            // FIXME: Should this not be comparing the first letter of the title? instead of the whole title?
            //return value1.compareTo(value2);
            return value1.substring(0, 1).compareTo(value2.substring(0, 1));
        } else if (column == 1) { // compare floats - Rating
            return Float.compare(Float.parseFloat(value1), Float.parseFloat(value2));
        } else if (column == 2 || column == 3) { // compare integers - Duration and StartTime
            return Integer.compare(Integer.parseInt(value1), Integer.parseInt(value2));
        } else { // throw an error if the column is not between 0 and 3
            throw new IllegalArgumentException("Column index must be between 0 and 3");
        }
    }
    
}
