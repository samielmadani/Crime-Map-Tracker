package seng202.group7.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import seng202.group7.data.QueryBuilder;

/**
 * Controller class. Linked to filter menu FXML.
 * Handles initialization of filter type boxes.
 * Links GUI to filter class.
 *
 * @author Sami Elmadani
 * @author John Elliott
 */
public class FilterController implements Initializable {

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<String> primaryBox;

    @FXML
    private ComboBox<String> locationBox;

    @FXML
    private TextField wardField;

    @FXML
    private TextField beatField;

    @FXML
    private ComboBox<String> arrestBox;

    @FXML
    private ComboBox<String> domesticBox;

    /**
     * Possible pseudoClasses for the class, errorClass changes formatting for invalid entries and the others 
     * alert the validation class what validation is required
     */
    private PseudoClass integerFormat = PseudoClass.getPseudoClass("integer");
    private PseudoClass dateFormat = PseudoClass.getPseudoClass("date");
    private PseudoClass dateEditor = PseudoClass.getPseudoClass("dateEditor");

    private ArrayList<Node> allValues;

    /**
     * This method is run during the loading of the data view fxml file.
     * It generates what values will be stored in the columns.
     *
     * @param location      A URL object.
     * @param resources     A ResourceBundle object.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        primaryBox.setItems(FXCollections.observableArrayList(
                null, "THEFT", "ASSAULT", "DECEPTIVE PRACTICE", "BATTERY", "HOMICIDE", "OTHER OFFENSE",
                "CRIMINAL DAMAGE", "WEAPONS VIOLATION", "CRIMINAL TRESPASS", "ARSON", "MOTOR VEHICLE THEFT",
                "ROBBERY", "STALKING", "BURGLARY", "OFFENSE INVOLVING CHILDREN", "SEX OFFENSE", "CRIMINAL SEXUAL ASSAULT",
                "NARCOTICS", "PUBLIC PEACE VIOLATION", "PROSTITUTION", "INTERFERENCE WITH PUBLIC OFFICER",
                "INTIMIDATION", "CONCEALED CARRY LICENSE VIOLATION", "KIDNAPPING", "LIQUOR LAW VIOLATION",
                "OTHER NARCOTIC VIOLATION").sorted());

        locationBox.setItems(FXCollections.observableArrayList(
                null, "APARTMENT", "STREET", "SIDEWALK", "PARK PROPERTY", "RESIDENCE", "DRUG STORE",
                "DEPARTMENT STORE", "PARKING LOT / GARAGE (NON RESIDENTIAL)", "ALLEY", "RESIDENCE - PORCH / HALLWAY",
                "SMALL RETAIL STORE", "AUTO", "RESTAURANT", "GROCERY FOOD STORE", "CTA PLATFORM", "GAS STATION",
                "POLICE FACILITY / VEHICLE PARKING LOT", "TAXICAB", "RESIDENCE - YARD (FRONT / BACK)",
                "CONVENIENCE STORE", "GOVERNMENT BUILDING / PROPERTY", "OTHER (SPECIFY)", "CTA TRAIN",
                "SCHOOL - PRIVATE GROUNDS", "LAKEFRONT / WATERFRONT / RIVERBANK", "DRIVEWAY - RESIDENTIAL",
                "VEHICLE NON-COMMERCIAL", "HOSPITAL BUILDING / GROUNDS", "AUTO / BOAT / RV DEALERSHIP",
                "SCHOOL - PRIVATE BUILDING", "BAR OR TAVERN", "OTHER COMMERCIAL TRANSPORTATION", "CTA BUS STOP",
                "RESIDENCE - GARAGE", "SCHOOL - PUBLIC BUILDING", "COMMERCIAL / BUSINESS OFFICE", "VEHICLE - COMMERCIAL",
                "CHA PARKING LOT / GROUNDS", "MEDICAL / DENTAL OFFICE", "NURSING / RETIREMENT HOME",
                "ABANDONED BUILDING", "CLEANING STORE", "APPLIANCE STORE", "CHURCH / SYNAGOGUE / PLACE OF WORSHIP",
                "ATM (AUTOMATIC TELLER MACHINE)", "BANK", "HOTEL / MOTEL",
                "VEHICLE - OTHER RIDE SHARE SERVICE (LYFT, UBER, ETC.)", "CAR WASH",
                "COLLEGE / UNIVERSITY - RESIDENCE HALL", "JAIL / LOCK-UP FACILITY",
                "OTHER RAILROAD PROPERTY / TRAIN DEPOT", "CHA APARTMENT", "TAVERN / LIQUOR STORE", "CONSTRUCTION SITE",
                "CTA STATION", "PARKING LOT", "AIRPORT PARKING LOT", "AIRPORT TERMINAL UPPER LEVEL - SECURE AREA",
                "CTA PARKING LOT / GARAGE / OTHER PROPERTY", "ATHLETIC CLUB", "CHA HALLWAY / STAIRWELL / ELEVATOR",
                "CTA BUS", "BOWLING ALLEY", "AIRPORT TERMINAL LOWER LEVEL - NON-SECURE AREA", "VACANT LOT / LAND",
                "AIRPORT BUILDING NON-TERMINAL - NON-SECURE AREA", "BARBERSHOP", "BRIDGE", "COLLEGE / UNIVERSITY - GROUNDS",
                "VACANT LOT", "COIN OPERATED MACHINE", "LIBRARY", "CEMETERY", "CURRENCY EXCHANGE", "MOVIE HOUSE / THEATER",
                "HIGHWAY / EXPRESSWAY", "FACTORY / MANUFACTURING BUILDING", "POOL ROOM",
                "AIRPORT EXTERIOR - NON-SECURE AREA", "ANIMAL HOSPITAL", "PAWN SHOP",
                "AIRPORT BUILDING NON-TERMINAL - SECURE AREA", "AIRCRAFT", "WAREHOUSE",
                "AIRPORT TERMINAL UPPER LEVEL - NON-SECURE AREA", "BOAT / WATERCRAFT", "PORCH", "SCHOOL - PUBLIC GROUNDS",
                "HOUSE", "DAY CARE CENTER", "AIRPORT VENDING ESTABLISHMENT", "SPORTS ARENA / STADIUM").sorted());

        arrestBox.getItems().addAll(null, "Y", "N");

        domesticBox.getItems().addAll(null, "Y", "N");

        prepareValidation();
    }

    /**
     * Sets the types of validation required on each input node
     */
    private void prepareValidation() {
        TextField dateText = datePicker.getEditor();
        dateText.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                try {
                    DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("d/M/yyyy");
                    LocalDate date = LocalDate.parse(dateText.getText(), dateTimeFormat);
                    datePicker.setValue(date);
                } catch (DateTimeParseException e) {
                    return;
                } finally {
                    activeValidate(event);
                }
            }
        });

        allValues = new ArrayList<>(Arrays.asList(datePicker, dateText, primaryBox, locationBox, wardField, beatField,
            arrestBox, domesticBox));
        
        datePicker.valueProperty().addListener((observable, oldDate, newDate)->{
            DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("d/M/yyyy");
            dateText.setText(datePicker.getValue().format(dateTimeFormat));
            ControllerData.getInstance().validate(dateText);
        });

        dateText.pseudoClassStateChanged(dateFormat, true);
        dateText.pseudoClassStateChanged(dateEditor, true);
    
        wardField.pseudoClassStateChanged(integerFormat, true);
        beatField.pseudoClassStateChanged(integerFormat, true);
    }

    /**
     * When a key is pressed on a node with this set, send the node to validation
     * @param event The keyboard event trigger.
     */
    public void activeValidate(KeyEvent event) {
        Node inputBox = (Node) event.getSource();
        ControllerData.getInstance().validate(inputBox);
    }

    /**
     * Makes an array list with all the user input conditions given to filter with.
     *
     * @param event   The event action that was triggered.
     */
    public void viewFilteredResults(ActionEvent event) throws IOException {
        for (Node node : allValues) {
            if (!ControllerData.getInstance().validate(node)) {
                return;
            }
        }
        String query = QueryBuilder.where(datePicker.getValue(), primaryBox.getValue(), locationBox.getValue(),
                getIntegerFromString(wardField.getText()), getIntegerFromString(beatField.getText()),
                getBooleanFromString(arrestBox.getValue()), getBooleanFromString(domesticBox.getValue()));

        // By setting this where query when the paginator is generated the data accessor will apply it to the search.
        ControllerData.getInstance().setWhereQuery(query);
        // As the side panels root is the main border panel we use .getRoot().
        BorderPane pane = (BorderPane) (((Node) event.getSource()).getScene()).getRoot();
        BorderPane tableView = FXMLLoader.load(Objects.requireNonNull(MenuController.class.getResource("/gui/pages.fxml")));
        // Changes side menu to the filter menu.
        pane.setCenter(tableView);
    }

    private Integer getIntegerFromString(String str) {
        if(str.equals("")){
            return null;
        }
        return Integer.parseInt(str);
    }

    private Boolean getBooleanFromString(String str) {
        if(str == null){
            return null;
        }
        return str.equals("Y") ? true : false;
    }

    public void clearDate(){
        datePicker.setValue(null);
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

        // This removes the current search effect being applied to the table when the paginator is initialized.
        ControllerData.getInstance().setWhereQuery("");
        BorderPane tableView = FXMLLoader.load(Objects.requireNonNull(MenuController.class.getResource("/gui/pages.fxml")));
        // Changes side menu to the filter menu.
        pane.setCenter(tableView);
    }
}
