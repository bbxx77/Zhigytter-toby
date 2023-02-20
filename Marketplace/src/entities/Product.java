package entities;

import entities.interfaces.Colors;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Product implements Colors {
    private int id = -1;
    private String name;
    private float price;
    private int quantity;

    public Product() {}

    public Product(int id, String name, float price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
    public void printAllProducts(Connection conn) {
        System.out.println("+----+--------+-------+--------+");
        System.out.println("| \033[1mID\u001B[0m | \033[1mName\u001B[0m   | \033[1mPrice\u001B[0m  | \033[1mQuantity\u001B[0m |");
        System.out.println("+----+--------+-------+--------+");
        try {
            String query = "select * from products";
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()) {
                System.out.print(new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getFloat("price"),
                    rs.getInt("quantity")
                ));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("+----+--------+-------+--------+");
    }

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
    public void setQuantity(int quantity) {quantity = quantity;}
}