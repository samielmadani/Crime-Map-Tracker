package seng202.group7.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainScreen extends Application {

    @Override
    public void start(Stage initialStage) throws IOException  {
        initialStage.setTitle("Main Screen");
        Parent root = FXMLLoader.load(getClass().getResource("/gui/mainScreen.fxml"));
        initialStage.setScene(new Scene(root, 300, 275));
        initialStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
