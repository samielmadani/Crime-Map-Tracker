package seng202.group7.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * Controller class. Linked to filter menu FXML.
 * Handles initialization of filter type boxes.
 * Links GUI to filter class.
 *
 * @author Sami Elmadani
 * @author John Elliott
 */
public class filterController implements Initializable {

    @FXML
    public DatePicker dateRange;

    @FXML
    private ChoiceBox crimeType;

    @FXML
    private ChoiceBox locationType;

    @FXML
    private TextField wardInput;

    @FXML
    private TextField beatInput;

    private String arrest = "N";
    private String domestic = "N";

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

        Collections.sort(crimeTypes);
        for (String crime : crimeTypes) {
            crimeType.getItems().add(crime);
        }

        ArrayList<String> locationTypes = new ArrayList<>(Arrays.asList(
                "APARTMENT",
                "STREET",
                "SIDEWALK",
                "PARK PROPERTY",
                "RESIDENCE",
                "DRUG STORE",
                "DEPARTMENT STORE",
                "PARKING LOT / GARAGE (NON RESIDENTIAL)",
                "ALLEY",
                "RESIDENCE - PORCH / HALLWAY",
                "SMALL RETAIL STORE",
                "AUTO",
                "RESTAURANT",
                "GROCERY FOOD STORE",
                "CTA PLATFORM",
                "GAS STATION",
                "POLICE FACILITY / VEHICLE PARKING LOT",
                "TAXICAB",
                "RESIDENCE - YARD (FRONT / BACK)",
                "CONVENIENCE STORE",
                "GOVERNMENT BUILDING / PROPERTY",
                "OTHER (SPECIFY)",
                "CTA TRAIN",
                "SCHOOL - PRIVATE GROUNDS",
                "LAKEFRONT / WATERFRONT / RIVERBANK",
                "DRIVEWAY - RESIDENTIAL",
                "VEHICLE NON-COMMERCIAL",
                "HOSPITAL BUILDING / GROUNDS",
                "AUTO / BOAT / RV DEALERSHIP",
                "SCHOOL - PRIVATE BUILDING",
                "BAR OR TAVERN",
                "OTHER COMMERCIAL TRANSPORTATION",
                "CTA BUS STOP",
                "RESIDENCE - GARAGE",
                "SCHOOL - PUBLIC BUILDING",
                "COMMERCIAL / BUSINESS OFFICE",
                "VEHICLE - COMMERCIAL",
                "CHA PARKING LOT / GROUNDS",
                "MEDICAL / DENTAL OFFICE",
                "NURSING / RETIREMENT HOME",
                "ABANDONED BUILDING",
                "CLEANING STORE",
                "APPLIANCE STORE",
                "CHURCH / SYNAGOGUE / PLACE OF WORSHIP",
                "ATM (AUTOMATIC TELLER MACHINE)",
                "BANK",
                "HOTEL / MOTEL",
                "VEHICLE - OTHER RIDE SHARE SERVICE (LYFT, UBER, ETC.)",
                "CAR WASH",
                "COLLEGE / UNIVERSITY - RESIDENCE HALL",
                "JAIL / LOCK-UP FACILITY",
                "OTHER RAILROAD PROPERTY / TRAIN DEPOT",
                "CHA APARTMENT",
                "TAVERN / LIQUOR STORE",
                "CONSTRUCTION SITE",
                "CTA STATION",
                "PARKING LOT",
                "AIRPORT PARKING LOT",
                "AIRPORT TERMINAL UPPER LEVEL - SECURE AREA",
                "CTA PARKING LOT / GARAGE / OTHER PROPERTY",
                "ATHLETIC CLUB",
                "CHA HALLWAY / STAIRWELL / ELEVATOR",
                "CTA BUS",
                "BOWLING ALLEY",
                "AIRPORT TERMINAL LOWER LEVEL - NON-SECURE AREA",
                "VACANT LOT / LAND",
                "AIRPORT BUILDING NON-TERMINAL - NON-SECURE AREA",
                "BARBERSHOP",
                "BRIDGE",
                "COLLEGE / UNIVERSITY - GROUNDS",
                "VACANT LOT",
                "COIN OPERATED MACHINE",
                "LIBRARY",
                "CEMETARY",
                "CURRENCY EXCHANGE",
                "MOVIE HOUSE / THEATER",
                "HIGHWAY / EXPRESSWAY",
                "FACTORY / MANUFACTURING BUILDING",
                "POOL ROOM",
                "AIRPORT EXTERIOR - NON-SECURE AREA",
                "ANIMAL HOSPITAL",
                "PAWN SHOP",
                "AIRPORT BUILDING NON-TERMINAL - SECURE AREA",
                "AIRCRAFT",
                "WAREHOUSE",
                "AIRPORT TERMINAL UPPER LEVEL - NON-SECURE AREA",
                "BOAT / WATERCRAFT",
                "PORCH",
                "SCHOOL - PUBLIC GROUNDS",
                "HOUSE",
                "DAY CARE CENTER",
                "AIRPORT VENDING ESTABLISHMENT",
                "SPORTS ARENA / STADIUM"));

        Collections.sort(locationTypes);
        for (String place : locationTypes) {
            locationType.getItems().add(place);
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

    /**
     * Toggles the value of whether an arrest was involved between Y and N.
     *
     * @param actionEvent   The event action that was triggered.
     */
    public void toggleArrest(ActionEvent actionEvent) {
        if (arrest == "N") {
            arrest = "Y";
        } else {
            arrest = "N";
        }
    }

    /**
     * Toggles the value of whether domestic violence was involved between Y and N.
     *
     * @param actionEvent   The event action that was triggered.
     */
    public void toggleDomestic(ActionEvent actionEvent) {
        if (domestic == "N") {
            domestic = "Y";
        } else {
            domestic = "N";
        }
    }

    /**
     * Makes an array list with all the user input conditions given to filter with.
     *
     * @param actionEvent   The event action that was triggered.
     */
    public void viewFilteredResults(ActionEvent actionEvent) {
        ArrayList conditions = new ArrayList(Arrays.asList(
                dateRange.getValue(),
                crimeType.getValue(),
                locationType.getValue(),
                wardInput.getText(),
                beatInput.getText(),
                arrest,
                domestic));

        // System.out.println(conditions);
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
}

