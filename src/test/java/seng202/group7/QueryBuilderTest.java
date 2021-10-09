package seng202.group7;

import org.junit.jupiter.api.Test;

import seng202.group7.data.FilterConditions;
import seng202.group7.data.QueryBuilder;

import java.sql.Timestamp;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests to ensure that where query is correctly generated when called.
 *
 * @author John Elliott
 */
public class QueryBuilderTest {

    /**
     * Tests the creation of a where query using the query builder.
     */
    @Test
    public void whereQueryTest() {
        LocalDate testDate = LocalDate.now();
        FilterConditions conditions = new FilterConditions(testDate, testDate, "test", null, 30, null, true, null);
        String query = QueryBuilder.where(conditions);
        assertEquals("date >= "+ Timestamp.valueOf(testDate.atStartOfDay()).getTime() +
                " AND date < "+ Timestamp.valueOf(testDate.plusDays(1).atStartOfDay()).getTime() +
                " AND primary_description='test' AND ward=30 AND arrest=1", query);
    }

    /**
     * Tests that the WHERE query returns an empty string if the inputs are all null.
     */
    @Test
    public void whereQueryEmptyTest() {
        FilterConditions conditions = new FilterConditions(null, null, null, null, null, null, null, null);
        String query = QueryBuilder.where(conditions);
        assertEquals("", query);
    }
}
