package seng202.group7.controllers;

import javafx.animation.FadeTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import seng202.group7.data.CustomException;
import seng202.group7.data.DataAccessor;
import seng202.group7.view.MainScreen;

/**
 * The controller, used by / linked to, the Start Screen FXML file.
 *
 * @author Jack McCorkindale
 * @author John Elliott
 * @author Shaylin Simadari
 */
public class StartScreenController implements Initializable {
    /**
     * Is the parent node panel to all other nodes.
     */
    @FXML
    private BorderPane rootPane;

    @FXML
    private TableView<String> table;

    @FXML
    private TableColumn<String, String> listNames;

    @FXML
    private Button newList, delete, rename, load;

    @FXML
    private TextField newListText, renameListText;

    private String selectedList;

    /**
     * This method is run during the loading of the data view fxml file.
     * It generates what values will be stored in the columns.
     *
     * @param location      A URL object.
     * @param resources     A ResourceBundle object.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        InputValidator.addValidation(newListText, InputType.LISTNAME);
        InputValidator.addValidation(renameListText, InputType.LISTNAME);

        listNames.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
        setListNames();

        table.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 1) {
                    selectedList = table.getSelectionModel().getSelectedItem();
                    listSelected(selectedList != null);
                }
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    loadList();                   
                }
            }
        });

    }

    /**
     * Adds all lists in the database to the list table
     */
    private void setListNames() {
        ObservableList<String> lists = DataAccessor.getInstance().getLists();
        table.setItems(lists);
    }

    /**
     * Runs when a list is selected and makes the relevant user input available for use
     */
    public void listSelected(boolean isList) {
        rename.setDisable(!isList);
        renameListText.setDisable(!isList);
        delete.setDisable(!isList);
        load.setDisable(!isList);
    }

    /**
     * Deletes the selected list.
     */
    public void deleteList() {
        DataAccessor.getInstance().deleteList(table.getSelectionModel().getSelectedItem());
        setListNames();
        listSelected(false);
    }

    /**
     * Loads the selected list and moves to the table view.
     */
    public void loadList() {
        int listId = DataAccessor.getInstance().getListId(selectedList);
        ControllerData.getInstance().setCurrentList(listId);
        fadeOutScene();
    }

    /**
     * Creates a new list with the name the user has input into the TextField which is next to the create button.
     */
    public void createList() {
        if (InputValidator.validate(newListText)) {
            DataAccessor.getInstance().createList(newListText.getText());
            setListNames();
            newListText.clear();
        }
    }

    /**
     * Renames the selected list to what is in the TextField next to the rename button.
     */
    public void renameList() {
        if (InputValidator.validate(renameListText)) {
            String list = table.getSelectionModel().getSelectedItem();
            String newName = renameListText.getText();
            DataAccessor.getInstance().renameList(list, newName);
            setListNames();
            table.getSelectionModel().select(newName);
            renameListText.clear();
        }
    }

    /**
     * Set up the fade out transition which will then load the next scene.
     */
    public void fadeOutScene() {
        // Creates the fade transition and assigns it a set of properties used to outline its style.
        Stage stage = (Stage) rootPane.getScene().getWindow();
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(50));
        fade.setNode(rootPane);
        fade.setFromValue(1);
        fade.setToValue(0);
        // Creates a trigger that will, at the end of the transition, activate the method toNextScene.
        fade.setOnFinished(actionEvent -> {
            // Transitions to the next scene.
            try {
                toNextScene(stage);
            } catch (CustomException e) {
                MainScreen.createErrorWin(e);
            }
        });
        // Runs the fade out.
        fade.play();
    }


    /**
     * Loads the next scene, menu.fxml, onto the stage.
     * This is the start of the main application.
     *
     * @param stage             The event action that was triggered.
     * @throws CustomException      An error that occurs when loading the FXML file.
     */
    private void toNextScene(Stage stage) throws CustomException {
        Parent newRoot;
        try {
            newRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/gui/menus/mainMenu.fxml")));
        } catch (IOException | NullPointerException e) {
            throw new CustomException("Error caused when loading the Menu screens FXML file.", e.getClass().toString());
        }
        Scene scene = stage.getScene();
        scene.setRoot(newRoot);
    }
}
