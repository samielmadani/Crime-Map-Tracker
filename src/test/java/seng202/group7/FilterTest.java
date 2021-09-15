package seng202.group7;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.io.File;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import seng202.group7.analyses.Filter;

public class FilterTest {
    //private static CSVDataAccessor dataAccessor;
    private static DataAccessor dataAccessor;
    private static ArrayList<Report> unfilteredData;
    private static File smallFile = new File("src/test/files/smallCrimeData.csv");
    private static File mediumFile = new File("src/test/files/crimeData.csv");
    private static File commaFile = new File("src/test/files/commaInFieldTestData.csv");
    private static File blankFieldFile = new File("src/test/files/blankFieldTestData.csv");
    private static File blankRowFile = new File("src/test/files/blankRowTestData.csv");
    private static File testFile = new File("src/test/files/testData.csv");

    private String testFileString = "src/test/files/testData.csv";
    
    /**
     * Sets up the tests by reading data and creating a filter to reduce the time taken
     * TODO Create tests that can be used with different files being used initially, i.e mediumFile
     */
    @BeforeAll
    public static void setup() {
        dataAccessor = CSVDataAccessor.getInstance();
        unfilteredData = dataAccessor.read(smallFile);
    }

    /**
     * Checks that there are no matches for the coordinates (0,0) (1,1)
     */
    @Test
    public void geoFilter_noMatches() {
        Integer xcord1 = 0;
        Integer xcord2 = 1;
        Integer ycord1 = 0;
        Integer ycord2 = 1;
        ArrayList<Report> filteredData = Filter.geoFilter(unfilteredData, xcord1, xcord2, ycord1, ycord2);
        assertEquals(0, filteredData.size());
    }

    /**
     * Checks that there are 8 matches in the region (0,0) (10000000, 10000000)
     */
    @Test
    public void geoFilter_8Matches() {
        Integer xcord1 = 0;
        Integer xcord2 = 10000000;
        Integer ycord1 = 0;
        Integer ycord2 = 10000000;
        ArrayList<Report> filteredData = Filter.geoFilter(unfilteredData, xcord1, xcord2, ycord1, ycord2);
        assertEquals(8, filteredData.size());
    }

    /**
     * Checks that there is 1 match in the region (1183633, 1851786) (1183633, 1851786)
     */
    @Test
    public void geoFilter_1Match() {
        Integer xcord1 = 1183633;
        Integer xcord2 = 1183633;
        Integer ycord1 = 1851786;
        Integer ycord2 = 1851786;
        ArrayList<Report> filteredData = Filter.geoFilter(unfilteredData, xcord1, xcord2, ycord1, ycord2);
        assertEquals(1, filteredData.size());
    }

    /**
     * Checks that there is 1 match between 28/04/2019 and 12/12/2020
     */
    @Test
    public void timeFilter_1Match() {
        LocalDateTime start = LocalDateTime.parse("2019-04-28T01:00");
        LocalDateTime end = LocalDateTime.parse("2020-12-12T01:00");
        ArrayList<Report> filteredData = Filter.timeFilter(unfilteredData, start, end);
        assertEquals(1, filteredData.size());
    }

    /**
     * Checks that there are no matches between 28/04/2010 and 19/04/2010
     */
    @Test
    public void timeFilter_noMatches() {
        LocalDateTime start = LocalDateTime.parse("2010-04-28T01:00");
        LocalDateTime end = LocalDateTime.parse("2010-04-29T01:00");
        ArrayList<Report> filteredData = Filter.timeFilter(unfilteredData, start, end);
        assertEquals(0, filteredData.size());
    }

    /**
     * Checks that there are no matches between 28/04/2010 and 19/04/2025
     */
    @Test
    public void timeFilter_10Matches() {
        LocalDateTime start = LocalDateTime.parse("2010-04-28T01:00");
        LocalDateTime end = LocalDateTime.parse("2025-04-29T01:00");
        ArrayList<Report> filteredData = Filter.timeFilter(unfilteredData, start, end);
        assertEquals(10, filteredData.size());
    }

    /**
     * Ensures the program doesn't break when the order of the days is reversed
     * TODO Check this is intended behavior
     */
    @Test
    public void timeFilter_endBeforeStart() {
        LocalDateTime start = LocalDateTime.parse("2025-04-29T01:00");
        LocalDateTime end = LocalDateTime.parse("2010-04-28T01:00");
        assertThrows(IllegalArgumentException.class, () -> Filter.timeFilter(unfilteredData, start, end));
    }

    /**
     * Checks that there are 3 entries that have theft in the primary description
     */
    @Test
    public void stringFilter_primaryTheft() {
        ArrayList<Report> filteredData = Filter.stringFilter(unfilteredData, "PRIMARY", "THEFT");
        assertEquals(3, filteredData.size());
    }

    /**
     * Checks that there are 2 entries that have sidewalk in the location description
     */
    @Test
    public void stringFilter_secondarySidewalk() {
        ArrayList<Report> filteredData = Filter.stringFilter(unfilteredData, "LOCATION", "SIDEWALK");
        assertEquals(2, filteredData.size());
    }

    /**
     * Checks that there are 0 entries that have a random string in the primary description
     */
    @Test
    public void StringFilter_randomString() {
        ArrayList<Report> filteredData = Filter.stringFilter(unfilteredData, "PRIMARY", "Random string");
        assertEquals(0, filteredData.size());
    }

    /**
     * Checks that there are 9 entries that resulted in no arrests
     */
    @Test
    public void boolFilter_arrestFalse() {
        ArrayList<Report> filteredData = Filter.boolFilter(unfilteredData, "ARREST", false);
        assertEquals(9, filteredData.size());
    }
}

