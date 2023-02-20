import java.sql.*;
import java.util.Objects;
import java.util.Scanner;

public class Auth {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_ORANGE = "\u001B[38;5;208m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static User main(Connection conn) {
        Scanner scan = new Scanner(System.in);
        try {
            while(true) {
                System.out.println("1. Login");
                System.out.println("2. Sign up");
                System.out.println(ANSI_RED + "3. Exit.\n" + ANSI_RESET);
                System.out.println("*************************");
                System.out.print("Choose an option: ");

                String choice = scan.next();
                if (Objects.equals(choice, "1")) {
                    String email, password;
                    while(true) {
                        System.out.print("Enter email: ");
                        email = scan.next();
                        if (!EmailValidator.isValid(email)) {
                            System.out.println(ANSI_RED + "Error: Invalid email, try again." + ANSI_RESET);
                        } else if (checkEmail(conn, email)) {
                            break;
                        } else {
                            System.out.println(ANSI_RED + "Error: This account does not exist." + ANSI_RESET);
                            System.out.println(ANSI_RED + "Error: Enter another account or register a new one." + ANSI_RESET);
                        }
                    }
                    for (int i = 1; true; i++) {
                        System.out.print("Enter password: ");
                        password = scan.next();
                        if (checkUser(conn, email, password)) {
                            System.out.println(ANSI_GREEN + "Login successful!" + ANSI_RESET);
                            int id = getId(conn, email, password);
                            return getUserById(conn, id);
                        } else if (i == 3) {
                            System.out.println(ANSI_RED + "3 incorrect password attempts\n" + ANSI_RESET);
                            return new User();
                        }
                        System.out.println(ANSI_RED + "Error: Sorry, try again." + ANSI_RESET);
                    }
                } else if (Objects.equals(choice, "2")) {
                    System.out.print("Enter first name: ");
                    String firstName = scan.next();
                    System.out.print("Enter last name: ");
                    String lastName = scan.next();
                    String email, password, confirmPassword;
                    while(true) {
                        System.out.print("Enter email: ");
                        email = scan.next();
                        if (checkEmail(conn, email)) {
                            System.out.println(ANSI_ORANGE + "Error: Current email exist, try again." + ANSI_RESET);
                        } else if (!EmailValidator.isValid(email)) {
                            System.out.println(ANSI_RED + "Error: Invalid email, try again." + ANSI_RESET);
                        } else {
                            break;
                        }
                    }
                    while (true) {
                        PasswordValidator.print();
                        System.out.print("Create a password: ");
                        password = scan.next();
                        if (PasswordValidator.isValid(password)) {
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
                    signUpUser(conn, firstName, lastName, email, password);
                    int id = getId(conn, email, password);
                    return getUserById(conn, id);
                } else if (Objects.equals(choice, "3")) {
                    System.out.println(ANSI_BLUE + "Bye!" + ANSI_RESET);
                    System.exit(0);
                } else {
                    System.out.println(ANSI_RED + "Error: Invalid option, try again" + ANSI_RESET);
                    System.out.println("*************************\n");
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        return new User();
    }

    private static boolean checkEmail(Connection conn, String email) {
        ResultSet rs;
        Statement statement;
        try {
            String query = String.format("select * from %s where email='%s'", "users", email);
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        return false;
    }
    private static boolean checkUser(Connection conn, String email, String password) {
        ResultSet rs;
        Statement statement;
        try {
            String query = String.format("select * from %s where email='%s' and password='%s'", "users", email, password);
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        return false;
    }
    private static void signUpUser(Connection conn, String first_name, String last_name, String email, String password){
        Statement statement;
        try {
            String query = String.format("insert into users (first_name, last_name, email, password, orders, wishlist, cart, wallet) values('%s', '%s', '%s', '%s', ARRAY[]::integer[], ARRAY[]::integer[], ARRAY[]::integer[], 0);", first_name, last_name, email, password);
            statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println(ANSI_GREEN + "Registration successful!" + ANSI_RESET);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    private static int getId(Connection conn, String email, String password) {
        ResultSet rs;
        Statement statement;
        try {
            String query = String.format("select * from %s where email='%s' and password='%s'", "users", email, password);
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        return -1;
    }
    public static User getUserById(Connection conn, int id) {
        Statement statement;
        ResultSet rs;
        try {
            String query = String.format("select * from users where id='%d'", id);
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            if(rs.next()) {
                return new User(
                        id,
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        IntegerArrayToInt(rs.getArray("orders")),
                        IntegerArrayToInt(rs.getArray("wishlist")),
                        IntegerArrayToInt(rs.getArray("cart")),
                        rs.getFloat("wallet")
                );
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return new User();
    }
    private static int[] IntegerArrayToInt(Array array) throws SQLException {
        Integer[] integers = (Integer[]) array.getArray();
        int[] intArray = new int[integers.length];
        for (int i = 0; i < integers.length; i++) {
            intArray[i] = integers[i];
        }
        return  intArray;
    }
}


