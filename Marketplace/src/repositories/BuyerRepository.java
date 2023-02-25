package repositories;

import data.interfaces.IDB;
import entities.Buyer;
import entities.Product;
import repositories.interfaces.IBuyerRepository;
import repositories.interfaces.IColors;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BuyerRepository implements IBuyerRepository, IColors {
    private final IDB db;
    public final Buyer buyer;

    public BuyerRepository(IDB db, Buyer buyer) {
        this.db = db;
        this.buyer = buyer;
    }

    @Override
    public void printCash() {
        System.out.println(ANSI_BOLD + "Your cash: " + buyer.getWallet() + "$" + ANSI_RESET);
    }

    @Override
    public void topUpMoney(float amount) {
        Connection conn = null;
        buyer.setWallet(Math.abs(amount));
        try {
            conn = db.getConnection();
            String query = String.format("update users set wallet='%f' where id='%d'", buyer.getWallet(), buyer.getId());
            Statement statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println(IColors.ANSI_GREEN + "+" + amount + "$ to your wallet" + IColors.ANSI_RESET);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void withdrawMoney(float amount) {
        Connection conn = null;
        buyer.setWallet(-Math.abs(amount));
        try {
            conn = db.getConnection();
            String query = String.format("update users set wallet='%f' where id='%d'", buyer.getWallet(), buyer.getId());
            Statement statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println(IColors.ANSI_GREEN + "-" + amount + "$ from your wallet" + IColors.ANSI_RESET);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void printOrders() {
        System.out.println(IColors.ANSI_RED + "Your orders: " + IColors.ANSI_RESET);
        for (int productId : buyer.getOrders()) {
            System.out.print(db.getProductById(productId));
        }
    }

    @Override
    public void printWishlist() {
        System.out.println(IColors.ANSI_BOLD + "Your wishlist: " + IColors.ANSI_RESET);
        for (int productId : buyer.getWishlist()) {
            System.out.print(db.getProductById(productId));
        }
    }

    @Override
    public void printCart() {
        System.out.println(IColors.ANSI_BOLD + "Your cart: " + IColors.ANSI_RESET);
        for (int productId : buyer.getCart()) {
            System.out.print(db.getProductById(productId));
        }
    }


    @Override
    public void addToCart(Product product) {
        Connection conn = null;
        try {
            conn = db.getConnection();
            String query = String.format("update users set cart = cart || %s where id=%s", product.getId(), buyer.getId());
            Statement statement = conn.createStatement();
            statement.executeUpdate(query);

            ArrayList<Integer> cart = buyer.getCart();
            cart.add(product.getId());
            buyer.setCart(cart);

            System.out.println(IColors.ANSI_GREEN + product.getName() + " added to cart." + IColors.ANSI_RESET);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void addToWishlist(Product product) {
        Connection conn = null;
        try {
            conn = db.getConnection();
            String query = String.format("update users set wishlist = wishlist || %s where id=%s", product.getId(), buyer.getId());
            Statement statement = conn.createStatement();
            statement.executeUpdate(query);

            ArrayList<Integer> wishlist = buyer.getWishlist();
            wishlist.add(product.getId());
            buyer.setWishlist(wishlist);

            System.out.println(IColors.ANSI_GREEN + product.getName() + " added to wishlist." + IColors.ANSI_RESET);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void Buy(Product product) {
        Connection conn = null;
        try {
            conn = db.getConnection();
            if (buyer.getWallet() >= product.getPrice()) {
                if (product.getQuantity() > 0) {
                    //update entities.User orders
                    String query = String.format("update users set orders = orders || %s where id=%s", product.getId(), buyer.getId());
                    Statement statement = conn.createStatement();
                    statement.executeUpdate(query);
                    System.out.println(IColors.ANSI_GREEN + "You have successfully purchased." + IColors.ANSI_RESET);
                    product.setQuantity(product.getQuantity() - 1);
                    withdrawMoney(product.getPrice());

                    ArrayList<Integer> orders = buyer.getOrders();
                    orders.add(product.getId());
                    buyer.setOrders(orders);

                    //update entities.Product quantity
                    query = String.format("update products set quantity = %d where id=%s", product.getQuantity(), product.getId());
                    statement = conn.createStatement();
                    statement.executeUpdate(query);

                } else {
                    System.out.println(IColors.ANSI_RED + "Error: product is ended up" + IColors.ANSI_RESET);
                }
            } else {
                System.out.println(IColors.ANSI_RED + "Error: Insufficient funds" + IColors.ANSI_RESET);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public String toString() {
        return "Username: " + buyer.getUsername() + "\nemail: " + buyer.getEmail() + "\n" + "Your cash: " + buyer.getWallet() + "$";
    }
}
