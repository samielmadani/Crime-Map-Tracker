package seng202.group7.controllers;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import javafx.event.ActionEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import seng202.group7.CSVDataAccessor;
import seng202.group7.Report;

/**
 * The controller, used by / linked to, the Start Screen FXML file.
 *
 * @author John Elliott
 * @author Jack McCorkindale
 */
public class StartScreenController {
    /**
     * Is the parent node panel to all other nodes.
     */
    @FXML
    private BorderPane rootPane;
    /**
     * Is the button used to transition to the next scene.
     */
    @FXML
    private Button startButton;

    /**
     * Set up the fade out transition which will then load the next scene.
     *
     * @param event     The event action that was triggered.
     */
    public void fadeOutScene(ActionEvent event) {
        // Creates the fade transition and assigns it a set of properties used to outline its style.
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(300));
        fade.setNode(rootPane);
        fade.setFromValue(1);
        fade.setToValue(1);
        // Creates a trigger that will, at the end of the transition, activate the method toNextScene.
        fade.setOnFinished(actionEvent -> {
            try {
                // Transitions to the next scene.
                toNextScene(event);
            } catch (IOException e) {
                // catches an error that can be thrown if there is an error when loading the next FXML file.
                e.printStackTrace();
            }
        });
        // Runs the fade out.
        fade.play();
    }

    /**
     * Loads the next scene, dataView.fxml, onto the stage.
     *
     * @param event             The event action that was triggered.
     * @throws IOException      An error that occurs when loading the FXML file.
     */
    private void toNextScene(ActionEvent event) throws IOException {
        BorderPane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/gui/dataView.fxml")));
        BorderPane scene = ((BorderPane) (((Node) event.getSource()).getScene()).getRoot());
        var test = scene.getCenter();
        scene.setCenter(root);
    }

    /**
     * Closes the stage and therefore exits the application.
     *
     * @param event     The event action that was triggered.
     */
    public void exitStage(ActionEvent event) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Launches a file chooser that will get CSV file types and then record the data.
     * So it can be used by the controllers.
     *
     * @param event     The event action that was triggered.
     */
    public void getFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("src/test/files"));
        fileChooser.setTitle("Open data file");
        // Limits the types of files to only CSV.
        fileChooser.getExtensionFilters().add(new ExtensionFilter(".csv files", "*.csv"));

        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        // Launches the file chooser.
        File selectedFile = fileChooser.showOpenDialog(stage);
        // If the file chooser is exited before a file is selected it will be a NULL value and should not continue.
        if (selectedFile != null) {
            // Uses the CSVDataAccessor class to read the file and get the list of data as an array list of reports.
            ArrayList<Report> reports = CSVDataAccessor.getInstance().read(selectedFile);
            // Uses the singleton class ControllerData which can allow the reports to be store
            // and then retrieved by other controllers.
            ControllerData.getInstance().setReports(reports);
            // Allows the user to use the start button which changes to the dataView scene.
            startButton.setDisable(false);
        }
    }
}
