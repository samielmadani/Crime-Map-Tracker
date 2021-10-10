package seng202.group7.controllers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.fxml.FXMLLoader;
import seng202.group7.analyses.Comparer;
import seng202.group7.controllers.data.ControllerData;
import seng202.group7.data.Crime;
import seng202.group7.data.Report;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ControllerDataTest {

    @BeforeAll
    public static void makeReports() {
        ControllerData controllerData = ControllerData.getInstance();
    }

    /**
     * Tests timeDifference with a specific case to check if returns the correct value for minutes.
     */
    @Test
    public void timeDifferenceTest_specificCase() {
        
    }
    
}
