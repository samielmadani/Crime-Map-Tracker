package seng202.group7.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import seng202.group7.data.DataAccessor;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

/**
 * The controller, used by / linked to, the General Menu FXML file.
 * It links it buttons to the main menu screen and its controller.
 *
 * @author Jack McCorkindale
 * @author John Elliott
 */
public class GeneralMenuController {

    @FXML
    private Node frame;

    /**
     * Gets the current side panel and replace it with the filter menu panel.
     *
     * @throws IOException      An error that occurs when loading the FXML file.
     */
    public void toFilter() throws IOException {
        BorderPane pane = (BorderPane) frame.getParent();
        VBox menuItems = FXMLLoader.load(Objects.requireNonNull(MenuController.class.getResource("/gui/filterMenu.fxml")));
        // Changes side menu to the filter menu.
        pane.setLeft(menuItems);
    }

    /**
     * Changes the menu bar to show the comparison menu.
     *
     * @throws IOException An error that occurs when loading the FXML file.
     */
    public void toCompare() throws IOException{
        BorderPane pane = (BorderPane) frame.getParent();
        VBox menuItems = FXMLLoader.load(Objects.requireNonNull(MenuController.class.getResource("/gui/compareMenu.fxml")));
        // Changes side menu to the filter menu.
        pane.setLeft(menuItems);
    }

    /**
     * Gets a new file to be stored in the main database.
     * If no file is selected the table is reloaded with the current file.
     *
     * @param event             The event action that was triggered.
     * @throws IOException      An error that occurs when loading the FXML file.
     */
    public void newImport(ActionEvent event) throws IOException {

        ControllerData.getInstance().getFile(event);
        BorderPane rootPane = (BorderPane) frame.getParent();
        // Loads the paginator screen.
        BorderPane dataView = FXMLLoader.load(Objects.requireNonNull(MenuController.class.getResource("/gui/pages.fxml")));

        // Adds the data view to the center of the screen.
        rootPane.setCenter(dataView);
    }

    /**
     * Gets the conditions the user has active and the location to save a new file before sending it to the DataAccessor to export
     */
    public void exportWithFilter() {
        String conditions = ControllerData.getInstance().getWhereQuery();
        File saveLocation = getLocation();
        if (saveLocation == null) {
            return;
        }
        try {
            DataAccessor.getInstance().export(conditions, ControllerData.getInstance().getCurrentList(), saveLocation.toString());
        } catch (SQLException e) {
            ControllerData.getInstance().createError("Could not export data. Error:" + e);
        }
    }

    /**
     * Gets the location to save a new file before sending it to the DataAccessor to export
     */
    public void exportWithoutFilter() {
        File saveLocation = getLocation();
        if (saveLocation == null) {
            return;
        }
        try {
            DataAccessor.getInstance().export("", ControllerData.getInstance().getCurrentList(), saveLocation.toString());
        } catch (SQLException e) {
            ControllerData.getInstance().createError("Could not export data. Error:" + e);
        }
    }

    /**
     * Allows the user to select a location to save the database they are exporting.
     * @return The file to be created.
     */
    private File getLocation() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setInitialDirectory(new File("./"));
        FileChooser.ExtensionFilter dbFilter = new FileChooser.ExtensionFilter("Database (*.db)", "*.db");
        FileChooser.ExtensionFilter csvFilter = new FileChooser.ExtensionFilter("CSV (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().addAll(dbFilter, csvFilter);

        fileChooser.setTitle("Select save location");
        Stage stage = (Stage) frame.getScene().getWindow();

        // Launches the file chooser.
        File selectedFile = fileChooser.showSaveDialog(stage);
        // If the file chooser is exited before a file is selected it will be a NULL value and should not continue.
        if (selectedFile != null) {
            return selectedFile;
        }
        return null;
    }

    /**
     * Moves the program to an empty entry view to create a new crime object.
     * @throws IOException An error that occurs when loading the FXML file.
     */
    public void toNewEntry() throws IOException {
        BorderPane rootPane = (BorderPane) frame.getParent();

        ControllerData.getInstance().setCurrentRow(null);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/entryView.fxml"));

        Node newFrame = loader.load();

        ((EntryController) loader.getController()).setLastFrame(rootPane.getCenter());

        rootPane.setCenter(newFrame);
    }

    /**
     * Loads the search menu into the side menu view.
     *
     * @throws IOException      An error that occurs when loading the FXML file.
     */
    public void toSearch() throws IOException {
        BorderPane rootPane = (BorderPane) frame.getParent();
        VBox searchMenu = FXMLLoader.load(Objects.requireNonNull(MenuController.class.getResource("/gui/searchMenu.fxml")));
        rootPane.setLeft(searchMenu);
    }
}
