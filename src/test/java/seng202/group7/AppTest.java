package seng202.group7;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
    
    @Test
    public void powerTest() {
        assertEquals(9, App.power(3, 2));
    }

    @Test
    public void numberTest() {
        Assertions.assertTrue(App.numbers(1, 0));
        Assertions.assertFalse(App.numbers(1, 1));
        Assertions.assertFalse(App.numbers(0, 1));
    }

    @Test
    public void addTest() {
        Assertions.assertEquals(5, App.add(3, 2));
        Assertions.assertEquals(6, App.add(3, 3));
    }

}
