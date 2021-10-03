package seng202.group7.controllers;

import javafx.animation.FadeTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import seng202.group7.data.DataAccessor;

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
    private TableView <String> table;

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

        listNames.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
        setListNames();

        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            listSelected(newSelection != null);
        });

    }

    private void setListNames() {
        ObservableList<String> lists = DataAccessor.getInstance().getLists();
        System.out.println(lists);
        table.setItems(lists);
    }

    public void listSelected(boolean isList) {
        rename.setDisable(!isList);
        renameListText.setDisable(!isList);
        delete.setDisable(!isList);
        load.setDisable(!isList);
        selectedList = table.getSelectionModel().getSelectedItem();
    }

    public void deleteList() {
        DataAccessor.getInstance().deleteList(table.getSelectionModel().getSelectedItem());
        setListNames();
    }

    public void loadList() {
        int listId = DataAccessor.getInstance().getListId(selectedList);
        ControllerData.getInstance().setCurrentList(listId);
        fadeOutScene(new ActionEvent());
    }

    public void createList() {
        DataAccessor.getInstance().createList("test");
        setListNames();
    }

    public void renameList() {
        String list = table.getSelectionModel().getSelectedItem();
        String newName = renameListText.getText();
        DataAccessor.getInstance().renameList(list, newName);
        setListNames();
        table.getSelectionModel().select(newName);
    }

    /**
     * Set up the fade out transition which will then load the next scene.
     *
     * @param event     The event action that was triggered.
     */
    public void fadeOutScene(ActionEvent event) {
        // Creates the fade transition and assigns it a set of properties used to outline its style.
        // Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Stage stage = (Stage) rootPane.getScene().getWindow();
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(100));
        fade.setNode(rootPane);
        fade.setFromValue(1);
        fade.setToValue(0);
        // Creates a trigger that will, at the end of the transition, activate the method toNextScene.
        fade.setOnFinished(actionEvent -> {
            try {
                // Transitions to the next scene.
                toNextScene(stage);
            } catch (IOException e) {
                // Catches an error that can be thrown if there is an error when loading the next FXML file.
                e.printStackTrace();
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
     * @throws IOException      An error that occurs when loading the FXML file.
     */
    private void toNextScene(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/gui/menu.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    /**
     * Calls a method to run a file picker to import a file into the database.
     * It then if a file is selected load the application.
     *
     * @param event     The event action that was triggered.
     */
    public void getFile(ActionEvent event) {
        // Checks that a file was actually selected.
        if (ControllerData.getInstance().getFile(event)) {
            fadeOutScene(event);
        }
    }
}
