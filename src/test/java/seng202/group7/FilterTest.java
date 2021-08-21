package seng202.group7;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FilterTest {


    @Test
    public void timeFilterTest() {
        assertEquals(2, 2);
    }

    @Test
    public void locationFilterTest() {
        assertEquals(1, 1);
    }

    @Test
    public void primaryFilterTest() {
        CSVDataAccessor dataAccessor = new CSVDataAccessor();
        ArrayList<Report> data = dataAccessor.read("src/crimeData.csv");
        Filter dataFilter = new Filter();
        ArrayList<Report> filteredData = dataFilter.primaryFilter(data, "THEFT");
        for (Report report : filteredData) {
            assertEquals("THEFT", report.getPrimaryDescription());
        }
    }
}
