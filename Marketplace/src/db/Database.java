package db;

import entities.Buyer;
import entities.Product;
import entities.interfaces.Colors;
import entities.interfaces.Hasher;

import java.sql.*;

public class Database implements Colors, Hasher {
    public Connection connect(String dbname, String user, String pass) {
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
            System.out.println(e);
        }
        return conn;
    }
    public Product getProductById(Connection conn, int id) {
        Product product = new Product();
        try {
            String query = String.format("select * from products where id='%d'", id);
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            product.setId(id);
            product.setName(rs.getString("name"));
            product.setPrice(rs.getFloat("price"));
            product.setQuantity(rs.getInt("quantity"));
            return product;
        } catch (Exception e) {
            System.out.println(ANSI_RED + "Error: Invalid product ID." + ANSI_RESET);
        }
        return product;
    }
    public boolean checkEmail(Connection conn, String email) {
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
    public void signUpUser(Connection conn, String username, String email, String password) throws SQLException {
        try {
            String hashedPassword = getHashPassword(password);

            String query = String.format("insert into users (username, email, password, orders, wishlist, cart, wallet) values('%s', '%s', '%s', ARRAY[]::integer[], ARRAY[]::integer[], ARRAY[]::integer[], 0);", username, email, hashedPassword);
            Statement statement = conn.createStatement();

            statement.executeUpdate(query);
            System.out.println(ANSI_GREEN + "Registration successful!" + ANSI_RESET);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public int checkLogin(Connection conn, String email, String password) throws  SQLException {
        try {
            String hashedPassword = getHashPassword(password);

            String query = String.format("select * from users where email='%s' and password='%s'", email, hashedPassword);
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                return rs.getInt("id");
            }
            return -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }
    public Buyer getBuyerById(Connection conn, int id) {
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
    public void insertProduct(Connection conn, String name, double price, int quantity) {
        try {
            String query = String.format("insert into products(name, price, quantity) values('%s', '%s', '%s');", name, price, quantity);
            Statement statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Row inserted");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
