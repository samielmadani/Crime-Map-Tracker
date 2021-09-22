package seng202.group7.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import seng202.group7.analyses.Rank;
import seng202.group7.analyses.Tuple;
import seng202.group7.data.DataAccessor;
import seng202.group7.data.Report;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;


public class GraphViewController implements Initializable {


    @FXML
    private BarChart<?, ?> crimeChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;


    private ArrayList<Tuple> currentData;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        XYChart.Series defaultSet = new XYChart.Series<>();

        currentData = Rank.primaryFrequencyRank(DataAccessor.getInstance().getAll());
        for (Tuple tuple: currentData) {
            defaultSet.getData().add(new XYChart.Data (tuple.x, tuple.y));
            }
        this.crimeChart.getData().addAll(defaultSet);
        this.crimeChart.setTitle("Crime Frequency");
        this.xAxis.setLabel("Crime Type");
        this.yAxis.setLabel("Frequency");
    }


    public void back(ActionEvent event) throws IOException {
        // As the side panels root is the main border panel we use .getRoot().
        BorderPane pane = (BorderPane) (((Node) event.getSource()).getScene()).getRoot();
        VBox menuItems = FXMLLoader.load(Objects.requireNonNull(MenuController.class.getResource("/gui/generalMenu.fxml")));
        // Changes side menu to the filter menu.
        pane.setLeft(menuItems);
    }
}
