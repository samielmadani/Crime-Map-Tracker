package seng202.group7.analyses;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import seng202.group7.data.Crime;
import seng202.group7.data.Report;


/**
 * A set of static methods that ranks data to a certain value.
 *
 * @author Sam McMillan
 * @author Sami Elmadani
 */
public class Rank {

    /**
     * Parses the data and finds the highest to the lowest frequency of crime type (primary) in the whole data set.
     * @param data  The selected data to be analysed for frequencies.
     * @return      A list of tuples (string(primary description), int (frequency)), sorted from highest to lowest frequencies.
     */
    public static ArrayList<Tuple<String, Integer>> primaryFrequencyRank(ArrayList<Report> data) {
        HashMap<String, Integer> map = new HashMap<>();

        for(Report report : data){
            String primary = report.getPrimaryDescription();
            map.put(primary, map.getOrDefault(primary, 0) + 1);
        }
        return hashToList(map);
    }

    /**
     * Parses the data and finds the wards with the highest frequency of crime over the dataset.
     * @param data  The selected data to be analysed for frequencies
     * @return      A list of tuples (Int(Ward), int (frequency)), sorted from highest to lowest frequencies
     */
    public static ArrayList<Tuple<String, Integer>> wardFrequencyRank(ArrayList<Report> data) {
        HashMap<String, Integer> map = new HashMap<>();

        for(Report report : data){
            try {
                Crime c = (Crime) report;
                String ward = String.valueOf(c.getWard());
                map.put(ward, map.getOrDefault(ward, 0) + 1);
            } catch (NullPointerException e) {
                Crime c = (Crime) report;
                System.out.println("Ward value for crime " + c.getCaseNumber() + " is null.");
            }
        }
        return hashToList(map);
    }

    public static ArrayList<Tuple<String, Integer>> beatFrequencyRank(ArrayList<Report> data) {
        HashMap<String, Integer> map = new HashMap<>();

        for(Report report: data) {
            try {
                Crime c = (Crime) report;
                String beat = String.valueOf(c.getBeat());
                map.put(beat, map.getOrDefault(beat, 0) + 1);
            } catch (NullPointerException e) {
                Crime c = (Crime) report;
                System.out.println("Beat value for crime " + c.getCaseNumber() + "is null");
            }
        }
        return hashToList(map);
    }

    /**
     * Parses the data and finds the highest to the lowest frequency of crime in an area (Most dangerous Street).
     *
     * @param data  The selected data to be analysed for frequencies
     * @return      A list of tuples (string(address), int (frequency)), sorted from highest to lowest frequencies
     */
    public static ArrayList<Tuple<String, Integer>> streetRank(ArrayList<Report> data) {
        HashMap<String, Integer> map = new HashMap<>();

        for(Report report : data){
            try {
                Crime c = (Crime) report;
                String address = c.getBlock().substring(6);
                map.put(address, map.getOrDefault(address, 0) + 1);
            } catch (NullPointerException e) {
                Crime c = (Crime) report;
                System.out.println("Street value for crime " + String.valueOf((c.getCaseNumber()) + " is null"));
            }
        }
        return hashToList(map);
    }

    public static ArrayList<CrimeFrequency> crimeOverTime(ArrayList<Report> data) {

        ArrayList<CrimeFrequency>  crimeOverTime = new ArrayList<>();
        if (data.size() == 0) {
            return crimeOverTime;
        }
        int yearValue = data.get(0).getDate().getYear();
        int monthValue = data.get(0).getDate().getMonthValue();
        int lastYear = data.get(data.size() - 1).getDate().getYear();
        int lastMonth = data.get(data.size() -1).getDate().getMonthValue();
        boolean lastValueNotFound = true;

        while(lastValueNotFound) {

            if (yearValue == lastYear && monthValue == lastMonth) {
                lastValueNotFound = false;
            }

            CrimeFrequency freq = new CrimeFrequency(monthValue + " " + yearValue, 0);
            crimeOverTime.add(freq);

            monthValue += 1;
            if (monthValue == 13) {
                monthValue = 1;
                yearValue += 1;
            }
        }

        boolean crimeMatch;
        int index = 0;
        for (CrimeFrequency freq: crimeOverTime){
            crimeMatch = true;
            while (crimeMatch) {
                LocalDateTime date = data.get(index).getDate();
                String dateString = String.valueOf(date.getMonthValue()) + " " + String.valueOf(date.getYear());
                if(dateString.equals(freq.getDate())) {
                    freq.setFrequency(freq.getFrequency() + 1);
                    index += 1;
                    if (index == data.size()) {
                        crimeMatch = false;
                    }
                } else {
                    crimeMatch = false;
                }
            }
        }
        return crimeOverTime;
    }

    /**
     * Helper function for block and primary frequency rank, converts a hash table into a sorted list of tuples.
     *
     * @param hashMap   A hash map of String key and int values corresponding to frequency of occurrence in the data.
     * @return          A list of tuples (String, int), sorted from highest to lowest frequency
     */
    public static  ArrayList<Tuple<String, Integer>> hashToList(HashMap<String, Integer> hashMap) {
        ArrayList<Tuple<String, Integer>> list = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            list.add(new Tuple<>(entry.getKey(), entry.getValue()));
        }
        list.sort((a, b) -> a.y - (int) b.y);
        return list;
    }
}
