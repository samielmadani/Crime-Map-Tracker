package seng202.group7;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import seng202.group7.analyses.*;

public class ComparerTest {
    //TODO Create tests to ensure the correct values are returned, speed test methods with large data files

    private static CSVDataAccessor dataAccessor;
    private static ArrayList<Report> data;
    public static Comparer comparer;
    private static String smallFile = "src/test/files/smallCrimeData.csv";




    @BeforeAll
    public static void setup() {
        dataAccessor = new CSVDataAccessor();
        data = dataAccessor.read(smallFile);
    }

    /**
     * Tests timeDifference with a specific case to check if returns the correct value
     */
    @Test
    public void timeDifferenceTest_specificCase() {
        ArrayList<Long> list = comparer.timeDifference(data.get(0), data.get(1));
        assertEquals(25, list.get(0)); //Minutes
        assertEquals(18, list.get(1)); //Hours
        assertEquals(202, list.get(2)); // Days
        assertEquals(0, list.get(3)); //Years
    }

    /**
     * Test timeDifference function with the same time value to ensure it returns 0
     */
    @Test
    public void timeDifferenceTest_boundaryCase() {
        ArrayList<Long> list = comparer.timeDifference(data.get(0), data.get(0));
        assertEquals(0, list.get(0));
        assertEquals(0, list.get(1));
        assertEquals(0, list.get(2));
        assertEquals(0, list.get(3));
    }

    /**
     * Tests locationDifference with a specific case to check if it returns the correct value
     */
    @Test
    public void locationDifference_specificCase() {
        Double difference  = comparer.locationDifference(data.get(1), data.get(2));
        assertEquals(true, difference >= 18.4 || difference <= 18.6);
    }

    /**
     * Test the locationDifference with the same value to ensure it returns 0
     */
    @Test
    public void locationDifference_boundaryCase() {
        Double difference = comparer.locationDifference(data.get(1), data.get(1));
        assertEquals(0, difference);
    }
}
