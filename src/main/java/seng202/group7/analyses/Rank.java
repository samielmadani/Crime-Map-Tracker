package seng202.group7.analyses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seng202.group7.data.Report;
import seng202.group7.data.Crime;


/**
 * A set of static methods that ranks data to a certain value.
 *
 * @author Sam McMillan
 * @author Sami Elmadani
 */
public class Rank {

    /**
     * Parses the data and finds the highest to the lowest frequency of Report type (primary) in the whole data set.
     *
     * @param data  The selected data to be analysed for frequencies.
     * @return      A list of tuples (string(primary description), int (frequency)), sorted from highest to lowest frequencies.
     */
    public static List<Tuple<String, Integer>> primaryFrequencyRank(List<Report> data) {
        return getFrequencyStringInteger(data);
    }

    /**
     * overloaded method of primaryFrequencyRank that filters by a ward first
     * in a specific ward.
     *
     * @param data  The selected data to be analysed for frequencies
     * @param ward  The ward where the Reports will be analysed
     * @return      A list of tuples (string(primary description), int (frequency)), sorted from highest to lowest frequencies
     */
    public static List<Tuple<String, Integer>> primaryFrequencyRank(List<Report> data, int ward) {
        List<Report> filteredData = Filter.intFilter(data, "WARD", ward);
        return getFrequencyStringInteger(filteredData);
    }

    /**
     * Helper function for frequency rank converts the data into a hash map of string keys and integer values
     * then converts it to a sorted List and returns.
     *
     * @param data   The selected data to be analysed for frequencies
     * @return        A list of tuples (string(primary description), int (frequency)), sorted from highest to lowest frequencies
     */
    private static List<Tuple<String, Integer>> getFrequencyStringInteger(List<Report> data) {
        HashMap<String, Integer> map = new HashMap<>();

        for(Report report : data){
            String primary = report.getPrimaryDescription();
            map.put(primary, map.getOrDefault(primary, 0) + 1);
        }
        return hashToList(map);
    }

    /**
     * Parses the data and finds the wards with the highest frequency of Report over the dataset.
     *
     * @param data  The selected data to be analysed for frequencies
     * @return      A list of tuples (Int(Ward), int (frequency)), sorted from highest to lowest frequencies
     */
    public static List<Tuple<String, Integer>> wardFrequencyRank(List<Report> data) {
        return getFrequencyIntegerInteger(data);
    }

    /**
     * Overloaded method of wardFrequency, that filters by a Report type first.
     *
     * @param data      The selected data to be analysed for frequencies
     * @param primary   Report type
     * @return          A list of tuples (Int(Ward), int (frequency)), sorted from highest to lowest frequencies
     */
    public static List<Tuple<String, Integer>> wardFrequencyRank(List<Report> data, String primary) {
        List<Report> filteredData = Filter.stringFilter(data, "PRIMARY", primary);
        return getFrequencyIntegerInteger(filteredData);
    }

    /**
     * Helper function for frequency rank converts the data into a hash map of integer keys and integer values
     * then converts it to a sorted List and returns.
     *
     * @param data   The selected data to be analysed for frequencies
     * @return        A list of tuples (string(primary description), int (frequency)), sorted from highest to lowest frequencies
     */
    private static List<Tuple<String, Integer>> getFrequencyIntegerInteger(List<Report> data) {
        HashMap<String, Integer> map = new HashMap<>();

        for(Report report : data){
            try {
                String ward = String.valueOf(((Crime) report).getWard());
                map.put(ward, map.getOrDefault(ward, 0) + 1);
            } catch (NullPointerException e) {
                System.out.println("Ward value for Report " + String.valueOf(((Crime) report).getId()) + " is null.");
            }
        }
        return hashToList(map);
    }


    /**
     * Parses the data and finds the highest to the lowest frequency of Report in an area (Most dangerous Street).
     *
     * @param data  The selected data to be analysed for frequencies
     * @return      A list of tuples (string(address), int (frequency)), sorted from highest to lowest frequencies
     */
    public static List<Tuple<String, Integer>> streetRank(List<Report> data) {
        HashMap<String, Integer> map = new HashMap<>();

        for(Report report : data){
            try {
                String address = ((Crime) report).getBlock().substring(6);
                map.put(address, map.getOrDefault(address, 0) + 1);
            } catch (NullPointerException e) {
                System.out.println("Street value for Report " + String.valueOf((((Crime) report).getId()) + " is null"));
            }
        }
        return hashToList(map);
    }

    /**
     * Helper function for block and primary frequency rank, converts a hash table into a sorted list of tuples.
     *
     * @param hashMap   A hash map of String key and int values corresponding to frequency of occurrence in the data.
     * @return          A list of tuples (String, int), sorted from highest to lowest frequency
     */
    public static  List<Tuple<String, Integer>> hashToList(HashMap<String, Integer> hashMap) {
        List<Tuple<String, Integer>> list = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            list.add(new Tuple<>(entry.getKey(), entry.getValue()));
        }
        list.sort((a, b) -> a.y - (int) b.y);
        Collections.reverse(list);
        return list;
    }
}
