package seng202.group7;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
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
    public void geoFilterTest() {
        assertEquals(1, 1);
    }

    @Test
    public void timeFilterTest() {
        LocalDateTime start = LocalDateTime.parse("2019-04-28T01:00");
        LocalDateTime end = LocalDateTime.parse("2020-12-12T01:00");
        ArrayList<Report> filteredData = this.dataFilter.timeFilter(this.unfilteredData, start, end);
        assertEquals(1, filteredData.size());
    }

}

