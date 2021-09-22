package seng202.group7.controllers;

import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import seng202.group7.analyses.Rank;
import seng202.group7.analyses.Tuple;
import seng202.group7.data.DataAccessor;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * @Author Jack
 * @Author Sam
 */
public class GraphMenuController implements Initializable {


    @FXML
    private ChoiceBox<String> graphType;

    @FXML
    private Node frame;

    private PseudoClass frequentCrime;
    private PseudoClass wardDanger;
    private PseudoClass streetDanger;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        frequentCrime = PseudoClass.getPseudoClass("frequentCrime");
        graphType.getItems().add("Most Frequent Crime types");

        wardDanger = PseudoClass.getPseudoClass("wardDanger");
        graphType.getItems().add("Most Dangerous Wards");

        streetDanger = PseudoClass.getPseudoClass("streetDanger");
        graphType.getItems().add("Most Dangerous Streets");
    }

    public void selectGraph(ActionEvent actionEvent) throws IOException {
        ArrayList<Tuple> data = new ArrayList<>();
        String graphSelection = graphType.getValue();
        GridPane graphView = FXMLLoader.load(Objects.requireNonNull(MenuController.class.getResource("/gui/graphView.fxml")));


        graphView.pseudoClassStateChanged(frequentCrime, graphSelection.equals("Most Frequent Crime types"));
        graphView.pseudoClassStateChanged(wardDanger, graphSelection.equals("Most Dangerous Wards"));
        graphView.pseudoClassStateChanged(streetDanger, graphSelection.equals("Most Dangerous Streets"));
        var test = graphView.getPseudoClassStates();
        ((BorderPane) frame.getParent()).setCenter(graphView);
        // Node graphPane = ((BorderPane) frame.getParent()).getCenter();


        // if (graphSelection.equals("Most Frequent Crime type")) {
        //     data = Rank.primaryFrequencyRank(DataAccessor.getInstance().getAll());
        //   //  GraphViewController.generateGraph(data, "Most Frequent Crime type", "Crime Type", "Frequency");
        // } else if (graphSelection.equals("Most Dangerous Wards")) {
        //     data = Rank.wardFrequencyRank(DataAccessor.getInstance().getAll());
        //   //  GraphViewController.generateGraph(data, "Most Dangerous Wards", "Ward", "Frequency");
        // } else if (graphSelection.equals("Most dangerous Streets")) {
        //     data = Rank.streetRank(DataAccessor.getInstance().getAll());
        //    // GraphViewController.generateGraph(data, "Most Dangerous Streets", "Street", "Frequency");
        // }
    }

}
