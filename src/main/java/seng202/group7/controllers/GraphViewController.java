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
import seng202.group7.analyses.Rank;
import seng202.group7.analyses.Tuple;
import seng202.group7.data.DataAccessor;
import seng202.group7.data.Report;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Graph View Controller controls displays three different bar graphs based on user input from Graph Menu Controller.
 *
 * @author Jack McCorkindale
 * @author Sam McMillan
 */
public class GraphViewController implements Initializable {

    @FXML
    private BarChart<String, Integer> crimeChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private Node frame;

    /**
     * This method is run during the loading of the graph menu fxml file.
     *
     * @param location      A URL object.
     * @param resources     A ResourceBundle object.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        frame.parentProperty().addListener((obs, oldParent, newParent) -> {

            if (newParent != null) {
                prepareGraph(frame.getPseudoClassStates());
            }

        });
    }

    /**
     * Called from the Graph View controller when the scene is initialized, checks what input is currently
     * in the combo box from graph menu class with pseudo classes and loads the correct graph accordingly.
     *
     * @param pseudoClassStates     Has three different States which determines what graph is loaded.
     */
    public void prepareGraph(ObservableSet<PseudoClass> pseudoClassStates) {
        ArrayList<Report> data = DataAccessor.getInstance().getAll(ControllerData.getInstance().getCurrentList());
        ArrayList<Tuple<String, Integer>> dataSet;
        if (pseudoClassStates.contains(PseudoClass.getPseudoClass("frequentCrime")) || (pseudoClassStates.size() == 0)) {
            dataSet = Rank.primaryFrequencyRank(data);
            generateGraph(dataSet, "Most Frequent Types of Crime", "Crime Types", "Frequency of Crime");
        } else if (pseudoClassStates.contains(PseudoClass.getPseudoClass("wardDanger"))) {
            dataSet = Rank.wardFrequencyRank(data);
            generateGraph(dataSet, "Most Dangerous Wards", "Ward", "Frequency of Crime");
        } else if (pseudoClassStates.contains(PseudoClass.getPseudoClass("streetDanger"))) {
            dataSet = Rank.streetRank(data);
            generateGraph(dataSet, "Most Dangerous Streets", "Street", "Frequency of Crime");
        }
    }

    /**
     *Create the graph by inputting the values into the bar chart object.
     *
     * @param data      The arraylist of String integer tuples from Rank class
     * @param title     The String title of the graph
     * @param xLabel    The x axis label of the graph
     * @param yLabel    The y axis label of the graph
     */
    public void generateGraph(ArrayList<Tuple<String, Integer>> data, String title, String xLabel, String yLabel) {

        XYChart.Series<String, Integer> dataSet = new XYChart.Series<>();
        int i = 0;
        for (Tuple<String, Integer> tuple: data) {
            i++;
            if (i == 21) {
                break;
            }
            dataSet.getData().add(new XYChart.Data<> (tuple.x, tuple.y));
        }
        this.crimeChart.setLegendVisible(false);
        this.crimeChart.getData().addAll(dataSet);
        this.crimeChart.setTitle(title);
        this.xAxis.setLabel(xLabel);
        this.yAxis.setLabel(yLabel);
    }
}
