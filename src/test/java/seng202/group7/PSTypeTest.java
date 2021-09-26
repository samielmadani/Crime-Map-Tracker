package seng202.group7;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng202.group7.data.Crime;
import seng202.group7.data.DataAccessor;
import seng202.group7.data.PSTypes;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests to ensure that data inserted using the PSType classes methods
 * are correctly inserted into the database.
 *
 * @author John Elliott
 */
public class PSTypeTest {

    public static Connection connection;


    /**
     * Creates an empty database before running the tests.
     */
    @BeforeAll
    public static void connectDatabase() {
        DataAccessor.getInstance().changeConnection("src/test/files/TestDatabase.db");
        connection = DataAccessor.getInstance().getConnection();
        try {
            Statement stmt = DataAccessor.getInstance().getConnection().createStatement();
            stmt.execute("DELETE FROM crimes");
            stmt.execute("DELETE FROM reports");
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests to see if data inserted using the PSType classes methods correctly inserts the data for Strings.
     */
    @Test
    public void setString() {

        try {
            // Uses the int pstype method
            PreparedStatement ps = connection.prepareStatement("INSERT OR REPLACE INTO crimes(case_number) " +
                    "VALUES (?);");
            PSTypes.setPSString(ps, 1, "TestNumber"); // Case Number
            ps.execute();
            ps.close(); // Closes the ps statement, so it can use the connection.

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM crimes WHERE case_number='TestNumber'");
            assertEquals(rs.getString(1), "TestNumber");
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Tests to see if data inserted using the PSType classes methods correctly inserts the data for Doubles.
     */
    @Test
    public void setZeroDouble() {

        try {
            long time = Timestamp.valueOf(LocalDate.now().atStartOfDay()).getTime();
            // Uses the int pstype method
            PreparedStatement ps = connection.prepareStatement("INSERT OR REPLACE INTO reports(report_id, date, primary_description, secondary_description, latitude) " +
                    "VALUES ('TestNumber', "+time+", 'test', 'test', ?);");
            PSTypes.setPSDouble(ps, 1, 0.0); // Case Number
            ps.execute();
            ps.close(); // Closes the ps statement, so it can use the connection.

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM reports WHERE latitude=0.0");
            assertEquals(rs.getString(1), "TestNumber");
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Tests to see if data inserted using the PSType classes methods correctly inserts the data for Ints.
     */
    @Test
    public void setNegativeInt() {

        try {
            // Uses the int pstype method
            PreparedStatement ps = connection.prepareStatement("INSERT OR REPLACE INTO crimes(case_number, beat) " +
                    "VALUES ('TestNumber', ?);");
            PSTypes.setPSInteger(ps, 1, -10); // Case Number
            ps.execute();
            ps.close(); // Closes the ps statement, so it can use the connection.

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM crimes WHERE beat=-10");
            assertEquals(rs.getString(1), "TestNumber");
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests to see if data inserted using the PSType classes methods correctly inserts the data for Booleans.
     */
    @Test
    public void setBoolean() {

        try {
            // Uses the int pstype method
            PreparedStatement ps = connection.prepareStatement("INSERT OR REPLACE INTO crimes(case_number, arrest) " +
                    "VALUES ('TestNumber', ?);");
            PSTypes.setPSBoolean(ps, 1, true); // Case Number
            ps.execute();
            ps.close(); // Closes the ps statement, so it can use the connection.

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM crimes WHERE arrest=1");
            assertEquals(rs.getString(1), "TestNumber");
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
