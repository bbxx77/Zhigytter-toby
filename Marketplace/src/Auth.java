import java.sql.*;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Auth {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_ORANGE = "\u001B[38;5;208m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static User main(String DB_NAME, String DB_USERNAME, String DB_PASSWORD) {
        Scanner scan = new Scanner(System.in);
        try {
            DbFunctions db = new DbFunctions();
            Connection conn = db.connect_to_db(DB_NAME, DB_USERNAME, DB_PASSWORD);
            while(true) {
                System.out.println("1. Login");
                System.out.println("2. Sign up");
                System.out.println(ANSI_RED + "3. Exit.\n" + ANSI_RESET);
                System.out.println("*************************");
                System.out.print("Choose an option: ");

                int choice = scan.nextInt();
                if (choice == 1) {
                    String email, password;
                    while(true) {
                        System.out.print("Enter email: ");
                        email = scan.next();
                        if (checkEmail(conn, email)) {
                            break;
                        } else {
                            if (isValidEmail(email)) {
                                System.out.println(ANSI_RED + "Invalid email, try again." + ANSI_RESET);
                                continue;
                            }
                            System.out.println(ANSI_RED + "This account does not exist." + ANSI_RESET);
                            System.out.println(ANSI_RED + "Enter another account or register a new one." + ANSI_RESET);
                        }
                    }
                    for (int i = 1; true; i++) {
                        System.out.print("Enter password: ");
                        password = scan.next();
                        if (checkPassword(conn, email, password)) {
                            System.out.println(ANSI_GREEN + "Login successful!" + ANSI_RESET);
                            int id = getId(conn, email, password);
                            return getUserById(conn, id);
                        } else if (i == 3) {
                            System.out.println(ANSI_RED + "3 incorrect password attempts" + ANSI_RESET);
                            return new User();
                        }
                        System.out.println(ANSI_RED + "Sorry, try again." + ANSI_RESET);
                    }
                } else if (choice == 2) {
                    System.out.print("Enter first name: ");
                    String firstName = scan.next();
                    System.out.print("Enter last name: ");
                    String lastName = scan.next();
                    String email, password, confirmPassword;
                    while(true) {
                        System.out.print("Enter email: ");
                        email = scan.next();
                        if (checkEmail(conn, email)) {
                            System.out.println(ANSI_ORANGE + "Current email exist, try again." + ANSI_RESET);
                        } else if (isValidEmail(email)) {
                            System.out.println(ANSI_RED + "Invalid email, try again." + ANSI_RESET);
                        } else {
                            break;
                        }
                    }
                    while (true) {
                        System.out.println(ANSI_GREEN + "1. A password must have at least 8 characters.\n" +
                                "2. A password consists of only letters and digits.\n" +
                                "3. A password must contain at least 2 digits \n" + ANSI_RESET);
                        System.out.print("Create a password: ");
                        password = scan.next();
                        if (isValidPassword(password)) {
                            break;
                        }
                        System.out.println(ANSI_RED + "Invalid password, try again." + ANSI_RESET);
                    }
                    while (true) {
                        System.out.println("Confirm a password: ");
                        confirmPassword = scan.next();
                        if (password.equals(confirmPassword)) {
                            break;
                        }
                        System.out.println(ANSI_RED + "Error: Passwords do not match." + ANSI_RESET);
                    }
                    signUp(conn, firstName, lastName, email, password);
                    int id = getId(conn, email, password);
                    return new User(id, firstName, lastName, email, password);
                } else if (choice == 3) {
                    System.out.println(ANSI_BLUE + "Bye!" + ANSI_RESET);
                    System.exit(0);
                } else {
                    System.out.println(ANSI_RED + "Invalid option" + ANSI_RESET);
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
    private static boolean checkPassword(Connection conn, String email, String password) {
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
    private static void signUp(Connection conn, String first_name, String last_name, String email, String password){
        Statement statement;
        try {
            String query = String.format("insert into %s(first_name, last_name, email, password) values('%s', '%s', '%s', '%s');", "users", first_name, last_name, email, password);
            statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Registration successful!");
        } catch (Exception e) {
            e.getStackTrace();
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
    private static User getUserById(Connection conn, int id) {
        ResultSet rs;
        Statement statement;
        try {
            String query = String.format("select * from %s where id='%s'", "users", id);
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            if (rs.next()) {
                return new User(
                    id,
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    rs.getString("password")
                );
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        return new User();
    }
    private static boolean isValidEmail(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return true;
        return !pat.matcher(email).matches();
    }
    private static boolean isValidPassword(String password) {
        int PASSWORD_LENGTH = 8;
        if (password.length() < PASSWORD_LENGTH) return false;
        int charCount = 0;
        int numCount = 0;
        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            if (is_Numeric(ch)) numCount++;
            else if (is_Letter(ch)) charCount++;
            else return false;
        }
        return (charCount >= 2 && numCount >= 2);
    }
    private static boolean is_Letter(char ch) {
        ch = Character.toUpperCase(ch);
        return (ch >= 'A' && ch <= 'Z');
    }
    private static boolean is_Numeric(char ch) {
        return (ch >= '0' && ch <= '9');
    }
}
