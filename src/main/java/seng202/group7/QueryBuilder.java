package seng202.group7;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

public class QueryBuilder {

    /**
     * builds a SQL query to retrieve data that meets the conditions of the parameters. null parameters are ommited.
     * @return a String representation of a SQL query
     */
    public static String where(LocalDate date, String primaryDescription, String locationDescription, Integer ward,
                               Integer beat, Boolean arrest, Boolean domestic){

        String query = "SELECT * FROM crimedb WHERE ";

        if(date != null){
            query += addAndCondition("date", date.toString());
        }
        if(primaryDescription != null){
            query += addAndCondition("primary_description", primaryDescription);
        }
        if(locationDescription != null){
            query += addAndCondition("location_description", locationDescription);
        }
        if(ward != null){
            query += addAndCondition("ward", ward);
        }
        if(beat != null){
            query += addAndCondition("beat", beat);
        }
        if(arrest != null){
            query += addAndCondition("arrest", arrest);
        }
        if(domestic != null){
            query += addAndCondition("domestic", domestic);
        }
        return query.substring(0, query.length()-5) + ";";
    }

    private static String addAndCondition(String field, boolean value) {
        return field + "=" + (value ? 1 : 0) + " AND ";
    }

    private static String addAndCondition(String field, int value) {
        return field + "=" + value + " AND ";
    }

    private static String addAndCondition(String field, String value) {
        return field + "=\'" + value + "\' AND ";
    }
}
