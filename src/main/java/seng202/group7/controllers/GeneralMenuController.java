package seng202.group7.controllers;

import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import java.io.IOException;
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
     * Changes the menu bar to show the distance comparison menu.
     *
     * @throws IOException An error that occurs when loading the FXML file.
     */
    public void toDistance() throws IOException{
        BorderPane pane = (BorderPane) frame.getParent();
        
        VBox menuItems = FXMLLoader.load(Objects.requireNonNull(MenuController.class.getResource("/gui/compareMenu.fxml")));
        menuItems.pseudoClassStateChanged(PseudoClass.getPseudoClass("distance"), true);
        // Changes side menu to the filter menu.
        pane.setLeft(menuItems);
    }

    /**
     * Changes the menu bar to show the time comparison menu.
     *
     * @throws IOException An error that occurs when loading the FXML file.
     */
    public void toTime() throws IOException{
        BorderPane pane = (BorderPane) frame.getParent();
        
        VBox menuItems = FXMLLoader.load(Objects.requireNonNull(MenuController.class.getResource("/gui/compareMenu.fxml")));
        menuItems.pseudoClassStateChanged(PseudoClass.getPseudoClass("time"), true);
        
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

        if (ControllerData.getInstance().getFile(event)) {
            BorderPane rootPane = (BorderPane) frame.getParent();
            // Loads the paginator screen.
            BorderPane dataView = FXMLLoader.load(Objects.requireNonNull(MenuController.class.getResource("/gui/pages.fxml")));

            // Adds the data view to the center of the screen.
            rootPane.setCenter(dataView);
        }
    }

    public void exportToCSV() {
        System.out.println("test");
    }

    public void exportToDB() {
        System.out.println("test");
    }

    /**
     * Moves the program to an empty entry view to create a new crime object.
     * @throws IOException An error that occurs when loading the FXML file.
     */
    public void toNewEntry() throws IOException {
        BorderPane rootPane = (BorderPane) frame.getParent();

        ControllerData.getInstance().setCurrentRow(null);
        Node dataView = rootPane.getCenter();
        ControllerData.getInstance().setTableState(dataView);
        Node newFrame;
        newFrame = FXMLLoader.load(Objects.requireNonNull(MenuController.class.getResource("/gui/entryView.fxml")));

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
