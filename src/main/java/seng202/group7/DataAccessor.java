package seng202.group7;

import java.util.ArrayList;

interface DataAccessor {

    public ArrayList<Report> read(String pathname);
    public void write(ArrayList<Report> reports, String pathname);
    
}