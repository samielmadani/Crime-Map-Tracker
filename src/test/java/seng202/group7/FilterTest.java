package seng202.group7;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FilterTest {
    private CSVDataAccessor dataAccessor;
    private ArrayList<Report> unfilteredData;
    private Filter dataFilter;
    private String smallFile = "src/test/files/smallCrimeData.csv";
    private String mediumFile = "src/test/files/crimeData.csv";
    private String testFile = "src/test/files/testData.csv";
    private String commaFile = "src/test/files/commaInFieldTestData.csv";
    private String blankFieldFile = "src/test/files/blankFieldTestData.csv";
    private String blankRowFile = "src/test/files/blankRowTestData.csv";
    
    @BeforeEach
    public void init() {
        dataAccessor = new CSVDataAccessor();
        unfilteredData = dataAccessor.read(smallFile);
        dataFilter = new Filter();
    }

    @Test
    public void geoFilter_noMatches() {
        Integer xcord1 = 0;
        Integer xcord2 = 1;
        Integer ycord1 = 0;
        Integer ycord2 = 1;
        ArrayList<Report> filteredData = dataFilter.geoFilter(unfilteredData, xcord1, xcord2, ycord1, ycord2);
        assertEquals(0, filteredData.size());
    }

    @Test
    public void geoFilter_8Matches() {
        Integer xcord1 = 0;
        Integer xcord2 = 10000000;
        Integer ycord1 = 0;
        Integer ycord2 = 10000000;
        ArrayList<Report> filteredData = dataFilter.geoFilter(unfilteredData, xcord1, xcord2, ycord1, ycord2);
        assertEquals(8, filteredData.size());
    }

    @Test
    public void geoFilter_1Match() {
        Integer xcord1 = 1183633;
        Integer xcord2 = 1183633;
        Integer ycord1 = 1851786;
        Integer ycord2 = 1851786;
        ArrayList<Report> filteredData = dataFilter.geoFilter(unfilteredData, xcord1, xcord2, ycord1, ycord2);
        assertEquals(1, filteredData.size());
    }

    @Test
    public void timeFilter_1Match() {
        LocalDateTime start = LocalDateTime.parse("2019-04-28T01:00");
        LocalDateTime end = LocalDateTime.parse("2020-12-12T01:00");
        ArrayList<Report> filteredData = dataFilter.timeFilter(unfilteredData, start, end);
        assertEquals(1, filteredData.size());
    }

    @Test
    public void timeFilter_noMatches() {
        LocalDateTime start = LocalDateTime.parse("2010-04-28T01:00");
        LocalDateTime end = LocalDateTime.parse("2010-04-29T01:00");
        ArrayList<Report> filteredData = dataFilter.timeFilter(unfilteredData, start, end);
        assertEquals(0, filteredData.size());
    }

    @Test
    public void timeFilter_10Matches() {
        LocalDateTime start = LocalDateTime.parse("2010-04-28T01:00");
        LocalDateTime end = LocalDateTime.parse("2025-04-29T01:00");
        ArrayList<Report> filteredData = dataFilter.timeFilter(unfilteredData, start, end);
        assertEquals(10, filteredData.size());
    }

    @Test
    public void timeFilter_endBeforeStart() {
        LocalDateTime start = LocalDateTime.parse("2025-04-29T01:00");
        LocalDateTime end = LocalDateTime.parse("2010-04-28T01:00");
        assertThrows(IllegalArgumentException.class, () -> dataFilter.timeFilter(unfilteredData, start, end));
    }

    @Test
    public void stringFilter_primaryTheft() {
        ArrayList<Report> filteredData = dataFilter.stringFilter(unfilteredData, "PRIMARY", "THEFT");
        assertEquals(3, filteredData.size());
    }

    @Test
    public void stringFilter_secondarySidewalk() {
        ArrayList<Report> filteredData = dataFilter.stringFilter(unfilteredData, "SECONDARY", "SIDEWALK");
        assertEquals(2, filteredData.size());
    }

    @Test
    public void StringFilter_randomString() {
        ArrayList<Report> filteredData = dataFilter.stringFilter(unfilteredData, "PRIMARY", "Random string");
        assertEquals(0, filteredData.size());
    }

    @Test
    public void boolFilter_arrestFalse() {
        ArrayList<Report> filteredData = dataFilter.boolFilter(unfilteredData, "ARREST", false);
        assertEquals(9, filteredData.size());
    }
}

