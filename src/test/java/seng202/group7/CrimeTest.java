package seng202.group7;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CrimeTest
{
    private Crime crime;

    /**
     * Creates a new crime object for each test case to use
     */
    @BeforeEach
    public void init() {
        crime = new Crime();
    }

    /**
     * Tests the creation of a crime object
     */
    @Test
    public void init_crimeTest() {
        crime = new Crime(null, null, null, null, null, null, null, true, false, 0, 0, null, 0, 0, null, null);
        assertTrue(crime.getArrest());
    }
}