import java.sql.Connection;
import java.sql.Statement;
import java.util.Arrays;

public class User {
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";

    private int id = -1;
    private String first_name = null;
    private String last_name = null;
    private String email = null;
    private String password = null;
    private int[] orders = new int[0];
    private int[] wishlist = new int[0];
    private int[] cart = new int[0];
    private float wallet = -1;

    public User() {
        
    }
    public User(int id, String first_name, String last_name, String email, String password, int[] orders, int[] wishlist, int[] cart, float wallet) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.orders = orders;
        this.wishlist = wishlist;
        this.wallet = wallet;
        this.cart = cart;
    }

    public String getFirstName() {return first_name;}
    public String getLastName() {return last_name;}
    public String getEmail() {return email;}
    public String getPassword() {return password;}
    public int[] getOrders() {return orders;}
    public int[] getWishlist() {return wishlist;}
    public int[] getCart() {return cart;}
    public float getWallet() {return wallet;}
    public int getId() {return id;}

    public void topUpMoney(Connection conn, float amount) {
        wallet += Math.abs(amount);
        try {
            String query = String.format("update users set wallet='%f' where id='%d'", wallet, id);
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
            String query = String.format("update users set wallet='%f' where id='%d'", wallet, id);
            Statement statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println(ANSI_GREEN + "-" + amount + "$ from your wallet" + ANSI_RESET);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void updatePassword(Connection conn, String new_password) {
        password = new_password;
        try {
            String query = String.format("update users set password='%s' where id='%d'", password, id);
            Statement statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println(ANSI_GREEN + "Password updated successfully!" + ANSI_RESET);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void deleteAccount(Connection conn) {
        Statement statement;
        try{
            String query=String.format("delete from users where id='%s'", id);
            statement=conn.createStatement();
            statement.executeUpdate(query);
            System.out.println(ANSI_GREEN + "Account deleted successfully!" + ANSI_RESET);
        }catch (Exception e){
            System.out.println(e);
        }
    }

}
