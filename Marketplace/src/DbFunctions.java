//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.sql.Connection;
import java.sql.DriverManager;

public class DbFunctions {
    public DbFunctions() {
    }

    public Connection connect_to_db(String dbname, String user, String pass) {
        Connection conn = null;

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + dbname, user, pass);
            if (conn != null) {
                System.out.println("\u001b[32mConnection Established!\u001b[0m");
            } else {
                System.out.println("\u001b[31mConnection Failed!\u001b[0m");
            }
        } catch (Exception var6) {
            var6.getStackTrace();
        }

        return conn;
    }
}
