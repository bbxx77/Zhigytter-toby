package controllers;

import repositories.ProductRepository;
import repositories.interfaces.IProductRepository;

public class ProductController {
    private final IProductRepository repo;

    public ProductController(IProductRepository repo) {
        this.repo = repo;
    }

    public void printAllProducts() {
        repo.printAllProducts();
    }
}
