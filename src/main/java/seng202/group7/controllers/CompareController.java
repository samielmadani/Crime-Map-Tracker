package seng202.group7.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.collections.ObservableSet;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import seng202.group7.data.DataAccessor;
import seng202.group7.data.Report;
import seng202.group7.analyses.Comparer;

/**
 * The controller, used by / linked to, the compares FXML file.
 * Handles the comparisons of two crime objects.
 *
 * @author Jack McCorkindale
 * @author Shaylin Simadari
 */
public class CompareController implements Initializable, SavesGUIFields {

    @FXML
    private TextField reportOneText, reportTwoText;
    @FXML
    private Label resultText, menuText;
    @FXML
    private VBox frame;

    private static String report1;
    private static String report2;

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

        frame.parentProperty().addListener((obs, oldParent, newParent) -> {

            if (newParent != null) {
                setType(frame.getPseudoClassStates());
            }

        });
        loadGUIFields();
    }

    /**
     * Changes the name of the menu to the type of data that is being compared.
     * @param pseudoClasses     A style class.
     */
    public void setType(ObservableSet<PseudoClass> pseudoClasses) {
        if (pseudoClasses.contains(PseudoClass.getPseudoClass("distance"))) {
            menuText.setText("Distance Compare");
            frame.pseudoClassStateChanged(PseudoClass.getPseudoClass("distance"), false);
        } else if (pseudoClasses.contains(PseudoClass.getPseudoClass("time"))) {
            menuText.setText("Time Compare");
            frame.pseudoClassStateChanged(PseudoClass.getPseudoClass("time"), false);
        }
    }

    /**
     * Gets the current side panel and replaces it with the general menu panel.
     *
     * @throws IOException      An error that occurs when loading the FXML file.
     */
    public void toMenu() throws IOException {
        saveGUIFields();
        BorderPane pane = (BorderPane) frame.getParent();
        VBox menuItems = FXMLLoader.load(Objects.requireNonNull(MenuController.class.getResource("/gui/generalMenu.fxml")));
        // Changes side menu to the filter menu.
        pane.setLeft(menuItems);
    }

    /**
     * Clears results and sends the two reports requested to the correct comparison types.
     */
    public void compareReports() {
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

    /**
     * Compares the distance between the two reports and displays it for the user.
     *
     * @param reportOne     The first report to be compared
     * @param reportTwo     The second report to be compared
     */
    private void compareDistance(Report reportOne, Report reportTwo) {
        double distance = Comparer.locationDifference(reportOne, reportTwo);
        resultText.setText(String.format("Crime %s and %s occurred %.2fkm apart.", reportOneText.getText(), reportTwoText.getText(), distance));
    }

    /**
     * Compares the time difference between the two reports and displays it for the user.
     *
     * @param reportOne     The first report to be compared
     * @param reportTwo     The second report to be compared
     */
    private void compareTime(Report reportOne, Report reportTwo) {
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
        if (timeString.equals("")) {
            resultText.setText(String.format("Crime %s and %s occurred at the same time.", reportOneText.getText(), reportTwoText.getText()));
        } else {
            resultText.setText(String.format("Crime %s and %s occurred %sapart.", reportOneText.getText(), reportTwoText.getText(), timeString));
        }
    }

    @Override
    public void saveGUIFields(){
        if(!reportOneText.getText().equals("")) {
            report1 = reportOneText.getText();
        }
        if(!reportTwoText.getText().equals("")) {
            report2 = reportTwoText.getText();
        }
    }

    @Override
    public void loadGUIFields(){
        if(report1 != null) {
            reportOneText.setText(report1);
        }
        if(report2 != null) {
            reportTwoText.setText(report2);
        }
    }
}
