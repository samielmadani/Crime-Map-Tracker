package seng202.group7;

import java.util.ArrayList;
import java.time.LocalDateTime;

public class Filter {

    public Filter() {}
    public ArrayList<Report> timeFilter(ArrayList<Report> currentData, LocalDateTime start, LocalDateTime end) {

        ArrayList<Report> filteredData = new ArrayList<>();
        for (Report data: currentData) {
            if (((data.getDate().isAfter(start)) && (data.getDate().isBefore(end))) || data.getDate().equals(start) ||
                    data.getDate().equals(end)) {
                filteredData.add(data);
            }
        }
        return filteredData;
    }


    public ArrayList<Report> geoFilter(ArrayList<Report> currentData, Integer xcord1, Integer xcord2, Integer ycord1, Integer ycord2) {
        ArrayList<Report> filteredData = new ArrayList<>();
        for (Report data: currentData) {
            if (data.getXCoord() != null && data.getYCoord() != null &&
                    ((data.getXCoord() >= xcord1) && (data.getXCoord() <= xcord2)) &&
                    ((data.getYCoord() >= ycord1) && (data.getYCoord() <= ycord2))) {
                filteredData.add(data);
            }
        }
        return filteredData;
    }


    public ArrayList<Report> stringFilter(ArrayList<Report> currentData, int attribute, String filterSelection) {
        ArrayList<Report> filteredData = new ArrayList<>();
        switch(attribute) {
            case 2: blockFilter(currentData, filteredData, filterSelection);
                    break;
            case 4: primaryFilter(currentData, filteredData, filterSelection);
                    break;
            case 6: locationFilter(currentData, filteredData, filterSelection);
                    break;
            default:
                    break;
        }
        return filteredData;
    }

    public void blockFilter(ArrayList<Report> currentData, ArrayList<Report> filteredData, String filterSelection) {
        for (Report data: currentData) {
            Crime c = (Crime)data;
            if(c.getBlock().equals(filterSelection)) {
                filteredData.add(data);
            }
        }
    }

    public void primaryFilter(ArrayList<Report> currentData, ArrayList<Report> filteredData, String filterSelection) {
        for (Report data: currentData) {
            if(data.getPrimaryDescription().equals(filterSelection)) {
                filteredData.add(data);
            }
        }
    }

    public void locationFilter(ArrayList<Report> currentData, ArrayList<Report> filteredData, String filterSelection) {
        for (Report data: currentData) {
            if(data.getLocationDescription().equals(filterSelection)) {
                filteredData.add(data);
            }
        }
    }


    public ArrayList<Report> boolFilter(ArrayList<Report> currentData, int attribute, boolean filterSelection) {
        ArrayList<Report> filteredData = new ArrayList<>();
        switch(attribute) {
            case 7:
                arrestFilter(currentData, filteredData,filterSelection);
                break;
            case 8:
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
