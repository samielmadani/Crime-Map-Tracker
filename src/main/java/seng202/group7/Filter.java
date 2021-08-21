package seng202.group7;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.time.LocalDateTime;

public class Filter {


    public ArrayList<Report> timeFilter(ArrayList<Report> currentData, LocalDateTime start, LocalDateTime end) {

        ArrayList<Report> filteredData = new ArrayList<Report>();
        for (Report data: currentData) {
            if ((data.getDate().isAfter(start)) && (data.getDate().isBefore(end))) {
                filteredData.add(data);
            }
        }
        return filteredData;
    }

    public ArrayList<Report> locationFilter(ArrayList<Report> currentData, int xcord1, int xcord2, int ycord1, int ycord2) {

        ArrayList<Report> filteredData = new ArrayList<Report>();
        for (Report data: currentData) {
            if ((data.getXCoord() >= xcord1 && data.getXCoord() <= xcord2) &&
                    (data.getYCoord() >= ycord1 && data.getYCoord() <= ycord2)) {
                filteredData.add(data);
            }
        }
        return filteredData;
    }

    public ArrayList<Report> primaryFilter(ArrayList<Report> currentData, String primary) {
        ArrayList<Report> filteredData = new ArrayList<Report>();
        for (Report data: currentData) {
            if (data.getPrimaryDescription().equals(primary)) {
                filteredData.add(data);
            }
        }
        return filteredData;
    }
}
