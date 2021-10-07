package seng202.group7.analyses;

import seng202.group7.data.Report;
import seng202.group7.data.Crime;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

/**
 * Adds a set of static methods that allow a set of reports to be filtered based on certain conditions.
 *
 * @author Sam McMillan
 * @author Sami Elmadani
 */
public final class Filter {

    /**
     * Searches the data and returns a new list with only the conforming data
     *
     * @param currentData                   The List of reports to search
     * @param start                         The time to start the search from
     * @param end                           The time to stop the search from
     *
     * @return filteredData                 Reports that are within the times provided
     * @throws IllegalArgumentException     If the start time is after the end time
     */
    public static List<Report> timeFilter(List<Report> currentData, LocalDateTime start, LocalDateTime end) throws IllegalArgumentException{
        List<Report> filteredData = new ArrayList<>();
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
     * Determines which attribute is to be searched and goes to relevant method
     * @param currentData           The data to filter
     * @param attribute             The attribute to filter by
     * @param filterSelection       What is to be filtered
     * @return filteredData         An list with the filtered results
     */
    public static List<Report> stringFilter(List<Report> currentData, String attribute, String filterSelection) {
        List<Report> filteredData = new ArrayList<>();
        switch (attribute) {
            case "BLOCK" -> blockFilter(currentData, filteredData, filterSelection);
            case "PRIMARY" -> primaryFilter(currentData, filteredData, filterSelection);
            case "LOCATION" -> locationFilter(currentData, filteredData, filterSelection);
            default -> {
            }
        }
        return filteredData;
    }

    /**
     * Filters the block attribute and returns an List of reports that match the selection.
     *
     * @param currentData       The data to filter
     * @param filteredData      The filtered data
     * @param filterSelection   What is to be filtered
     */
    private static void blockFilter(List<Report> currentData, List<Report> filteredData, String filterSelection) {
        for (Report data: currentData) {
            if(((Crime) data).getBlock().equals(filterSelection)) {
                filteredData.add(data);
            }
        }
    }

    /**
     * Filters the primary description attribute and returns an List of reports that match the selection.
     *
     * @param currentData       The data to filter
     * @param filteredData      The filtered data
     * @param filterSelection   What is to be filtered
     */
    private static void primaryFilter(List<Report> currentData, List<Report> filteredData, String filterSelection) {
        for (Report data: currentData) {
            if(data.getPrimaryDescription().equals(filterSelection)) {
                filteredData.add(data);
            }
        }
    }

    /**
     * Filters the location attribute and returns an List of reports that match the selection.
     *
     * @param currentData       The data to filter
     * @param filteredData      The filtered data
     * @param filterSelection   What is to be filtered
     */
    private static void locationFilter(List<Report> currentData, List<Report> filteredData, String filterSelection) {
        for (Report data: currentData) {
            if(data.getLocationDescription().equals(filterSelection)) {
                filteredData.add(data);
            }
        }
    }

    /**
     * Determines which attribute is to be searched and goes to relevant method.
     *
     * @param currentData       The data to filter
     * @param attribute         The attribute to filter by
     * @param filterSelection   What is to be filtered
     * @return filteredData     An list with the filtered results
     */
    public static List<Report> boolFilter(List<Report> currentData, String attribute, boolean filterSelection) {
        List<Report> filteredData = new ArrayList<>();
        switch (attribute) {
            case "ARREST" -> arrestFilter(currentData, filteredData, filterSelection);
            case "DOMESTIC" -> domesticFilter(currentData, filteredData, filterSelection);
            default -> {
            }
        }
        return filteredData;
    }

    /**
     * Filters the arrest attribute and returns an List of reports that match the selection.
     *
     * @param currentData       The data to filter
     * @param filteredData      The filtered data
     * @param filterSelection   What is to be filtered
     */
    public static void arrestFilter(List<Report> currentData, List<Report> filteredData, boolean filterSelection) {
        for(Report data: currentData) {
            if (((Crime) data).getArrest() == filterSelection) {
                filteredData.add(data);
            }
        }
    }

    /**
     * Filters the domestic attribute and returns an List of reports that match the selection.
     *
     * @param currentData       The data to filter
     * @param filteredData      The filtered data
     * @param filterSelection   What is to be filtered
     */
    public static void domesticFilter(List<Report> currentData, List<Report> filteredData, boolean filterSelection) {
        for(Report data: currentData) {
            if (data.getDomestic() == filterSelection) {
                filteredData.add(data);
            }
        }
    }

    /**
     * Determines which attribute is to be searched and goes to relevant method.
     *
     * @param currentData       The data to filter
     * @param attribute         The attribute to filter by
     * @param filterSelection   What is to be filtered
     * @return filteredData     An list with the filtered results.
     */
    public static List<Report>  intFilter(List<Report> currentData, String attribute, int filterSelection) {
        List<Report> filteredData = new ArrayList<>();
        if ("WARD".equals(attribute)) {
            wardFilter(currentData, filteredData, filterSelection);
        }
        return filteredData;
    }

    /**
     * Filters the ward attribute and returns an List of reports that match the selection.
     *
     * @param currentData       The data to filter
     * @param filteredData      The filtered data
     * @param filterSelection   What is to be filtered
     */
    private static void wardFilter(List<Report> currentData, List<Report> filteredData, int filterSelection) {
        for (Report data: currentData) {
            if(((Crime) data).getWard() == filterSelection) {
                filteredData.add(data);
            }
        }
    }
}
