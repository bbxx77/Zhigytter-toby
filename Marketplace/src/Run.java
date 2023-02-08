import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Run {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BOLD = "\033[1m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_ORANGE = "\u001B[38;5;208m";

    public static void main(String DB_NAME, String DB_USERNAME, String DB_PASSWORD) throws SQLException {
        DbFunctions db = new DbFunctions();
        Connection conn = db.connect_to_db(DB_NAME, DB_USERNAME, DB_PASSWORD);
        Scanner scan = new Scanner(System.in);

        while(true) {
            System.out.println(ANSI_BOLD + "\nMarketplace functions:" + ANSI_RESET);
            System.out.println("1) To show all products.");
            System.out.println("2) To buy a product.");
            System.out.println("3) To show orders of User.");
            System.out.println("4) To show wishlist of User.");
            System.out.println(ANSI_RED + "5) Exit.\n" + ANSI_RESET);
            System.out.println("*************************");

            ArrayList<Product> Products = new ArrayList<>();

            try {
                System.out.print("Enter option (1-5): ");
                int choice = scan.nextInt();
                switch (choice){
                    case 1:
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
                        break;
                    case 2:
                        String userQuery = scan.next();

                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        System.out.println("Bye!");
                        System.exit(0);
                    default:
                        System.out.println(ANSI_ORANGE + "Wrong option! Try again." + ANSI_RESET);
                        break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            System.out.println("*************************");
        }
    }
    public static void printProduct(Product product) {
        System.out.printf("| %2d  |\u001B[32m %-6s \u001B[0m|\u001B[34m %5.2f$ \u001B[0m|\u001B[38;5;208m %7d \u001B[0m|\n", product.getId(),
                        product.getName(),
                        product.getPrice(),
                        product.getQuantity());
    }
}
