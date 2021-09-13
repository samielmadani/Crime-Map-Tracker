package seng202.group7.controllers;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;
import seng202.group7.CSVDataAccessor;
import seng202.group7.DataAccessor;
import seng202.group7.Report;
import seng202.group7.SQLiteAccessor;

/**
 * The controller, used by / linked to, the Start Screen FXML file.
 *
 * @author John Elliott
 * @author Jack McCorkindale
 * @author Shaylin Simadari
 */
public class StartScreenController {
    /**
     * Is the parent node panel to all other nodes.
     */
    @FXML
    private BorderPane rootPane;
//    /**
//     * Is the button used to transition to the next scene.
//     */
//    @FXML
//    private Button startButton;

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
        fade.setToValue(0);
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
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/gui/dataView.fxml")));
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
        fileChooser.getExtensionFilters().add(new ExtensionFilter(".csv, .db files", "*.csv", "*.db"));

        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        // Launches the file chooser.
        File selectedFile = fileChooser.showOpenDialog(stage);
        // If the file chooser is exited before a file is selected it will be a NULL value and should not continue.
        if (selectedFile != null) {
            ArrayList<Report> reports = null;
            switch(getFileExtension(selectedFile)){
                case ".csv":
                    CSVDataAccessor.getInstance().read(selectedFile);
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Convert file");
                    alert.setHeaderText("Would you like to convert this file to a database?");
                    alert.setContentText("Cancelling will proceed with .csv file");

                    Optional<ButtonType> result = alert.showAndWait();
                    reports = CSVDataAccessor.getInstance().read(selectedFile);
                    if (result.get() == ButtonType.OK){
                        reports = chooseDBDirectory(stage, reports);
                    }
                    break;
                case ".sqlite":
                    reports = SQLiteAccessor.getInstance().read(selectedFile);
                    break;
                default:
                    break;
            }
            // Uses the singleton class ControllerData which can allow the reports to be store
            // and then retrieved by other controllers.
            ControllerData.getInstance().setReports(reports);
            // Allows the user to use the start button which changes to the dataView scene.
            fadeOutScene(event);
        }
    }

    public void connectToDB(ActionEvent event) {

    }

    public void convertFile(ActionEvent event) {

    }

    @NotNull
    private String getFileExtension(File selectedFile) {
        String path = selectedFile.getPath();
        return path.substring(path.lastIndexOf("."));
    }

    /**
     * Launches a fileChooser to choose directory to create a DB in
     * @param stage stage to show the filechooser on
     * @param reports report to write to the DB
     * @return reports in the DB
     */
    private ArrayList<Report> chooseDBDirectory(Stage stage, ArrayList<Report> reports) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("src/test/files"));
        fileChooser.setTitle("Create .sqlite file");
        fileChooser.getExtensionFilters().add(new ExtensionFilter(".db files", "*.db"));
        File selectedFile = fileChooser.showSaveDialog(stage);
        if(selectedFile != null) {
            reports = convertCSVtoDB(reports, selectedFile);
        }
        return reports;
    }

    /**
     * creates a new database and populates it
     * @param reports reports to write to database
     * @param selectedFile location to create the database in
     * @return reports in the database
     */
    private ArrayList<Report> convertCSVtoDB(ArrayList<Report> reports, File selectedFile) {
        SQLiteAccessor.getInstance().connect(selectedFile);
        SQLiteAccessor.getInstance().create(selectedFile);
        SQLiteAccessor.getInstance().write(reports, selectedFile);
        reports = SQLiteAccessor.getInstance().read(selectedFile);
        return reports;
    }

}
