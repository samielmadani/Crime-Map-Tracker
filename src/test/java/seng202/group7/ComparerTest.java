package seng202.group7;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import seng202.group7.analyses.*;
import seng202.group7.data.Crime;


public class ComparerTest {

    private static Crime reportOne;
    private static Crime reportTwo;

    @BeforeAll
    public static void init() {
        LocalDateTime date1 = LocalDateTime.of(2020, 03, 28, 14, 28);
        LocalDateTime date2 = LocalDateTime.of(2020, 02, 20, 19, 33);
        reportOne = new Crime("1",  date1, "NA", "NA", "THEFT", "NA", null,  false, false, 1, 1, "NA", 1183633, 1851786, 41.748486365, -87.602675062);
        reportTwo = new Crime("2",  date2, "NA", "NA", "THEFT", "NA", null,  false, false, 1, 1, "NA", 1148227, 1899678, 41.880660786, -87.731186405);
    }


    /**
     * Tests timeDifference with a specific case to check if returns the correct value
     */

    @Test
    public void timeDifferenceTest_specificCase() {
        ArrayList<Long> list = Comparer.timeDifference(reportOne, reportTwo);
        assertEquals(55, list.get(0)); //Minutes
        assertEquals(18, list.get(1));//Hours
        assertEquals(36, list.get(2)); // Days
        assertEquals(0, list.get(3));//Years
    }

    /**
     * Test timeDifference function with the same time value to ensure it returns 0
     */
    @Test
    public void timeDifferenceTest_boundaryCase() {
        ArrayList<Long> list = Comparer.timeDifference(reportOne, reportOne);
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
        double difference  = Comparer.locationDifference(reportOne, reportTwo);
        assertTrue(difference >= 17.9 || difference <= 18.1);
    }

    /**
     * Test the locationDifference with the same value to ensure it returns 0
     */

    @Test
    public void locationDifference_boundaryCase() {
        Double difference = Comparer.locationDifference(reportOne, reportOne);
        assertEquals(0, difference);
    }
}
