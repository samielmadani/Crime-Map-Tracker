package seng202.group7;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import seng202.group7.analyses.Rank;
import seng202.group7.analyses.Tuple;
import seng202.group7.data.Report;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RankTest {

    private static ArrayList<Report> data;

    @BeforeAll
    public static void setup() {
        data = CreateTestList.CreateList();
    }

    /**
     * Tests to see the correct crime type(theft) is returned from frequency ranker
     */
    @Test
    public void primaryFrequencyRank() {
        ArrayList<Tuple<String, Integer>> list = Rank.primaryFrequencyRank(data);
        assertEquals("THEFT", list.get(0).x);
    }

    /**
     * Tests to see the correct crime type (theft) is returned when specifying a ward in frequency ranker
     */
    @Test
    public void primaryFrequencyRank_ward1() {
        ArrayList<Tuple<String, Integer>> list = Rank.primaryFrequencyRank(data, 1);
        assertEquals("THEFT", list.get(0).x);
    }

    /**
     * Tests to see the correct ward (1) is returned as it has the highest amount of crimes in the data set
     */
    @Test
    public void wardFrequencyRank() {
        ArrayList<Tuple<String, Integer>> list = Rank.wardFrequencyRank(data);
        assertEquals("1", list.get(0).x);
    }

    /**
     * Tests to see the correct ward (20) is returned as it has the highest amount of Sex offenses in the data set
     */
    @Test
    public void wardFrequencyRank_sexOffense() {
        ArrayList<Tuple<String, Integer>> list = Rank.wardFrequencyRank(data, "SEX OFFENSE");
        assertEquals("20", list.get(0).x);
    }

    /**
     * Tests to see the correct street is returned ("S DR MARTIN LUTHER KING JR DR") is returned as it has the
     * highest crime rate in the data set
     */
    @Test
    public void streetRankt() {
        ArrayList<Tuple<String, Integer>> list = Rank.streetRank(data);
        assertEquals("S DR MARTIN LUTHER KING JR DR", list.get(0).x);
    }
}
