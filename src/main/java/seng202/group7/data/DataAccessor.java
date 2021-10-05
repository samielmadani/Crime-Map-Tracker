package seng202.group7.data;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seng202.group7.controllers.ControllerData;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

/**
 * This class works to link the SQLite database with our java application.
 * It gets key data from the database during the runtime of the application.
 *
 * @author Jack McCorkindale
 * @author John Elliott
 * @author Shaylin Simadari
 */
public final class DataAccessor {
    /**
     * This creates a singleton instants of the class.
     */
    private final static DataAccessor INSTANCE = new DataAccessor();

    /**
     * The connection made to the database. This is closed when the JavaFX stage is closed.
     */
    private Connection connection;

    /**
     * The constructor which is made private so that it can not be initialized from other classes.
     */
    private DataAccessor() {
        File database = new File("MainDatabase.db");
        try {
            if (!database.exists()) {
                createDatabase("MainDatabase.db");
            }
            connection = DriverManager.getConnection("jdbc:sqlite:MainDatabase.db");
            runStatement("PRAGMA foreign_keys = ON;");
        } catch (SQLException e) {
            System.out.println("SQLiteAccessor.connect: " + e);
        }
    }

    /**
     * Changes the connection of the database that is currently loaded.
     * @param path
     */
    public void changeConnection(String path){
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:"+path);
        } catch (SQLException e) {
            System.out.println("SQLiteAccessor.connect: " + e);
        }
    }

    /**
     * Creates a new database if one doesn't exist
     * @throws SQLException
     */
    private void createDatabase(String location) throws SQLException {
        Connection newdb = DriverManager.getConnection("jdbc:sqlite:" + location);

        Statement stmt = newdb.createStatement();
        stmt.execute("PRAGMA foreign_keys = ON;");

        stmt.execute("CREATE TABLE lists (" + 
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name VARCHAR(32) UNIQUE NOT NULL" +
                ")");
        stmt.execute("CREATE TABLE reports (" + 
                "id VARCHAR(8) NOT NULL, " + 
                "list_id INTEGER NOT NULL, " +
                "date TIMESTAMP NOT NULL, " +
                "primary_description VARCHAR(50) NOT NULL, " +
                "secondary_description VARCHAR(50) NOT NULL, " + 
                "domestic BOOLEAN, " +
                "x_coord INT, " +
                "y_coord INT, " +
                "latitude FLOAT, " +
                "longitude FLOAT, " +
                "location_description VARCHAR(50), " +
                "PRIMARY KEY(id, list_id), " +
                "FOREIGN KEY(list_id) REFERENCES lists(id) ON UPDATE CASCADE ON DELETE CASCADE" +
                ")");
        stmt.execute("CREATE TABLE crimes (" + 
                "report_id VARCHAR(8) NOT NULL, " +
                "list_id INTEGER NOT NULL, " +
                "block VARCHAR(50), " +
                "iucr VARCHAR(4), " +
                "fbicd VARCHAR(3), " + 
                "arrest BOOLEAN, " +
                "beat INT, " +
                "ward INT, " +
                "FOREIGN KEY(report_id, list_id) REFERENCES reports(id, list_id) ON UPDATE CASCADE ON DELETE CASCADE, " +
                "PRIMARY KEY(report_id, list_id)" +
                ")");
        stmt.execute("CREATE VIEW crimedb AS " +
                "SELECT *" + 
                "FROM reports JOIN crimes c ON id=c.report_id AND reports.list_id=c.list_id");
        stmt.close();
        newdb.close();
    }


    /**
     * Getter for the connected to the database.
     *
     * @return connection       The connection to the database.
     */
    public Connection getConnection() {
        return this.connection;
    }

    /**
     * Used to get the singleton instants of the class when assessing. This is done over a "static" class due to it
     * implementing an interface.
     *
     * @return INSTANCE     The only instants of this class.
     */
    public static DataAccessor getInstance() {
        return INSTANCE;
    }

    /**
     * Gets the number of entries in the database.
     *
     * @return Size     The number of entries.
     */
    public int getSize(int listId) {
        String condition = ControllerData.getInstance().getWhereQuery();
        // See how many entries are in the view crimedb.
        String query = "SELECT COUNT(*) FROM crimedb WHERE "+ condition;
        if (!condition.isEmpty()) {
            query += " AND";
        }
        query += " list_id=" + listId +";";
        int size = 0;
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            size = rs.getInt(1);
            // Closes the statement and result set.
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("SQLiteAccessor.size: " + e);
        }
        return size;
    }

    /**
     * Using the current page of the paginator this method returns the relevant section
     * of reports to be used from the database in the TableView.
     *
     * @return reports      The list of reports to display.
     */
    public ArrayList<Report> getPageSet(int listId) {
        ControllerData connData = ControllerData.getInstance();
        String condition = connData.getWhereQuery();
        int page = connData.getCurrentPage();
        // This only get 1000 reports per page of the paginator.
        int start = page * 1000;
        int end = 1000;

        String query = "SELECT * FROM crimedb WHERE " + condition;
        if (!condition.isEmpty()) {
            query += " AND ";
        }
        query +=" list_id=" + listId + " ORDER BY id LIMIT "+end+" OFFSET "+start+";";
        return selectReports(query);
    }

    /**
     * Gets all the reports from the database.
     *
     * @return  All reports from the database.
     */
    public ArrayList<Report> getAll(int listId) {
        String query = "SELECT * FROM crimedb WHERE list_id=" + listId;
        return selectReports(query);
    }

    /**
     * Uses a query for the crimedb view to get a selection of reports.
     *
     * @param query     The query for the crimedb view.
     * @return          A list of reports.
     */
    private ArrayList<Report> selectReports(String query) {
        ArrayList<Report> reports = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            // Converts all results into crimes.
            while (rs.next()) {
                Crime crime = generateCrime(rs);
                reports.add(crime);
            }
            // Closes the statement and result set.
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("SQLiteAccessor.selectReports: " + e);
            return null;
        }
        return reports;
    }

    /**
     * Turns an entry in a result set into a Crime object.
     * @param rs The result of the result set to be turned into a Crime object.
     * @return The created Crime object.
     * @throws SQLException Thrown if a value is invalid.
     */
    private Crime generateCrime(ResultSet rs) throws SQLException{
        Timestamp tt = rs.getTimestamp("date");
        Crime crime = new Crime(
                rs.getString("id"),
                tt.toLocalDateTime(),
                rs.getString("block"),
                rs.getString("iucr"),
                rs.getString("primary_description"),
                rs.getString("secondary_description"),
                rs.getString("location_description"),
                rs.getBoolean("arrest"),
                rs.getBoolean("domestic"),
                rs.getInt("beat"),
                rs.getInt("ward"),
                rs.getString("fbicd"),
                rs.getInt("x_coord"),
                rs.getInt("y_coord"),
                rs.getDouble("latitude"),
                rs.getDouble("longitude")
        );
        return crime;
    }

    /**
     * Gets a single crime entry from the database.
     *
     * @param entry     The case number of a crime object.
     * @return          A single report.
     */
    public Crime getCrime(String entry, int listId) {
        Crime crime = null;
        try {
            PreparedStatement psCrime = connection.prepareStatement("SELECT " + 
            "id, list_id, date, primary_description, secondary_description, domestic, " +
            "x_coord, y_coord, latitude, longitude, location_description, block, iucr, fbicd, arrest, beat, ward" +
            " FROM crimedb WHERE id=? AND list_id=?;");
            psCrime.setString(1, entry);
            psCrime.setInt(2, listId);
            ResultSet rs = psCrime.executeQuery();

            while (rs.next()) {
                crime = generateCrime(rs);
            }
            
            // Closes the statement.
            psCrime.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println("SQLiteAccessor.getCrime: " + e);
            return null;
        }
        return crime;
    }

    /**
     * Deletes an entry from the database.
     *
     * @param entryId       The case number of the crime object.
     */
    public void deleteReport(String entryId, int listId) throws SQLException{
        String reportQuery = "DELETE FROM reports WHERE id = '" + entryId + "' AND list_id=" + listId;
        runStatement(reportQuery);
    }

    /**
     * Runs during the importing of a outside database into the main database.
     *
     * @param selectedFile      The Database file.
     */
    public void importInDB(File selectedFile, int listId) throws SQLException {
        runStatement("ATTACH '" + selectedFile + "' AS " + "newCrimeDB;");
        runStatement("INSERT OR REPLACE INTO reports " +
            "SELECT " +
            "id, " + 
            "'" + listId + "' ," +
            "date, " +
            "primary_description, " +
            "secondary_description, " + 
            "domestic, " +
            "x_coord, " +
            "y_coord, " +
            "latitude, " +
            "longitude, " +
            "location_description " + 
            "FROM newCrimeDB.reports");

        runStatement("INSERT OR REPLACE INTO crimes SELECT " +
            "report_id, " + 
            "'" + listId + "', " +
            "block, " +
            "iucr, " +
            "fbicd, " + 
            "arrest, " +
            "beat, " +
            "ward " + 
            "FROM newCrimeDB.crimes");

        runStatement("DETACH DATABASE " + "'newCrimeDB';");
    }


    /**
     * Runs a query with no result set and ensures that the statements are then closed.
     * @param query     The query being used.
     * @throws SQLException
     */
    private void runStatement(String query) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute(query);
        stmt.close();
    }

    /**
     * Is given a CSV file and imports it into the database.
     *
     * @param pathname      CSV file.
     */
    public void readToDB(File pathname, int listId) throws SQLException {
        ArrayList<String> schemaDefault = new ArrayList<>(Arrays.asList("CASE#", "DATE  OF OCCURRENCE", "BLOCK", " IUCR", " PRIMARY DESCRIPTION", " SECONDARY DESCRIPTION",
                " LOCATION DESCRIPTION", "ARREST", "DOMESTIC", "BEAT", "WARD", "FBI CD", "X COORDINATE", "Y COORDINATE",
                "LATITUDE", "LONGITUDE", "LOCATION"));
        try {
            FileReader csvFile = new FileReader(pathname);
            CSVReader reader = new CSVReader(csvFile);
            ArrayList<String[]> rows = new ArrayList<>();
            String[] row;
            ArrayList<String> schema = new ArrayList<>(Arrays.asList(reader.readNext())); // Will likely be used to determine crime vs incident
            while ((row = reader.readNext()) != null) {
                try {
                    rows.add(row);
                } catch (Exception e) {
                    System.out.println("readToDB" + e);
                }
            }
            reader.close();
            if (schemaDefault.equals(schema)) {
                // After getting all rows goes to write them into the database.
                write(rows, listId);
            } else {
                System.out.println("Invalid Schema.");
            }

        } catch (IOException e) {
            System.out.println("readToDB" + e);
        }
    }

    /**
     * Adds the ability to edit or add data into the database from a crime object.
     *
     * @param crime     A crime object.
     */
    public void editCrime(Crime crime, int listId) {
        try {
            PreparedStatement psReport = connection.prepareStatement("INSERT OR REPLACE INTO reports(id, list_id, date, primary_description, secondary_description, domestic, x_coord, y_coord, latitude, longitude, location_description) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
            PreparedStatement psCrime = connection.prepareStatement("INSERT OR REPLACE INTO crimes(report_id, list_id, block, iucr, fbicd, arrest, beat, ward) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?);");

            PSTypes.setPSString(psCrime, 1, crime.getCaseNumber()); // Case Number
            PSTypes.setPSInteger(psCrime, 2, listId); // List
            PSTypes.setPSString(psCrime, 3, crime.getBlock()); // Block
            PSTypes.setPSString(psCrime, 4, crime.getIucr()); // Iucr
            PSTypes.setPSString(psCrime, 5, crime.getFbiCD()); // FbiCD
            PSTypes.setPSBoolean(psCrime, 6, crime.getArrest()); // Arrest
            PSTypes.setPSInteger(psCrime, 7, crime.getBeat()); // Beat
            PSTypes.setPSInteger(psCrime, 8, crime.getWard()); // Ward

            PSTypes.setPSString(psReport, 1, crime.getCaseNumber()); // Case Number

            Timestamp date = Timestamp.valueOf(crime.getDate());
            PSTypes.setPSInteger(psReport, 2, listId); // List
            psReport.setTimestamp(3, date); // Date
            PSTypes.setPSString(psReport, 4, crime.getPrimaryDescription()); // Primary Description
            PSTypes.setPSString(psReport, 5, crime.getSecondaryDescription()); // Secondary Description
            PSTypes.setPSBoolean(psReport, 6, crime.getDomestic()); // Domestic
            PSTypes.setPSInteger(psReport, 7, crime.getXCoord()); // X Coordinate
            PSTypes.setPSInteger(psReport, 8, crime.getYCoord()); // Y Coordinate
            PSTypes.setPSDouble(psReport, 9, crime.getLatitude()); // Latitude
            PSTypes.setPSDouble(psReport, 10, crime.getLongitude()); // Longitude
            PSTypes.setPSString(psReport, 11, crime.getLocationDescription()); // Location Description

            psReport.execute();
            psReport.close();

            psCrime.execute();
            psCrime.close();

        } catch (SQLException e) {
            System.out.println("SQLiteAccessor.edit: " + e);
        }
    }


    /**
     * Adds a list of reports in the form of a string[] into the database.
     *
     * @param rows                  All rows from the CSV file.
     * @throws SQLException         An error during the insertion.
     */
    private void write(ArrayList<String[]> rows, int listId) throws SQLException {
        // Turn off autocommit to increase speed.
        connection.setAutoCommit(false);
        try (PreparedStatement psCrime = connection.prepareStatement("INSERT OR REPLACE INTO crimes(report_id, list_id, " + 
                "block, iucr, fbicd, arrest, beat, ward) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?);");
            PreparedStatement psReport = connection.prepareStatement("INSERT OR REPLACE INTO reports(id, list_id, date, " + 
                "primary_description, secondary_description, domestic, x_coord, y_coord, latitude, longitude, location_description) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);")) {
            // Uses batches to chain all the individual insertions into a single query to the database. This is also to increase speed.
            for (String[] row : rows) {
                addCrime(row, listId, psReport, psCrime);
            }
            // Executes all the insertions.
            psReport.executeBatch();
            psCrime.executeBatch();
            // Commits the changes and re-enables the auto commit.
            connection.commit();
        } catch (SQLException e) {
            System.out.println("SQLiteAccessor.write: " + e);
        }
        connection.setAutoCommit(true);
    }

    /**
     * Adds a crime to the report and crime statements required to add the crime to the database.
     */
    private void addCrime(String[] row, int listId, PreparedStatement psReport, PreparedStatement psCrime) throws SQLException, DateTimeParseException {
        PSTypes.setPSString(psReport, 1, row[0]); // Case Number
        PSTypes.setPSInteger(psReport, 2, listId); // List
        if (Objects.equals(row[1], "")) {
            psReport.setNull(3, Types.TIME);
        } else {
            psReport.setTimestamp(3, Timestamp.valueOf(parseDate(row[1])));
        } // Date
        PSTypes.setPSString(psReport, 4, row[4]); // Primary Description
        PSTypes.setPSString(psReport, 5, row[5]); // Secondary Description
        PSTypes.setPSBoolean(psReport, 6, row[8]); // Domestic
        PSTypes.setPSInteger(psReport, 7, row[12]); // X Coordinate
        PSTypes.setPSInteger(psReport, 8, row[13]); // Y Coordinate
        PSTypes.setPSDouble(psReport, 9, row[14]); // Latitude
        PSTypes.setPSDouble(psReport, 10, row[15]); // Longitude
        PSTypes.setPSString(psReport, 11, row[6]); // Location Description
        psReport.addBatch();
        
        PSTypes.setPSString(psCrime, 1, row[0]); // Case Number
        PSTypes.setPSInteger(psCrime, 2, listId); // List
        PSTypes.setPSString(psCrime, 3, row[2]); // Block
        PSTypes.setPSString(psCrime, 4, row[3]); // Iucr
        PSTypes.setPSString(psCrime, 5, row[11]); // FbiCD
        PSTypes.setPSBoolean(psCrime, 6, row[7]); // Arrest
        PSTypes.setPSInteger(psCrime, 7, row[9]); // Beat
        PSTypes.setPSInteger(psCrime, 8, row[10]); // Ward
        psCrime.addBatch();
    }

    /**
     * Tries the 2 valid date formats used by the application.
     * @param date The string format of the date to be converted to LocalDateTime.
     * @return The LocalDateTime from a successful parsing.
     */
    private LocalDateTime parseDate(String date) {
        DateTimeFormatter formatter;
        try {
            formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a", Locale.US);
            return LocalDateTime.parse(date, formatter);
        } catch (DateTimeParseException ignore) {
            formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss");
            return LocalDateTime.parse(date, formatter);
        }
    }

    /**
     * Creates a new list in the database with the name passed in.
     * @param name The name of the new database.
     */
    public void createList(String name) {
        try (PreparedStatement psList = connection.prepareStatement("INSERT INTO lists(name) VALUES (?);")) {
            PSTypes.setPSString(psList, 1, name);
            psList.execute();
        } catch (SQLException e) {
            //TODO: handle exception
            System.out.println("SQLiteAccessor.createList: " + e);
        }
    }

    /**
     * 
     * @return An ObservableList containing all the lists in the database.
     */
    public ObservableList<String> getLists() {
        ArrayList<String> lists = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT name FROM lists");
            while (rs.next()) {
                lists.add(rs.getString("name"));
            }
            // Closes the statement and result set.
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("SQLiteAccessor.selectReports: " + e);
        }
        ObservableList<String> details = FXCollections.observableArrayList(lists);
        return details;
    }

    /**
     * Renames a list contained in the database.
     * @param oldName The current name of the list.
     * @param newName The name that the list will be changed to.
     */
    public void renameList(String oldName, String newName) {
        try {
            PreparedStatement psList = connection.prepareStatement("UPDATE lists SET name=? WHERE name=?;");
            psList.setString(1, newName);
            psList.setString(2, oldName);
            psList.execute();
            psList.close();
        } catch (SQLException e) {
            System.out.println("SQLiteAccessor.renameList: " + e);
        }  
    }

    /**
     * Returns the numeric ID of a list given its name.
     * @param selectedList The name of the required list
     * @return The numeric ID of the selected list.
     */
    public Integer getListId(String selectedList) {
        Integer listId = null;
        try (PreparedStatement psList = connection.prepareStatement("SELECT id FROM lists WHERE name=?;")) {
            psList.setString(1, selectedList);
            ResultSet lists = psList.executeQuery();
            while(lists.next()) {
                listId = lists.getInt("id");
            }
            // Closes the statement and result set.
            lists.close();
        } catch (SQLException e) {
            System.out.println("SQLiteAccessor.getListId: " + e);
        }
        return listId;
    }

    /**
     * Deletes the selected list from the database.
     * @param selectedList The name of the list to be deleted.
     */
    public void deleteList(String selectedList) {
        try (PreparedStatement psList = connection.prepareStatement("DELETE FROM lists WHERE name=?;")) {
            psList.setString(1, selectedList);
            psList.execute();
        } catch (SQLException e) {
            System.out.println("SQLiteAccessor.deleteList: " + e);
        }
    }

    /**
     * Determines what type of file needs to be exported to and navigates to the relevant method.
     * @param conditions The filter conditions to be complied with.
     * @param listId The list the data is being exported from.
     * @param saveLocation The location tof the new file.
     * @throws SQLException
     */
    public void export(String conditions, int listId, String saveLocation) throws SQLException{
        if (saveLocation.endsWith(".db")) {
            exportDB(conditions, listId, saveLocation);
        } else if (saveLocation.endsWith("csv")) {
            exportCSV(conditions, listId, saveLocation);
        }
    }

    /**
     * Writes the data contained in the database relevant to the current filter conditions into a csv file.
     * @param conditions The filter conditions to be complied with.
     * @param listId The list the data is being exported from.
     * @param saveLocation The location of the new file.
     * @throws SQLException
     */
    private void exportCSV(String conditions, int listId, String saveLocation) throws SQLException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(saveLocation), ','); Statement stmt = connection.createStatement();){
        
            writer.writeNext("CASE#,DATE  OF OCCURRENCE,BLOCK, IUCR, PRIMARY DESCRIPTION, SECONDARY DESCRIPTION, LOCATION DESCRIPTION,ARREST,DOMESTIC,BEAT,WARD,FBI CD,X COORDINATE,Y COORDINATE,LATITUDE,LONGITUDE,LOCATION".split(","));

            String query = "SELECT " +
                "id, " + 
                "date, " +
                "block, " +
                "iucr, " +
                "primary_description, " +
                "secondary_description, " + 
                "location_description, " + 
                "arrest, " +
                "domestic, " +
                "beat, " +
                "ward, " + 
                "fbicd, " +
                "x_coord, " +
                "y_coord, " +
                "latitude, " +
                "longitude " +
                "FROM crimedb WHERE ";
            query = addConditions(query, conditions, listId);
            ResultSet rs = stmt.executeQuery(query);
            writer.writeAll(rs, false);
            rs.close();
        } catch (IOException e) {
            System.out.println(e);
        }   
    }

    /**
     * Writes the data contained in the database relevant to the current filter conditions into a database file.
     * @param conditions The filter conditions to be complied with.
     * @param listId The list the data is being exported from.
     * @param saveLocation The location of the new file.
     * @throws SQLException
     */
    private void exportDB(String conditions, int listId, String saveLocation) throws SQLException {
        createExportDatabase(saveLocation);
        runStatement("ATTACH DATABASE '" + saveLocation + "' AS other;");

        String query = "INSERT INTO other.reports SELECT " +
        "id, " + 
        "date, " +
        "primary_description, " +
        "secondary_description, " + 
        "domestic, " +
        "x_coord, " +
        "y_coord, " +
        "latitude, " +
        "longitude, " +
        "location_description " + 
        "FROM crimedb WHERE ";
        query = addConditions(query, conditions, listId);
        runStatement(query);

        query = "INSERT INTO other.crimes SELECT " +
        "report_id, " + 
        "block, " +
        "iucr, " +
        "fbicd, " + 
        "arrest, " +
        "beat, " +
        "ward " + 
        "FROM crimedb WHERE ";
        query = addConditions(query, conditions, listId);
        runStatement(query);
    }

    /**
     * Adds the required list to the conditions.
     * @param query SELECT statement to be refined.
     * @param conditions Filter conditions applied to query.
     * @param listId List to query data from.
     * @return
     */
    private String addConditions(String query, String conditions, int listId) {
        query += conditions;
        if (!conditions.isEmpty()) {
            query += " AND";
        }
        query += " list_id=" + listId +";";
        return query;
    }

    /**
     * Creates a database for the data to be exported into at the given location.
     * @param location The location to create the database.
     */
    private void createExportDatabase(String location) {
        try (Connection newdb = DriverManager.getConnection("jdbc:sqlite:" + location); Statement stmt = newdb.createStatement()) {
            
            stmt.execute("PRAGMA foreign_keys = ON;");

            stmt.execute("CREATE TABLE reports (" + 
                    "id VARCHAR(8) NOT NULL, " + 
                    "date TIMESTAMP NOT NULL, " +
                    "primary_description VARCHAR(50) NOT NULL, " +
                    "secondary_description VARCHAR(50) NOT NULL, " + 
                    "domestic BOOLEAN, " +
                    "x_coord INT, " +
                    "y_coord INT, " +
                    "latitude FLOAT, " +
                    "longitude FLOAT, " +
                    "location_description VARCHAR(50), " +
                    "PRIMARY KEY(id) " +
                    ")");
            stmt.execute("CREATE TABLE crimes (" + 
                    "report_id VARCHAR(8) NOT NULL, " +
                    "block VARCHAR(50), " +
                    "iucr VARCHAR(4), " +
                    "fbicd VARCHAR(3), " + 
                    "arrest BOOLEAN, " +
                    "beat INT, " +
                    "ward INT, " +
                    "FOREIGN KEY(report_id) REFERENCES reports(id) ON UPDATE CASCADE ON DELETE CASCADE, " +
                    "PRIMARY KEY(report_id)" +
                    ")");
        } catch (SQLException e) { 
            System.out.println("SQLiteAccessor.createExportDB: " + e);
        }
    }

}
