package data;

import data.interfaces.IDB;
import entities.Buyer;
import entities.Product;
import repositories.interfaces.IColors;
import java.sql.*;

public class PostgresDB implements IDB, IColors {
    private final String DB_USERNAME = "postgres";
    private final String DB_PASSWORD = "1234567";
    private final String DB_NAME = "marketplacejava";

    @Override
    public Connection getConnection() throws SQLException, ClassNotFoundException {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + DB_NAME, DB_USERNAME, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Product getProductById(int id){
        Connection conn = null;
        try {
            conn = getConnection();
            String query = String.format("select * from products where id='%d'", id);
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            return new Product(id, rs.getString("name"), rs.getFloat("price"), rs.getInt("quantity"));
        } catch (SQLException e) {
            System.out.println(ANSI_RED + "Error: Invalid product ID." + ANSI_RESET);
            return  null;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Buyer getBuyerById(int id) {
        Connection conn = null;
        try {
            conn = getConnection();
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
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return new Buyer();
    }
}