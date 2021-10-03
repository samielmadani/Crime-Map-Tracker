package seng202.group7.controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javafx.collections.ObservableSet;
import javafx.scene.Node;
import javafx.css.PseudoClass;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import seng202.group7.data.DataAccessor;

/**
 * Used to prepare and validate input nodes.
 */
public class InputValidator {

    /**
     * Sets the error PseudoClass which gives a Node a red border.
     */
    private static PseudoClass errorClass = PseudoClass.getPseudoClass("error");

    /**
     * Passes the value through the required validation methods and sets the Node's error style.
     *
     * @param inputNode The input to be validated.
     * @return If the input is valid.
     */
    public static boolean validate(Node inputNode) {
        if (inputNode.isDisabled()) {
            return true;
        }
        boolean valid = true;
        String input = getInput(inputNode);
        if (inputNode.getPseudoClassStates().contains(InputType.REQUIRED.getValidationType())) {
            valid &= validateRequired(input);
        }
        if (valid && !"".equals(input)) {
            valid &= validateInput(input, inputNode.getPseudoClassStates());
        }
        inputNode.pseudoClassStateChanged(errorClass, !valid);
        return valid;
    }

    /**
     * Gets the user input from the given node.
     * @param inputNode The node that the user can use.
     * @return The input value contained in the input node.
     */
    private static String getInput(Node inputNode) {
        String input = null;
        if (inputNode instanceof TextField) {
            input = ((TextField) inputNode).getText();
        } else if (inputNode instanceof TextArea) {
            input = ((TextArea) inputNode).getText();
        } else if (inputNode instanceof DatePicker) {
            input = ((DatePicker) inputNode).getEditor().getText();
        }
        return input;
    }

    /**
     * Checks if the input has a value and adds the error class if it is invalid.
     *
     * @return If the field has an entry
     */
    private static boolean validateRequired(String input) {
        boolean valid;
        valid = !(input.isEmpty());
        return valid;
    }

    /**
     * Validates the value in each box according to the validation PseudoClasses previously set.
     *
     * @param input             The input to be validated
     * @param classes           The pseudo classes.
     * @return                  If the input is valid
     */
    private static boolean validateInput(String input, ObservableSet<PseudoClass> classes) {
        boolean valid = true;
        if (classes.contains(InputType.INTEGER.getValidationType())) {
            try {
                Integer.parseInt(input);
            } catch (NumberFormatException e) {
                valid = false;
            }
        }

        if (classes.contains(InputType.DOUBLE.getValidationType())) {
            try {
                Double.parseDouble(input);
            } catch (NumberFormatException e) {
                valid = false;
            }
        }

        if (classes.contains(InputType.TIME.getValidationType())) {
            try {
                DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("H:mm");
                LocalTime.parse(input, dateTimeFormat);
            } catch (DateTimeParseException e) {
                valid = false;
            }
        }

        if (classes.contains(InputType.DATE.getValidationType())){
            try {
                DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("d/M/yyyy");
                LocalDate.parse(input, dateTimeFormat);
            } catch (DateTimeParseException e) {
                valid = false;
            }
        }
        if (classes.contains(InputType.ID.getValidationType()) && DataAccessor.getInstance().getCrime(input) != null) {
            valid = false;
        }
        return valid;
    }
    
    public static void addValidation(Node inputNode, InputType requiredValidation) {
        inputNode.pseudoClassStateChanged(requiredValidation.getValidationType(), true);
    }
}

