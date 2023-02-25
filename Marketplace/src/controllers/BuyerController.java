package controllers;

import entities.Product;
import repositories.BuyerRepository;

public class BuyerController {
    public final BuyerRepository repo;

    public BuyerController(BuyerRepository buyerRepository) {
        this.repo = buyerRepository;
    }

    public void printCash() {
        repo.printCash();
    }

    public void topUpMoney(float amount) {
        repo.topUpMoney(amount);
    }

    public void Buy(Product product) {
        repo.Buy(product);
    }

    public void addToCart(Product product) {
        repo.addToCart(product);
    }

    public void addToWishlist(Product product) {
        repo.addToWishlist(product);
    }

    public void printOrders() {
        repo.printOrders();
    }

    public void printWishlist() {
        repo.printWishlist();
    }

    public void printCart() {
        repo.printCart();
    }
}
