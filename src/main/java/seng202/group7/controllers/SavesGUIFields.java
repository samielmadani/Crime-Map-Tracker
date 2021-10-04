package seng202.group7.controllers;

/**
 * An interface that represents the functionality of storing the fields of a javafx controller's gui
 * as static variables.
 * Abstracts the ability to save those fields to the variable and load them back into the gui at a later point.
 */
public interface SavesGUIFields {

    /**
     * Sets all the fields in the controller's gui to those from a static field
     */
    void loadGUIFields();

    /**
     * Saves all the fields in the controller's gui to a static variable
     */
    void saveGUIFields();
}
