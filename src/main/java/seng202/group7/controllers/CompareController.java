package seng202.group7.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import seng202.group7.data.Crime;
import seng202.group7.data.DataAccessor;
import seng202.group7.data.Report;
import seng202.group7.analyses.Comparer;

/**
 * The controller, used by / linked to, the compares FXML file.
 * Handles the comparisons of two crime objects.
 *
 * @author Jack McCorkindale John Elliot Sam McMillan
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

    /**
     * Will check to see if the data is being viewed through the table or data entry. It will then run the respective
     * methods to get the case number of the crime and insert the value into the appropriate table based on the button clicked.
     *
     * @param event         The button event that triggered this method.
     */
    public void addSelected(ActionEvent event) {
        String selectedCrime;
        // Starts with getting the root panel.
        BorderPane pane = (BorderPane) (((Node) event.getSource()).getScene()).getRoot();
        // Then it gets the pagination node from the centre.
        Node centreNode = ((BorderPane) pane.getCenter()).getCenter();
        try {
            if (centreNode instanceof Pagination) {
                selectedCrime = getFromPages(centreNode);
            } else {
                selectedCrime = getFromEntry(centreNode);
            }
        } catch (Exception ignore) {
            selectedCrime = null; // No correct data loaded so no value can be selected.
        }

        if (selectedCrime != null) {
            Button addButton = (Button) event.getSource();
            // Checks to see which add button was clicked so that it can be added to the right text field.
            if (Objects.equals(addButton.getId(), "addR1")) {
                reportOneText.setText(selectedCrime);
            } else {
                reportTwoText.setText(selectedCrime);
            }
        }
    }

    /**
     * Gets given a node which corresponding to a Pagination node and from this it gets the table and the currently
     * selected value of the table which it then returns.
     *
     * @param centreNode    The Pagination node.
     * @return  The case number.
     */
    private String getFromPages(Node centreNode) {
        Pagination pagination = (Pagination) centreNode;
        // From currentStakePane being stored in the Pagination class the children (which is the dataView FXML file) are retrieved.
        BorderPane tablePane = ((BorderPane) ((StackPane) pagination.getChildrenUnmodifiable().get(0)).getChildren().get(0));
        // Uses the "?" as the casting is happening during the runtime of the application and so it can not check the type held within the classes.
        // Instead, now when retrieving items from the table we have to cast them to crime objects.
        TableView<?> tableView = (TableView<?>) tablePane.getCenter();
        Crime crime = (Crime) tableView.getSelectionModel().getSelectedItem();
        if (crime != null) {
            return crime.getCaseNumber();
        } else {
            return null;
        }
    }

    /**
     * Gets given a node which corresponding to a GridPane which holds a set of VBoxes with TextFields inside them.
     * It uses the lookup method on first the GridPane to get the VBox needed and then the VBox to find the TextField.
     * It then returns the string within the textField.
     *
     * @param centreNode    The GridPane node.
     * @return  The case number.
     */
    private String getFromEntry(Node centreNode) {
        GridPane gridEntry = (GridPane) centreNode;
        String selectedCrime = ((TextField) gridEntry.lookup("#generalInformation").lookup("#cNoText")).getText();
        if (Objects.equals(selectedCrime, "")) {
            return null;
        } else {
            return selectedCrime;
        }
    }
}
