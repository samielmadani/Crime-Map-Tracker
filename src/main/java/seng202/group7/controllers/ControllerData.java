package seng202.group7.controllers;


import javafx.scene.control.Pagination;
import seng202.group7.data.Crime;
import seng202.group7.data.Report;
import seng202.group7.data.DataAccessor;

import java.io.File;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * This class acts as a connection between the controllers passing data through here so that only the controller's
 * initialization it can get the relevant data it needs. It accomplishes this by being a singleton class so only one
 * instance of it can exist.
 *
 * @author John Elliott
 */
public final class ControllerData {
    /**
     * This creates a singleton instants of the class.
     */
    private final static ControllerData INSTANCE = new ControllerData();

    private Crime currentRow;

    /**
     * Stores the current table state so when the back button is pushed the position is the same.
     */
    private Pagination tableState;

    /**
     * Stores the current page of the paginator.
     */
    private int currentPage = 0;


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
    public void setTableState(Pagination tableView) {
        tableState = tableView;
    }

    /**
     * Getter for retrieving the current state of the table.
     *
     * @return tableState       The current state of the table.
     */
    public Pagination getTableState() {
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
            ArrayList<Report> reports;
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
}
