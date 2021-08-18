package seng202.group7;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class CSVDataAccessorTest {

    @Test
    public void readFileData() {
        CSVDataAccessor dataAccessor = new CSVDataAccessor();
        ArrayList<Report> data = dataAccessor.read("src/crimeData.csv");
        assertEquals(Crime.class, data.get(0).getClass());
    }

    public void readFileAmount() {
        CSVDataAccessor dataAccessor = new CSVDataAccessor();
        ArrayList<Report> data = dataAccessor.read("src/crimeData.csv");
        assertEquals(5000, data.size(), String.format("Not all data was imported correctly." +
        " %f out of 5000 were imported.", data.size()));
        
    }
}