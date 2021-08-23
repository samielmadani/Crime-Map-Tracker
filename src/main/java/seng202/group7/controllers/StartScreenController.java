package seng202.group7.controllers;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

public class StartScreenController {
    @FXML
    private BorderPane rootPane;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void toNextScene(ActionEvent event) throws IOException {
        fadeOutScene();
        root = FXMLLoader.load(getClass().getResource("/gui/dataView.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
    }

    public void fadeOutScene() {
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(300));
        fade.setNode(rootPane);
        fade.setFromValue(1);
        fade.setToValue(0);
        fade.setOnFinished(actionEvent -> loadScene());
        fade.play();
    }

    private void loadScene() {
        stage.setScene(scene);
        stage.show();
    }

    public void exitStage(ActionEvent event) {
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
