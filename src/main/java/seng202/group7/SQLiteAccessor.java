package seng202.group7;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;

public class SQLiteAccessor implements DataAccessor {
    /**
     * This creates a singleton instants of the class.
     */
    private final static SQLiteAccessor INSTANCE = new SQLiteAccessor();

    /**
     * The constructor which is made private so that it can not be initialized from other classes.
     */
    private SQLiteAccessor() {}

    /**
     * Used to get the singleton instants of the class when assessing. This is done over a "static" class due to it
     * implementing an interface.
     *
     * @return INSTANCE     The only instants of this class.
     */
    public static SQLiteAccessor getInstance() {
        return INSTANCE;
    }


    public static Connection connect(String url) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }

    @Override
    public ArrayList<Report> read(File pathname) {
        Connection connection = connect("jdbc:sqlite:" + pathname);

        ArrayList<Report> reports = new ArrayList<>();
        String query = "SELECT * from crimedb";
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Crime crime = new Crime(
                        rs.getString("id"),
                        rs.getTimestamp("date").toLocalDateTime(),
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

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return reports;
    }

    @Override
    public void write(ArrayList<Report> reports, String pathname) {

    }

}
