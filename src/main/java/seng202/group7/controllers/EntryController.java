package seng202.group7.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import seng202.group7.Crime;
import seng202.group7.Report;
import javafx.event.ActionEvent;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
            wardText, coordsText, locText, priText;
    @FXML
    private TextArea secText, locAreaText;
    @FXML
    private DatePicker dateText;
    @FXML
    private CheckBox arrestCheck, domesticCheck;
    @FXML
    private Button editButton, deleteButton, saveButton, cancelButton;

    ArrayList<Node> editableValues;


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
        editableValues = new ArrayList<>(Arrays.asList(iucrText, fbiText, blockText, beatText, wardText, coordsText, locText,
        priText, secText, locAreaText, dateText, arrestCheck, domesticCheck));

        data = master.getCurrentRow();
        if (data != null) {
            setData();
        } else {
            editEntry(new ActionEvent());
        }
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
        LocalDate date = data.getDate().toLocalDate();
        dateText.setValue(date);
        iucrText.setText(data.getIucr());
        fbiText.setText(data.getFbiCD());
        // Location Information:
        blockText.setText(data.getBlock());
        beatText.setText(String.valueOf(data.getBeat()));
        wardText.setText(String.valueOf(data.getWard()));
        String coords = "("+data.getXCoord()+", "+data.getYCoord()+")";
        coordsText.setText(coords);
        String pos = "("+data.getLatitude()+", "+data.getLongitude()+")";
        locText.setText(pos);
        // Case Description:
        priText.setText(data.getPrimaryDescription());
        secText.setText(data.getSecondaryDescription());
        locAreaText.setText(data.getLocationDescription());

        for (Node node : editableValues) {
            node.setDisable(true);
        }
        cNoText.setDisable(true);

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
     * Changes the edit button and shows the save and cancel buttons
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
        // CheckBoxes:
        boolean arrest = arrestCheck.isSelected();
        boolean domestic = domesticCheck.isSelected();
        
        // General Information:
        // TODO Date
        LocalDateTime date = LocalDateTime.now();
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
        // TODO change coords & lat/long into 2 text boxes
        // String coords = "("+data.getXCoord()+", "+data.getYCoord()+")";
        int xCoord = 0;
        int yCoord = 0;
        double latitude = 0;
        double longitude = 0;
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
