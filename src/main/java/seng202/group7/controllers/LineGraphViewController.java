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
    public void initialize(URL url, ResourceBundle resourceBundle) {}


    public void prepareLineGraph(String query, ArrayList<String> choices) {
        ArrayList<Report> sortedData = DataAccessor.getInstance().getData(query);
        ArrayList<CrimeFrequency> crimeOverTime = Rank.crimeOverTime(sortedData);
        String title = getTitle(choices);
        XYChart.Series<String, Integer> dataSet = new XYChart.Series<>();
        for (CrimeFrequency freq: crimeOverTime) {
            dataSet.getData().add(new XYChart.Data<> (freq.getDate(), freq.getFrequency()));
        }
        this.overTimeChart.setLegendVisible(false);
        this.overTimeChart.getData().addAll(dataSet);
        this.overTimeChart.setTitle(title);
        this.xAxis.setLabel("Date");
        this.yAxis.setLabel("Number of Crime");
    }

    public String getTitle(ArrayList<String> choices) {
        String title = "";
        if (choices.get(0) != null)  {
            title += (choices.get(0).substring(0,1).toUpperCase()) + (choices.get(0).substring(1).toUpperCase());
            title += " Over Time";
        } else {
            title += "Crime Over Time";
        }
        if (choices.get(1) != null) {
            title += " in Ward " + choices.get(1);
        }
        if (choices.get(2) != null) {
            title += " and Beat " + choices.get(2);
        }
        return title;
    }

}
