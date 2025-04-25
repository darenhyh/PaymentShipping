import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestConnection {
    public static void main(String[] args) {
        try {
            // Load Derby JDBC driver
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            
            // Attempt connection
            System.out.println("Trying to connect...");
            try (Connection conn = DriverManager.getConnection(
                    "jdbc:derby://localhost:1527/glowyDays", "nbuser", "nbuser")) {
                System.out.println("Success! Connected to glowyDays database.");
                
                // Test a simple query
                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery("SELECT 1 FROM SYSIBM.SYSDUMMY1")) {
                    if (rs.next()) {
                        System.out.println("Database test query successful!");
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Connection failed!");
            e.printStackTrace();
        }
    }
}