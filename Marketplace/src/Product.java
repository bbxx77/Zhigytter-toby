import java.sql.Connection;
import java.sql.Statement;

public class Product {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";

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
        Statement statement;
        try {
            String query = String.format("update users set cart = cart || %s where id=%s", id, user_id);
            statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println(ANSI_GREEN + name + " added to cart." + ANSI_RESET);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void addToWishlist(Connection conn, int user_id) {
        Statement statement;
        try {
            String query = String.format("update users set wishlist = wishlist || %s where id=%s", id, user_id);
            statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println(ANSI_GREEN + name + " added to wishlist." + ANSI_RESET);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void Buy(Connection conn, User user) {
        Statement statement;
        try {
            if (user.getWallet() >= price) {
                if (quantity > 0) {
                    String query = String.format("update users set orders = orders || %s where id=%s", id, user.getId());
                    statement = conn.createStatement();
                    statement.executeUpdate(query);
                    System.out.println(ANSI_GREEN + "You have successfully purchased." + ANSI_RESET);
                    this.quantity -= 1;
                    user.withdrawMoney(conn, price);
                    user = new User(
                        user.getId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.getPassword(),
                        user.getOrders(),
                        user.getWishlist(),
                        user.getCart(),
                        user.getWallet()
                    );
                } else {
                    System.out.println(ANSI_RED + "Error: product is ended up" + ANSI_RESET);
                }
            } else {
                System.out.println(ANSI_RED + "Error: Insufficient funds" + ANSI_RESET);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    int getId() {return id;}
    String getName() {return name;}
    float getPrice() {return price;}
    int getQuantity() {return quantity;}
}
