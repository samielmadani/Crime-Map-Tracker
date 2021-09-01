package seng202.group7;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class CSVDataAccessorTest {

    //TODO: create test naming convention

    @Test
    public void isCrimeInstance() {
        CSVDataAccessor dataAccessor = CSVDataAccessor.getInstance();
        ArrayList<Report> data = dataAccessor.read(new File("src/crimeData.csv"));

        for(Report report : data){
            assertTrue(report instanceof Crime);
        }
    }

    @Test
    public void loadsAllRows() {
        CSVDataAccessor dataAccessor = CSVDataAccessor.getInstance();
        ArrayList<Report> data = dataAccessor.read(new File("src/crimeData.csv"));

        assertEquals(5000, data.size(), String.format("Not all data was imported correctly." +
        " %d out of 5000 were imported.", data.size()));
        
    }


    @Test
    public void testDataFromFile() {
        CSVDataAccessor dataAccessor = CSVDataAccessor.getInstance();
        ArrayList<Report> data = dataAccessor.read(new File("src/crimeData.csv"));
        String gotCaseNumber = ((Crime) data.get(0)).getBlock();
        assertEquals("073XX S SOUTH SHORE DR", gotCaseNumber, "Data does not have the right value for case Number");
    }

    @Test
    public void handlesCommaInField() {
        CSVDataAccessor dataAccessor = CSVDataAccessor.getInstance();
        ArrayList<Report> data = dataAccessor.read(new File("src/commaInFieldTestData.csv"));

        Report actualReport = data.get(0);
        Report expectedReport = new Crime("JD389017", CSVDataAccessor.getLocalDateTime("10/05/2020 03:31:00 AM"), "006XX W HARRISON ST", "0454", "BATTERY", "AGGRAVATED P.O. - HANDS, FISTS, FEET, NO / MINOR INJURY", "OTHER COMMERCIAL TRANSPORTATION", true, false, 124, 25, "08B", 1172257, 1897564, 41.874363279, -87.643013039);
        assertEquals(expectedReport, actualReport);
    }

    @Test
    public void handlesBlankField() {
        CSVDataAccessor dataAccessor = CSVDataAccessor.getInstance();
        ArrayList<Report> data = dataAccessor.read(new File("src/blankFieldTestData.csv"));

        Report actualReport = data.get(0);
        Report expectedReport = new Crime("JE163990", CSVDataAccessor.getLocalDateTime("11/23/2020 03:05:00 PM"), "073XX S SOUTH SHORE DR", "820", "THEFT", "$500 AND UNDER", "APARTMENT", false, false, 334, 7, "6", null, null, null, null);
        assertEquals(expectedReport, actualReport);
    }

    @Test
    public void handlesBlankRow() {
        CSVDataAccessor dataAccessor = CSVDataAccessor.getInstance();
        ArrayList<Report> data = dataAccessor.read(new File("src/blankRowTestData.csv"));

        Report actualReport = data.get(0);
        Report expectedReport = new Crime(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
        assertEquals(expectedReport, actualReport);
    }
}