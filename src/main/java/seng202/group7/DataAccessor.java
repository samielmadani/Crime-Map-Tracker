package seng202.group7;

import java.util.ArrayList;
import java.io.File;

public interface DataAccessor {

    ArrayList<Report> read(File pathname);
    void write(ArrayList<Report> reports);
    
}