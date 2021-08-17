package seng202.group7;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class CSVDataAccessorTest {

    @Test
    public void readFile() {
        CSVDataAccessor dataAccessor = new CSVDataAccessor();
        ArrayList<Report> data = dataAccessor.read("src/crimeData.csv");
        System.out.println(data.get(0).getClass());
        assertEquals(Crime.class, data.get(0).getClass());
    }
}