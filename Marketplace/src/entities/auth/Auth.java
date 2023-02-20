package entities.auth;

import entities.Buyer;
import db.Database;
import entities.interfaces.Colors;
import entities.interfaces.EmailValidator;
import entities.interfaces.PasswordValidator;

import java.sql.*;
import java.util.Scanner;

public class Auth implements Colors, EmailValidator, PasswordValidator {

    public  Buyer main(Connection conn, Database db) throws SQLException {
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.println("1. Login");
            System.out.println("2. Sign up");
            System.out.println(Colors.ANSI_RED + "3. Exit.\n" + Colors.ANSI_RESET);
            System.out.println("*************************");
            System.out.print("Choose an option: ");

            String choice = scan.next();
            String username, email, password, confirmPassword;

            switch (choice) {
                case "1" -> {
                    while (true) {
                        System.out.print("Enter email: ");
                        email = scan.next();
                        if (!isValidEmail(email)) {
                            System.out.println(Colors.ANSI_RED + "Error: Invalid email, try again." + Colors.ANSI_RESET);
                        } else if (db.checkEmail(conn, email)) {
                            break;
                        } else {
                            System.out.println(Colors.ANSI_RED + "Error: This account does not exist." + Colors.ANSI_RESET);
                            System.out.println(Colors.ANSI_RED + "Error: Enter another account or register a new one." + Colors.ANSI_RESET);
                        }
                    }
                    for (int i = 1; true; i++) {
                        System.out.print("Enter password: ");
                        password = scan.next();
                        int id = db.checkLogin(conn, email, password);
                        if (id != -1) {
                            System.out.println(Colors.ANSI_GREEN + "Login successful!" + Colors.ANSI_RESET);
                            return db.getBuyerById(conn, id);
                        } else if (i == 3) {
                            System.out.println(Colors.ANSI_RED + "3 incorrect password attempts\n" + Colors.ANSI_RESET);
                            return new Buyer();
                        }
                        System.out.println(Colors.ANSI_RED + "Error: Sorry, try again." + Colors.ANSI_RESET);
                    }
                }
                case "2" -> {
                    System.out.print("Enter Username: ");
                    username = scan.next();
                    while (true) {
                        System.out.print("Enter email: ");
                        email = scan.next();
                        if (db.checkEmail(conn, email)) {
                            System.out.println(Colors.ANSI_ORANGE + "Error: Current email exist, try again." + Colors.ANSI_RESET);
                        } else if (!isValidEmail(email)) {
                            System.out.println(Colors.ANSI_RED + "Error: Invalid email, try again." + Colors.ANSI_RESET);
                        } else {
                            break;
                        }
                    }
                    while (true) {
                        System.out.println(passwordRequirements);
                        System.out.print("Create a password: ");
                        password = scan.next();
                        if (isValidPassword(password)) {
                            break;
                        }
                        System.out.println(Colors.ANSI_RED + "Error: Invalid password, try again." + Colors.ANSI_RESET);
                    }
                    while (true) {
                        System.out.print("Confirm a password: ");
                        confirmPassword = scan.next();
                        if (password.equals(confirmPassword)) {
                            break;
                        }
                        System.out.println(Colors.ANSI_RED + "Error: Passwords do not match." + Colors.ANSI_RESET);
                    }
                    db.signUpUser(conn, username, email, password);
                    int id = db.checkLogin(conn, email, password);
                    return new Buyer(id, email, password);
                }
                case "3" -> {
                    System.out.println(Colors.ANSI_BLUE + "Bye!" + Colors.ANSI_RESET);
                    System.exit(0);
                }
                default -> {
                    System.out.println(Colors.ANSI_RED + "Error: Invalid option, try again" + Colors.ANSI_RESET);
                    System.out.println("*************************\n");
                }
            }
        }
    }
}


