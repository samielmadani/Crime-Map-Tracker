package seng202.group7.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import seng202.group7.data.CustomException;
import seng202.group7.view.MainScreen;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * This menu allows a user to pick a field to search and then attempts to match
 * text entered by the user to that in the database.
 *
 * @author John Elliott
 */
public class SearchController implements Initializable {
    @FXML
    private ComboBox<String> fieldBox;
    @FXML
    private Label errorLabel;
    @FXML
    private TextField inputField;
    @FXML
    private Button searchButton;

    /**
     * This method is run during the loading of the search menu fxml file.
     * It generates the options that are displayed in the choice box.
     *
     * @param location      A URL object.
     * @param resources     A ResourceBundle object.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fieldBox.setItems(FXCollections.observableArrayList(
                "Case Number", "Primary Description", "Secondary Description", "Location Description", "FBI Code", "Illinois Number"));
    }

    /**
     * Gets the current side panel and replaces it with the general menu panel.
     *
     * @param event             The event action that was triggered.
     */
    public void toMenu(ActionEvent event){
        // As the side panels root is the main border panel we use .getRoot().
        BorderPane pane = (BorderPane) (((Node) event.getSource()).getScene()).getRoot();
        try {
            VBox menuItems = FXMLLoader.load(Objects.requireNonNull(MenuController.class.getResource("/gui/menu/generalMenu.fxml")));
            // Changes side menu to the filter menu.
            pane.setLeft(menuItems);
        } catch (IOException | NullPointerException e) {
            MainScreen.createErrorWin(new CustomException("Error caused when loading the General Menu screens FXML file.", e.getClass().toString()));
        }

        // This removes the current search effect being applied to the table when the paginator is initialized.
        ControllerData.getInstance().setWhereQuery("");
        try {
            BorderPane tableView = FXMLLoader.load(Objects.requireNonNull(MenuController.class.getResource("/gui/views/pageView.fxml")));
            // Changes side menu to the filter menu.
            pane.setCenter(tableView);
        } catch (IOException | NullPointerException e) {
            MainScreen.createErrorWin(new CustomException("Error caused when loading the Pagination screens FXML file.", e.getClass().toString()));
        }

    }

    /**
     * This method ensures that the user has typed in something before the search is allowed.
     */
    public void validateText() {
        String input = inputField.getText();
        if (Objects.equals(input, "")) {
            searchButton.setDisable(true);
            errorLabel.setText("No Input Given");
        } else {
            errorLabel.setText("");
            searchButton.setDisable(false);
        }
    }

    /**
     * This method allows the user to enter information and there search once they have selected a field.
     */
    public void enableMenu() {
        inputField.setDisable(false);
        if (Objects.equals(inputField.getText(), "")) {
            errorLabel.setText("No Input Given");
        }
    }

    /**
     * This method is used to determine the condition that should be applied to when the paginator is initialized
     * and therefore allow a user to search based on the text entered.
     *
     * @param event             The event action that was triggered.
     */
    public void searchInput(ActionEvent event) {
        String field = fieldBox.getValue();
        String input = inputField.getText();
        // Determines the condition that will be used.
        String query = switch (field) {
            case "Case Number" -> " id LIKE '" + input + "%'";
            case "Primary Description" -> " primary_description LIKE '" + input + "%'";
            case "Secondary Description" -> " secondary_description LIKE '" + input + "%'";
            case "Location Description" -> " location_description LIKE '" + input + "%'";
            case "FBI Code" -> " fbicd LIKE '" + input + "%'";
            case "Illinois Number" -> " iucr LIKE '" + input + "%'";
            default -> "";
        };
        // By setting this where query when the paginator is generated the data accessor will apply it to the search.
        ControllerData.getInstance().setWhereQuery(query);
        // As the side panels root is the main border panel we use .getRoot().
        BorderPane pane = (BorderPane) (((Node) event.getSource()).getScene()).getRoot();
        try {
            BorderPane tableView = FXMLLoader.load(Objects.requireNonNull(MenuController.class.getResource("/gui/pages.fxml")));
            // Changes side menu to the filter menu.
            pane.setCenter(tableView);
        } catch (IOException | NullPointerException e) {
            MainScreen.createErrorWin(new CustomException("Error caused when loading the Pagination screens FXML file.", e.getClass().toString()));
        }

    }
}
