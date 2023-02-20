package entities;

import db.Database;
import entities.interfaces.EmailValidator;
import entities.interfaces.PasswordValidator;
import entities.interfaces.Colors;

import java.sql.Connection;
import java.util.Objects;
import java.util.Scanner;

public class Run implements Colors, EmailValidator, PasswordValidator {

    public  void main(Connection conn, Buyer buyer) {
        Scanner scan = new Scanner(System.in);
        boolean flag = false;
        while (!flag) {
            buyer.printCash();
            System.out.println(ANSI_BOLD + "\nMarketplace functions:" + ANSI_RESET);
            System.out.println("0) My Profile");
            System.out.println("1) To show all products.");
            System.out.println("2) To select a product.");
            System.out.println("3) To show orders");
            System.out.println("4) To show wishlist");
            System.out.println("5) To show cart");
            System.out.println(ANSI_RED + "6) Log out.\n" + ANSI_RESET);
            System.out.println("*************************");

            System.out.print("Enter option (1-6): ");
            String choice = scan.next();

            switch (choice) {
                case "0" -> {
                    while (!flag) {
                        System.out.println(buyer);
                        System.out.println("1) To Up the Balance");
                        System.out.println("2) Change the password");
                        System.out.println(ANSI_RED + "3) Delete the Account" + ANSI_RESET);
                        System.out.println(ANSI_RED + "4) Back.\n" + ANSI_RESET);
                        System.out.print("Enter option (1-4): ");
                        String f = scan.next();
                        switch (f) {
                            case "1":
                                System.out.print("Input amount: ");
                                float amount = scan.nextFloat();
                                buyer.topUpMoney(conn, amount);
                                break;
                            case "2":
                                String password, confirmPassword;
                                while (true) {
                                    System.out.println(passwordRequirements);
                                    System.out.print("Create a password: ");
                                    password = scan.next();
                                    if (isValidPassword(password)) {
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
                                buyer.updatePassword(conn, password);
                                break;
                            case "3":
                                String answer;
                                System.out.print("Do you want to delete? (yes/no): ");
                                answer = scan.next();
                                if (Objects.equals(answer, "yes") || Objects.equals(answer, "y")) {
                                    buyer.deleteUser(conn);
                                    flag = true;
                                    break;
                                }
                                break;
                            case "4":
                                break;
                            default:
                                System.out.println(ANSI_ORANGE + "Error: Invalid option, try again" + ANSI_RESET);
                                break;
                        }
                        if (f.equals("4")) {
                            break;
                        }
                        System.out.println("*************************");
                    }
                }
                case "1" -> new Product().printAllProducts(conn);
                case "2" -> {
                    String product_id;
                    Product product = new Product();
                    product.printAllProducts(conn);
                    System.out.print(ANSI_BOLD + "Write ID of product: " + ANSI_RESET);
                    product_id = scan.next();
                    if (!(product_id != null && product_id.matches("[0-9.]+"))) {
                        System.out.println(ANSI_RED + "Error: Invalid product ID." + ANSI_RESET);
                        break;
                    }
                    product = new Database().getProductById(conn, Integer.parseInt(product_id));
                    if (product.getId() == -1) {
                        break;
                    }
                    while (true) {
                        System.out.println(product);
                        System.out.println("1) Buy");
                        System.out.println("2) Add to Cart");
                        System.out.println("3) Add to Wishlist");
                        System.out.println(ANSI_RED + "4) Back.\n" + ANSI_RESET);
                        System.out.print("Enter option (1-4): ");
                        String f = scan.next();
                        switch (f) {
                            case "1":
                                buyer.Buy(conn, product);
                                break;
                            case "2":
                                buyer.addToCart(conn, product);
                                break;
                            case "3":
                                buyer.addToWishlist(conn, product);
                                break;
                            case "4":
                                break;
                            default:
                                System.out.println(ANSI_RED + "Error: Invalid option, try again" + ANSI_RESET);
                                break;
                        }
                        if (f.equals("4")) {
                            break;
                        }
                        System.out.println("*************************");
                    }
                }
                case "3" -> buyer.printOrders(conn);
                case "4" -> buyer.printWishlist(conn);
                case "5" -> buyer.printCart(conn);
                case "6" -> flag = true;
                default -> System.out.println(ANSI_RED + "Error: Invalid option, try again" + ANSI_RESET);
            }
            System.out.println("*************************");
        }
    }
}
