package seng202.group7;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class CSVDataAccessor implements DataAccessor {

    /**
     * Opens a file (currently supports csv files) and creates a list of reports (currently supports Crimes only)
     * TODO add support for csv files
     * TODO add support for incidents
     * TODO fix bug with csv files containing entries with commas
     * @param pathname
     * 
     * @return reports
     */
    @Override
    public ArrayList<Report> read(String pathname) {
        // TODO refactor once it is know how the file is passed in
        // TODO refactor creation once crime class is developed
        ArrayList<Report> reports = new ArrayList<Report>();
        int counter = 0;
        int errors = 0;
        try {
            CSVReader reader = new CSVReader(new FileReader(pathname));
            String [] columns;
            String [] schema = reader.readNext();
            while ((columns = reader.readNext()) != null) {
                try {
                    reports.add(createCrime(columns));
                    counter ++;
                } catch (Exception e) {
                    System.out.println(counter + errors);
                    System.out.println(e);
                    errors ++;
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException io) {
            System.out.println(io);
        }
        return reports;
    }

    /**
     * Takes a row from a csv and converts elements to an instance of crime
     * @param columns
     * @return crime
     */
    private Crime createCrime(String[] columns) {
        Crime crime = new Crime();
        crime.setCaseNumber(columns[0]);

        // Currently set for American time MM/dd/yyyy hh:mm:ss a
        if (!columns[1].isEmpty()) {
            crime.setDate(getLocalDateTime(columns[1]));
        }

        crime.setBlock(columns[2]);
        crime.setiucr(columns[3]);
        crime.setPrimaryDescription(columns[4]);
        crime.setSecondaryDescription(columns[5]);
        crime.setLocationDescription(columns[6]);

        if (!columns[7].isEmpty()) {
            crime.setArrest(columns[7].equals("Y") || columns[7].equals("true"));
        }

        if (!columns[8].isEmpty()) {
            crime.setDomestic(columns[8].equals("Y") || columns[8].equals("true"));
        }

        if (!columns[9].isEmpty()) {
            crime.setBeat(Integer.parseInt(columns[9]));
        }

        if (!columns[10].isEmpty()) {
            crime.setWard(Integer.parseInt(columns[10]));
        }

        crime.setFbiCD(columns[11]);

        if (!columns[12].isEmpty()) {
            crime.setXCoord(Integer.parseInt(columns[12]));
        }

        if (!columns[13].isEmpty()) {
            crime.setYCoord(Integer.parseInt(columns[13]));
        }

        if (!columns[14].isEmpty()) {
            crime.setLatitude(Double.parseDouble(columns[14]));
        }

        if (!columns[15].isEmpty()) {
            crime.setLongitude(Double.parseDouble(columns[15]));
        }
        return crime;
    }

    public static LocalDateTime getLocalDateTime(String column) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a", Locale.US);
        return LocalDateTime.parse(column, formatter);
    }

    @Override
    public void write(ArrayList<Report> reports, String pathname) {
        // TODO Auto-generated method stub
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(pathname), ',');
            // feed in your array (or convert your data to an array)
            String[] attributes;

            writer.writeNext("CASE#,DATE  OF OCCURRENCE,BLOCK, IUCR, PRIMARY DESCRIPTION, SECONDARY DESCRIPTION, LOCATION DESCRIPTION,ARREST,DOMESTIC,BEAT,WARD,FBI CD,X COORDINATE,Y COORDINATE,LATITUDE,LONGITUDE,LOCATION".split(","));

            for (Report report : reports) {
                attributes = report.getAttributes().toArray(new String[0]);
                writer.writeNext(attributes);
            }
            writer.close();
        } catch (IOException e) {
            System.out.println(e);
        }   
    }
}