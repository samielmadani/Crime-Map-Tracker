package seng202.group7.controllers;


import javafx.collections.ObservableSet;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import jdk.jfr.Frequency;
import seng202.group7.analyses.Rank;
import seng202.group7.analyses.Tuple;
import seng202.group7.data.DataAccessor;
import seng202.group7.data.Report;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * @Author Jack
 * @Author Sam McMillan
 */
public class GraphViewController implements Initializable {


    @FXML
    private BarChart<?, ?> crimeChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private Node frame;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        frame.parentProperty().addListener((obs, oldParent, newParent) -> {

            if (newParent != null) {
                prepareGraph(frame.getPseudoClassStates());
            }

        });

        // BorderPane stage = (BorderPane) frame.getParent();
        // VBox menu = (VBox) stage.getLeft();
        // // ChoiceBox<String> choiceBox;
        // Button graphButton = (Button) menu.getChildren().get(2);

        // graphButton.setOnAction((event) -> {
        //     frame.getPseudoClassStates().contains(PseudoClass.getPseudoClass("wardDanger"));
        //     prepareGraph();
        // });

        // ArrayList<Tuple> data = Rank.primaryFrequencyRank(DataAccessor.getInstance().getAll());
        // generateGraph(data, "Most Frequent Types of Crime", "Crime Type", "Frequency");
    }

    public void prepareGraph(ObservableSet<PseudoClass> pseudoClassStates) {
        ArrayList<Report> data = DataAccessor.getInstance().getAll();
        ArrayList<Tuple> dataSet = new ArrayList<>();
        if (pseudoClassStates.contains(PseudoClass.getPseudoClass("frequentCrime"))) {
            dataSet = Rank.primaryFrequencyRank(data);
            generateGraph(dataSet, "Most Frequent Types of Crime", "Crime Types", "Frequency of Crime");
        } else if (pseudoClassStates.contains(PseudoClass.getPseudoClass("wardDanger"))) {
            dataSet = Rank.wardFrequencyRank(data);
            generateGraph(dataSet, "Most Dangerous Streets", "Ward", "Frequency of Crime");
        } else if (pseudoClassStates.contains(PseudoClass.getPseudoClass("streetDanger"))) {
            dataSet = Rank.streetRank(data);
            generateGraph(dataSet, "Most Dangerous Streets", "Street", "Frequency of Crime");
        }
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
