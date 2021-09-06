package seng202.group7.view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import seng202.group7.controllers.ControllerData;
import seng202.group7.controllers.MenuController;
import seng202.group7.controllers.StartScreenController;

import java.io.IOException;

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
        windowStage.setTitle("Main Screen");
        windowStage.setMinHeight(400);
        windowStage.setMinWidth(600);
        // Loads first FXML scene. Checks to ensure that the file is not NULL.
        BorderPane pane = FXMLLoader.load(getClass().getResource("/gui/startScreen.fxml"));
        BorderPane borderPane = new BorderPane();
        ToolBar navMenu = createNavMenu();
        VBox filterMenu = createFilterMenu();

        borderPane.setTop(navMenu);
        borderPane.setLeft(filterMenu);
        borderPane.setCenter(pane);
        Scene scene = new Scene(borderPane);

        windowStage.setScene(scene);
        windowStage.show();

    }

    private ToolBar createNavMenu() {
        ToolBar menuBar = new ToolBar();
        Button menu = new Button("Menu");
        Button home = new Button("Home");
        Button view = new Button("View data");
        Button analysis = new Button("Analyse data");

        menu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                MenuController.showMenu(e);
            }
        });
        home.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                MenuController.showHomeView(e);
            }
        });
        view.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                MenuController.showDataView(e);
            }
        });
        analysis.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                MenuController.showAnalysisView(e);
            }
        });
        menuBar.getItems().addAll(menu, home, view, analysis);
        return menuBar;
    }

    private VBox createFilterMenu() {
        VBox menu = new VBox(10);
        menu.setPrefWidth(75);
        menu.setFillWidth(true);
        menu.setAlignment(Pos.BASELINE_CENTER);
        menu.setStyle("-fx-background-color: white; -fx-border-color: grey;");
        
        Button filter = new Button("Filter");
        Button sort = new Button("Sort");
        Button add = new Button("Add Data");
        Button importButton = new Button("Import");
        Button time = new Button("Time Difference");
        Button distance = new Button("Distance");
        Button search = new Button("Search");

        importButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                ControllerData.getFile(e);
            }
        });

        filter.setPrefWidth(50);
        sort.setPrefWidth(50);
        add.setPrefWidth(50);
        importButton.setPrefWidth(50);
        time.setPrefWidth(50);
        distance.setPrefWidth(50);
        search.setPrefWidth(50);
        
        menu.getChildren().addAll(filter, sort, add, importButton, time, distance, search);

        return menu;
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
