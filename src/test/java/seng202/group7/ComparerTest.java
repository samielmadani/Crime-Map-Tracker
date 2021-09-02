package seng202.group7;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import seng202.group7.analyses.*;

public class ComparerTest {
    private static CSVDataAccessor dataAccessor;
    private static ArrayList<Report> data;
    public static Comparer comparer;
    private static String smallFile = "src/test/files/smallCrimeData.csv";

    @BeforeAll
    public static void setup() {
        dataAccessor = new CSVDataAccessor();
        data = dataAccessor.read(smallFile);
    }

    @Test
    public void timeDifferenceTest() {
        comparer.timeDifference(data.get(0), data.get(1));
    }

    @Test
    public void locationDifference() {
        System.out.println(comparer.locationDifference(data.get(1), data.get(2)));
    }
}
