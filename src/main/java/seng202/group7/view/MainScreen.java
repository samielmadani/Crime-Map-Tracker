package seng202.group7.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Initializes the GUI stage and loads the first FXML scene.
 *
 * @author John Elliott
 * @author Sam MCMillan
 * @author Sami Elmadani
 * @author Shaylin Simadari
 * @author Jack McCorkindale
 */
public class MainScreen extends Application {

    /**
     * The stage used to display the GUI scenes.
     */
    private Stage windowStage;

    /**
     * Loads the first FXML file and sets it to the current scene for the stage.
     *
     * @param initialStage      The stage that the scene will be load onto.
     * @throws IOException      An error that occurs when loading the FXML file.
     */
    @Override
    public void start(Stage initialStage) throws IOException {
        // Sets the initialStage as the class variable.
        setStage(initialStage);
        windowStage.setTitle("LookOut");
        windowStage.setMinHeight(400);
        windowStage.setMinWidth(600);
        // Loads first FXML scene. Checks to ensure that the file is not NULL.
        Parent view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/gui/startScreen.fxml")));
        Scene scene = new Scene(view);
        windowStage.setScene(scene);
        windowStage.show();
    }


    /**
     * Setter for the window stage.
     *
     * @param initialStage      The stage that the scenes will be load onto.
     */
    private void setStage(Stage initialStage) {
        windowStage = initialStage;
    }


    /**
     * Launches the application with the provided arguments passed through.
     * Uses the launch method which is inherited from this class's parent Application.
     *
     * @param args      The arguments given when running the compiled source code.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
