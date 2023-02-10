import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Run {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BOLD = "\033[1m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_ORANGE = "\u001B[38;5;208m";
    public static final String ANSI_GREEN = "\u001B[32m";

    public static void main(Connection conn, User user) {
        Scanner scan = new Scanner(System.in);
        boolean flag = false;
        while(!flag) {
            printCash(user);
            System.out.println(ANSI_BOLD + "\nMarketplace functions:" + ANSI_RESET);
            System.out.println("0) My Profile");
            System.out.println("1) To show all products.");
            System.out.println("2) To select a product.");
            System.out.println("3) To show orders");
            System.out.println("4) To show wishlist");
            System.out.println("5) To show cart");
            System.out.println(ANSI_RED + "6) Log out.\n" + ANSI_RESET);
            System.out.println("*************************");

            try {
                System.out.print("Enter option (1-6): ");
                int choice = scan.nextInt();
                switch (choice){
                    case 0:
                        while(!flag) {
                            printUser(user);
                            System.out.println("1) To Up the Balance");
                            System.out.println("2) Change the password");
                            System.out.println(ANSI_RED + "3) Delete the Account" + ANSI_RESET);
                            System.out.println(ANSI_RED + "4) Back.\n" + ANSI_RESET);
                            System.out.print("Enter option (1-4): ");
                            int f = scan.nextInt();
                            switch (f) {
                                case 1:
                                    System.out.print("Input amount: ");
                                    float amount = scan.nextFloat();
                                    user.topUpMoney(conn, amount);
                                    break;
                                case 2:
                                    String password, confirmPassword;
                                    while (true) {
                                        PasswordValidator.print(); // prints criteria for password
                                        System.out.print("Create a password: ");
                                        password = scan.next();
                                        if (PasswordValidator.validate(password)) {
                                            break;
                                        }
                                        System.out.println(ANSI_RED + "Error: Invalid password, try again." + ANSI_RESET);
                                    }
                                    while (true) {
                                        System.out.print("Confirm a password: ");
                                        confirmPassword = scan.next();
                                        if (password.equals(confirmPassword)) {
                                            break;
                                        }
                                        System.out.println(ANSI_RED + "Error: Passwords do not match." + ANSI_RESET);
                                    }
                                    user.updatePassword(conn, password);
                                    break;
                                case 3:
                                    String answer;
                                    System.out.print("Do you want to delete? (yes/no): ");
                                    answer = scan.next();
                                    if (Objects.equals(answer, "yes") || Objects.equals(answer, "y")) {
                                        user.deleteAccount(conn);
                                        flag = true;
                                        break;
                                    }
                                    break;
                                case 4:
                                    break;
                                default:
                                    System.out.println(ANSI_ORANGE + "Error: Invalid option, try again" + ANSI_RESET);
                                    break;
                            }
                            if (f == 4) {
                                break;
                            }
                            System.out.println("*************************");
                        }
                        break;
                    case 1:
                        printAllProducts(conn);
                        break;
                    case 2:
                        int product_id;
                        Product product;
                        printAllProducts(conn);
                        System.out.print(ANSI_BOLD + "Write ID of product: " + ANSI_RESET);
                        product_id = scan.nextInt();
                        product = getProductById(conn ,product_id);
                        if (product.getId() == -1) {
                            System.out.println(ANSI_RED + "Error: Invalid product ID." + ANSI_RESET);
                            break;
                        }
                        while(true) {
                            printProduct(product);
                            System.out.println("1) Buy");
                            System.out.println("2) Add to Cart");
                            System.out.println("3) Add to Wishlist");
                            System.out.println(ANSI_RED + "4) Back.\n" + ANSI_RESET);
                            System.out.print("Enter option (1-4): ");
                            int f = scan.nextInt();
                            switch (f) {
                                case 1:
                                    product.Buy(conn, user);
                                    user = Auth.getUserById(conn, user.getId());
                                    break;
                                case 2:
                                    product.addToCart(conn, user.getId());
                                    user = Auth.getUserById(conn, user.getId());
                                    break;
                                case 3:
                                    product.addToWishlist(conn, user.getId());
                                    user = Auth.getUserById(conn, user.getId());
                                    break;
                                case 4:
                                    break;
                                default:
                                    System.out.println(ANSI_RED + "Error: Invalid option, try again" + ANSI_RESET);
                                    break;
                            }
                            if (f == 4) {
                                break;
                            }
                            System.out.println("*************************");
                        }
                        break;
                    case 3:
                        System.out.println(ANSI_BOLD + "Your orders: " + ANSI_RESET);
                        for (int productId : user.getOrders()) {
                            printProduct(getProductById(conn, productId));
                        }
                        break;
                    case 4:
                        System.out.println(ANSI_BOLD + "Your wishlist: " + ANSI_RESET);
                        for (int productId : user.getWishlist()) {
                            printProduct(getProductById(conn, productId));
                        }
                        break;
                    case 5:
                        System.out.println(ANSI_BOLD + "Your cart: " + ANSI_RESET);
                        for (int productId : user.getCart()) {
                            printProduct(getProductById(conn, productId));
                        }
                        break;
                    case 6:
                        flag = true;
                        break;
                    default:
                        System.out.println(ANSI_RED + "Error: Invalid option, try again" + ANSI_RESET);
                        break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            System.out.println("*************************");
        }
    }
    public static void printAllProducts(Connection conn) {
        Statement statement;
        ResultSet rs;
        System.out.println("+----+--------+-------+--------+");
        System.out.println("| \033[1mID\u001B[0m | \033[1mName\u001B[0m   | \033[1mPrice\u001B[0m  | \033[1mQuantity\u001B[0m |");
        System.out.println("+----+--------+-------+--------+");
        try {
            String query = "select * from products";
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                float price = rs.getFloat("price");
                int quantity = rs.getInt("quantity");
                Product product = new Product(id, name, price, quantity);
                printProduct(product);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("+----+--------+-------+--------+");
    }
    public static void printProduct(Product product) {
        System.out.printf("| %2d  |\u001B[32m %-6s \u001B[0m|\u001B[34m %5.2f$ \u001B[0m|\u001B[38;5;208m %7d \u001B[0m|\n", product.getId(),
            product.getName(),
            product.getPrice(),
            product.getQuantity());
    }
    public static Product getProductById(Connection conn, int id) {
        Statement statement;
        ResultSet rs;
        try {
            String query = String.format("select * from products where id='%d'", id);
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            rs.next();
            return new Product(
                id,
                rs.getString("name"),
                rs.getFloat("price"),
                rs.getInt("quantity")
            );
        } catch (Exception e) {
            System.out.println(e);
        }
        return new Product();
    }
    public static void printCash(User user) {
        System.out.println(ANSI_BOLD + "Your cash: " + user.getWallet() + "$" + ANSI_RESET);
    }
    public static void printUser(User user) {
        System.out.println("Name: " + user.getFirstName());
        System.out.println("Surname: " + user.getLastName());
        System.out.println("email: " + user.getEmail());
        printCash(user);
    }
}
