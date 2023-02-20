import java.sql.*;
import java.util.Scanner;

public class Auth extends Colors {

    public Buyer main(Connection conn, DbFunctions db) {
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.println("1. Login");
            System.out.println("2. Sign up");
            System.out.println(ANSI_RED + "3. Exit.\n" + ANSI_RESET);
            System.out.println("*************************");
            System.out.print("Choose an option: ");

            String choice = scan.next();
            String username, email, password, confirmPassword;

            switch (choice) {
                case "1" -> {
                    while (true) {
                        System.out.print("Enter email: ");
                        email = scan.next();
                        if (!EmailValidator.isValid(email)) {
                            System.out.println(ANSI_RED + "Error: Invalid email, try again." + ANSI_RESET);
                        } else if (db.checkEmail(conn, email)) {
                            break;
                        } else {
                            System.out.println(ANSI_RED + "Error: This account does not exist." + ANSI_RESET);
                            System.out.println(ANSI_RED + "Error: Enter another account or register a new one." + ANSI_RESET);
                        }
                    }
                    for (int i = 1; true; i++) {
                        System.out.print("Enter password: ");
                        password = scan.next();
                        if (db.checkUser(conn, email, password)) {
                            System.out.println(ANSI_GREEN + "Login successful!" + ANSI_RESET);
                            int id = db.getId(conn, email, password);
                            return db.getBuyerById(conn, id);
                        } else if (i == 3) {
                            System.out.println(ANSI_RED + "3 incorrect password attempts\n" + ANSI_RESET);
                            return new Buyer();
                        }
                        System.out.println(ANSI_RED + "Error: Sorry, try again." + ANSI_RESET);
                    }
                }
                case "2" -> {
                    System.out.print("Enter Username: ");
                    username = scan.next();
                    while (true) {
                        System.out.print("Enter email: ");
                        email = scan.next();
                        if (db.checkEmail(conn, email)) {
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
                    db.signUpUser(conn, username, email, password);
                    int id = db.getId(conn, email, password);
                    return new Buyer(id, email, password);
                }
                case "3" -> {
                    System.out.println(ANSI_BLUE + "Bye!" + ANSI_RESET);
                    System.exit(0);
                }
                default -> {
                    System.out.println(ANSI_RED + "Error: Invalid option, try again" + ANSI_RESET);
                    System.out.println("*************************\n");
                }
            }
        }
    }
}


