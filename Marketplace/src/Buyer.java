import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

public class Buyer extends User {
    private ArrayList<Integer> orders = new ArrayList<>();
    private ArrayList<Integer> wishlist = new ArrayList<>();
    private ArrayList<Integer> cart = new ArrayList<>();
    private float wallet;

    public Buyer() {
        super();
    }
    public Buyer(int id, String username, String email) {
        super(id, username, email);
        wallet = 0;
    }
    public Buyer(int id, String username, String email, Integer[] orders, Integer[] cart, Integer[] wishlist, float wallet) {
        super(id, username, email);
        this.wallet = wallet;
        this.orders = new ArrayList<>(Arrays.asList(orders));
        this.cart = new ArrayList<>(Arrays.asList(cart));
        this.wishlist = new ArrayList<>(Arrays.asList(wishlist));
    }
    public void addToCart(Connection conn, Product product) {
        try {
            String query = String.format("update users set cart = cart || %s where id=%s", product.getId(), getId());
            Statement statement = conn.createStatement();
            statement.executeUpdate(query);
            cart.add(product.getId());
            System.out.println(ANSI_GREEN + product.getName() + " added to cart." + ANSI_RESET);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void addToWishlist(Connection conn, Product product) {
        try {
            String query = String.format("update users set wishlist = wishlist || %s where id=%s", product.getId(), getId());
            Statement statement = conn.createStatement();
            statement.executeUpdate(query);
            wishlist.add(product.getId());
            System.out.println(ANSI_GREEN + product.getName() + " added to wishlist." + ANSI_RESET);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void Buy(Connection conn, Product product) {
        try {
            if (wallet >= product.getPrice()) {
                if (product.getQuantity() > 0) {
                    //update User orders
                    String query = String.format("update users set orders = orders || %s where id=%s", product.getId(), getId());
                    Statement statement = conn.createStatement();
                    statement.executeUpdate(query);
                    System.out.println(ANSI_GREEN + "You have successfully purchased." + ANSI_RESET);
                    product.setQuantity(product.getQuantity() - 1);
                    withdrawMoney(conn, product.getPrice());
                    orders.add(product.getId());

                    //update Product quantity
                    query = String.format("update products set quantity = %d where id=%s", product.getQuantity(), product.getId());
                    statement = conn.createStatement();
                    statement.executeUpdate(query);

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
    public void printOrders(Connection conn) {
        System.out.println(ANSI_RED + "Your orders: " + ANSI_RESET);
        for (int productId : orders) {
            System.out.print(new DbFunctions().getProductById(conn, productId));
        }
    }
    public void printWishlist(Connection conn) {
        System.out.println(ANSI_BOLD + "Your wishlist: " + ANSI_RESET);
        for (int productId : wishlist) {
            System.out.print(new DbFunctions().getProductById(conn, productId));
        }
    }
    public void printCart(Connection conn) {
        System.out.println(ANSI_BOLD + "Your cart: " + ANSI_RESET);
        for (int productId : cart) {
            System.out.print(new DbFunctions().getProductById(conn, productId));
        }
    }
    public void topUpMoney(Connection conn, float amount) {
        wallet += Math.abs(amount);
        try {
            String query = String.format("update users set wallet='%f' where id='%d'", wallet, super.getId());
            Statement statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println(ANSI_GREEN + "+" + amount + "$ to your wallet" + ANSI_RESET);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void withdrawMoney(Connection conn, float amount) {
        wallet -= Math.abs(amount);
        try {
            String query = String.format("update users set wallet='%f' where id='%d'", wallet, super.getId());
            Statement statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println(ANSI_GREEN + "-" + amount + "$ from your wallet" + ANSI_RESET);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void printCash() {
        System.out.println(ANSI_BOLD + "Your cash: " + wallet + "$" + ANSI_RESET);
    }
    public String toString() {
        return "Username: " + getUsername() + "\n" + "email: " + getEmail() + "\n" + ANSI_BOLD + "Your cash: " + wallet + "$" + ANSI_RESET;
    }

    public ArrayList<Integer> getOrders() {return orders;}
    public ArrayList<Integer> getWishlist() {return wishlist;}
    public ArrayList<Integer> getCart() {return cart;}
    public float getWallet() {return wallet;}

}