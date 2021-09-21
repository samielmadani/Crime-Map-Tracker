package seng202.group7.controllers;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;
import javafx.event.ActionEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

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
        Stage stage = getStage(event);
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(300));
        fade.setNode(rootPane);
        fade.setFromValue(1);
        fade.setToValue(0);
        // Creates a trigger that will, at the end of the transition, activate the method toNextScene.
        fade.setOnFinished(actionEvent -> {
            try {
                // Transitions to the next scene.
                toNextScene(stage);
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
     * Closes the stage and therefore exits the application.
     *
     * @param event     The event action that was triggered.
     */
    public void exitStage(ActionEvent event) {
        Stage stage = getStage(event);
        stage.close();
    }

    /**
     * Launches a file chooser and returns selected file or null is cancelled
     * So it can be used by the controllers.
     *
     * @param stage     The event action that was triggered.
     * @return the selected file or null
     */
    public void getFile(ActionEvent event) {
        ControllerData.getInstance().getFile(event);
        // Allows the user to use the start button which changes to the dataView scene.
        fadeOutScene(event);
    }

    // public File getFile(Stage stage) {
    //     FileChooser fileChooser = new FileChooser();
    //     fileChooser.setInitialDirectory(new File("src/test/files"));
    //     fileChooser.setTitle("Select file");
    //     // Limits the types of files to only CSV.
    //     fileChooser.getExtensionFilters().add(new ExtensionFilter(".csv, .db files", "*.csv", "*.db"));

    //     // Launches the file chooser.
    //     File selectedFile = fileChooser.showOpenDialog(stage);

    //     return selectedFile;
    // }

    // public void convertFile(ActionEvent event) {
    //     Stage stage = getStage(event);
    //     File selectedFile = getFile(stage);
    //     if(selectedFile == null) {
    //         return;
    //     }
    //     ArrayList<Report> reports = CSVDataAccessor.getInstance().read(selectedFile);
    //     reports = chooseDBDirectory(stage, reports);
    //     // Uses the singleton class ControllerData which can allow the reports to be store
    //     // and then retrieved by other controllers.
    //     ControllerData.getInstance().setReports(reports);
    //     // Allows the user to use the start button which changes to the dataView scene.
    //     fadeOutScene(stage);

    // }

    /**
     * gets the stage from the event
     * @param event
     * @return
     */
    private Stage getStage(ActionEvent event) {
        return (Stage) ((Node) event.getSource()).getScene().getWindow();
    }

}
