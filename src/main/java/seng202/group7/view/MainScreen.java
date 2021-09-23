package seng202.group7.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import seng202.group7.data.DataAccessor;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Initializes the GUI stage and loads the first FXML scene.
 *
 * @author Jack McCorkindale
 * @author John Elliott
 * @author Sam McMillan
 * @author Sami Elmadani
 * @author Shaylin Simadari
 */
public class MainScreen extends Application {

    /**
     * Loads the first FXML file and sets it to the current scene for the stage.
     *
     * @param windowStage      The stage that the scene will be load onto.
     * @throws IOException      An error that occurs when loading the FXML file.
     */
    @Override
    public void start(Stage windowStage) throws IOException {
        windowStage.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindow);
        windowStage.setTitle("LookOut");
        // Loads first FXML scene. Checks to ensure that the file is not NULL.
        Parent view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/gui/startScreen.fxml")));
        Scene scene = new Scene(view);
        windowStage.setScene(scene);
        windowStage.show();
    }

    /**
     * As connection is made at the start of the application this event ensures,
     * that the database is closed at the end of the application.
     * @param event     The window event action that was triggered.
     */
    private void closeWindow(WindowEvent event) {
        try {
            DataAccessor.getInstance().getConnection().close();
        } catch (SQLException error) {
            error.printStackTrace();
        }
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
