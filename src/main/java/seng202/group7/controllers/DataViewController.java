package seng202.group7.controllers;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import seng202.group7.Crime;
import seng202.group7.Report;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;


/**
 * The controller, used by / linked to, the Data View FXML file.
 * Handles the generation of the table on initialization.
 *
 * @author John Elliott
 */
public class DataViewController implements Initializable {
    /**
     * This is the GridPane that holds the table and is the root node.
     */
    @FXML
    private GridPane tablePane;

    /**
     * This is the Table.
     */
    @FXML
    private TableView<Crime> tableView;
    /**
     * This is the columns of the table with the type string.
     */
    @FXML
    private TableColumn<Crime, String> caseCol, wardCol, descCol, dateCol;
    /**
     * This is the columns of the table with the type boolean.
     */
    @FXML
    private TableColumn<Crime, Boolean> arrestCol;

    /**
     * This method is run during the loading of the data view fxml file.
     * It generates what values will be stored in the columns.
     *
     * @param location      A URL object.
     * @param resources     A ResourceBundle object.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        caseCol.setCellValueFactory(new PropertyValueFactory<>("CaseNumber"));
        wardCol.setCellValueFactory(new PropertyValueFactory<>("Ward"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("PrimaryDescription"));
        arrestCol.setCellValueFactory(new PropertyValueFactory<>("Arrest"));
        // Sets up a call to firstly create a DateTime pattern and then coverts our local date time stored in class.
        dateCol.setCellValueFactory(setup -> {
                    SimpleStringProperty property = new SimpleStringProperty();
                    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                    property.setValue(dateFormat.format(setup.getValue().getDate()));
                    return property;
                });

        // On a double click and the row isn't empty it will trigger the swap view method.
        tableView.setRowFactory( tv -> {
            TableRow<Crime> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Crime rowData = row.getItem();
                    swapViews(event, rowData);
                }
            });
            return row ;
        });
        setTableContent();
    }


    /**
     * This method stores that current state of the table and the selected row in the ControllerData and then loads,
     * the detailed data view screen and swaps it for the current raw data view screen.
     *
     * @param event         The double click mouse event trigger.
     * @param rowData       The Crime object from the selected row.
     */
    private void swapViews(MouseEvent event, Crime rowData) {
        // This section must come first as the rowData is need when initializing the crimeEdit FXML.
        ControllerData master = ControllerData.getInstance();
        master.setCurrentRow(rowData);
        master.setTableState(tablePane);

        // As the side panels root is the main border panel we use .getRoot().
        BorderPane pane = (BorderPane) (((Node) event.getSource()).getScene()).getRoot();
        GridPane detailView = null;
        try {
            detailView = FXMLLoader.load(Objects.requireNonNull(MenuController.class.getResource("/gui/crimeEdit.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Changes center screen to the crime edit.
        pane.setCenter(detailView);

    }


    /**
     * Creates an observable list which is used to store the data that will be displayed in the table.
     */
    private void setTableContent() {
        ArrayList<Report> reports = new ArrayList<>(ControllerData.getInstance().getReports());
        ObservableList<Crime> data = FXCollections.observableArrayList();
        // As the reports can be either a crime or an incident we must check the object type and cast them appropriately.
        for (Report report : reports) {
            data.add((Crime) report);
        }
        // Displays the table.
        tableView.setItems(data);
    }

    /**
     * Updates the table with a new list of reports to be observed.
     *
     * @param reports       The new list of reports that will be shown in the table.
     */
    public static void updateTableContents(ArrayList<Report> reports) {
        reports.addAll(ControllerData.getInstance().getReports());
        ObservableList<Crime> data = FXCollections.observableArrayList();
        // As the reports can be either a crime or an incident we must check the object type and cast them appropriately.
        for (Report report : reports) {
            data.add((Crime) report);
        }
    }
}
