package seng202.group7;

import java.util.ArrayList;
import java.time.LocalDateTime;

public class Filter {
    // TODO This function repeats itself too much, all of the methods do the exact same thing

    /**
     * Searches the data and returns a new list with only the conforming data
     * @param currentData The ArrayList of reports to search
     * @param start The time to start the search from
     * @param end The time to stop the search from
     * @return Reports that are within the times provided
     * @throws IllegalArgumentException If the start time is after the end time
     */
    public ArrayList<Report> timeFilter(ArrayList<Report> currentData, LocalDateTime start, LocalDateTime end) throws IllegalArgumentException{
        ArrayList<Report> filteredData = new ArrayList<Report>();
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("End is before start");
        }
        for (Report data: currentData) {
            if (((data.getDate().isAfter(start)) && (data.getDate().isBefore(end))) || data.getDate().equals(start) ||
                    data.getDate().equals(end)) {
                filteredData.add(data);
            }
        }
        return filteredData;
    }

    /**
     * Searchs the data and returns a new ArrayList of data within the area
     * @param currentData The ArrayList of reports to search
     * @param xcord1 
     * @param xcord2
     * @param ycord1
     * @param ycord2
     * @return
     */
    public ArrayList<Report> geoFilter(ArrayList<Report> currentData, Integer xcord1, Integer xcord2, Integer ycord1, Integer ycord2) {
        ArrayList<Report> filteredData = new ArrayList<Report>();
        for (Report data: currentData) {
            if (data.getXCoord() != null && data.getYCoord() != null &&
                    ((data.getXCoord() >= xcord1) && (data.getXCoord() <= xcord2)) &&
                    ((data.getYCoord() >= ycord1) && (data.getYCoord() <= ycord2))) {
                filteredData.add(data);
            }
        }
        return filteredData;
    }

    // Is this required? It seems pointless as we could just call the relevant one to start with
    /**
     * Determines which attribute is to be searched and goes to relevant method
     * @param currentData The data to filter
     * @param attribute The attribute to filter by
     * @param filterSelection What is to be filtered
     * @return An arraylist with the filtered results
     */
    public ArrayList<Report> stringFilter(ArrayList<Report> currentData, String attribute, String filterSelection) {
        ArrayList<Report> filteredData = new ArrayList<Report>();
        switch(attribute) {
            case "BLOCK": 
                blockFilter(currentData, filteredData, filterSelection);
                break;
            case "PRIMARY": 
                primaryFilter(currentData, filteredData, filterSelection);
                break;
            case "LOCATION": 
                locationFilter(currentData, filteredData, filterSelection);
                break;
            default:
                break;
        }
        return filteredData;
    }

    /**
     * Filters the block attribute and returns an ArrayList of reports that match the selection
     * @param currentData The data to filter
     * @param filteredData The filtered data
     * @param filterSelection What is to be filtered
     */
    private void blockFilter(ArrayList<Report> currentData, ArrayList<Report> filteredData, String filterSelection) {
        for (Report data: currentData) {
            Crime c = (Crime)data;
            if(c.getBlock().equals(filterSelection)) {
                filteredData.add(data);
            }
        }
    }

    /**
     * Filters the primary description attribute and returns an ArrayList of reports that match the selection
     * @param currentData The data to filter
     * @param filteredData The filtered data
     * @param filterSelection What is to be filtered
     */
    private void primaryFilter(ArrayList<Report> currentData, ArrayList<Report> filteredData, String filterSelection) {
        for (Report data: currentData) {
            if(data.getPrimaryDescription().equals(filterSelection)) {
                filteredData.add(data);
            }
        }
    }

    /**
     * Filters the location attribute and returns an ArrayList of reports that match the selection
     * @param currentData The data to filter
     * @param filteredData The filtered data
     * @param filterSelection What is to be filtered
     */
    private void locationFilter(ArrayList<Report> currentData, ArrayList<Report> filteredData, String filterSelection) {
        for (Report data: currentData) {
            if(data.getLocationDescription().equals(filterSelection)) {
                filteredData.add(data);
            }
        }
    }

    /**
     * Determines which attribute is to be searched and goes to relevant method
     * @param currentData The data to filter
     * @param attribute The attribute to filter by
     * @param filterSelection What is to be filtered
     * @return An arraylist with the filtered results
     */
    public ArrayList<Report> boolFilter(ArrayList<Report> currentData, String attribute, boolean filterSelection) {
        ArrayList<Report> filteredData = new ArrayList<Report>();
        switch(attribute) {
            case "ARREST": 
                arrestFilter(currentData, filteredData,filterSelection);
                break;
            case "DOMESTIC": 
                domesticFilter(currentData, filteredData, filterSelection);
                break;
            default:
                break;
        }
        return filteredData;
    }

    /**
     * Filters the arrest attribute and returns an ArrayList of reports that match the selection
     * @param currentData The data to filter
     * @param filteredData The filtered data
     * @param filterSelection What is to be filtered
     */
    public void arrestFilter(ArrayList<Report> currentData, ArrayList<Report> filteredData, boolean filterSelection) {
        for(Report data: currentData) {
            Crime c = (Crime)data;
            if (c.getArrest() == filterSelection) {
                filteredData.add(data);
            }
        }
    }

    /**
     * Filters the domestic attribute and returns an ArrayList of reports that match the selection
     * @param currentData The data to filter
     * @param filteredData The filtered data
     * @param filterSelection What is to be filtered
     */
    public void domesticFilter(ArrayList<Report> currentData, ArrayList<Report> filteredData, boolean filterSelection) {
        for(Report data: currentData) {
            if (data.getDomestic() == filterSelection) {
                filteredData.add(data);
            }
        }
    }
}
