package seng202.group7.controllers;


import seng202.group7.data.Crime;
import seng202.group7.data.DataAccessor;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javafx.collections.ObservableSet;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * This class acts as a connection between the controllers passing data through here so that only the controller's
 * initialization it can get the relevant data it needs. It accomplishes this by being a singleton class so only one
 * instance of it can exist.
 *
 * @author John Elliott
 * @author Jack McCorkindale
 */
public final class ControllerData {
    /**
     * This creates a singleton instants of the class.
     */
    private final static ControllerData INSTANCE = new ControllerData();

    /**
     * This holds the current row selected when going to the edit menu screen.
     */
    private Crime currentRow;

    /**
     * Stores the current table state so when the back button is pushed the position is the same.
     */
    private Node tableState;

    /**
     * Stores the current page of the paginator.
     */
    private int currentPage = 0;

    /**
     * This is a condition that is used by the data accessor when searching the database.
     */
    private String whereQuery = "";

    private PseudoClass errorClass = PseudoClass.getPseudoClass("error");
    private PseudoClass required = PseudoClass.getPseudoClass("required");
    private PseudoClass doubleFormat = PseudoClass.getPseudoClass("double");
    private PseudoClass integerFormat = PseudoClass.getPseudoClass("integer");
    private PseudoClass uniqueId = PseudoClass.getPseudoClass("id");
    private PseudoClass dateEditor = PseudoClass.getPseudoClass("dateEditor");
    private PseudoClass timeFormat = PseudoClass.getPseudoClass("time");


    /**
     * The constructor which is made private so that it can not be initialized from other classes.
     */
    private ControllerData() {}

    /**
     * Used to get the singleton instants of the class when assessing. This is done over a "static" class due to it
     * implementing an interface.
     *
     * @return INSTANCE     The only instants of this class.
     */
    public static ControllerData getInstance() {
        return INSTANCE;
    }

    /**
     * Setter for storing a crime object assigned to a selected row.
     *
     * @param rowData       The selected crime object.
     */
    public void setCurrentRow(Crime rowData) {
        currentRow = rowData;
    }

    /**
     * Getter for retrieving a crime object assigned to a selected row.
     *
     * @return currentRow       The stored selected crime object.
     */
    public Crime getCurrentRow() {
        return currentRow;
    }

    /**
     * Setter for storing the current state of the table.
     *
     * @param tableView       The table state.
     */
    public void setTableState(Node tableView) {
        tableState = tableView;
    }

    /**
     * Getter for retrieving the current state of the table.
     *
     * @return tableState       The current state of the table.
     */
    public Node getTableState() {
        return tableState;
    }


    /**
     * Makes a screen to get a file from a user using the FilerChooser class.
     * Then it sends this files data to the data access layer and returns the data that need to be stored.
     *
     * @param event     The event action that was triggered.
     */
    public boolean getFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("src/test/files"));
        fileChooser.setTitle("Select file");
        fileChooser.getExtensionFilters().add(new ExtensionFilter(".csv, .db files", "*.csv", "*.db"));

        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        // Launches the file chooser.
        File selectedFile = fileChooser.showOpenDialog(stage);
        // If the file chooser is exited before a file is selected it will be a NULL value and should not continue.
        if (selectedFile != null) {
            String fileName = selectedFile.getName();
            DataAccessor accessor = DataAccessor.getInstance();
            if (fileName.endsWith(".csv")) {
                // Reads a CSV into the database.
                accessor.readToDB(selectedFile);
            } else {
                // Reads a outside database into the main database.
                accessor.importInDB(selectedFile);
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gets the current page of the paginator.
     *
     * @return Page     The current page.
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * Sets the current page of the paginator.
     *
     * @param currentPage       The current page.
     */
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * The getter for the where query.
     *
     * @return whereQuery       The condition being applied.
     */
    public String getWhereQuery() {
        return whereQuery;
    }

    /**
     * The setter for the where query.
     *
     * @param searchingQuery    The condition being applied.
     */
    public void setWhereQuery(String searchingQuery) {
        this.whereQuery = searchingQuery;
    }

    /**
     * Checks if the input has a value and adds the error class if it is invalid.
     * @return If the field has an entry
     */
    private boolean validateRequired(String input) {
        boolean valid;
        valid = !(input.isEmpty());
        return valid;
    }

    /**
     * Validates the value in each box. The validation is currently limited to Integer, Double, Date, and Time. <p>
     * For an input box to be validated against a condition it must be added to the ArrayList during initialization.
     * @param input             The input to be validated
     * @param classes           The pseudo classes.
     * @return                  If the input is valid
     */
    private boolean validateText(String input, ObservableSet<PseudoClass> classes) {
        boolean valid = true;
        if (classes.contains(integerFormat)) {
            try {
                Integer.parseInt(input);
            } catch (NumberFormatException e) {
                valid = false;
            }
        }
        if (classes.contains(doubleFormat)) {
            try {
                Double.parseDouble(input);
            } catch (NumberFormatException e) {
                valid = false;
            }
        }
        if (classes.contains(timeFormat)) {
            try {
                DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("H:mm");
                LocalTime.parse(input, dateTimeFormat);
            } catch (DateTimeParseException e) {
                valid = false;
            }
        }
        if (classes.contains(uniqueId) && DataAccessor.getInstance().getCrime(input) != null) {
            valid = false;
        }
        return valid;
    }

    /**
     * Passes the value through the required validation methods
     * @param inputBox The input to be validated
     * @return If the input is valid
     */
    public boolean validate(Node inputBox) {
        boolean valid = true;
        String input = null;
        if (inputBox.getClass() == TextField.class) {
            input = ((TextField) inputBox).getText();
        } else if (inputBox.getClass() == TextArea.class) {
            input = ((TextArea) inputBox).getText();
        } else if (inputBox.getClass() == DatePicker.class) {
            input = ((DatePicker) inputBox).getEditor().getText();
        }
        if (inputBox.getPseudoClassStates().contains(dateEditor)) {
            input = ((TextField) inputBox).getText();
            try {
                DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("d/M/yyyy");
                LocalDate.parse(input, dateTimeFormat);
            } catch (DateTimeParseException e) {
                if (!input.equals("")) {
                    valid = false;
                }
            }
        }
        if (inputBox.getPseudoClassStates().contains(required)) {
            valid &= validateRequired(input);
        }
        if (valid && !"".equals(input)) {
            valid &= validateText(input, inputBox.getPseudoClassStates());
        }
        if (valid && inputBox.getPseudoClassStates().contains(uniqueId)) {
            valid &= validateText(input, inputBox.getPseudoClassStates());
        }
        inputBox.pseudoClassStateChanged(errorClass, !valid);
        return valid;
    }
}
