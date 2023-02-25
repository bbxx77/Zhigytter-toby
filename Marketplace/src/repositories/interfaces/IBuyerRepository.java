package repositories.interfaces;

import entities.Buyer;
import entities.Product;

public interface IBuyerRepository {
    void printCash();
    void topUpMoney(float amount);
    void withdrawMoney(float amount);
    void printOrders();
    void printWishlist();
    void printCart();
    void addToCart(Product product);
    void addToWishlist(Product product);
    void Buy(Product product);
}
