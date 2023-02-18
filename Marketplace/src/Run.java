import logs.Logs;
import java.sql.Connection;
import java.util.Objects;
import java.util.Scanner;

public class Run extends Logs {
    static Connection conn = db.connection();

    public static void main(Buyer buyer) {
        Scanner scan = new Scanner(System.in);
        boolean flag = false;
        while(!flag) {
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
                                    PasswordValidator.print(); // prints criteria for password
                                    System.out.print("Create a password: ");
                                    password = scan.next();
                                    if (PasswordValidator.isValid(password)) {
                                        break;
                                    }
                                    invalidPassword();
                                }
                                while (true) {
                                    System.out.print("Confirm a password: ");
                                    confirmPassword = scan.next();
                                    if (password.equals(confirmPassword)) {
                                        break;
                                    }
                                    System.out.println(ANSI_RED + "Error: Passwords do not match." + ANSI_RESET);
                                }
                                buyer.updatePassword(password);
                                break;
                            case "3":
                                String answer;
                                System.out.print("Do you want to delete? (yes/no): ");
                                answer = scan.next();
                                if (Objects.equals(answer, "yes") || Objects.equals(answer, "y")) {
                                    buyer.deleteUser();
                                    flag = true;
                                    break;
                                }
                                break;
                            case "4":
                                break;
                            default:
                                invalidOption();
                                break;
                        }
                        if (f.equals("4")) {
                            break;
                        }
                        System.out.println("*************************");
                    }
                }
                case "1" -> Product.printAllProducts(conn);
                case "2" -> {
                    String product_id;
                    Product product;
                    Product.printAllProducts(conn);
                    System.out.print(ANSI_BOLD + "Write ID of product: " + ANSI_RESET);
                    product_id = scan.next();
                    if (!isNumeric(product_id)) {
                        System.out.println(ANSI_RED + "Error: Invalid product ID." + ANSI_RESET);
                        break;
                    }
                    product = db.getProductById(Integer.parseInt(product_id));
                    if (product.getId() == -1) {
                        break;
                    }
                    while (true) {
                        product.printProduct();
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
                                invalidOption();
                                break;
                        }
                        if (f.equals("4")) {
                            break;
                        }
                        System.out.println("*************************");
                    }
                }
                case "3" -> buyer.printOrders();
                case "4" -> buyer.printWishlist();
                case "5" -> buyer.printCart();
                case "6" -> flag = true;
                default -> invalidOption();
            }
            System.out.println("*************************");
        }
    }
    private static boolean isNumeric(String str){
        return str != null && str.matches("[0-9.]+");
    }
}
