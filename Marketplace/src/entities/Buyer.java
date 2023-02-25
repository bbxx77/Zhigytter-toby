package entities;

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

    public void setOrders(ArrayList<Integer> orders) {
        this.orders = orders;
    }

    public void setWishlist(ArrayList<Integer> wishlist) {
        this.wishlist = wishlist;
    }

    public void setCart(ArrayList<Integer> cart) {
        this.cart = cart;
    }

    public void setWallet(float amount) {
        this.wallet += amount;
    }

    public ArrayList<Integer> getOrders() {
        return orders;
    }

    public ArrayList<Integer> getWishlist() {
        return wishlist;
    }

    public ArrayList<Integer> getCart() {
        return cart;
    }

    public float getWallet() {
        return wallet;
    }

    @Override
    public String toString() {
        return "Username: " + getUsername() + "\nemail: " + getEmail() + "\n" + "Your cash: " + wallet + "$";
    }
}