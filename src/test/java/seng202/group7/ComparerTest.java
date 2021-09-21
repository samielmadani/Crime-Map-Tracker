package seng202.group7;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import seng202.group7.analyses.*;
import seng202.group7.data.DataAccessor;
import seng202.group7.data.Report;

public class ComparerTest {
    //TODO Create tests to ensure the correct values are returned, speed test methods with large data files

    private static ArrayList<Report> data;
    private static final File smallFile = new File("src/test/files/smallCrimeData.csv");
    private static final File mediumFile = new File("src/test/files/crimeData.csv");
    private static final File commaFile = new File("src/test/files/commaInFieldTestData.csv");
    private static final File blankFieldFile = new File("src/test/files/blankFieldTestData.csv");
    private static final File blankRowFile = new File("src/test/files/blankRowTestData.csv");
    private static final File testFile = new File("src/test/files/testData.csv");

    /*

    @BeforeAll
    public static void setup() {
        DataAccessor dataAccessor = DataAccessor.getInstance();
        // Ensures the reports are the same.
        dataAccessor.readToDB(smallFile);
        data = dataAccessor.getPageSet(0);
    }

    public static void setData(ArrayList<Report> data) {
        ComparerTest.data = data;
    }

    /**
     * Tests timeDifference with a specific case to check if returns the correct value
     */
    /*
    @Test
    public void timeDifferenceTest_specificCase() {
        ArrayList<Long> list = Comparer.timeDifference(data.get(0), data.get(1));
        assertEquals(-40, list.get(0)); //Minutes
        assertEquals(-1, list.get(1)); //Hours
        assertEquals(0, list.get(2)); // Days
        assertEquals(0, list.get(3)); //Years
    }

    /**
     * Test timeDifference function with the same time value to ensure it returns 0
     *//*
    @Test
    public void timeDifferenceTest_boundaryCase() {
        ArrayList<Long> list = Comparer.timeDifference(data.get(0), data.get(0));
        assertEquals(0, list.get(0));
        assertEquals(0, list.get(1));
        assertEquals(0, list.get(2));
        assertEquals(0, list.get(3));
    }

    /**
     * Tests locationDifference with a specific case to check if it returns the correct value
     *//*
    @Test
    public void locationDifference_specificCase() {
        double difference  = Comparer.locationDifference(data.get(1), data.get(2));
        assertTrue(difference >= 18.4 || difference <= 18.6);
    }

    /**
     * Test the locationDifference with the same value to ensure it returns 0
     *//*
    @Test
    public void locationDifference_boundaryCase() {
        Double difference = Comparer.locationDifference(data.get(1), data.get(1));
        assertEquals(0, difference);
    }
    */
}
