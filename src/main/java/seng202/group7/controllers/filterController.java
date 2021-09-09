package seng202.group7.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;


/**
 * Controller class. Linked to filter menu FXML.
 * Handles initialization of filter type boxes.
 * Links GUI to filter class.
 *
 * @author John Elliott
 * @author Sami Elmadani
 */
public class filterController implements Initializable {

    @FXML
    public TextField wardInput;

    @FXML
    public TextField beatInput;

    @FXML
    private ChoiceBox crimeType;

    /**
     * This method is run during the loading of the data view fxml file.
     * It generates what values will be stored in the columns.
     *
     * @param location      A URL object.
     * @param resources     A ResourceBundle object.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<String> crimeTypes = new ArrayList<>(Arrays.asList(
                "THEFT",
                "ASSAULT",
                "DECEPTIVE PRACTICE",
                "BATTERY",
                "HOMICIDE",
                "OTHER OFFENSE",
                "CRIMINAL DAMAGE",
                "WEAPONS VIOLATION",
                "CRIMINAL TRESPASS",
                "ARSON",
                "MOTOR VEHICLE THEFT",
                "ROBBERY",
                "STALKING",
                "BURGLARY",
                "OFFENSE INVOLVING CHILDREN",
                "SEX OFFENSE",
                "CRIMINAL SEXUAL ASSAULT",
                "NARCOTICS",
                "PUBLIC PEACE VIOLATION",
                "PROSTITUTION",
                "INTERFERENCE WITH PUBLIC OFFICER",
                "INTIMIDATION",
                "CONCEALED CARRY LICENSE VIOLATION",
                "KIDNAPPING",
                "LIQUOR LAW VIOLATION",
                "OTHER NARCOTIC VIOLATION"));

        for (String crime : crimeTypes) {
            crimeType.getItems().add(crime);
        }

    }


    /**
     * Checks user input given is digits only.
     *
     * @param currentField      The text field being filled in by user.
     * @return inputInt         A digit only user input
     */
    private Integer numberOnly(TextField currentField) {

        String input = currentField.getText();
        if (!input.matches("\\d*")) {
            currentField.setText(input.replaceAll("[^\\d]", ""));
        }

        if (currentField.getText().length() != 0) {
            return Integer.parseInt(currentField.getText());
        }

        return -1;
    }


    /**
     * Checks ward input is given a value between 0 and 50.
     *
     * @param event     The event action that was triggered.
     */
    public void wardCheck(KeyEvent event) {
        Integer input = numberOnly(wardInput);
        if (input != -1) {
            if (input < 0) {
                wardInput.setText("0");
            } else if (input > 50) {
                wardInput.setText("50");
            }
        }

        wardInput.positionCaret(wardInput.getText().length());

    }

    /**
     * Checks beat is given a value between 0 and 2000.
     *
     * @param event     The event action that was triggered.
     */
    public void beatCheck(KeyEvent event) {
        Integer input = numberOnly(beatInput);
        if (input != -1) {
            if (input < 0) {
                beatInput.setText("0");
            } else if (input > 2000) {
                beatInput.setText("2000");
            }
        }

        beatInput.positionCaret(beatInput.getText().length());
    }



}

