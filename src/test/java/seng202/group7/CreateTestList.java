package seng202.group7;

import seng202.group7.data.Crime;
import seng202.group7.data.Report;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class CreateTestList {


    public static ArrayList<Report>  CreateList() {
        ArrayList<Report> data = new ArrayList<Report>();
        LocalDateTime date2 = LocalDateTime.of(2020, 02, 20, 19, 33);
        Crime reportOne = new Crime("1", date2, "073XX S SOUTH SHORE DR", "NA", "THEFT", "NA", null, false, false, 1, 1, "NA", 1183633, 1851786, 41.748486365, -87.602675062);
        Crime reportTwo = new Crime("2", date2, "109XX S WALLACE ST", "NA", "SEX OFFENSE", "NA", null, false, false, 1, 20, "NA", 1148227, 1899678, 41.880660786, -87.731186405);
        Crime reportThree = new Crime("3", date2, "029XX S DR MARTIN LUTHER KING JR DR", "NA", "THEFT", "NA", null, false, false, 1, 10, "NA", 1148227, 1899678, 41.880660786, -87.731186405);
        Crime reportFour = new Crime("4", date2, "064XX S DR MARTIN LUTHER KING JR DR", "NA", "MURDER", "NA", null, false, false, 1, 1, "NA", 1148227, 1899678, 41.880660786, -87.731186405);
        Crime reportFive = new Crime("5", date2, "055XX S DR MARTIN LUTHER KING JR DR", "NA", "THEFT", "NA", null, false, false, 1, 1, "NA", 1148227, 1899678, 41.880660786, -87.731186405);
        data.add(reportOne);
        data.add(reportTwo);
        data.add(reportThree);
        data.add(reportFour);
        data.add(reportFive);
        return data;
    }
}

