import java.sql.Connection;
import java.sql.DriverManager;

public class DbFunctions {
    public Connection connect_to_db(String dbname, String user, String pass) {
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + dbname, user, pass);
            if (conn != null) {
                System.out.println("\u001B[32m" + "Connection Established!" + "\u001B[0m");
            } else {
                System.out.println("\u001B[31m" + "Connection Failed!" + "\u001B[0m");
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        return conn;
    }
}