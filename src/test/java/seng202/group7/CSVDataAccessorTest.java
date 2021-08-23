package seng202.group7;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class CSVDataAccessorTest {
    private String smallFile = "src/test/files/smallCrimeData.csv";
    private String mediumFile = "src/test/files/crimeData.csv";
    private String testFile = "src/test/files/testData.csv";
    private String commaFile = "src/test/files/commaInFieldTestData.csv";
    private String blankFieldFile = "src/test/files/blankFieldTestData.csv";
    private String blankRowFile = "src/test/files/blankRowTestData.csv";

    //TODO: create test naming convention

    @Test
    public void isCrimeInstance() {
        CSVDataAccessor dataAccessor = new CSVDataAccessor();
        ArrayList<Report> data = dataAccessor.read(mediumFile);

        for(Report report : data){
            assertTrue(report instanceof Crime);
        }
    }

    @Test
    public void loadsAllRows() {
        CSVDataAccessor dataAccessor = new CSVDataAccessor();
        ArrayList<Report> data = dataAccessor.read(mediumFile);

        assertEquals(5000, data.size(), String.format("Not all data was imported correctly." +
        " %d out of 5000 were imported.", data.size()));
        
    }

    @Test
    public void handlesCommaInField() {
        CSVDataAccessor dataAccessor = new CSVDataAccessor();
        ArrayList<Report> data = dataAccessor.read(commaFile);

        Report actualReport = data.get(0);
        Report expectedReport = new Crime("JD389017", CSVDataAccessor.getLocalDateTime("10/05/2020 03:31:00 AM"), "006XX W HARRISON ST", "0454", "BATTERY", "AGGRAVATED P.O. - HANDS, FISTS, FEET, NO / MINOR INJURY", "OTHER COMMERCIAL TRANSPORTATION", true, false, 124, 25, "08B", 1172257, 1897564, 41.874363279, -87.643013039);
        assertEquals(expectedReport, actualReport);
    }

    @Test
    public void handlesBlankField() {
        CSVDataAccessor dataAccessor = new CSVDataAccessor();
        ArrayList<Report> data = dataAccessor.read(blankFieldFile);
        System.out.println(data.size());

        Report actualReport = data.get(0);
        Report expectedReport = new Crime("JE163990", CSVDataAccessor.getLocalDateTime("11/23/2020 03:05:00 PM"), "073XX S SOUTH SHORE DR", "820", "THEFT", "$500 AND UNDER", "APARTMENT", false, false, 334, 7, "6", null, null, null, null);
        assertEquals(expectedReport, actualReport);
    }

    @Test
    public void handlesBlankRow() {
        CSVDataAccessor dataAccessor = new CSVDataAccessor();
        ArrayList<Report> data = dataAccessor.read(blankRowFile);

        Report actualReport = data.get(0);
        Report expectedReport = new Crime(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
        assertEquals(expectedReport, actualReport);
    }

    @Test
    public void write_smallFile() {
        CSVDataAccessor dataAccessor = new CSVDataAccessor();
        ArrayList<Report> data1 = dataAccessor.read(mediumFile);
        dataAccessor.write(data1, testFile);
        ArrayList<Report> data2 = dataAccessor.read(testFile);

        for (int i = 0; i < data1.size(); i++) {
            if (!data1.get(i).equals(data2.get(i))) {
                System.out.println(i);
            }
        }
    }
}