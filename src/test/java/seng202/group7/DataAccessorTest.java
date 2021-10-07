package seng202.group7;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng202.group7.data.Crime;
import seng202.group7.data.CustomException;
import seng202.group7.data.DataAccessor;
import seng202.group7.data.Report;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Tests the features of importing and getting data using the data accessor.
 *
 * @author John Elliott
 */
public class DataAccessorTest {

    public static DataAccessor accessor = DataAccessor.getInstance();

    /**
     * Changes the connection to the test database.
     */
    @BeforeAll
    public static void changeConnection() {
        // Changes the connection to the testDatabase.db
        accessor.changeConnection("src/test/files/TestDatabase.db");
    }

    /**
     * Adds the single test crime to the database and ensures no other entries exist.
     */
    @BeforeEach
    public void createNewTestDatabase() {
        try {
            Statement stmt = accessor.getConnection().createStatement();
            stmt.execute("DELETE FROM crimes");
            stmt.execute("DELETE FROM reports");
            stmt.execute("DELETE FROM lists");
            stmt.execute("INSERT INTO lists(id, name) VALUES(1, 'testList')");
            stmt.close();

            Crime crimeOne = new Crime("TestNumber", LocalDateTime.now(), null, null, "test", "test", null, null, null, null, null, null, null, null, null, null);
            accessor.editCrime(crimeOne, 1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Checks that the size of the database is correct.
     */
    @Test
    public void checkSize() {
        assertEquals(accessor.getSize(1), 1);
    }

    /**
     * Adds a entry and test deleting it.
     */
    @Test
    public void deleteTest() {
        Crime crimeTwo = new Crime("TestToDelete", LocalDateTime.now(), null, null, "test", "test", null, null, null, null, null, null, null, null, null, null);
        accessor.editCrime(crimeTwo, 1);
        try {
            accessor.deleteReport("TestToDelete", 1);
        } catch (CustomException e) {
            fail();
        }
        Crime crime = accessor.getCrime("TestToDelete", 1);
        assertNull(crime);
    }

    /**
     * Reads a test CSV and checks it adds all data correctly.
     */
    @Test
    public void readToDBTest() {
        try {
            accessor.readToDB(new File("src/test/files/testCSV.csv"), 1);
        } catch (SQLException|CustomException e) {
            // TODO Auto-generated catch block
            fail();
        }
        Crime crime = accessor.getCrime("JE163990", 1);
        assertEquals(crime.getId(), "JE163990");
    }

    /**
     * Reads another database into the testing database and checks it adds the data correctly.
     */
    @Test
    public void importDBTest() {
        try {
            accessor.importInDB(new File("src/test/files/TestImporting.db"), 1);
        } catch (SQLException e) {
           fail();
        }
        Crime crime = accessor.getCrime("TestNumber", 1);
        assertEquals(crime.getId(), "TestNumber");
    }

    @Test
    public void getListIdTest() {
        assertEquals(1, accessor.getListId("testList"));
    }

    /**
     * Checks that the page set get the correctly set of reports.
     */
    @Test
    public void pageSetTest() {
        List<Report> reports = accessor.getPageSet(1);
        assertEquals(((Crime) reports.get(0)).getId(), "TestNumber");
    }

}
