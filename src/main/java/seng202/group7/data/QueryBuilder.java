package seng202.group7.data;

import java.sql.Timestamp;
import java.time.LocalDate;

/**
 * builds queries based on parameters and returns them as Strings
 * @author Shaylin Simadari
 */
public final class QueryBuilder {

    /**
     * builds an SQL query to retrieve data that meets the conditions of the parameters.
     * null parameters are omitted.
     * conditions are connected by AND keywords
     *
     * @return      A String representation of am SQL query
     */
    public static String where(FilterConditions fc){

        String query = "WHERE ";

        if (fc.getDateFrom() != null){
            query += addAndCondition(fc.getDateFrom());
        }
        if (fc.getPrimaryDescription() != null){
            query += addAndCondition("primary_description", fc.getPrimaryDescription());
        }
        if (fc.getLocationDescription() != null){
            query += addAndCondition("location_description", fc.getLocationDescription());
        }
        if (fc.getWard() != null){
            query += addAndCondition("ward", fc.getWard());
        }
        if (fc.getBeat() != null){
            query += addAndCondition("beat", fc.getBeat());
        }
        if (fc.getArrest() != null){
            query += addAndCondition("arrest", fc.getArrest());
        }
        if (fc.getDomestic() != null){
            query += addAndCondition("domestic", fc.getDomestic());
        }

        if (query.equals("WHERE ")) {
            return "";
        } else {
            return query.substring(0, query.length()-5);
        }
    }

    /**
     * Is a builder that adds an and condition. For a boolean value.
     *
     * @param field     The name of the column in the database
     * @param value     The value that the filed must be to meet the condition
     * @return          A String to append to a where query
     */
    private static String addAndCondition(String field, boolean value) {
        return field + "=" + (value ? 1 : 0) + " AND ";
    }

    /**
     *  Is a builder that adds an and condition. For an integer value.
     *
     * @param field     The name of the column in the database
     * @param value     The value that the filed must be to meet the condition
     * @return          A String to append to a where query
     */
    private static String addAndCondition(String field, int value) {
        return field + "=" + value + " AND ";
    }


    /**
     *  Is a builder that adds an and condition. For a string value.
     *
     * @param field     The name of the column in the database
     * @param value     The value that the filed must be to meet the condition
     * @return          A String to append to a where query
     */
    private static String addAndCondition(String field, String value) {
        return field + "='" + value + "' AND ";
    }


    /**
     *  Is a builder that adds an and condition. For a string value.
     *
     * @param value     The value that the filed must be to meet the condition
     * @return          A String to append to a where query
     */
    private static String addAndCondition(LocalDate value) {
        return "date >= " + Timestamp.valueOf(value.atStartOfDay()).getTime()
                + " AND date < " + Timestamp.valueOf(value.plusDays(1).atStartOfDay()).getTime() + " AND ";
    }
}
