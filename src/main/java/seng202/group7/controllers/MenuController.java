package seng202.group7.controllers;

import java.io.IOException;
import java.util.Objects;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.Node;


public class MenuController {

    private static BorderPane scene;
    
    public static void showDataView(ActionEvent e) {
        BorderPane root;
        try {
            root = FXMLLoader.load(MenuController.class.getResource("/gui/dataView.fxml"));
        } catch (IOException error) {
            System.out.println(error);
            return;
        }
        scene = ((BorderPane) (((Node) e.getSource()).getScene()).getRoot());
        scene.setCenter(root);
    }

    public static void showHomeView(ActionEvent e) {
        BorderPane root;
        try {
            root = FXMLLoader.load(MenuController.class.getResource("/gui/startScreen.fxml"));
        } catch (IOException error) {
            return;
        }
        scene = ((BorderPane) (((Node) e.getSource()).getScene()).getRoot());
        scene.setCenter(root);
    }

    public static void showAnalysisView(ActionEvent e) {
    }

    private static void changeRoot(ActionEvent event) {

    }
}
