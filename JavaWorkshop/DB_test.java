import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB_test {
    private static final String URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static void main(String[] args) {
        try {
            // Load MySQL JDBC driver explicitly
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Create connection
            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
                System.out.println("Database connection successful!");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Error: MySQL JDBC Driver not found!");
            System.err.println("Please add mysql-connector-j.jar to the project's Referenced Libraries");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error: Database connection failed!");
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
