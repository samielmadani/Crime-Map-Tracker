package seng202.group7.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import seng202.group7.data.Crime;
import javafx.css.PseudoClass;
import seng202.group7.data.DataAccessor;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;


/**
 * The controller, used by / linked to, the crime edit FXML file.
 * Makes a detailed view of the data retrieved from the current row selected in the ControllerData object.
 *
 * @author Jack McCorkindale
 * @author John Elliott
 */
public class EntryController implements Initializable {
    /**
     * The current selected rows crime object.
     */
    Crime data;

    @FXML
    private TextField cNoText, iucrText, fbiText, blockText, beatText,
                      wardText, xCoordText, yCoordText, latText, longText, priText, timeText;
    @FXML
    private TextArea secText, locAreaText;
    @FXML
    private DatePicker datePicker;
    @FXML
    private CheckBox arrestCheck, domesticCheck;
    @FXML
    private Button editButton, deleteButton, saveButton, cancelButton;
    @FXML
    private Node frame;

    private TextField dateText;

    private ArrayList<Node> allValues, editableValues;

    /**
     * Possible pseudoClasses for the class, errorClass changes formatting for invalid entries and the others 
     * alert the validation class what validation is required
     */
    private final PseudoClass required = PseudoClass.getPseudoClass("required");
    private final PseudoClass doubleFormat = PseudoClass.getPseudoClass("double");
    private final PseudoClass integerFormat = PseudoClass.getPseudoClass("integer");
    private final PseudoClass dateFormat = PseudoClass.getPseudoClass("date");
    private final PseudoClass dateEditor = PseudoClass.getPseudoClass("dateEditor");
    private final PseudoClass timeFormat = PseudoClass.getPseudoClass("time");
    private final PseudoClass uniqueId = PseudoClass.getPseudoClass("id");


    /**
     * This method is run during the loading of the crime edit fxml file.
     * It initializes the value validation classes before running methods to get
     * and store values in the text fields of the screen.
     *
     * @param location      A URL object.
     * @param resources     A ResourceBundle object.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ControllerData master = ControllerData.getInstance();
        dateText = datePicker.getEditor();

        prepareValidation();
    
        allValues = new ArrayList<>(Arrays.asList(cNoText, iucrText, fbiText, blockText, beatText, wardText, xCoordText, yCoordText, latText, longText,
        priText, secText, locAreaText, dateText, datePicker, arrestCheck, domesticCheck, timeText));
        editableValues = new ArrayList<>(Arrays.asList(iucrText, fbiText, blockText, beatText, wardText, xCoordText, yCoordText, latText, longText,
        priText, secText, locAreaText, dateText, datePicker, arrestCheck, domesticCheck, timeText));

        data = master.getCurrentRow();
        if (data != null) {
            setData();
        } else {
            cNoText.pseudoClassStateChanged(uniqueId, true);
            editEntry();
        }
    }

    /**
     * Sets the types of validation required on each input node
     */
    private void prepareValidation() {
        dateText.setOnKeyTyped(event -> {
            try {
                DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("d/M/yyyy");
                LocalDate date = LocalDate.parse(dateText.getText(), dateTimeFormat);
                datePicker.setValue(date);
            } catch (DateTimeParseException ignored) {
            } finally {
                activeValidate(event);
            }
        });

        datePicker.valueProperty().addListener((observable, oldDate, newDate)->{
            DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("d/M/yyyy");
            dateText.setText(datePicker.getValue().format(dateTimeFormat));
            ControllerData.getInstance().validate(dateText);
        });

        cNoText.pseudoClassStateChanged(required, true);
        dateText.pseudoClassStateChanged(required, true);
        timeText.pseudoClassStateChanged(required, true);
        priText.pseudoClassStateChanged(required, true);
        secText.pseudoClassStateChanged(required, true);

        beatText.pseudoClassStateChanged(integerFormat, true);
        wardText.pseudoClassStateChanged(integerFormat, true);
        xCoordText.pseudoClassStateChanged(integerFormat, true);
        yCoordText.pseudoClassStateChanged(integerFormat, true);

        latText.pseudoClassStateChanged(doubleFormat, true);
        longText.pseudoClassStateChanged(doubleFormat, true);

        dateText.pseudoClassStateChanged(dateFormat, true);
        timeText.pseudoClassStateChanged(timeFormat, true);

        dateText.pseudoClassStateChanged(dateEditor, true);

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
     *  This method gets all the related data from the crime object and set it as the default text in its relevant field.
     */
    private void setData() {
        // CheckBoxes:
        arrestCheck.setSelected(data.getArrest());
        domesticCheck.setSelected(data.getDomestic());
        // General Information:
        cNoText.setText(data.getCaseNumber());
        LocalDateTime date = data.getDate();
        if (date == null) {
            datePicker.setValue(null);
            timeText.setText(null);
        } else {
            datePicker.setValue(date.toLocalDate());
            String time = date.getHour() + ":" + String.format("%02d", date.getMinute());
            timeText.setText(time);
        }

        iucrText.setText(data.getIucr());
        fbiText.setText(data.getFbiCD());
        // Location Information:
        blockText.setText(data.getBlock());
        if (data.getBeat() != null) {
            beatText.setText(String.valueOf(data.getBeat()));
        }
        if (data.getWard() != null) {
            wardText.setText(String.valueOf(data.getWard()));
        }
        if (data.getXCoord() != null) {
            xCoordText.setText(String.valueOf(data.getXCoord()));
        }
        if (data.getYCoord() != null) {
            yCoordText.setText(String.valueOf(data.getYCoord()));
        }
        if (data.getLatitude() != null) {
            latText.setText(String.valueOf(data.getLatitude()));
        }
        if (data.getLongitude() != null) {
            longText.setText(String.valueOf(data.getLongitude()));
        }

        // Case Description:
        priText.setText(data.getPrimaryDescription());
        secText.setText(data.getSecondaryDescription());
        locAreaText.setText(data.getLocationDescription());

        for (Node node : allValues) {
            node.setDisable(true);
        }

    }

    /**
     * Returns to the stored table state by retrieving it from the ControllerData object and setting it on the root pane.
     */
    public void returnView() {
        BorderPane pane = (BorderPane) frame.getParent();
        ControllerData controllerData = ControllerData.getInstance();
        Node table = controllerData.getTableState();
        pane.setCenter(table);
    }

    /**
     * Allows the user to edit the values in value boxes by enabling them all. Also changes to save/cancel buttons.
     */
    public void editEntry() {
        editButton.setVisible(false);
        editButton.setManaged(false);
        deleteButton.setVisible(false);
        deleteButton.setManaged(false);

        saveButton.setVisible(true);
        saveButton.setManaged(true);
        cancelButton.setVisible(true);
        cancelButton.setManaged(true);

        for (Node node : editableValues) {
            node.setDisable(false);
        }
    }

    /**
     * Checks if there is valid data, if there is, modifies the visible buttons and calls the method
     * to fill all fields with the data. Otherwise, returns to previous view.
     */
    public void finishEdit() {
        if (data == null) {
            returnView();
            return;
        }
        editButton.setVisible(true);
        editButton.setManaged(true);
        deleteButton.setVisible(true);
        deleteButton.setManaged(true);

        saveButton.setVisible(false);
        saveButton.setManaged(false);
        cancelButton.setVisible(false);
        cancelButton.setManaged(false);
        setData();
    }

    /**
     * Checks if all the data is valid, then modifies the existing data object to reflect the changes the user made.
     */
    public void saveEdit() {
        boolean valid = true;
        for (Node node : allValues) {
            if (!ControllerData.getInstance().validate(node)) {
                valid = false;
            }
        }
        if (!valid) {
            return;
        } 

        // CheckBoxes:
        Boolean arrest = arrestCheck.isSelected();
        Boolean domestic = domesticCheck.isSelected();
        // General Information:
        
        String caseNumber = cNoText.getText();
        LocalDateTime date = getDateTime();
        String iucr = iucrText.getText();
        String fbiCD = fbiText.getText();

        // Location Information:
        String block = blockText.getText();

        Integer beat = getInt(beatText);
        Integer ward = getInt(wardText);
        Integer xCoord = getInt(xCoordText);
        Integer yCoord = getInt(yCoordText);
 
        Double latitude = getDouble(latText);
        Double longitude = getDouble(longText);

        // Case Description:
        String primaryDescription = priText.getText();
        String secondaryDescription = secText.getText();
        String locationDescription = locAreaText.getText();

        DataAccessor dataAccessor = DataAccessor.getInstance();

        cNoText.pseudoClassStateChanged(uniqueId, false);
        data = new Crime(caseNumber, date, block, iucr, primaryDescription, secondaryDescription, locationDescription, arrest, domestic, beat, ward, fbiCD, xCoord, yCoord, latitude, longitude);
        dataAccessor.editCrime(data);

        finishEdit();
    }

    /**
     * Converts the LocalDate and LocalTime values from datePicker and timeText into a LocalDateTime object.
     *
     * @return LocalDateTime combination of datePicker and timeText
     */
    private LocalDateTime getDateTime() {
        if (dateText.getText() != null && timeText.getText() != null) {
            DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("H:mm");
            LocalTime time = LocalTime.parse(timeText.getText(), timeFormat);
            return LocalDateTime.of(datePicker.getValue(), time);
        }
        return null;
    }

    /**
     * Turns a TextField node into an integer value.
     *
     * @param node      A TextField to convert to an Integer
     * @return          The integer value.
     */
    private Integer getInt(TextField node) {
        if (!node.getText().isEmpty()) {
            return Integer.parseInt(node.getText());
        }
        return null;
    }

    /**
     * Turns a TextField node into a Double value.
     *
     * @param node      A TextField to convert to a Double
     * @return          The double value.
     */
    private Double getDouble(TextField node) {
        if (!node.getText().isEmpty()) {
            return Double.parseDouble(node.getText());
        }
        return null;
    }

    /**
     * Deletes the entry that is currently being viewed.
     */
    public void deleteEntry() {
        DataAccessor dataAccessor = DataAccessor.getInstance();

        dataAccessor.delete(cNoText.getText());
        returnView();
    }

}
