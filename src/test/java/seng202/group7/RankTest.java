package seng202.group7;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import seng202.group7.analyses.Filter;
import seng202.group7.analyses.Rank;
import seng202.group7.analyses.Tuple;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RankTest {
    private static CSVDataAccessor dataAccessor;
    private static ArrayList<Report> unfilteredData;
    private static Rank dataRank;
    private static String smallFile = "src/test/files/smallCrimeData.csv";
    private static String mediumFile = "src/test/files/crimeData.csv";

    /**
     * Sets up the tests by reading data and creating a filter to reduce the time taken
     * TODO Create tests that can be used with different files being used initialy, i.e mediumFile
     */
    @BeforeAll
    public static void setup() {
        dataAccessor = new CSVDataAccessor();
        unfilteredData = dataAccessor.read(smallFile);
        dataRank = new Rank();
    }

    /**
     * Checks that there are no matches for the coordinates (0,0) (1,1)
     */
    @Test
    public void frequencyRank_smallTest() {
        ArrayList<Tuple> list = dataRank.frequencyRank(unfilteredData);
        for(Tuple tup : list) {
            System.out.println(tup.x + ";" + tup.y);
        }
    }

}
