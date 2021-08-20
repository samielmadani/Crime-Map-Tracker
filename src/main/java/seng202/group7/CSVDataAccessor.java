package seng202.group7;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

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
            BufferedReader csvReader = new BufferedReader(new FileReader(pathname));
            String row;
            String schema = csvReader.readLine();
            while ((row = csvReader.readLine()) != null) {
                String[] columns = row.split(",", -1);
                try {
                    reports.add(createCrime(columns));
                    counter ++;
                } catch (Exception e) {
                    System.out.println(counter + errors);
                    System.out.println(e);
                    errors ++;
                }
            }
            csvReader.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException io) {
            System.out.println(io);
        }
        return reports;
    }

    /**
     * Takes in a row which has more than the expected 8 columns and concatenates the extra columns into
     * the secondary description space
     * @param row
     * @return
     */
    private String[] fixColumns(String[] row) {
        String[] rowCopy = row.clone();
        int rowLength = row.length;

        String[] secondaryDescriptionElements = Arrays.copyOfRange(row, 5, 5 + (rowLength - 18));
        String secondaryDescription = String.join(", ", secondaryDescriptionElements);

        row = new String[18];
        for (int i = 0; i < 5; i++)  {
            row[i] = rowCopy[i];
        }
        row[5] = secondaryDescription;
        for (int i = 6; i < 18; i++)  {
            row[i] = rowCopy[i + (rowLength - 18)];
        }
        return row;
    }

    /**
     * Takes a row from a csv and converts elements to an instance of crime
     * @param columns
     * @return crime
     */
    private Crime createCrime(String[] columns) {
        Crime crime = new Crime();
        if (columns.length > 18) {
            columns = fixColumns(columns);
        }

        crime.setCaseNumber(columns[0]);

        // Currently set for American time MM/DD/YYYY

        int month = Integer.parseInt(columns[1].substring(0, 2));
        int dayOfMonth = Integer.parseInt(columns[1].substring(3, 5));
        int year = Integer.parseInt(columns[1].substring(6, 10));
        int hour = Integer.parseInt(columns[1].substring(11, 13));
        if (columns[1].substring(21) == "PM") {
            hour += 12;
        }
        int minute = Integer.parseInt(columns[1].substring(14, 16));

        crime.setDate(LocalDateTime.of(year, month, dayOfMonth, hour, minute));
      
        crime.setBlock(columns[2]);
        crime.setiucr(columns[3]);
        crime.setPrimaryDescription(columns[4]);
        crime.setSecondaryDescription(columns[5]);
        crime.setLocationDescription(columns[6]);
        crime.setArrest(columns[7] == "Y");
        crime.setDomestic(columns[8] == "Y");

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

    @Override
    public void write(ArrayList<Report> reports) {
        // TODO Auto-generated method stub
        
    }
}