package seng202.group7.analyses;

/**
 *
 * Tuple class used for converting hash table into sorted list for the "Rank" data class.
 * @param <X> The string key the value that is being measured
 * @param <Y> The int value for occurrence in the data of x
 * @author Sam McMillan
 * @author Shaylin
 */
public class Tuple<X, Y> {
    public X x;
    public Y y;
    public Tuple(X x, Y y) {
        this.x = x;
        this.y = y;
    }
}
