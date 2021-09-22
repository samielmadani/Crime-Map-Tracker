package seng202.group7.data;

import au.com.bytecode.opencsv.CSVReader;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

/**
 * This class works to link the SQLite database with our java application.
 * It gets key data from the database during the runtime of the application.
 *
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
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/testing/MainDatabase.db");
        } catch (SQLException e) {
            System.out.println("SQLiteAccessor.connect: " + e);
        }
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
    public int getSize() {
        // See how many entries are in the view crimedb.
        String query = "SELECT COUNT(*) FROM crimedb;";
        int size = 0;
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            size = rs.getInt(1);
            // Closes the statement and result set.
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("SQLiteAccessor.delete: " + e);
        }
        return size;
    }

    /**
     * Using the current page of the paginator this method returns the relevant section
     * of reports to be used from the database in the tableview.
     *
     * @param page          The current page.
     * @return reports      The list of reports to display.
     */
    public ArrayList<Report> getPageSet(int page) {
        // This only get 1000 reports per page of the paginator.
        int start = page * 1000;
        int end = 1000;
        String query = "SELECT * FROM crimedb LIMIT "+end+" OFFSET "+start+";";
        return getData(query);
    }

    public ArrayList<Report> getAll() {
        String query = "SELECT * FROM crimedb";
        return getData(query);
    }


    private ArrayList<Report> getData(String query) {
        ArrayList<Report> reports = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            // Converts all results into crimes.
            while (rs.next()) {
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
                reports.add(crime);
            }
            // Closes the statement and result set.
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("SQLiteAccessor.select: " + e);
        }
        return reports;
    }

    //TODO add functionality and use this method.
    public void delete(Connection connection, String table){
        String query = "DELETE FROM " + table;
        try {
            Statement stmt = connection.createStatement();
            stmt.executeQuery(query);
            stmt.close();
        } catch (SQLException e) {
            System.out.println("SQLiteAccessor.delete: " + e);
        }
    }

    /**
     * Gets the schema for the connected database and stores it as a string.
     *
     * @param conn          The database connection being used.
     * @return schema       The database's schema.
     */
    private String getSchema(Connection conn) {
        try {
            DatabaseMetaData md = conn.getMetaData();
            ResultSet rs = md.getTables(null, null, "%", new String[]{"TABLE"});
            StringBuilder sb = new StringBuilder();
            while(rs.next()) {
                String name = rs.getString(3);
                sb.append(name);
                sb.append(" {");
                // Gets the first entry
                String query = "SELECT * FROM " + name + " LIMIT 1;";
                Statement stmt = connection.createStatement();
                //Gets the schema of the entry
                ResultSetMetaData rsmd = stmt.executeQuery(query).getMetaData();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    sb.append("(").append(rsmd.getColumnName(i)).append(", ");
                    sb.append(rsmd.getColumnType(i)).append(")");
                    sb.append(", ");
                }
                sb.append("\n");
                stmt.close();
            }
            rs.close();
            return sb.toString();
        } catch (SQLException e) {
            System.out.println("SQLiteAccessor.getSchema: " + e);
        }
        return null;
    }

    /**
     * Creates a connection to the outside database and check if its schema matches the main databases.
     * @param selectedFile      The outside database.
     * @return valid            True if the database if valid.
     */
    private boolean validateSchema(File selectedFile){
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + selectedFile);
            String newSchema = getSchema(conn);
            conn.close();
            // Compares main schema to schema of new file.
            return Objects.equals(getSchema(connection), newSchema);
        } catch (SQLException e) {
            System.out.println("SQLiteAccessor.validateSchema: " + e);
        }
        return false;
    }


    /**
     * Runs during the importing of a outside database into the main database.
     *
     * @param selectedFile      The Database file.
     */
    public void importInDB(File selectedFile) {
        // Checks if schema is valid then attaches the database and inserts the data from the table and then detaches the database.
        if (validateSchema(selectedFile)) {
            runStatement("ATTACH '" + selectedFile + "' AS " + "newCrimeDB;");
            runStatement("INSERT OR REPLACE INTO crimes SELECT * FROM newCrimeDB.crimes");
            runStatement("DETACH DATABASE " + "'newCrimeDB';");
        } else {
            //TODO add error handling for when database and CSV can be used.
            System.out.println("Invalid Schema.");
        }
    }

    /**
     * Runs a query with no result set and ensures that the statements are then closed.
     * @param query     The query being used.
     */
    private void runStatement(String query) {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            System.out.println("SQLiteAccessor.runningStatement: " + query + " " + e);
        } finally {
            try {
                if (stmt != null) { stmt.close();}
            } catch (SQLException e) {
                System.out.println("SQLiteAccessor.CloseStmt: " + query + " " + e);
            }
        }
    }

    /**
     * Is given a CSV file and imports it into the database.
     *
     * @param pathname      CSV file.
     */
    public void readToDB(File pathname) {
        try {
            FileReader csvFile = new FileReader(pathname);
            CSVReader reader = new CSVReader(csvFile);
            ArrayList<String[]> rows = new ArrayList<>();
            String[] row;
            String [] schema = reader.readNext(); // Will likely be used to determine crime vs incident
            while ((row = reader.readNext()) != null) {
                try {
                    rows.add(row);
                } catch (Exception e) {
                    System.out.println("test " + e.getMessage());
                }
            }
            reader.close();
            // After getting all rows goes to write them into the database.
            write(rows);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a list of reports in the form of a string[] into the database.
     *
     * @param rows                  All rows from the CSV file.
     * @throws SQLException         An error during the insertion.
     */
    private void write(ArrayList<String[]> rows) throws SQLException {
        // Turn off autocommit to increase speed.
        connection.setAutoCommit(false);
        PreparedStatement psCrime = null;
        PreparedStatement psReport = null;
        try {
            psCrime = connection.prepareStatement("INSERT OR REPLACE INTO crimes(case_number, block, iucr, fbicd, arrest, beat, ward) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?);");
            psReport = connection.prepareStatement("INSERT OR REPLACE INTO reports(report_id, date, primary_description, secondary_description, domestic, x_coord, y_coord, latitude, longitude, location_description) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
            // Uses batchs to chain all the individual insertions into a single query to the database. This is also to increase speed.
            for (String[] row : rows) {
                PSTypes.setPSString(psCrime, 1, row[0]); // Case Number
                PSTypes.setPSString(psCrime, 2, row[2]); // Blockps.setString(2, row[2]);
                PSTypes.setPSString(psCrime, 3, row[3]); // Iucr
                PSTypes.setPSString(psCrime, 4, row[11]); // FbiCD
                PSTypes.setPSBoolean(psCrime, 5, row[7]); // Arrest
                PSTypes.setPSInteger(psCrime, 6, row[9]); // Beat
                PSTypes.setPSInteger(psCrime, 7, row[10]); // Ward
                psCrime.addBatch();

                PSTypes.setPSString(psReport, 1, row[0]); // Case Number
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a", Locale.US);
                if (Objects.equals(row[1], "")) {
                    psReport.setNull(2, Types.TIME);
                } else {
                    psReport.setTimestamp(2, Timestamp.valueOf(LocalDateTime.parse(row[1], formatter)));
                } // Date
                PSTypes.setPSString(psReport, 3, row[4]); // Primary Description
                PSTypes.setPSString(psReport, 4, row[5]); // Secondary Description
                PSTypes.setPSBoolean(psReport, 5, row[8]); // Domestic
                PSTypes.setPSInteger(psReport, 6, row[12]); // X Coordinate
                PSTypes.setPSInteger(psReport, 7, row[13]); // Y Coordinate
                PSTypes.setPSDouble(psReport, 8, row[14]); // Latitude
                PSTypes.setPSDouble(psReport, 9, row[15]); // Longitude
                PSTypes.setPSString(psReport, 10, row[6]); // Location Description
                psReport.addBatch();
            }
            // Executes all the insertions.
            psCrime.executeBatch();
            psReport.executeBatch();
            // Commits the changes and re-enables the auto commit.
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println("SQLiteAccessor.write: " + e);
        } finally {
            // Closes all prepared statements.
            try {
                if (psCrime != null) { psCrime.close(); }
                if (psReport != null) { psReport.close(); }
            } catch (SQLException e) {
                System.out.println("SQLiteAccessor.ClosePS: " + e);
            }
        }

    }

}
