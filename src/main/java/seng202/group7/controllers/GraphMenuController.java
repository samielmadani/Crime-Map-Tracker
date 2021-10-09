package seng202.group7.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import seng202.group7.data.CustomException;
import seng202.group7.data.DataAccessor;
import seng202.group7.view.MainScreen;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
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

    @FXML
    private ComboBox<String> crimeType;

    @FXML
    private ComboBox<String> wardField;

    @FXML
    private ComboBox<String> beatField;

    @FXML
    private Parent root;


    /**
     * This method is run during the loading of the graph menu fxml file.
     * @param location      A URL object.
     * @param resources     A ResourceBundle object.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<String> crimeType = new ArrayList<>();
        ArrayList<Integer> wards = new ArrayList<>();

        try {
            crimeType = DataAccessor.getInstance().getColumnString("primary_description", "");
            wards = DataAccessor.getInstance().getColumnInteger("ward", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assert wards != null;
        Collections.sort(wards);

        this.crimeType.getItems().add(null);
        assert crimeType != null;
        for (String type: crimeType) {
            this.crimeType.getItems().add(type);
        }

        this.wardField.getItems().add(null);
        for (Integer ward: wards) {
            this.wardField.getItems().add(String.valueOf(ward));
        }
        graphType.getItems().add("Most Frequent Crime Types");
        graphType.getItems().add("Most Dangerous Wards");
        graphType.getItems().add("Most Dangerous Beats");
        graphType.getItems().add("Most Dangerous Streets");
        graphType.getItems().add("Less Frequent Crime Types");
        graphType.getItems().add("Safest Beats");
        graphType.getItems().add("Safest Wards");
    }

    /**
     * This method monitors everytime a ward combo box is changed and then updates the beat combobox so only values are encapsulated by the
     * corresponding ward is available to be selected by the user.
     * @throws SQLException
     */
    public void changeBeat() throws SQLException {
        ArrayList<Integer> beats = DataAccessor.getInstance().getColumnInteger("beat", "WHERE WARD=" + wardField.getValue());
        beatField.setValue(null);
        if (wardField.getValue() == null) {
            beatField.setDisable(true);
            return;
        } else {
            beatField.setDisable(false);
        }
        beatField.getItems().clear();
        this.beatField.getItems().add(null);
        assert beats != null;
            Collections.sort(beats);
        for (Integer beat: beats) {
            this.beatField.getItems().add(String.valueOf(beat));
        }
    }

    /**
     * Method triggered when the user changes the value in the crime type combo box, Checks what selection is made
     * and reloads the bar graph view.
     *
     * @throws IOException The exception that is thrown when the FXML Loader can't load the fxml file
     */
    public void selectBarGraph() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/barGraphView.fxml"));
        root = loader.load();
        BarGraphViewController graphView = loader.getController();
        try {
            graphView.prepareBarGraph(graphType.getValue());
        } catch (CustomException e) {
            MainScreen.createWarnWin(e);
        }

        ((BorderPane) frame.getParent()).setCenter(root);
    }

    /**
     * Gets the values from crime type, ward, and beat combo box's and creates a string list of the values
     * @return choices an ArrayList of strings to send to line graph view.
     */
    public ArrayList<String> getChoices() {
        ArrayList<String> choices = new ArrayList<>();
        choices.add(crimeType.getValue());
        choices.add(wardField.getValue());
        choices.add(beatField.getValue());
        return choices;
    }
}



