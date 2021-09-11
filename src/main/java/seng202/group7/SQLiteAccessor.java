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

    public ArrayList<Report> select(Connection connection, String query){
        ArrayList<Report> reports = new ArrayList<>();
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
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return reports;
    }

    @Override
    public ArrayList<Report> read(File pathname) {
        Connection connection = connect("jdbc:sqlite:" + pathname);
        String query = "SELECT * FROM crimedb";
        ArrayList<Report> reports = select(connection, query);
        return reports;

    }

//    public ArrayList<Report> getFiltered(File pathname) {
//        Connection connection = connect("jdbc:sqlite:" + pathname);
//        String query = "SELECT * FROM crimedb";
//        ArrayList<Report> reports = select(connection, query);
//        return reports;
//    }

    //can only insert values into a fresh table
    @Override
    public void write(ArrayList<Report> reports, String pathname) {
        Connection connection = connect("jdbc:sqlite:" + pathname);
        PreparedStatement ps = null;
        try {
            for(Report report : reports) {
                String id = null;
                if (report instanceof Crime) {
                    Crime crime = (Crime) report;
                    id = crime.getCaseNumber();
                    ps = connection.prepareStatement("INSERT INTO crimes(case_number, block, iucr, fbicd, arrest, beat, ward) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?);");
                    ps.setString(1, id);
                    ps.setString(2, crime.getBlock());
                    ps.setString(3, crime.getIucr());
                    ps.setString(4, crime.getFbiCD());
                    ps.setBoolean(5, crime.getArrest());
                    ps.setInt(6, crime.getBeat());
                    ps.setInt(7, crime.getWard());
                }
                ps = connection.prepareStatement("INSERT INTO reports(report_id, date, primary_description, secondary_description, domestic, x_coord, y_coord, latitude, longitude) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
                ps.setString(1, "");
                ps.setTimestamp(2, Timestamp.valueOf(report.getDate()));
                ps.setString(3, report.getPrimaryDescription());
                ps.setString(4, report.getSecondaryDescription());
                ps.setString(5, report.getLocationDescription());
                ps.setBoolean(6, report.getDomestic());
                ps.setInt(7, report.getXCoord());
                ps.setInt(8, report.getYCoord());
                ps.setDouble(9, report.getLatitude());
                ps.setDouble(10, report.getLongitude());
                ps.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
