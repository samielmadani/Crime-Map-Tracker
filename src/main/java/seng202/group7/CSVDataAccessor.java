package seng202.group7;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

import javax.naming.directory.InvalidAttributeValueException;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import seng202.group7.controllers.ControllerData;

public final class CSVDataAccessor implements DataAccessor {
    /**
     * This creates a singleton instants of the class.
     */
    private final static CSVDataAccessor INSTANCE = new CSVDataAccessor();

    /**
     * The constructor which is made private so that it can not be initialized from other classes.
     */
    private CSVDataAccessor() {}

    /**
     * Used to get the singleton instants of the class when assessing. This is done over a "static" class due to it
     * implementing an interface.
     *
     * @return INSTANCE     The only instants of this class.
     */
    public static CSVDataAccessor getInstance() {
        return INSTANCE;
    }

    /**
     * The constructor which is made private so that it can not be initialized from other classes.
     */
    private CSVDataAccessor() {}

    /**
     * Used to get the singleton instants of the class when assessing. This is done over a "static" class due to it
     * implementing an interface.
     *
     * @return INSTANCE     The only instants of this class.
     */
    public static CSVDataAccessor getInstance() {
        return INSTANCE;
    }

    @Override
    public ArrayList<Report> read(File pathname) {
        // TODO refactor once it is know how the file is passed in
        // TODO refactor creation once crime class is developed
        ArrayList<Report> reports = new ArrayList<>();
        int counter = 0;
        int errors = 0;
        try {
            FileReader csvFile = new FileReader(pathname);
            CSVReader reader = new CSVReader(csvFile);
            String [] columns;
            String [] schema = reader.readNext(); // Will likely be used to determine crime vs incident
            while ((columns = reader.readNext()) != null) {
                try {
                    reports.add(createCrime(columns));
                    counter ++;
                } catch (Exception e) {
                    System.out.println(counter + errors);
                    System.out.println(e.getMessage());
                    errors ++;
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return reports;
    }

    /**
     * Takes a row from a csv and converts elements to an instance of crime
     * @param columns The column which 
     * @return crime
     * @throws InvalidAttributeValueException
     */
    private Crime createCrime(String[] columns) throws InvalidAttributeValueException {
        String caseNumber = columns[0];
        if (caseNumber.isEmpty()) {
            throw new InvalidAttributeValueException("Case number is null");
        }
        
        LocalDateTime date = null;
        // Currently set for American time MM/dd/yyyy hh:mm:ss a
        if (!columns[1].isEmpty()) {
            date = getLocalDateTime(columns[1]);
        } else {
            throw new InvalidAttributeValueException("Date is null");
        }
        
        String block = columns[2];

        crime.setBlock(columns[2]);
        crime.setIucr(columns[3]);
        crime.setPrimaryDescription(columns[4]);
        crime.setSecondaryDescription(columns[5]);
        crime.setLocationDescription(columns[6]);

        String primaryDescription = columns[4];
        if (primaryDescription.isEmpty()) {
            throw new InvalidAttributeValueException("Primary description is null");
        }

        String secondaryDescription  = columns[5];
        if (primaryDescription.isEmpty()) {
            throw new InvalidAttributeValueException("Secondary description is null");
        }

        String locationDescription =columns[6];
        
        Boolean arrest = null;
        if (!columns[7].isEmpty()) {
            arrest = columns[7].equals("Y") || columns[7].equals("true");
        }
        
        Boolean domestic = null;
        if (!columns[8].isEmpty()) {
            domestic  = columns[8].equals("Y") || columns[8].equals("true");
        }
        
        Integer beat = null;
        if (!columns[9].isEmpty()) {
            try{
                beat = Integer.parseInt(columns[9]);
            } catch (Exception e) {
                beat = null;
            }
        }
        
        Integer ward = null;
        if (!columns[10].isEmpty()) {
            ward = Integer.parseInt(columns[10]);
        }
        
        String fbiCD = columns[11];
        
        Integer xCoord = null;
        if (!columns[12].isEmpty()) {
            xCoord = Integer.parseInt(columns[12]);
        }
        
        Integer yCoord = null;
        if (!columns[13].isEmpty()) {
            yCoord = Integer.parseInt(columns[13]);
        }
        
        Double latitude = null;
        if (!columns[14].isEmpty()) {
            latitude = Double.parseDouble(columns[14]);
        }
        
        Double longitude = null;
        if (!columns[15].isEmpty()) {
            longitude = Double.parseDouble(columns[15]);
        }

        Crime crime = new Crime(caseNumber, date, block, iucr, primaryDescription, secondaryDescription,
              locationDescription, arrest, domestic, beat, ward, fbiCD, xCoord, yCoord, latitude, longitude);
        return crime;
    }

    /**
     * Changes the string of date in a US format to the format used by the LocalDateTime class
     * @param date The date in the format "MM/dd/yyyy hh:mm:ss a"
     * @return A local date time
     */
    private static LocalDateTime getLocalDateTime(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a", Locale.US);
        return LocalDateTime.parse(date, formatter);
    }

    @Override
    public void write(ArrayList<Report> reports, String pathname) {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(pathname), ',');
            // feed in your array (or convert your data to an array)
            String[] attributes;

            //writer.writeNext("CASE#,DATE  OF OCCURRENCE,BLOCK, IUCR, PRIMARY DESCRIPTION, SECONDARY DESCRIPTION, LOCATION DESCRIPTION,ARREST,DOMESTIC,BEAT,WARD,FBI CD,X COORDINATE,Y COORDINATE,LATITUDE,LONGITUDE,LOCATION".split(","));
            String[] schema = reports.get(0).getSchema().toArray(new String[0]);
            writer.writeNext(schema);

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