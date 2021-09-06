package seng202.group7.analyses;

/**
 *
 * Tuple class used for converting hash table into sorted list for the "Rank" data class.
 * @param <X> The string key, Ether Primary description: "Theft", "Battery" ETC or location: "S SOUTH SHORE DR", "S DREXEL AVE"
 * @param <Y> The int value for occurrence of the string in the data
 */
public class Tuple<X, Y> {
    public X x;
    public Y y;
    public Tuple(X x, Y y) {
        this.x = x;
        this.y = y;
    }
}
