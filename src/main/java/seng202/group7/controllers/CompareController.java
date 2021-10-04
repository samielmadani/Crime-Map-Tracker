package seng202.group7.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import seng202.group7.data.Crime;
import seng202.group7.data.DataAccessor;
import seng202.group7.data.Report;
import seng202.group7.analyses.Comparer;

/**
 * The controller, used by / linked to, the compares FXML file.
 * Handles the comparisons of two crime objects.
 *
 * @author Jack McCorkindale
 */
public class CompareController implements Initializable {

    @FXML
    private TextField reportOneText, reportTwoText;
    @FXML
    private Label resultText, menuText;
    @FXML
    private VBox frame;

    /**
     * A style class that can be added to a node to add error formatting.
     */
    private PseudoClass errorClass;

    /**
     * This method is run during the loading of the compare menu fxml.
     *
     * @param location      A URL object.
     * @param resources     A ResourceBundle object.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        errorClass = PseudoClass.getPseudoClass("error");
    }


    /**
     * Gets the current side panel and replaces it with the general menu panel.
     *
     * @throws IOException      An error that occurs when loading the FXML file.
     */
    public void toMenu() throws IOException {
        BorderPane pane = (BorderPane) frame.getParent();
        VBox menuItems = FXMLLoader.load(Objects.requireNonNull(MenuController.class.getResource("/gui/generalMenu.fxml")));
        // Changes side menu to the filter menu.
        pane.setLeft(menuItems);
    }

    /**
     * Gets the two pieces of data and creates a string based on the distance and time comparison between the two values
     */
    public void compareReports() {
        DataAccessor data = DataAccessor.getInstance();
        resultText.setText("");
        String resultTextString = "";

        Crime reportOne = data.getCrime(reportOneText.getText());
        Crime reportTwo = data.getCrime(reportTwoText.getText());
        reportOneText.pseudoClassStateChanged(errorClass, reportOne == null);
        reportTwoText.pseudoClassStateChanged(errorClass, reportTwo == null);
        if (reportOne == null || reportTwo == null) {
            return;
        } else {
            if (reportOne.getCaseNumber().equals(reportTwo.getCaseNumber())) {
                resultTextString += "The two crimes are the same value, please select two different values.";
            } else {
                resultTextString += "Distance:";
                resultTextString += compareDistance(reportOne, reportTwo);
                resultTextString += "Time:";
                resultTextString += compareTime(reportOne, reportTwo);
                }
        }
        resultText.setText(resultTextString);
    }

    /**
     * Compares the distance between the two reports and returns a string representing this value
     *
     * @param reportOne     The first report to be compared
     * @param reportTwo     The second report to be compared
     */
    private String compareDistance(Report reportOne, Report reportTwo) {
        double distance = Comparer.locationDifference(reportOne, reportTwo);
        if (distance == -1) {
            return String.format("\nCrime %s has no location values.\n", reportOneText.getText());
        } else if(distance == -2) {
            return String.format("\nCrime %s has no location values.\n", reportTwoText.getText());
        } else if(distance == -3) {
            return String.format("\nCrime %s and Crime %s have no location values.\n", reportOneText.getText(), reportTwoText.getText());
        } else {
            return String.format("\nCrime %s and %s occurred %.2fkm apart.\n", reportOneText.getText(), reportTwoText.getText(), distance);
        }
    }

    /**
     * Compares the time difference between the two reports and returns a string representing this value
     *
     * @param reportOne     The first report to be compared
     * @param reportTwo     The second report to be compared
     */
    private String compareTime(Report reportOne, Report reportTwo) {
        ArrayList<Long> time = Comparer.timeDifference(reportOne, reportTwo);
        String timeString = "";
        if (time.get(3) > 0) {
            timeString += String.format("%d year(s) ", time.get(3));
        }
        if (time.get(2) > 0) {
            timeString += String.format("%d day(s) ", time.get(2));
        }
        if (time.get(1) > 0) {
            timeString += String.format("%d hour(s) ", time.get(1));
        }
        if (time.get(0) > 0) {
            timeString += String.format("%d minute(s) ", time.get(0));
        }
        if (timeString.equals("")) {
            return String.format("\nCrime %s and %s occurred at the same time.", reportOneText.getText(), reportTwoText.getText());
        } else {
            return String.format("\nCrime %s and %s occurred %sapart.", reportOneText.getText(), reportTwoText.getText(), timeString);
        }
    }
}
