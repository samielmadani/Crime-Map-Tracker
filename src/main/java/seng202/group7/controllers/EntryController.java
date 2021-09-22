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
    private ArrayList<Node> allValues, editableValues, requiredValues;
    private ArrayList<TextField> intValues, doubleValues;

    private PseudoClass errorClass;


    /**
     * This method is run during the loading of the crime edit fxml file.
     * It initializes the ArrayLists for value validation before running methods to get
     * and store values in the text fields of the screen.
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

    /**
     * Checks if the input has a value and adds the error class if it is invalid.
     * @param inputBox The input to be validated
     * @return If the field has an entry
     */
    private boolean validateRequired(TextField inputBox) {
        boolean valid;
        valid = !(inputBox.getText().isEmpty());
        inputBox.pseudoClassStateChanged(errorClass, !valid);
        return valid;
    }

    /**
     * Validates the value in each box. The validation is currently limited to Integer, Double, Date, and Time. <p>
     * For a input box to be validated against a condition it must be added to the ArrayList during initialization.
     * @param inputBox The input to be validated
     * @return If the input is valid
     */
    private boolean validateText(TextField inputBox) {
        boolean valid = true;
        if (intValues.contains(inputBox)) {
            valid &=  inputBox.getText().matches("\\d*");
        }
        if (doubleValues.contains(inputBox)) {
            valid &= inputBox.getText().matches("(-?)\\d*(\\.\\d+)?");
        }
        if (inputBox == dateText) {
            try {
                DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate date = LocalDate.parse(inputBox.getText(), dateFormat);
                datePicker.setValue(date);
            } catch (DateTimeParseException e) {
                valid = false;
            }
        }
        if (inputBox == timeText) {
            try {
                LocalTime.parse(inputBox.getText());
            } catch (DateTimeParseException e) {
                valid = false;
            }
        }
        inputBox.pseudoClassStateChanged(errorClass, !valid);
        return valid;
    }

    /**
     * Passes the value through the required validation methods
     * @param inputBox The input to be validated
     * @return If the input is valid
     */
    private boolean validate(TextField inputBox) {
        boolean valid = true;
        if (requiredValues.contains(inputBox)) {
            valid &= validateRequired(inputBox);
        }
        if (valid) {
            valid &= validateText(inputBox);
        }
        return valid;

    }

    /**
     * When a key is pressed on a node with this set, send the node to validation
     * @param event The keyboard event trigger.
     */
    public void activeValidate(KeyEvent event) {
        TextField inputBox = (TextField) event.getSource();
        validate(inputBox);
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
     * @param event The action event that was triggered.
     */
    public void returnView(ActionEvent event) {
        // As the side panels root is the main border panel we use .getRoot().
        BorderPane pane = (BorderPane) (((Node) event.getSource()).getScene()).getRoot();
        ControllerData master = ControllerData.getInstance();
        pane.setCenter(master.getTableState());
    }

    /**
     * Allows the user to edit the values in value boxes by enabling them all. Also changes to save/cancel buttons.
     * @param event The action event that was triggered.
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

    /**
     * Checks if there is valid data, if there is, modifies the visible buttons and calls the method
     * to fill all fields with the data. Otherwise returns to previous view.
     * @param event The action event that was triggered.
     */
    public void finishEdit(ActionEvent event) {
        if (data == null) {
            returnView(event);
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
     * @param event The action event that was triggered.
     */
    public void saveEdit(ActionEvent event) {
        boolean valid = true;
        ArrayList<String> invalidAttributes = new ArrayList<>();
        for (Node node : allValues) {
            try {
                if (!validate((TextField) node)) {
                    valid = false;
                    invalidAttributes.add(node.getId());
                }
            } catch (Exception e) {
                //TODO: handle exception
                
            }
        }
        if (!valid) {
            // TODO add error messages
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

        if (data == null) {
            data = new Crime(caseNumber, date, block, iucr, primaryDescription, secondaryDescription, locationDescription, arrest, domestic, beat, ward, fbiCD, xCoord, yCoord, latitude, longitude);
            //ControllerData.getInstance().addReports(new ArrayList<Report>(Arrays.asList(data)));
        } else {
            try {
                data.update(caseNumber, date, block, iucr, primaryDescription, secondaryDescription, locationDescription, arrest, domestic, beat, ward, fbiCD, xCoord, yCoord, latitude, longitude);
            } catch (InvalidAttributeValueException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        finishEdit(event);
    }

    /**
     * Converts the LocalDate and LocalTime values from datePicker and timeText into a LocalDateTime object
     * @return LocalDateTime combination of datePicker and timeText
     */
    private LocalDateTime getDateTime() {
        if (dateText.getText() != null && timeText.getText() != null) {
            LocalTime time = LocalTime.parse(timeText.getText());
            return LocalDateTime.of(datePicker.getValue(), time);
        }
        return null;
    }

    /**
     * Turns a TextField node into an integer value.
     * @param node A TextField to convert to an Integer
     * @return
     */
    private Integer getInt(TextField node) {
        if (!node.getText().isEmpty()) {
            return Integer.parseInt(node.getText());
        }
        return null;
    }

    /**
     * Turns a TextField node into a Double value.
     * @param node A TextField to convert to a Double
     * @return 
     */
    private Double getDouble(TextField node) {
        if (!node.getText().isEmpty()) {
            return Double.parseDouble(node.getText());
        }
        return null;
    }

}
