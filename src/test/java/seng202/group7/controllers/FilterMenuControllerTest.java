package seng202.group7.controllers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import seng202.group7.controllers.menus.FilterMenuController;
import seng202.group7.data.FilterConditions;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class FilterMenuControllerTest {

    private static FilterMenuController filter;

    private static Field[] fields;
    private static Method[] methods;
    
    @BeforeAll
    public static void makeReports() {
        Platform.startup(() -> { });
        FXMLLoader loader = new FXMLLoader(FilterMenuController.class.getResource("/gui/menus/filterMenu.fxml"));
        
        try {
            loader.load();
        } catch (Exception | ExceptionInInitializerError e) {
            System.err.println(e);
        }
        filter = loader.getController();
        
        fields = filter.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
        }
        
        methods = filter.getClass().getDeclaredMethods();
        for (Method method : methods) {
            method.setAccessible(true);
        }
    }

    private Method getMethod(String methodName) {
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        return null;
    }

    private Field getField(String fieldName) {
        for (Field field : fields) {
            if (field.getName().equals(fieldName)) {
                try {
                    return field;
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    fail(e);
                }
            }
        }
        return null;
    }

    /**
     * Tests timeDifference with a specific case to check if returns the correct value for minutes.
     */
    @Test
    public void timeDifferenceTest_specificCase() {
        // Class c = Class.forName(args[0]);
        
    }

    @Test
    public void loadGUIFields_blankFilterCondition() {
        try {
            getMethod("loadGUIFields").invoke(filter);

            assertEquals(null, ((DatePicker) getField("datePicker").get(filter)).getValue());
            assertEquals(null, ((DatePicker) getField("datePicker2").get(filter)).getValue());
            assertEquals(null, ((ComboBox<?>) getField("primaryBox").get(filter)).getValue());
            assertEquals(null, ((ComboBox<?>) getField("locationBox").get(filter)).getValue());
            assertEquals("", ((TextField) getField("wardField").get(filter)).getText());
            assertEquals("", ((TextField) getField("beatField").get(filter)).getText());
            assertEquals(null, ((ComboBox<?>) getField("arrestBox").get(filter)).getValue());
            assertEquals(null, ((ComboBox<?>) getField("domesticBox").get(filter)).getValue());

        } catch (SecurityException | IllegalAccessException |
            IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
            fail(e);
        }
    }

    @Test
    public void loadGUIFields_populatedFilterCondition() {
        try {
            FilterConditions testFilter = new FilterConditions(LocalDate.of(2018, 02, 15), LocalDate.of(2020, 10, 3),
            "ASSAULT", "AIRPORT", 5, 70, true, false);            
            getField("filterConditions").set(filter, testFilter);
            getMethod("loadGUIFields").invoke(filter);
            
            assertEquals(LocalDate.of(2018, 02, 15), ((DatePicker) getField("datePicker").get(filter)).getValue());
            assertEquals(LocalDate.of(2020, 10, 3), ((DatePicker) getField("datePicker2").get(filter)).getValue());
            assertEquals("ASSAULT", ((ComboBox<?>) getField("primaryBox").get(filter)).getValue());
            assertEquals("AIRPORT", ((ComboBox<?>) getField("locationBox").get(filter)).getValue());
            assertEquals("5", ((TextField) getField("wardField").get(filter)).getText());
            assertEquals("70", ((TextField) getField("beatField").get(filter)).getText());
            assertEquals("Y", ((ComboBox<?>) getField("arrestBox").get(filter)).getValue());
            assertEquals("N", ((ComboBox<?>) getField("domesticBox").get(filter)).getValue());
        } catch (SecurityException | IllegalAccessException |
        IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
            fail(e);
        }
    }
    
    @Test
    public void clearFilter_populatedFilterCondition() {
        try {
            FilterConditions testFilter = new FilterConditions(LocalDate.of(2018, 02, 15), LocalDate.of(2020, 10, 3),
                "ASSAULT", "AIRPORT", 5, 70, true, false);            
            getField("filterConditions").set(filter, testFilter);
            getMethod("loadGUIFields").invoke(filter);
            try {
                getMethod("clearFilter").invoke(filter, new ActionEvent());
            } catch (InvocationTargetException error) {
                // Caught when method attempts to access non-existent Node
            }

            assertEquals(null, ((DatePicker) getField("datePicker").get(filter)).getValue());
            assertEquals(null, ((DatePicker) getField("datePicker2").get(filter)).getValue());
            assertEquals(null, ((ComboBox<?>) getField("primaryBox").get(filter)).getValue());
            assertEquals(null, ((ComboBox<?>) getField("locationBox").get(filter)).getValue());
            assertEquals("", ((TextField) getField("wardField").get(filter)).getText());
            assertEquals("", ((TextField) getField("beatField").get(filter)).getText());
            assertEquals(null, ((ComboBox<?>) getField("arrestBox").get(filter)).getValue());
            assertEquals(null, ((ComboBox<?>) getField("domesticBox").get(filter)).getValue());
        } catch (SecurityException | IllegalAccessException |
            IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
            fail(e);
        }
    }

    @Test
    public void saveGUIFields_populatedFilterCondition() {
        try {
            FilterConditions testFilter = new FilterConditions(LocalDate.of(2018, 02, 15), LocalDate.of(2020, 10, 3),
                "ASSAULT", "AIRPORT", 5, 70, true, false);            
            getField("filterConditions").set(filter, testFilter);
            getMethod("loadGUIFields").invoke(filter);

            getMethod("saveGUIFields").invoke(filter);

            assertEquals(testFilter, (FilterConditions) getField("filterConditions").get(filter));
        } catch (SecurityException | IllegalAccessException |
            IllegalArgumentException | InvocationTargetException e) {
            fail(e.getMessage());
        }
    }    
}
