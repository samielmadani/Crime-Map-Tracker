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

    /**
     * Set up the fade out transition which will then load the next scene.
     *
     * @param event     The event action that was triggered.
     */
    public void fadeOutScene(ActionEvent event) {
        // Creates the fade transition and assigns it a set of properties used to outline its style.
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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
