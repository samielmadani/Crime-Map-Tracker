package seng202.group7;

import java.util.ArrayList;
import java.io.File;

public interface DataAccessor {

    /**
     * Read all reports in the file found at the end of pathname.
     * @param pathname Path to the file containing the reports to be created
     * @return An ArrayList of reports from the file
     */
    public ArrayList<Report> read(File pathname);

    /**
     * Writes an ArrayList of reports to a file created at the location of the pathname.
     * @param reports
     * @param pathname
     */
    public void write(ArrayList<Report> reports, String pathname);
    
}