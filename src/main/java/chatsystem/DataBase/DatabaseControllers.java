package chatsystem.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseControllers {
    private String databaseName;

    public DatabaseControllers(String databaseName) {
        this.databaseName = databaseName;
    }

    public Connection connect() {
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:" + databaseName;
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void close(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
