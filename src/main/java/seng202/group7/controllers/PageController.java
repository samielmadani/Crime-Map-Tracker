package seng202.group7.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import seng202.group7.data.DataAccessor;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import javax.xml.validation.Validator;

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
    private Node pageFrame;

    @FXML
    private TextField pageField;

    @FXML
    private Label dataTotal;


    /**
     * This method is run during the loading of the pages fxml file.
     * It generates the system for making tables when the pages are swapped.
     *
     * @param location      A URL object.
     * @param resources     A ResourceBundle object.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        InputValidator.addValidation(pageField, InputType.INTEGER);

        pageFrame.parentProperty().addListener((obs, oldParent, newParent) -> {
            if (newParent != null) {
                int size = DataAccessor.getInstance().getSize(ControllerData.getInstance().getCurrentList());
                dataTotal.setText("Data Total: "+size); // Sets current display total.
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

    /**
     * Sets the current page to the start.
     */
    public void toFront() {
        pages.setCurrentPageIndex(0);
        pageField.setText("1");
    }

    /**
     * Sets the current page to the end.
     */
    public void toEnd() {
        pages.setCurrentPageIndex(pages.getPageCount());
        pageField.setText(""+pages.getPageCount());
    }

    /**
     * Gets the current text for the page the user wants to goto and then checks if it's valid after which it will either
     * change the page to the new page or change the text field back to a valid input and then if it can change pages.
     */
    public void gotoPage() {
        String input = pageField.getText();
        if (InputValidator.validate(pageField) && !input.isEmpty()) {
            int queryPage = Integer.parseInt(input);
            if (queryPage > 0 && queryPage < pages.getPageCount()){
                pages.setCurrentPageIndex(Integer.parseInt(input) - 1); // Is valid and changes the page.
            }
        }
    }

    // /**
    //  * Checks the current page limit for the paginator and checks the value given if it is valid or not.
    //  *
    //  * @param input     The text input.
    //  * @return  The result of if the text input is valid.
    //  */
    // private boolean isValid(String input) {
    //     // TODO change to new validation method
    //     boolean valid;
    //     int numOfDig = String.valueOf(pages.getPageCount()).length();
    //     if (numOfDig == 0) {
    //         valid = false;
    //     } else if (numOfDig == 1){
    //         valid = input.matches("[1-"+pages.getPageCount()+"]");
    //     } else {
    //         valid = input.matches("[1-9]([0-9]{0,"+(numOfDig-1)+"})");
    //     }
    //     return valid;
    // }
}
