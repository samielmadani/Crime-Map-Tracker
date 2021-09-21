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


    public Connection connect(File file) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + file);
        } catch (SQLException e) {
            System.out.println("SQLiteAccessor.connect: " + e);
        }
        return connection;
    }

    //accepted schema (set):id, block, iucr, fbicd, arrest, beat, ward, date, primary_description, secondary_description, location_description, domestic, x_coord, y_coord, latitude, longitude
    public void meta(Connection connection) {
        try {
            DatabaseMetaData md = connection.getMetaData();
            ResultSet rs = md.getTables(null, null, "%", new String[]{"VIEW"});
            while(rs.next()) {
                String name = rs.getString(3);
                System.out.print(name);
                String query = "SELECT * FROM " + name;
                Statement stmt = connection.createStatement();
                ResultSetMetaData rsmd = stmt.executeQuery(query).getMetaData();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    System.out.print("\t" + rsmd.getColumnName(i));
                }
            }
        } catch (SQLException e) {
            System.out.println("SQLiteAccessor.meta: " + e);
        }
    }

    public void delete(Connection connection, String table){
        String query = "DELETE FROM " + table;
        try {
            Statement stmt = connection.createStatement();
            stmt.executeQuery(query);
        } catch (SQLException e) {
            System.out.println("SQLiteAccessor.delete: " + e);
        }
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
            System.out.println("SQLiteAccessor.select: " + e);
        }
        return reports;
    }

    @Override
    public ArrayList<Report> read(File file) {
        Connection connection = connect(file);
        String query = "SELECT * FROM crimedb";
        ArrayList<Report> reports = select(connection, query);
        return reports;

    }

    public void create(File file) {
        Connection connection = connect(file);
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(
                    "create table crimes\n" +
                    "(\n" +
                    "    case_number varchar(8) not null\n" +
                    "        constraint crimes_pk\n" +
                    "            primary key,\n" +
                    "    block       varchar(50),\n" +
                    "    iucr        varchar(4),\n" +
                    "    fbicd       varchar(3),\n" +
                    "    arrest      boolean,\n" +
                    "    beat        int,\n" +
                    "    ward        int\n" +
                    ");"
            );
            ps.executeUpdate();
            ps = connection.prepareStatement(
                    "create unique index crimes_case_number_uindex\n" +
                    "    on crimes (case_number);"
            );
            ps.executeUpdate();
            ps = connection.prepareStatement(
                    "create table reports\n" +
                    "(\n" +
                    "    report_id             varchar(8)  not null\n" +
                    "        constraint report_pk\n" +
                    "            primary key,\n" +
                    "    date                  timestamp   not null,\n" +
                    "    primary_description   varchar(50) not null,\n" +
                    "    secondary_description varchar(50) not null,\n" +
                    "    domestic              boolean,\n" +
                    "    x_coord               int,\n" +
                    "    y_coord               int,\n" +
                    "    latitude              float,\n" +
                    "    longitude             float,\n" +
                    "    location_description  varchar(50)\n" +
                    ");"
            );
            ps.executeUpdate();
            ps = connection.prepareStatement(
                    "CREATE VIEW crimedb as\n" +
                    "select reports.report_id as 'id',\n" +
                    "       block,\n" +
                    "       iucr,\n" +
                    "       fbicd,\n" +
                    "       arrest,\n" +
                    "       beat,\n" +
                    "       ward,\n" +
                    "       date,\n" +
                    "       primary_description,\n" +
                    "       secondary_description,\n" +
                    "       location_description,\n" +
                    "       domestic,\n" +
                    "       x_coord,\n" +
                    "       y_coord,\n" +
                    "       latitude,\n" +
                    "       longitude\n" +
                    "from crimes\n" +
                    "         inner join reports on crimes.case_number = reports.report_id;"
            );
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQLiteAccessor.create: " + e);
        }

    }

    //can only insert values into a fresh table
    @Override
    public void write(ArrayList<Report> reports, String pathname) {
        write(reports, new File(pathname));
    }

    public void write(ArrayList<Report> reports, File file) {
        Connection connection = connect(file);
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

                    ps.executeUpdate();
                }

                ps = connection.prepareStatement("INSERT INTO reports(report_id, date, primary_description, secondary_description, domestic, x_coord, y_coord, latitude, longitude, location_description) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
                ps.setString(1, id);
                ps.setTimestamp(2, Timestamp.valueOf(report.getDate()));
                ps.setString(3, report.getPrimaryDescription());
                ps.setString(4, report.getSecondaryDescription());
                ps.setString(5, report.getLocationDescription());
                ps.setBoolean(6, report.getDomestic());
                setInteger(ps, 7, report.getXCoord());
                setInteger(ps, 8, report.getYCoord());
                setDouble(ps, 9, report.getLatitude());
                setDouble(ps, 10, report.getLongitude());

                ps.executeUpdate();
            }

        } catch (SQLException e) {
            System.out.println("SQLiteAccessor.write: " + e);
        }
    }

    void setInteger(PreparedStatement ps, int parameterIndex, Integer x) {
        try {
            if (x == null) {
                ps.setNull(parameterIndex, Types.INTEGER);
            } else {
                ps.setInt(parameterIndex, x);
            }
        } catch (SQLException e) {
            System.out.println("SQLiteAccessor.setInteger: " + e);
        }
    }

    void setDouble(PreparedStatement ps, int parameterIndex, Double x) {
        try {
            if (x == null) {
                ps.setNull(parameterIndex, Types.DOUBLE);
            } else {
                ps.setDouble(parameterIndex, x);
            }
        } catch (SQLException e) {
            System.out.println("SQLiteAccessor.setDouble: " + e);
        }
    }

}
