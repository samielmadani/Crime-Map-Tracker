package seng202.group7.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import seng202.group7.analyses.CrimeFrequency;
import seng202.group7.analyses.Rank;
import seng202.group7.data.DataAccessor;
import seng202.group7.data.Report;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LineGraphViewController implements Initializable {

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private LineChart<String, Integer> overTimeChart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("hello");
    }

    public void prepareLineGraph() {
        ArrayList<Report> sortedData = DataAccessor.getInstance().getAllSortedByDate();
        ArrayList<CrimeFrequency> crimeOverTime = Rank.crimeOverTime(sortedData);

        XYChart.Series<String, Integer> dataSet = new XYChart.Series<>();
        for (CrimeFrequency freq: crimeOverTime) {
            dataSet.getData().add(new XYChart.Data<> (freq.getDate(), freq.getFrequency()));
        }
        this.overTimeChart.setLegendVisible(false);
        this.overTimeChart.getData().addAll(dataSet);
        this.overTimeChart.setTitle("Crime over time");
        this.xAxis.setLabel("reee");
        this.yAxis.setLabel("reee");
    }

}
