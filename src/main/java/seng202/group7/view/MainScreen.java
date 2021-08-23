package seng202.group7.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainScreen extends Application {

    private Stage windowStage;

    @Override
    public void start(Stage initialStage) throws IOException  {
        setStage(initialStage);
        windowStage.setTitle("Main Screen");
        windowStage.setMinHeight(400);
        windowStage.setMinWidth(600);
        Parent root = FXMLLoader.load(getClass().getResource("/gui/startScreen.fxml"));
        windowStage.setScene(new Scene(root));
        windowStage.show();
    }

    private void setStage(Stage initialStage) {
        windowStage = initialStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
