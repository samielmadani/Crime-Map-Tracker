package seng202.group7.controllers;

import seng202.group7.Report;
import java.util.ArrayList;

/**
 * This class acts as a connection between the controllers passing data through here so that only the controller's
 * initialization it can get the relevant data it needs. It accomplishes this by being a singleton class so only one
 * instance of it can exist.
 *
 * @author John Elliott
 */
public final class ControllerData {
    /**
     * This creates a singleton instants of the class.
     */
    private final static ControllerData INSTANCE = new ControllerData();

    /**
     * Stores the current reports (data) being used by the table and maps.
     */
    private ArrayList<Report> reports = new ArrayList<>();

    /**
     * The constructor which is made private so that it can not be initialized from other classes.
     */
    private ControllerData() {}

    /**
     * Used to get the singleton instants of the class when assessing. This is done over a "static" class due to it
     * implementing an interface.
     *
     * @return INSTANCE     The only instants of this class.
     */
    public static ControllerData getInstance() {
        return INSTANCE;
    }

    /**
     * A getter for the list of reports currently stored.
     *
     * @return reports      The list of reports currently being used.
     */
    public ArrayList<Report> getReports() {
        return reports;
    }

    /**
     * A setter for the list of reports.
     *
     * @param reports       A list of new reports.
     */
    public void setReports(ArrayList<Report> reports) {
        this.reports = reports;
    }
}
