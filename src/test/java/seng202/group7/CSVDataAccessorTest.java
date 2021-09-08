package seng202.group7;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


import java.time.LocalDateTime;
import java.time.Month;
import java.io.File;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CSVDataAccessorTest {
    private static DataAccessor dataAccessor;
    private File smallFile = new File("src/test/files/smallCrimeData.csv");
    private File mediumFile = new File("src/test/files/crimeData.csv");
    private File commaFile = new File("src/test/files/commaInFieldTestData.csv");
    private File blankFieldFile = new File("src/test/files/blankFieldTestData.csv");
    private File blankRowFile = new File("src/test/files/blankRowTestData.csv");
    private File testFile = new File("src/test/files/testData.csv");
    
    private String testFileString = "src/test/files/testData.csv";
    /**
     * Creates a CSVDataAccessor for any test to use
     */
    @BeforeAll
    public static void init(){
        dataAccessor = CSVDataAccessor.getInstance();
    }

    /**
     * Reads the test file and checks that it creates an instance of a crime object
     */
    @Test

    public void read_crimeData() {
        ArrayList<Report> data = dataAccessor.read(smallFile);

        for(Report report : data){
            assertTrue(report instanceof Crime);
        }
    }

    /**
     * Checks that all 5000 rows of a file are processed correctly
     */
    @Test

    public void read_5000Rows() {
        ArrayList<Report> data = dataAccessor.read(mediumFile);
        assertEquals(5000, data.size(), String.format("Not all data was imported correctly." +
        " %d out of 5000 were imported.", data.size()));
        
    }


    /**
     * Checks that the file reader will process data values that contain a comma correctly
     */
    @Test
    public void read_fileWithCommas() {
        ArrayList<Report> data = dataAccessor.read(commaFile);
        Report actualReport = data.get(0);
        Report expectedReport = new Crime("JD389017", LocalDateTime.of(2020, Month.OCTOBER, 5, 3, 31), "006XX W HARRISON ST", "0454", "BATTERY", "AGGRAVATED P.O. - HANDS, FISTS, FEET, NO / MINOR INJURY", "OTHER COMMERCIAL TRANSPORTATION", true, false, 124, 25, "08B", 1172257, 1897564, 41.874363279, -87.643013039);
        assertEquals(expectedReport, actualReport);
    }

    @Test
    public void testDataFromFile() {
        ArrayList<Report> data = dataAccessor.read(smallFile);
        String gotCaseNumber = ((Crime) data.get(0)).getBlock();
        assertEquals("073XX S SOUTH SHORE DR", gotCaseNumber, "Data does not have the right value for case Number");
    }

    /**
     * Checks that the file reader responds to a file with a blank entry correctly
     */
    @Test
    public void read_fileWithBlankFields() {
        ArrayList<Report> data = dataAccessor.read(blankFieldFile);
        System.out.println(data.size());

        Report actualReport = data.get(0);
        Report expectedReport = new Crime("JE163990", LocalDateTime.of(2020, Month.NOVEMBER, 23, 15, 5), "073XX S SOUTH SHORE DR", "820", "THEFT", "$500 AND UNDER", "APARTMENT", false, false, 334, 7, "6", null, null, null, null);
        assertEquals(expectedReport, actualReport);
    }

    /**
     * Checks that the file reader responds to a file with a row of null values correctly
     */
    @Test
    public void read_fileWithMissingRow() {
        ArrayList<Report> data = dataAccessor.read(blankRowFile);
        assertTrue(data.size() == 1);
    }

    /**
     * Tests that data can be written to a file before being re-read to create the same dataset
     */
    @Test
    public void write_mediumFile() {
        ArrayList<Report> data1 = dataAccessor.read(mediumFile);
        dataAccessor.write(data1, testFileString);
        ArrayList<Report> data2 = dataAccessor.read(testFile);

        System.out.println("test");
        assertTrue(data1.equals(data2), "Data is not the same after being read and written to file.");
    }
}