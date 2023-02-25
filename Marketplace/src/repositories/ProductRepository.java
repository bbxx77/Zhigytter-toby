package repositories;

import data.interfaces.IDB;
import entities.Product;
import repositories.interfaces.IProductRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProductRepository implements IProductRepository {
    private final IDB db;
    private final Product product;

    public ProductRepository(IDB db, Product product) {
        this.db = db;
        this.product = product;
    }

    @Override
    public void printAllProducts() {
        Connection conn = null;
        try {
            conn = db.getConnection();
            String query = "select * from products";
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);

            System.out.println("+----+--------+-------+--------+");
            System.out.println("| \033[1mID\u001B[0m | \033[1mName\u001B[0m   | \033[1mPrice\u001B[0m  | \033[1mQuantity\u001B[0m |");
            System.out.println("+----+--------+-------+--------+");
            while(rs.next()) {
                System.out.print(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getFloat("price"),
                        rs.getInt("quantity")
                ));
            }
            System.out.println("+----+--------+-------+--------+");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
