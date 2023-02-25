import controllers.BuyerController;
import controllers.ProductController;
import controllers.UserController;
import data.PostgresDB;
import entities.Product;
import repositories.ProductRepository;
import repositories.auth.interfaces.EmailInterface;
import repositories.auth.interfaces.PasswordInterface;
import repositories.interfaces.IColors;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;

public  class MyApplication implements PasswordInterface, EmailInterface, IColors {
    private final BuyerController buyerController;
    private final ProductController productController;
    private final UserController userController;
    private final Scanner scanner;

    public MyApplication(UserController userController, BuyerController buyerController, ProductController productController) {
        this.buyerController = buyerController;
        this.productController = productController;
        this.userController = userController;
        scanner = new Scanner(System.in);
    }

    public void start() {
        boolean flag = false;
        while (!flag) {
            buyerController.printCash();
            System.out.println(ANSI_BOLD + "Welcome to Marketplace App" + ANSI_RESET);
            System.out.println("Select option:");
            System.out.println("0. My Profile");
            System.out.println("1. To show all products.");
            System.out.println("2. To select a product.");
            System.out.println("3. To show orders");
            System.out.println("4. To show wishlist");
            System.out.println("5. To show cart");
            System.out.println(ANSI_RED + "6. Log out.\n" + ANSI_RESET);
            System.out.println();
            try {
                System.out.print("Enter option (0-6): ");
                int option = scanner.nextInt();
                switch (option) {
                    case 0 -> {
                        while (!flag) {
                            System.out.println(ANSI_BOLD + "Username: " + ANSI_RESET + buyerController.repo.buyer.getUsername() + ANSI_BOLD + "\nemail: " + ANSI_RESET + buyerController.repo.buyer.getEmail() + "\n" + ANSI_BOLD + "Your cash: " + ANSI_RESET + buyerController.repo.buyer.getWallet() + "$");
                            System.out.println("1) To Up the Balance");
                            System.out.println("2) Change the password");
                            System.out.println(ANSI_RED + "3) Delete the Account" + ANSI_RESET);
                            System.out.println(ANSI_RED + "4) Back.\n" + ANSI_RESET);
                            System.out.print("Enter option (1-4): ");
                            String f = scanner.next();
                            switch (f) {
                                case "1":
                                    System.out.print("Input amount: ");
                                    float amount = scanner.nextFloat();
                                    buyerController.topUpMoney(amount);
                                    break;
                                case "2":
                                    String password, confirmPassword;
                                    while (true) {
                                        System.out.println(PASSWORD_REQUIREMENTS);
                                        System.out.print("Create a password: ");
                                        password = scanner.next();
                                        if (isValidPassword(password)) {
                                            break;
                                        }
                                        System.out.println(ANSI_RED + "Error: Invalid password, try again." + ANSI_RESET);
                                    }
                                    while (true) {
                                        System.out.print("Confirm a password: ");
                                        confirmPassword = scanner.next();
                                        if (password.equals(confirmPassword)) {
                                            break;
                                        }
                                        System.out.println(ANSI_RED + "Error: Passwords do not match." + ANSI_RESET);
                                    }
                                    userController.updatePassword(password);
                                    break;
                                case "3":
                                    String answer;
                                    System.out.print("Do you want to delete? (yes/no): ");
                                    answer = scanner.next();
                                    if (Objects.equals(answer, "yes") || Objects.equals(answer, "y")) {
                                        userController.deleteUser();
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
                    case 1 -> productController.printAllProducts();
                    case 2 -> {
                        String product_id;
                        productController.printAllProducts();
                        System.out.print(ANSI_BOLD + "Write ID of product: " + ANSI_RESET);
                        product_id = scanner.next();
                        if (!(product_id != null && product_id.matches("[0-9.]+"))) {
                            System.out.println(ANSI_RED + "Error: Invalid product ID." + ANSI_RESET);
                            break;
                        }
                        Product product = new PostgresDB().getProductById(Integer.parseInt(product_id));
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
                            String f = scanner.next();
                            switch (f) {
                                case "1":
                                    buyerController.Buy(product);
                                    break;
                                case "2":
                                    buyerController.addToCart(product);
                                    break;
                                case "3":
                                    buyerController.addToWishlist(product);
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
                    case 3 -> buyerController.printOrders();
                    case 4 -> buyerController.printWishlist();
                    case 5 -> buyerController.printCart();
                    case 6 -> flag = true;
                    default -> System.out.println(ANSI_RED + "Error: Invalid option, try again" + ANSI_RESET);
                }
            } catch (InputMismatchException e) {
                System.out.println("Input must be integer " + e);
                scanner.nextLine(); // to ignore incorrect input
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            if (flag) {
                break;
            }

            System.out.println("*************************");

        }
    }

    @Override
    public boolean isValidPassword(String password) {
        Matcher matcher = VALID_PASSWORD_REGEX.matcher(password);
        return matcher.matches();
    }

    @Override
    public String getHashPassword(String password) {
        return null;
    }

    @Override
    public boolean isValidEmail(String email) {
        return false;
    }
}
