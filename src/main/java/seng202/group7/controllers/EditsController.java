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
import javafx.event.ActionEvent;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;


/**
 * The controller, used by / linked to, the crime edit FXML file.
 * Makes a detailed view of the data retrieved from the current row selected in the ControllerData object.
 *
 * @author John Elliott
 */
public class EditsController implements Initializable {
    /**
     * The current selected rows crime object.
     */
    Crime data;
    @FXML
    private TextField cNoText, iucText, fbiText, blockText, beatText,
            wardText, coordsText, locText, priText;
    @FXML
    private TextArea secText, locAreaText;
    @FXML
    private DatePicker dateText;
    @FXML
    private CheckBox arrestCheck, domesticCheck;
    @FXML
    private Button editButton, saveButton, cancelButton;


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
        data = master.getCurrentRow();
        setData();
    }

    /**
     *  This method gets all the related data from the crime object and set it as the default text in its relevant field.
     */
    private void setData() {
        // CheckBoxes:
        if (data.getArrest()) {
            arrestCheck.setSelected(true);
        }
        if (data.getDomestic()) {
            domesticCheck.setSelected(true);
        }
        // General Information:
        cNoText.setText(data.getCaseNumber());
        LocalDate date = data.getDate().toLocalDate();
        dateText.setValue(date);
        iucText.setText(data.getIucr());
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

    public void editEntry(ActionEvent event) {
        editButton.setVisible(false);
        editButton.setManaged(false);

        saveButton.setVisible(true);
        saveButton.setManaged(true);
        cancelButton.setVisible(true);
        cancelButton.setManaged(true);

    }

}
