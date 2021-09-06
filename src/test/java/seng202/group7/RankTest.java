package seng202.group7;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import seng202.group7.analyses.Filter;
import seng202.group7.analyses.Rank;
import seng202.group7.analyses.Tuple;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RankTest {
    private static CSVDataAccessor dataAccessor;
    private static ArrayList<Report> unfilteredData;
    private static String smallFile = "src/test/files/smallCrimeData.csv";
    private static String mediumFile = "src/test/files/crimeData.csv";

    /**
     *
     * TODO Create tests to ensure the correct values are returned, test with larger files to ensure rank is done in a timely manner
     */
    @BeforeAll
    public static void setup() {
        dataAccessor = new CSVDataAccessor();
        unfilteredData = dataAccessor.read(smallFile);
    }

    /**
     * Prints out an ordered list of primary description frequency in data
     */
    @Test
    public void primaryFrequencyRank_smallTest() {
        ArrayList<Tuple> list = Rank.primaryFrequencyRank(unfilteredData);
        for(Tuple tup : list) {
            System.out.println(tup.x + ";" + tup.y);
        }
    }

    /**
     * Prints out an order list of block frequency in data
     */
    @Test
    public void blockFrequencyRank_smallTest() {
        ArrayList<Tuple> list = Rank.blockFrequencyRank(unfilteredData);
        for(Tuple tup: list) {
            System.out.println(tup.x + ";" + tup.y);
        }
    }

}
