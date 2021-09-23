package seng202.group7.data;

import javax.swing.text.DateFormatter;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

/**
 * builds queries based on parameters and returns them as Strings
 * @author Shaylin Simadari
 */
public class QueryBuilder {

    /**
     * builds an SQL query to retrieve data that meets the conditions of the parameters.
     * null parameters are omitted.
     * conditions are connected by AND keywords
     *
     * @return      A String representation of am SQL query
     */
    public static String where(LocalDate date, String primaryDescription, String locationDescription, Integer ward,
                               Integer beat, Boolean arrest, Boolean domestic){

        String query = "WHERE ";

        if(date != null){
            //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a", Locale.US);
            //query += "date BETWEEN '"+ Timestamp.valueOf(date.atTime(LocalTime.MIDNIGHT)) + "' AND '"+ Timestamp.valueOf(date.atTime(LocalTime.MAX)) +"' AND ";
            //System.out.println("DATE(date) BETWEEN '"+ Timestamp.valueOf(date.atTime(LocalTime.MIDNIGHT)) + "' AND '"+ Timestamp.valueOf(date.atTime(LocalTime.MAX)) +"' AND ");
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
        return query.substring(0, query.length()-5);
    }

    /**
     * Is a builder that adds a and condition. For a boolean value.
     *
     * @param field     The name of the column in the database
     * @param value     The value that the filed must be to meet the condition
     * @return          A String to append to a where query
     */
    private static String addAndCondition(String field, boolean value) {
        return field + "=" + (value ? 1 : 0) + " AND ";
    }

    /**
     *  Is a builder that adds a and condition. For an integer value.
     *
     * @param field     The name of the column in the database
     * @param value     The value that the filed must be to meet the condition
     * @return          A String to append to a where query
     */
    private static String addAndCondition(String field, int value) {
        return field + "=" + value + " AND ";
    }


    /**
     *  Is a builder that adds a and condition. For a string value.
     *
     * @param field     The name of the column in the database
     * @param value     The value that the filed must be to meet the condition
     * @return          A String to append to a where query
     */
    private static String addAndCondition(String field, String value) {
        System.out.println(value);
        return field + "='" + value + "' AND ";
    }
}
