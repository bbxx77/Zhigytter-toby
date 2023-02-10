//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.sql.Connection;
import java.sql.Statement;

public class Product {
    public static final String ANSI_RED = "\u001b[31m";
    public static final String ANSI_GREEN = "\u001b[32m";
    public static final String ANSI_RESET = "\u001b[0m";
    private int id;
    private String name;
    private float price;
    private int quantity;

    public Product() {
    }

    public Product(int id, String name, float price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public void addToCart(Connection conn, int user_id) {
        try {
            String query = String.format("update users set cart = cart || %s where id=%s", this.id, user_id);
            Statement statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("\u001b[32m" + this.name + " added to cart.\u001b[0m");
        } catch (Exception var5) {
            System.out.println(var5);
        }

    }

    public void addToWishlist(Connection conn, int user_id) {
        try {
            String query = String.format("update users set wishlist = wishlist || %s where id=%s", this.id, user_id);
            Statement statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("\u001b[32m" + this.name + " added to wishlist.\u001b[0m");
        } catch (Exception var5) {
            System.out.println(var5);
        }

    }

    public void Buy(Connection conn, User user) {
        try {
            if (user.getWallet() >= this.price) {
                if (this.quantity > 0) {
                    String query = String.format("update users set orders = orders || %s where id=%s", this.id, user.getId());
                    Statement statement = conn.createStatement();
                    statement.executeUpdate(query);
                    System.out.println("\u001b[32mYou have successfully purchased.\u001b[0m");
                    --this.quantity;
                    user.withdrawMoney(conn, this.price);
                    new User(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getOrders(), user.getWishlist(), user.getCart(), user.getWallet());
                } else {
                    System.out.println("\u001b[31mError: product is ended up\u001b[0m");
                }
            } else {
                System.out.println("\u001b[31mError: Insufficient funds\u001b[0m");
            }
        } catch (Exception var5) {
            System.out.println(var5);
        }

    }

    int getId() {
        return this.id;
    }

    String getName() {
        return this.name;
    }

    float getPrice() {
        return this.price;
    }

    int getQuantity() {
        return this.quantity;
    }
}
