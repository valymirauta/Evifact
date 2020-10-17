package database;

import java.sql.*;

public class DatabaseHandler {
    public static DatabaseHandler handler = null;
    public final String DB_URL = "jdbc:sqlite:evifact.db";
    public static Connection conn = null;
    public static Statement stmt = null;

    private DatabaseHandler() {
        createConnection();
        creareTabelAsociatii();
        creareTabelFurnizori();
        creareTabelAsociatiiFurnizori();
        creareTabelCodClient();
    }

    public static DatabaseHandler getInstance() {
        if (handler == null) {
            handler = new DatabaseHandler();
        }
        return handler;
    }

    void createConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void creareTabelAsociatii() {
        String TABLE_NAME = "asociatii";

        try {
            stmt = conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toLowerCase(), null);
            if (!tables.next()) {
                stmt.execute("CREATE TABLE " + TABLE_NAME + "(" +
                        " Id integer PRIMARY KEY AUTOINCREMENT," +
                        " CIF text NOT NULL," +
                        " denumire text NOT NULL," +
                        " denumireScurta text NOT NULL," +
                        " adresa text NOT NULL)");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void creareTabelFurnizori() {
        String TABLE_NAME = "furnizori";

        try {
            stmt = conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toLowerCase(), null);
            if (!tables.next()) {
                stmt.execute("CREATE TABLE " + TABLE_NAME + "(" +
                        " Id integer PRIMARY KEY AUTOINCREMENT," +
                        " CUI text NOT NULL," +
                        " denumire text NOT NULL," +
                        " adresa text NOT NULL)");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void creareTabelAsociatiiFurnizori() {
        String TABLE_NAME = "asociatii_furnizori";

        try {
            stmt = conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toLowerCase(), null);
            if (!tables.next()) {
                stmt.execute("CREATE TABLE " + TABLE_NAME + "(" +
                        " Id integer PRIMARY KEY AUTOINCREMENT," +
                        " asociatiiID integer NOT NULL," +
                        " furnizoriID integer NOT NULL," +
                        " FOREIGN KEY (asociatiiID)" +
                        "   REFERENCES asociatii (Id)," +
                        " FOREIGN KEY (furnizoriID)" +
                        "   REFERENCES furnizori (Id))");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void creareTabelCodClient() {
        String TABLE_NAME = "codClient";

        try {
            stmt = conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toLowerCase(), null);
            if (!tables.next()) {
                stmt.execute("CREATE TABLE " + TABLE_NAME + "(" +
                        " Id integer PRIMARY KEY AUTOINCREMENT," +
                        " codClient text NOT NULL," +
                        " tipUtilitate text NOT NULL," +
                        " asociatiiID integer NOT NULL," +
                        " furnizoriID integer NOT NULL," +
                        " FOREIGN KEY (asociatiiID)" +
                        "   REFERENCES asociatii (Id)," +
                        " FOREIGN KEY (furnizoriID)" +
                        "   REFERENCES furnizori (Id))");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}


