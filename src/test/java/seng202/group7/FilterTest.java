package seng202.group7;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FilterTest {

    CSVDataAccessor dataAccessor;
    ArrayList<Report> unfilteredData;
    Filter dataFilter;

    @BeforeEach
    public void init() {
        this.dataAccessor = new CSVDataAccessor();
        this.unfilteredData = dataAccessor.read("src/smallCrimeData.csv");
        this.dataFilter = new Filter();
    }

    @Test
    public void geoFilterTest1() {
        Integer xcord1 = 0;
        Integer xcord2 = 1;
        Integer ycord1 = 0;
        Integer ycord2 = 1;
        ArrayList<Report> filteredData = this.dataFilter.geoFilter(this.unfilteredData, xcord1, xcord2, ycord1, ycord2);
        assertEquals(0, filteredData.size());
    }

    @Test
    public void geoFilterTest2() {
        Integer xcord1 = 0;
        Integer xcord2 = 10000000;
        Integer ycord1 = 0;
        Integer ycord2 = 10000000;
        ArrayList<Report> filteredData = this.dataFilter.geoFilter(this.unfilteredData, xcord1, xcord2, ycord1, ycord2);
        assertEquals(8, filteredData.size());
    }

    @Test
    public void geoFilterTest3() {
        Integer xcord1 = 1183633;
        Integer xcord2 = 1183633;
        Integer ycord1 = 1851786;
        Integer ycord2 = 1851786;
        ArrayList<Report> filteredData = this.dataFilter.geoFilter(this.unfilteredData, xcord1, xcord2, ycord1, ycord2);
        assertEquals(1, filteredData.size());
    }

    @Test
    public void timeFilterTest1() {
        LocalDateTime start = LocalDateTime.parse("2019-04-28T01:00");
        LocalDateTime end = LocalDateTime.parse("2020-12-12T01:00");
        ArrayList<Report> filteredData = this.dataFilter.timeFilter(this.unfilteredData, start, end);
        assertEquals(1, filteredData.size());
    }

    @Test
    public void timeFilter2() {
        LocalDateTime start = LocalDateTime.parse("2010-04-28T01:00");
        LocalDateTime end = LocalDateTime.parse("2010-04-29T01:00");
        ArrayList<Report> filteredData = this.dataFilter.timeFilter(this.unfilteredData, start, end);
        assertEquals(0, filteredData.size());
    }

    @Test
    public void timeFilter3() {
        LocalDateTime start = LocalDateTime.parse("2010-04-28T01:00");
        LocalDateTime end = LocalDateTime.parse("2025-04-29T01:00");
        ArrayList<Report> filteredData = this.dataFilter.timeFilter(this.unfilteredData, start, end);
        assertEquals(10, filteredData.size());
    }

    @Test
    public void timeFilter4() {
        LocalDateTime start = LocalDateTime.parse("2020-11-23T03:05");
        LocalDateTime end = LocalDateTime.parse("2020-11-29T01:00");
        ArrayList<Report> filteredData = this.dataFilter.timeFilter(this.unfilteredData, start, end);
        assertEquals(1, filteredData.size());
    }

    @Test
    public void stringFilterTest1() {
        ArrayList<Report> filteredData = this.dataFilter.stringFilter(this.unfilteredData, 4, "THEFT");
        assertEquals(3, filteredData.size());
    }

    @Test
    public void stringFilterTest2() {
        ArrayList<Report> filteredData = this.dataFilter.stringFilter(this.unfilteredData, 6, "SIDEWALK");
        assertEquals(2, filteredData.size());
    }

    @Test
    public void StringFilterTest3() {
        ArrayList<Report> filteredData = this.dataFilter.stringFilter(this.unfilteredData, 4, "RANDOMSTRING");
        assertEquals(0, filteredData.size());
    }

    @Test
    public void boolFilterTest1() {
        ArrayList<Report> filteredData = this.dataFilter.boolFilter(this.unfilteredData, 7, false );
        assertEquals(9, filteredData.size());
    }
}

