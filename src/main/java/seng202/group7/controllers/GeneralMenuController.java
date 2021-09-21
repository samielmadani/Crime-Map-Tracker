package seng202.group7.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.util.Objects;

/**
 * The controller, used by / linked to, the General Menu FXML file.
 * It links it buttons to the main menu screen and its controller.
 *
 * @author John Elliott
 */
public class GeneralMenuController {

    @FXML
    private Node frame;

    /**
     * Gets the current side panel and replace it with the filter menu panel.
     *
     * @param event             The event action that was triggered.
     * @throws IOException      An error that occurs when loading the FXML file.
     */
    public void toFilter(ActionEvent event) throws IOException {
        // As the side panels root is the main border panel we use .getRoot().
        BorderPane pane = (BorderPane) (((Node) event.getSource()).getScene()).getRoot();
        VBox menuItems = FXMLLoader.load(Objects.requireNonNull(MenuController.class.getResource("/gui/filterMenu.fxml")));
        // Changes side menu to the filter menu.
        pane.setLeft(menuItems);
    }

    /**
     * Gets a new file to be stored in the data view table.
     * If no file is selected the table is reloaded with the current file.
     *
     * @param event             The event action that was triggered.
     * @throws IOException      An error that occurs when loading the FXML file.
     */
    public void newImport(ActionEvent event) throws IOException {

        ControllerData.getInstance().getFile(event);
        BorderPane rootPane = (BorderPane) (((Node) event.getSource()).getScene()).getRoot();
        // Loads the raw data viewer screen.
        GridPane dataView = FXMLLoader.load(Objects.requireNonNull(MenuController.class.getResource("/gui/dataView.fxml")));

        // Adds the data view to the center of the screen.
        rootPane.setCenter(dataView);
    }

    /**
     * Moves the program to an empty entry view to create a new crime object.
     * @param event The event action that was triggered.
     * @throws IOException An error that occurs when loading the FXML file.
     */
    public void toNewEntry(ActionEvent event) throws IOException {
        BorderPane rootPane = (BorderPane) frame.getParent();

        ControllerData.getInstance().setCurrentRow(null);
        ControllerData.getInstance().setTableState((GridPane) rootPane.getCenter());
        Node newFrame;
        newFrame = FXMLLoader.load(Objects.requireNonNull(MenuController.class.getResource("/gui/entryView.fxml")));

        rootPane.setCenter(newFrame);
    }
}
