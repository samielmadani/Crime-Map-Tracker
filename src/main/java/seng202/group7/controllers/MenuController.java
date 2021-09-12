package seng202.group7.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.util.Duration;


/**
 * The controller, used by / linked to, the Menu FXML file.
 * This controller links the main screen menu system together with its components.
 *
 * @author John Elliott
 * @author Jack McCorkindale
 * @author Sami Elmadani
 */
public class MenuController implements Initializable {
    /**
     * The main boarder panel used to hold the windows components.
     */
    @FXML
    private BorderPane mainPane;

    /**
     * This method is run during the loading of the data view fxml file.
     * It generates what values will be stored in the columns.
     *
     * @param location      A URL object.
     * @param resources     A ResourceBundle object.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            // Loads the first side menu screen.
            VBox menuItems = FXMLLoader.load(Objects.requireNonNull(MenuController.class.getResource("/gui/generalMenu.fxml")));
            // Sets the menu to the main panel and hides it, so it starts closed.
            mainPane.setLeft(menuItems);
            VBox pane = (VBox) mainPane.getLeft();
            // This is used to determine the direction the open and close animation will play.
            pane.setTranslateX(-(pane.getPrefWidth()));
            mainPane.getLeft().setVisible(false);
            mainPane.getLeft().setManaged(false);
            toData(new ActionEvent());
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Runs the opening and closing animation for the side menu screen.
     *
     * @param event     The event action that was triggered.
     */
    public void menuAnimation(ActionEvent event) {
        VBox pane = (VBox) mainPane.getLeft();
        // Creates an animation for opening the menu.
        TranslateTransition openMenu = new TranslateTransition(new Duration(350), pane);
        openMenu.setToX(0);
        // Creates an animation for closing the menu.
        TranslateTransition closeMenu=new TranslateTransition(new Duration(350), pane);
        closeMenu.setOnFinished(action->{
            mainPane.getLeft().setVisible(false);
            mainPane.getLeft().setManaged(false);
        });
        // Determines by the off set position of the VBox if the open or close animation needs to be played.
        if (pane.getTranslateX()!= 0) {
            mainPane.getLeft().setVisible(true);
            mainPane.getLeft().setManaged(true);
            openMenu.play();
        } else {
            closeMenu.setToX(-(pane.getWidth()));
            closeMenu.play();
        }
    }

    public void toSearch(ActionEvent e) {
        WebView externalSearch = new WebView();
        externalSearch.getEngine().load("https://cse.google.com/cse?cx=59f99af6c7b75d889"); 
        mainPane.setCenter(externalSearch);
    }

    public void toData(ActionEvent e) throws IOException {
        // Loads the raw data viewer screen.
        GridPane dataView = FXMLLoader.load(Objects.requireNonNull(MenuController.class.getResource("/gui/dataView.fxml")));
        
        // Adds the data view to the center of the screen.
        mainPane.setCenter(dataView);
    }
}
