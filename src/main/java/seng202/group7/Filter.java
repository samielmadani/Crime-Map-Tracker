package seng202.group7;

import java.util.ArrayList;
import java.time.LocalDateTime;

public class Filter {

    /**
     * 
     * @param currentData
     * @param start
     * @param end
     * @return 
     * @throws IllegalArgumentException
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

    private void blockFilter(ArrayList<Report> currentData, ArrayList<Report> filteredData, String filterSelection) {
        for (Report data: currentData) {
            Crime c = (Crime)data;
            if(c.getBlock().equals(filterSelection)) {
                filteredData.add(data);
            }
        }
    }

    private void primaryFilter(ArrayList<Report> currentData, ArrayList<Report> filteredData, String filterSelection) {
        for (Report data: currentData) {
            if(data.getPrimaryDescription().equals(filterSelection)) {
                filteredData.add(data);
            }
        }
    }

    private void locationFilter(ArrayList<Report> currentData, ArrayList<Report> filteredData, String filterSelection) {
        for (Report data: currentData) {
            if(data.getLocationDescription().equals(filterSelection)) {
                filteredData.add(data);
            }
        }
    }


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

    public void arrestFilter(ArrayList<Report> currentData, ArrayList<Report> filteredData, boolean filterSelection) {
        for(Report data: currentData) {
            Crime c = (Crime)data;
            if (c.getArrest() == filterSelection) {
                filteredData.add(data);
            }
        }
    }

    public void domesticFilter(ArrayList<Report> currentData, ArrayList<Report> filteredData, boolean filterSelection) {
        for(Report data: currentData) {
            if (data.getDomestic() == filterSelection) {
                filteredData.add(data);
            }
        }
    }
}
