package seng202.group7;

import java.util.ArrayList;
import java.io.File;

interface DataAccessor {

    public ArrayList<Report> read(File pathname);
    public void write(ArrayList<Report> reports);
    
}