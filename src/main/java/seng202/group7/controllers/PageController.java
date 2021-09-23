package seng202.group7.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Pagination;
import seng202.group7.data.DataAccessor;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * This class creates the paginator that then itself generates the tables,
 * that are used to store selections of the database's information.
 *
 * @author Jack McCorkindale
 * @author John Elliott
 */
public class PageController implements Initializable {
    /**
     * The paginator node.
     */
    @FXML
    private Pagination pages;

    @FXML
    private Node frame;


    /**
     * This method is run during the loading of the pages fxml file.
     * It generates the system for making tables when the pages are swapped.
     *
     * @param location      A URL object.
     * @param resources     A ResourceBundle object.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        frame.parentProperty().addListener((obs, oldParent, newParent) -> {
            if (newParent != null) {
                int size = DataAccessor.getInstance().getSize();
                
                pages.setPageCount((int) Math.ceil(size/1000.0)); // Sets the number of pages with 1000 crimes per page.
                pages.setCurrentPageIndex(ControllerData.getInstance().getCurrentPage());
            }

        });

        pages.setPageFactory(this::createPage); // When ever a page is swapped it calls this method.
    }

    /**
     * This method is called when creating a new table for the paginator to display to the user.
     *
     * @param pageIndex     The current page.
     * @return table         The table node that will be displayed.
     */
    private Node createPage(int pageIndex) {
        // Stores the current page number so when the table is initialized it can get the correct set of data.
        ControllerData.getInstance().setCurrentPage(pageIndex);
        try {
            return FXMLLoader.load(Objects.requireNonNull(MenuController.class.getResource("/gui/dataView.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
