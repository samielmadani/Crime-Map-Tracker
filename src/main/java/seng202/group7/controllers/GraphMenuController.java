package seng202.group7.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Graph Menu Controller is the class which controls which graph is displayed by the program.
 *
 * @author Jack McCorkindale
 * @author Sam McMillan
 */
public class GraphMenuController implements Initializable {

    @FXML
    private ComboBox<String> graphType;

    @FXML
    private Node frame;

    private Parent root;

    /**
     * This method is run during the loading of the graph menu fxml file.
     *
     * @param location      A URL object.
     * @param resources     A ResourceBundle object.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        graphType.getItems().add("Most Frequent Crime Types");
        graphType.getItems().add("Most Dangerous Wards");
        graphType.getItems().add("Most Dangerous Streets");
        graphType.getItems().add("Crime Over Time");

        graphType.setValue("Most Frequent Crime Types");
    }

    /**
     * Method triggered when the user clicks on the generate graph button, Checks what selection is made by the user in
     * the combo box and reloads the graphView.
     *
     * @throws IOException      The exception that is thrown when the FXML Loader can't load the fxml file
     */
    public void selectGraph() throws IOException {

        ArrayList<Integer> graphVariables;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/barGraphView.fxml"));
        root = loader.load();
        BarGraphViewController graphView = loader.getController();
        graphView.prepareBarGraph(graphType.getValue());

        ((BorderPane) frame.getParent()).setCenter(root);
    }

}


