package seng202.group7;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import seng202.group7.controllers.ControllerData;
import seng202.group7.data.Crime;
import seng202.group7.data.DataAccessor;
import seng202.group7.data.Report;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DataAccessorTest {

    public static DataAccessor accessor = DataAccessor.getInstance();

    @BeforeAll
    public static void createNewTestDatabase() {
        // Changes the connection to the testDatabase.db
        accessor.changeConnection("src/test/files/TestDatabase.db");
        try {
            Statement stmt = accessor.getConnection().createStatement();
            stmt.execute("DELETE FROM crimes");
            stmt.execute("DELETE FROM reports");
            stmt.close();

            Crime crimeOne = new Crime("TestNumber", LocalDateTime.now(), null, null, "test", "test", null, null, null, null, null, null, null, null, null, null);
            accessor.editCrime(crimeOne);
            Crime crimeTwo = new Crime("TestToDelete", LocalDateTime.now(), null, null, "test", "test", null, null, null, null, null, null, null, null, null, null);
            accessor.editCrime(crimeTwo);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void checkSize() {
        assertEquals(accessor.getSize(), 2);
    }

    @Test
    public void pageSetTest() {
        ArrayList<Report> reports = accessor.getPageSet();
        assertEquals(((Crime) reports.get(0)).getCaseNumber(), "TestNumber");
    }

    @Test
    public void deleteTest() {
        accessor.delete("TestToDelete");
        Crime crime = accessor.getCrime("TestToDelete");
        assertNull(crime);
    }

}
