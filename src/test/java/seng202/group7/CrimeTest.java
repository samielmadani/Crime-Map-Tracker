package seng202.group7;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CrimeTest
{
    private Crime crime;

    @BeforeEach
    public void init() {
        crime = new Crime();
    }

    @Test
    public void init_crimeTest() {
        crime = new Crime(null, null, null, null, null, null, null, true, false, 0, 0, null, 0, 0, null, null);
        assertTrue(crime.getArrest());
    }
}