package seng202.group7.analyses;

import seng202.group7.data.Crime;
import seng202.group7.data.Report;

import java.util.*;

public class Rank {
    // TODO Added weighted frequencies based on crime type

    /**
     *Parses the data and finds the highest to the lowest frequency of crime type (primary) in the whole data set
     * @param data The selected data to be analysed for frequencies
     * @return a list of tuples (string(primary description), int (frequency)), sorted from highest to lowest frequencies
     */
    public static ArrayList<Tuple> primaryFrequencyRank(ArrayList<Report> data) {
        return getFrequencyStringInteger(data);
    }

    /**
     * overloaded method of primaryFrequencyRank that filters by a ward first
     * in a specific ward
     * @param data The selected data to be analysed for frequencies
     * @param ward The ward where the crimes will be analysed
     * @return a list of tuples (string(primary description), int (frequency)), sorted from highest to lowest frequencies
     */
    public static ArrayList<Tuple> primaryFrequencyRank(ArrayList<Report> data, int ward) {
        ArrayList<Report> filteredData = Filter.intFilter(data, "WARD", ward);
        return getFrequencyStringInteger(filteredData);
    }

    /**
     * Helper function for frequency rank converts the data into a hash map of string keys and integer values
     * then converts it to a sorted List and returns
     */
    private static ArrayList<Tuple> getFrequencyStringInteger(ArrayList<Report> data) {
        HashMap<String, Integer> map = new HashMap<>();

        for(Report report : data){
            String primary = report.getPrimaryDescription();
            map.put(primary, map.getOrDefault(primary, 0) + 1);
        }
        return hashToListString(map);
    }

    /**
     * Parses the data and finds the wards with the highest frequency of crime over the dataset
     * @param data The selected data to be analysed for frequencies
     * @return a list of tuples (Int(Ward), int (frequency)), sorted from highest to lowest frequencies
     */
    public static ArrayList<Tuple> wardFrequencyRank(ArrayList<Report> data) {
        return getFrequencyIntegerInteger(data);
    }

    /**
     *  overloaded method of wardFrequency, that filters by a crime type first
     * @param data The selected data to be analysed for frequencies
     * @param primary Crime type
     * @return a list of tuples (Int(Ward), int (frequency)), sorted from highest to lowest frequencies
     */
    public static ArrayList<Tuple> wardFrequencyRank(ArrayList<Report> data, String primary) {
        ArrayList<Report> filteredData = Filter.stringFilter(data, "PRIMARY", primary);
        return getFrequencyIntegerInteger(filteredData);
    }

    /**
     * Helper function for frequency rank converts the data into a hash map of integer keys and integer values
     * then converts it to a sorted List and returns
     */
    private static ArrayList<Tuple> getFrequencyIntegerInteger(ArrayList<Report> data) {
        HashMap<Integer, Integer> map = new HashMap<>();

        for(Report report : data){
            Crime c = (Crime) report;
            Integer ward = c.getWard();
            map.put(ward, map.getOrDefault(ward, 0) + 1);
        }
        return hashToListInt(map);
    }


    /**
     * Parses the data and finds the highest to the lowest frequency of crime in an area (Most dangerous Street)
     * @param data The selected data to be analysed for frequencies
     * @return a list of tuples (string(address), int (frequency)), sorted from highest to lowest frequencies
     */
    public static ArrayList<Tuple> streetRank(ArrayList<Report> data) {
        HashMap<String, Integer> map = new HashMap<String, Integer>();

        for(Report report : data){
            Crime c = (Crime)report;
            String address = c.getBlock().substring(6);
            map.put(address, map.getOrDefault(address, 0) + 1);
        }

        return hashToListString(map);
    }

    /**
     * Helper function for block and primary frequency rank, converts a hash table into a sorted list of tuples.
     * @param hashMap A hash map of String key and int values corresponding to frequency of occurrence in the data.
     * @return a list of tuples (String, int), sorted from highest to lowest frequency
     */
    public static  ArrayList<Tuple> hashToListString(HashMap<String, Integer> hashMap) {
        ArrayList<Tuple> list = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            list.add(new Tuple(entry.getKey(), entry.getValue()));
        }
        list.sort((a, b) -> (int) a.y - (int) b.y);
        Collections.reverse(list);
        return list;
    }

    public static  ArrayList<Tuple> hashToListInt(HashMap<Integer, Integer> hashMap) {
        ArrayList<Tuple> list = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : hashMap.entrySet()) {
            list.add(new Tuple(entry.getKey(), entry.getValue()));
        }
        list.sort((a, b) -> (int) a.y - (int) b.y);
        Collections.reverse(list);
        return list;
    }
}
