package seng202.group7.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import seng202.group7.analyses.Rank;
import seng202.group7.analyses.Tuple;
import seng202.group7.data.DataAccessor;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GraphMenuController implements Initializable {


    @FXML
    private ChoiceBox<String> graphType;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        graphType.getItems().add("Most Frequent Crime types");
        graphType.getItems().add("Most Dangerous Wards");
        graphType.getItems().add("Most Dangerous Streets");
    }

    public void selectGraph(ActionEvent actionEvent) {
        ArrayList<Tuple> data = new ArrayList<>();
        String graphSelection = graphType.getValue();
        if (graphSelection.equals("Most Frequent Crime type")) {
            data = Rank.primaryFrequencyRank(DataAccessor.getInstance().getAll());
          //  GraphViewController.generateGraph(data, "Most Frequent Crime type", "Crime Type", "Frequency");
        } else if (graphSelection.equals("Most Dangerous Wards")) {
            data = Rank.wardFrequencyRank(DataAccessor.getInstance().getAll());
          //  GraphViewController.generateGraph(data, "Most Dangerous Wards", "Ward", "Frequency");
        } else if (graphSelection.equals("Most dangerous Streets")) {
            data = Rank.streetRank(DataAccessor.getInstance().getAll());
           // GraphViewController.generateGraph(data, "Most Dangerous Streets", "Street", "Frequency");
        }
    }

}
