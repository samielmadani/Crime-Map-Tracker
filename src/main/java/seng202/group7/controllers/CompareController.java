package seng202.group7.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.collections.ObservableSet;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import seng202.group7.data.Crime;
import seng202.group7.data.DataAccessor;
import seng202.group7.data.Report;
import seng202.group7.analyses.Comparer;

public class CompareController implements Initializable {

    @FXML
    private TextField reportOneText, reportTwoText;

    @FXML
    private Label resultText;

    @FXML
    private Label menuText;

    @FXML
    private VBox frame;

    private PseudoClass errorClass;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        errorClass = PseudoClass.getPseudoClass("error");

        frame.parentProperty().addListener((obs, oldParent, newParent) -> {

            if (newParent != null) {
                setType(newParent.getPseudoClassStates());
            }

        });
    }

    public void setType(ObservableSet<PseudoClass> pseudoClasses) {
        if (pseudoClasses.contains(PseudoClass.getPseudoClass("distance"))) {
            menuText.setText("Distance Compare");
            frame.getParent().pseudoClassStateChanged(PseudoClass.getPseudoClass("distance"), false);
        } else if (pseudoClasses.contains(PseudoClass.getPseudoClass("time"))) {
            menuText.setText("Time Compare");
            frame.getParent().pseudoClassStateChanged(PseudoClass.getPseudoClass("time"), false);
        }
    }

    /**
     * Gets the current side panel and replaces it with the general menu panel.
     *
     * @param event             The event action that was triggered.
     * @throws IOException      An error that occurs when loading the FXML file.
     */
    public void toMenu(ActionEvent event) throws IOException {
        // As the side panels root is the main border panel we use .getRoot().
        BorderPane pane = (BorderPane) (((Node) event.getSource()).getScene()).getRoot();
        VBox menuItems = FXMLLoader.load(Objects.requireNonNull(MenuController.class.getResource("/gui/generalMenu.fxml")));
        // Changes side menu to the filter menu.
        pane.setLeft(menuItems);
    }

    public void compareReports(ActionEvent event) {
        DataAccessor data = DataAccessor.getInstance();
        resultText.setText("");

        Report reportOne = data.getCrime(reportOneText.getText());
        Report reportTwo = data.getCrime(reportTwoText.getText());
        reportOneText.pseudoClassStateChanged(errorClass, reportOne == null);
        reportTwoText.pseudoClassStateChanged(errorClass, reportTwo == null);
        if (reportOne == null || reportTwo == null) {
            return;
        }
        if (menuText.getText().contains("Distance")) {
            compareDistance(reportOne, reportTwo);
        } else if (menuText.getText().contains("Time")) {
            compareTime(reportOne, reportTwo);
        }
    }

    private void compareDistance(Report reportOne, Report reportTwo) {
        double distance = Comparer.locationDifference(reportOne, reportTwo);
        resultText.setText(String.format("Crime %s and %s occurred %.2fkm apart.", reportOneText.getText(), reportTwoText.getText(), distance));
    }

    private void compareTime(Report reportOne, Report reportTwo) {
        //TODO Have and between elements
        //TODO When value is 1 use singular
        ArrayList<Long> time = Comparer.timeDifference(reportOne, reportTwo);
        String timeString = "";
        if (time.get(3) > 0) {
            timeString += String.format("%d years ", time.get(3));
        }
        if (time.get(2) > 0) {
            timeString += String.format("%d days ", time.get(2));
        }
        if (time.get(1) > 0) {
            timeString += String.format("%d hours ", time.get(1));
        }
        if (time.get(0) > 0) {
            timeString += String.format("%d minutes ", time.get(0));
        }
        if (timeString == "") {
            resultText.setText(String.format("Crime %s and %s occurred at the same time.", reportOneText.getText(), reportTwoText.getText(), timeString));
        } else {
            resultText.setText(String.format("Crime %s and %s occurred %sapart.", reportOneText.getText(), reportTwoText.getText(), timeString));
        }
    }
}
