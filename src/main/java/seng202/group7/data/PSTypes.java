package seng202.group7.data;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

/**
 * This class adds a set of static methods that are used when checking if a CSV
 * is null which is "" inserting the corrected null value into the database.
 *
 * @author John Elliott
 * @author Shaylin Simadari
 */
public final class PSTypes {

    /**
     * Checks if an "integer" is "" or null and sets the PreparedStatement to null-Types.INTEGER instead.
     *
     * @param ps                    The PreparedStatement being used.
     * @param parameterIndex        The index in the PreparedStatement.
     * @param x                     The string being checked.
     */
    public static void setPSInteger(PreparedStatement ps, int parameterIndex, String x) {
        try {
            if (x.equals("")) {
                ps.setNull(parameterIndex, Types.INTEGER);
            } else {
                ps.setInt(parameterIndex, Integer.parseInt(x));
            }
        } catch (SQLException e) {
            System.out.println("SQLiteAccessor.setInteger: " + e);
        }
    }

    /**
     * Checks if a "double" is "" or null and sets the PreparedStatement to null-Types.DOUBLE instead.
     *
     * @param ps                    The PreparedStatement being used.
     * @param parameterIndex        The index in the PreparedStatement.
     * @param x                     The string being checked.
     */
    public static void setPSDouble(PreparedStatement ps, int parameterIndex, String x) {
        try {
            if (x.equals("")) {
                ps.setNull(parameterIndex, Types.DOUBLE);
            } else {
                ps.setDouble(parameterIndex, Double.parseDouble(x));
            }
        } catch (SQLException e) {
            System.out.println("SQLiteAccessor.setDouble: " + e);
        }
    }

    /**
     * Checks if a "string" is "" or null and sets the PreparedStatement to null-Types.VARCHAR instead.
     *
     * @param ps                    The PreparedStatement being used.
     * @param parameterIndex        The index in the PreparedStatement.
     * @param x                     The string being checked.
     */
    public static void setPSString(PreparedStatement ps, int parameterIndex, String x) {
        try {
            if (x.equals("")) {
                ps.setNull(parameterIndex, Types.VARCHAR);
            } else {
                ps.setString(parameterIndex, x);
            }
        } catch (SQLException e) {
            System.out.println("SQLiteAccessor.setString: " + e);
        }
    }

    /**
     * Checks if an "boolean" is "" or null and sets the PreparedStatement to null-Types.BOOLEAN instead.
     *
     * @param ps                    The PreparedStatement being used.
     * @param parameterIndex        The index in the PreparedStatement.
     * @param x                     The string being checked.
     */
    public static void setPSBoolean(PreparedStatement ps, int parameterIndex, String x) {
        try {
            if (x.equals("")) {
                ps.setNull(parameterIndex, Types.BOOLEAN);
            } else {
                ps.setBoolean(parameterIndex, x.equalsIgnoreCase("Y"));
            }
        } catch (SQLException e) {
            System.out.println("SQLiteAccessor.setBoolean: " + e);
        }
    }
}
