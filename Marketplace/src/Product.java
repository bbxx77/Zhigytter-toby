public class Product {
    private int id;
    private String name;
    private float price;
    private int quantity;

    public Product(int id, String name, float price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    int getId() {return id;}
    String getName() {return name;}
    float getPrice() {return price;}
    int getQuantity() {return quantity;}
}
