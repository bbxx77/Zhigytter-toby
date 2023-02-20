import logs.DbLogs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class db extends DbLogs {
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "1234567";
    private static final String DB_NAME = "marketplacejava";

    static Connection conn = null;

    public static Connection connection() {
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + DB_NAME, DB_USERNAME, DB_PASSWORD);
            if (conn == null) {
                connectionFailed();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return conn;
    }
    public static boolean checkEmail(String email) {
        try {
            String query = String.format("select * from %s where email='%s'", "users", email);
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        return false;
    }
    public static boolean checkUser(String email, String password) {
        try {
            String query = String.format("select * from %s where email='%s' and password='%s'", "users", email, password);
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        return false;
    }
    
    public static int getId(String email, String password) {
        try {
            String query = String.format("select * from %s where email='%s' and password='%s'", "users", email, password);
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        return -1;
    }
    public static Buyer getBuyerById(int id) {
        try {
            String query = String.format("select * from users where id='%d'", id);
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            if(rs.next()) {
                return new Buyer(
                    id,
                    rs.getString("username"),
                    rs.getString("email"),
                    (Integer[]) rs.getArray("orders").getArray(),
                    (Integer[]) rs.getArray("cart").getArray(),
                    (Integer[]) rs.getArray("wishlist").getArray(),
                    rs.getFloat("wallet")
                );
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return new Buyer();
    }
    public static Product getProductById(int id) {
        try {
            String query = String.format("select * from products where id='%d'", id);
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            return new Product(
                id,
                rs.getString("name"),
                rs.getFloat("price"),
                rs.getInt("quantity")
            );
        } catch (Exception e) {
            invalidProductId();
        }
        return new Product();
    }
    public static void signUpUser(String username, String email, String password){
        try {
            String query = String.format("insert into users (username, email, password, orders, wishlist, cart, wallet) values('%s', '%s', '%s', ARRAY[]::integer[], ARRAY[]::integer[], ARRAY[]::integer[], 0);", username, email, password);
            Statement statement = conn.createStatement();
            statement.executeUpdate(query);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}