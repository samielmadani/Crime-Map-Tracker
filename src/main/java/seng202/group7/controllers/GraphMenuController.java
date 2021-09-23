package seng202.group7.controllers;

import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * @author Jack McCorkindale
 * @author Sam McMillan
 *
 * Graph Menu Controller is the class which controls which graph is displayed by the program
 */
public class GraphMenuController implements Initializable {


    @FXML
    private ComboBox<String> graphType;

    @FXML
    private Node frame;

    private PseudoClass frequentCrime;
    private PseudoClass wardDanger;
    private PseudoClass streetDanger;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        frequentCrime = PseudoClass.getPseudoClass("frequentCrime");
        graphType.getItems().add("Most Frequent Crime Types");
        graphType.setValue("Most Frequent Crime Types");

        wardDanger = PseudoClass.getPseudoClass("wardDanger");
        graphType.getItems().add("Most Dangerous Wards");

        streetDanger = PseudoClass.getPseudoClass("streetDanger");
        graphType.getItems().add("Most Dangerous Streets");

    }

    /**
     * Method triggered when the user clicks on the generate graph button, Checks what selection is made by the user in
     * the combo box and reloads the graphView
     * @throws IOException The exception that is thrown when the FXML Loader can't laod the fxml file
     */
    public void selectGraph() throws IOException {
        String graphSelection = graphType.getValue();
        GridPane graphView = FXMLLoader.load(Objects.requireNonNull(MenuController.class.getResource("/gui/graphView.fxml")));

        graphView.pseudoClassStateChanged(frequentCrime, graphSelection.equals("Most Frequent Crime Types"));
        graphView.pseudoClassStateChanged(wardDanger, graphSelection.equals("Most Dangerous Wards"));
        graphView.pseudoClassStateChanged(streetDanger, graphSelection.equals("Most Dangerous Streets"));
        ((BorderPane) frame.getParent()).setCenter(graphView);
    }

}
