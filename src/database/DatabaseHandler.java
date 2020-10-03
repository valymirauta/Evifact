package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHandler {
    public DatabaseHandler handler = null;
    public final String DB_URL = "jdbc:sqlite:C:/sqlite/db/tests.db";
    public Connection conn = null;
    public Statement stmt = null;

    private DatabaseHandler() {
        createConnection();
        createNewTable();
    }

    public DatabaseHandler getInstance() {
        if (handler == null) {
            handler = new DatabaseHandler();
        }
        return handler;
    }

    void createConnection() {
        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void createNewTable() {
        String sql = "CREATE TABLE IF NOT EXIST asociatii (\n" +
                " ID integer PRIMARy KEY,\n" +
                " CIF text NOT NULL,\n" +
                " denumire text NOT NULL\n" +
                ");";
        try {
            stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
