package seng202.group7;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import seng202.group7.analyses.Rank;
import seng202.group7.analyses.Tuple;
import seng202.group7.data.DataAccessor;
import seng202.group7.data.Report;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RankTest {
    private static DataAccessor dataAccessor;
    private static ArrayList<Report> unfilteredData;
    private static final File smallFile = new File("src/test/files/smallCrimeData.csv");
    private static final File mediumFile = new File("src/test/files/crimeData.csv");
    private static final File commaFile = new File("src/test/files/commaInFieldTestData.csv");
    private static final File blankFieldFile = new File("src/test/files/blankFieldTestData.csv");
    private static final File blankRowFile = new File("src/test/files/blankRowTestData.csv");
    private static final File testFile = new File("src/test/files/testData.csv");


    /**
     * TODO Create tests to ensure the correct values are returned, test with larger files to ensure rank is done in a timely manner
     *//*

    @BeforeAll
    public static void setup() {
        DataAccessor dataAccessor = DataAccessor.getInstance();
        // Ensures the reports are the same.
        dataAccessor.readToDB(smallFile);
    }

    /**
     * Tests to see the correct crime type(theft) is returned from frequency ranker
     *//*
    @Test
    public void primaryFrequencyRank_mediumTest() {
        ArrayList<Tuple> list = Rank.primaryFrequencyRank(unfilteredData);
        assertEquals("THEFT", list.get(0).x);
    }

    /**
     * Tests to see the correct crime type (theft) is returned when specifying a ward in frequency ranker
     *//*
    @Test
    public void primaryFrequencyRank_ward1_mediumTest() {
        ArrayList<Tuple> list = Rank.primaryFrequencyRank(unfilteredData, 1);
        assertEquals("THEFT", list.get(0).x);
    }

    /**
     * Tests to see the correct ward (28) is returned as it has the highest amount of crimes in the data set
     *//*
    @Test
    public void wardFrequencyRank_mediumTest() {
        ArrayList<Tuple> list = Rank.wardFrequencyRank(unfilteredData);
        assertEquals(28, list.get(0).x);
    }

    /**
     * Tests to see the correct ward (20) is returned as it has the highest amount of Sex offenses in the data set
     *//*
    @Test
    public void wardFrequencyRank_sexOffense_mediumTest() {
        ArrayList<Tuple> list = Rank.wardFrequencyRank(unfilteredData, "SEX OFFENSE");
        assertEquals(20, list.get(0).x);
    }

    /**
     * Tests to see the correct street is returned ("S DR MARTIN LUTHER KING JR DR") is returned as it has the
     * highest crime rate in the data set
     *//*
    @Test
    public void streetRank_mediumTest() {
        ArrayList<Tuple> list = Rank.streetRank(unfilteredData);
        assertEquals("S DR MARTIN LUTHER KING JR DR", list.get(0).x);
    }
    */
}
