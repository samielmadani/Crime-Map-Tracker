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
import javafx.scene.layout.GridPane;
import seng202.group7.Crime;
import seng202.group7.Report;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import javax.naming.directory.InvalidAttributeValueException;


/**
 * The controller, used by / linked to, the crime edit FXML file.
 * Makes a detailed view of the data retrieved from the current row selected in the ControllerData object.
 *
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

    private TextField dateText;
    private ArrayList<Node> allValues;
    private ArrayList<Node> editableValues;
    private ArrayList<Node> requiredValues;
    private ArrayList<Node> intValues;
    private ArrayList<Node> doubleValues;

    private PseudoClass errorClass;


    /**
     * This method is run during the loading of the crime edit fxml file.
     * It runs methods to get and then store values in the text fields of the screen.
     *
     * @param location      A URL object.
     * @param resources     A ResourceBundle object.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ControllerData master = ControllerData.getInstance();

        dateText = datePicker.getEditor();
        dateText.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                activeValidate(event);
            }
        });
    
        allValues = new ArrayList<>(Arrays.asList(cNoText, iucrText, fbiText, blockText, beatText, wardText, xCoordText, yCoordText, latText, longText,
        priText, secText, locAreaText, dateText, datePicker, arrestCheck, domesticCheck, timeText));
        editableValues = new ArrayList<>(Arrays.asList(iucrText, fbiText, blockText, beatText, wardText, xCoordText, yCoordText, latText, longText,
        priText, secText, locAreaText, dateText, datePicker, arrestCheck, domesticCheck, timeText));
        requiredValues = new ArrayList<>(Arrays.asList(cNoText, dateText, timeText, priText));
        intValues = new ArrayList<>(Arrays.asList(beatText, wardText, xCoordText, yCoordText));
        doubleValues = new ArrayList<>(Arrays.asList(latText, longText));
        
        errorClass = PseudoClass.getPseudoClass("error");

        data = master.getCurrentRow();
        if (data != null) {
            setData();
        } else {
            editEntry(new ActionEvent());
        }
    }

    public boolean validateRequired(Node inputBox) {
        boolean valid;
        valid = !((TextField) inputBox).getText().isEmpty();
        inputBox.pseudoClassStateChanged(errorClass, !valid);
        return valid;
    }

    public boolean validateText(Node inputBox) {
        boolean valid = true;
        if (intValues.contains(inputBox)) {
            valid &=  ((TextField) inputBox).getText().matches("\\d*");
            inputBox.pseudoClassStateChanged(errorClass, !valid);
        }
        if (doubleValues.contains(inputBox)) {
            valid &= ((TextField) inputBox).getText().matches("(-?)\\d*(\\.\\d+)?");
            inputBox.pseudoClassStateChanged(errorClass, !valid);
        }
        if (inputBox == dateText) {
            try {
                // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a", Locale.US);
                // return LocalDateTime.parse(date, formatter);
                DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate date = LocalDate.parse(((TextField) inputBox).getText(), dateFormat);
                datePicker.setValue(date);
            } catch (DateTimeParseException e) {
                valid = false;
                inputBox.pseudoClassStateChanged(errorClass, !valid);
            }
        }
        if (inputBox == timeText) {
            try {
                LocalTime.parse(((TextField) inputBox).getText());
            } catch (DateTimeParseException e) {
                valid = false;
                inputBox.pseudoClassStateChanged(errorClass, !valid);
            }
        }
        return valid;
    }

    public boolean validateValue(Node inputBox) {
        boolean valid = true;
        if (requiredValues.contains(inputBox)) {
            valid &= validateRequired(inputBox);
        }
        if (valid) {
            valid &= validateText(inputBox);
        }
        return valid;

    }

    public void activeValidate(KeyEvent event) {
        Node inputBox = (Node) event.getSource();
        validateValue(inputBox);
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

        beatText.setText(String.valueOf(data.getBeat()));
        wardText.setText(String.valueOf(data.getWard()));
        xCoordText.setText(String.valueOf(data.getXCoord()));
        yCoordText.setText(String.valueOf(data.getYCoord()));
        latText.setText(String.valueOf(data.getLatitude()));
        longText.setText(String.valueOf(data.getLatitude()));

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
     * @param event      The event action that was triggered.
     */
    public void fullDataView(ActionEvent event) {
        // As the side panels root is the main border panel we use .getRoot().
        BorderPane pane = (BorderPane) (((Node) event.getSource()).getScene()).getRoot();
        ControllerData master = ControllerData.getInstance();
        GridPane table = master.getTableState();
        pane.setCenter(table);
    }

    /**
     * Allows the user to edit the values in value boxes by enabling them all. Also changes to save/cancel buttons.
     * @param event
     */
    public void editEntry(ActionEvent event) {
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

    public void finishEdit(ActionEvent event) {
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

    public void saveEdit(ActionEvent event) throws InvalidAttributeValueException {
        boolean valid = true;
        ArrayList<String> invalidAttributes = new ArrayList<>();
        for (Node node : allValues) {
            if (!validateValue(node)) {
                valid = false;
                invalidAttributes.add(node.getId());
            }
        }
        if (!valid) {
            // TODO add error messages
            return;
        } 

        // CheckBoxes:
        Boolean arrest = arrestCheck.isSelected();
        Boolean domestic = domesticCheck.isSelected();
        LocalDateTime date = null;
        // General Information:
        if (dateText.getText() != null && timeText.getText() != null) {
            // String dateTime = String.join("",dateText.getText(), "T", timeText.getText());
            LocalTime time = LocalTime.parse(timeText.getText());
            date = LocalDateTime.of(datePicker.getValue(), time);
            // date = LocalDateTime.parse(dateTime);
        }
        String caseNumber = cNoText.getText();
        String iucr = iucrText.getText();
        String fbiCD = fbiText.getText();

        // Location Information:
        String block = blockText.getText();
        Integer beat = null;
        if (!beatText.getText().isEmpty()) {
            beat = Integer.parseInt(beatText.getText());
        }

        Integer ward = null;
        if (!wardText.getText().isEmpty()) {
            ward = Integer.parseInt(wardText.getText());
        }

        Integer xCoord = null;
        if (!xCoordText.getText().isEmpty()) {
            xCoord = Integer.parseInt(xCoordText.getText());
        }

        Integer yCoord = null;
        if (!yCoordText.getText().isEmpty()) {
            yCoord = Integer.parseInt(yCoordText.getText());
        }

        Double latitude = null;
        if (!latText.getText().isEmpty()) {
            latitude = Double.parseDouble(yCoordText.getText());
        }

        Double longitude = null;
        if (!longText.getText().isEmpty()) {
            longitude = Double.parseDouble(latText.getText());
        }

        // coordsText.setText(coords);
        // String pos = "("+data.getLatitude()+", "+data.getLongitude()+")";
        // locText.setText(pos);

        // Case Description:
        String primaryDescription = priText.getText();
        String secondaryDescription = secText.getText();
        String locationDescription = locAreaText.getText();

        if (data == null) {
            data = new Crime(caseNumber, date, block, iucr, primaryDescription, secondaryDescription, locationDescription, arrest, domestic, beat, ward, fbiCD, xCoord, yCoord, latitude, longitude);
            ControllerData.getInstance().addReports(new ArrayList<Report>(Arrays.asList(data)));
        } else {
            data.update(caseNumber, date, block, iucr, primaryDescription, secondaryDescription, locationDescription, arrest, domestic, beat, ward, fbiCD, xCoord, yCoord, latitude, longitude);
        }
        finishEdit(event);
    }

}
