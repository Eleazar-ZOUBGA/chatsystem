package chatsystem.DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DataBase {
    private String databaseName;
    private Connection conn;

    public DataBase(String databaseName) {
        this.databaseName = databaseName;
    }

    public void connect(Connection conn) {
        this.conn = conn;
    }

    public void createUserMessagesTable() {
        String sql = "CREATE TABLE IF NOT EXISTS userMessages (" +
                "idMessage INTEGER PRIMARY KEY AUTOINCREMENT," +
                "senderId INTEGER NOT NULL," +
                "receiverId INTEGER NOT NULL," +
                "message TEXT," +
                "time TEXT" +
                ")";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("***Table userMessages has been created***");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertMessage(int senderId, int receiverId, String message, String date) {
        String sql = "INSERT INTO userMessages (senderId, receiverId, message, time) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, senderId);
            pstmt.setInt(2, receiverId);
            pstmt.setString(3, message);
            pstmt.setString(4, date);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<String> getMessagesBetween(int senderId, int receiverId) {
        List<String> messages = new ArrayList<>();
        String sql = "SELECT senderId, message, time FROM userMessages WHERE (senderId = ? AND receiverId = ?) OR (senderId = ? AND receiverId = ?) ORDER BY time";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, senderId);
            pstmt.setInt(2, receiverId);
            pstmt.setInt(3, receiverId);
            pstmt.setInt(4, senderId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String entity = rs.getInt("senderId") + "::" + rs.getString("message") + "::" + rs.getString("time");
                messages.add(entity);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public void close() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
