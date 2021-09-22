package seng202.group7.controllers;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import seng202.group7.analyses.Rank;
import seng202.group7.analyses.Tuple;
import seng202.group7.data.DataAccessor;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * @Author Sam McMillan
 */
public class GraphViewController implements Initializable {


    @FXML
    private BarChart<?, ?> crimeChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;


    @Override
    public void initialize(URL url, ResourceBundle rb) {


        ArrayList<Tuple> data = Rank.primaryFrequencyRank(DataAccessor.getInstance().getAll());
        generateGraph(data, "Most Frequent Types of Crime", "Crime Type", "Frequency");
    }



    public void generateGraph(ArrayList<Tuple> data, String title, String xLabel, String yLabel) {

        XYChart.Series dataSet = new XYChart.Series<>();

        for (Tuple tuple: data) {
            dataSet.getData().add(new XYChart.Data (tuple.x, tuple.y));
        }
        this.crimeChart.getData().addAll(dataSet);
        this.crimeChart.setTitle(title);
        this.xAxis.setLabel(xLabel);
        this.yAxis.setLabel(yLabel);
    }
}
