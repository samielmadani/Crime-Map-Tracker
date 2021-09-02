package seng202.group7.controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import seng202.group7.Crime;
import seng202.group7.Report;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

//TODO make check for empty data and that you cant launch the data view screen without having selected data.

/**
 * The controller, used by / linked to, the Data View FXML file.
 * Handles the generation of the table on initialization.
 *
 * @author John Elliott
 */
public class DataViewController implements Initializable {
    /**
     * Is the Table.
     */
    @FXML
    private TableView<Crime> tableView;
    /**
     * Is the columns of the table with the type string.
     */
    @FXML
    private TableColumn<Crime, String> caseCol, wardCol, descCol;
    /**
     * Is the columns of the table with the type boolean.
     */
    @FXML
    private TableColumn<Crime, Boolean> arrestCol;

    /**
     * This method is run during the loading of the data view fxml file.
     * It generates what values will be stored in the columns.
     *
     * @param Location      A URL object.
     * @param resources     A ResourceBundle object.
     */
    @Override
    public void initialize(URL Location, ResourceBundle resources) {
        caseCol.setCellValueFactory(new PropertyValueFactory<>("CaseNumber"));
        wardCol.setCellValueFactory(new PropertyValueFactory<>("Ward"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("PrimaryDescription"));
        arrestCol.setCellValueFactory(new PropertyValueFactory<>("Arrest"));
        //TODO Use this code section (commented out) and a new fxml file that either creates a new stage or a scene.
        // Can also create a hidden table which is only shown when clicked twice.
        /*
        tableView.setRowFactory( tv -> {
            TableRow<Crime> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Crime rowData = row.getItem();
                    System.out.println(rowData.getCaseNumber());
                }
            });
            return row ;
        });
        */
        setTableContent();
    }

    /**
     * Creates an observable list which is used to store the data that will be displayed in the table.
     */
    private void setTableContent() {
        ArrayList<Report> reports = ControllerData.getInstance().getReports();
        ObservableList<Crime> data = FXCollections.observableArrayList();
        // As the reports can be either a crime or an incident we must check the object type and cast them appropriately.
        for (Report report : reports) {
            data.add((Crime) report);
        }
        // Displays the table.
        tableView.setItems(data);
    }
}
