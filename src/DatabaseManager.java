import java.sql.*;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/chat_app";
    private static final String USER = "root"; // Update if needed
    private static final String PASS = "root";     // Update if needed

    private static Connection conn;

    public static void connect() throws SQLException {
        conn = DriverManager.getConnection(URL, USER, PASS);
    }

    public static void saveMessage(String username, String message) {
        String query = "INSERT INTO messages (username, message) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, message);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getMessageHistory() {
        StringBuilder history = new StringBuilder();
        String query = "SELECT username, message, timestamp FROM messages ORDER BY timestamp";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                history.append("[").append(rs.getTimestamp("timestamp")).append("] ");
                history.append(rs.getString("username")).append(": ");
                history.append(rs.getString("message")).append("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return history.toString();
    }
}
