package entities;

import repositories.interfaces.IColors;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Product implements IColors {
    private int id = -1;
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

    @Override
    public String toString() {
        return String.format("| %2d  |\u001B[32m %-6s \u001B[0m|\u001B[34m %5.2f$ \u001B[0m|\u001B[38;5;208m %7d \u001B[0m|\n", id,
            name,
            price,
            quantity);
    }

    public int getId() {return id;}
    public String getName() {return name;}
    public float getPrice() {return price;}
    public int getQuantity() {return quantity;}
    public void setId(int id) {this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setPrice(float price) {this.price = price;}
    public void setQuantity(int quantity) {this.quantity = quantity;}
}