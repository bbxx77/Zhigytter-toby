package repositories.auth;

import data.interfaces.IDB;
import entities.Buyer;
import repositories.auth.interfaces.AuthentificationInterface;
import repositories.auth.interfaces.EmailInterface;
import repositories.auth.interfaces.PasswordInterface;
import repositories.interfaces.IColors;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Scanner;
import java.util.regex.Matcher;

public class Authentification implements IColors, AuthentificationInterface, PasswordInterface, EmailInterface {
    private final IDB db;
    private final Scanner scanner;

    public Authentification(IDB db) {
        this.db = db;
        this.scanner = new Scanner(System.in);
    }

    public Buyer auth() {
        while (true) {
            System.out.println("1. Login");
            System.out.println("2. Sign up");
            System.out.println(ANSI_RED + "3. Exit.\n" + ANSI_RESET);
            System.out.println();
            System.out.print("Choose an option(1-3): ");
            String choice = scanner.next();
            String username, email, password, confirmPassword;
            switch (choice) {
                case "1" -> {
                    while (true) {
                        System.out.print("Enter email: ");
                        email = scanner.next();
                        if (!isValidEmail(email)) {
                            System.out.println(ANSI_RED + "Error: Invalid email, try again." + ANSI_RESET);
                        } else if (isEmailExist(email)) {
                            break;
                        } else {
                            System.out.println(ANSI_RED + "Error: This account does not exist." + ANSI_RESET);
                            System.out.println(ANSI_RED + "Error: Enter another account or register a new one." + ANSI_RESET);
                        }
                    }
                    for (int i = 1; true; i++) {
                        System.out.print("Enter password: ");
                        password = scanner.next();
                        int id = getId(email, password);
                        if (id != -1) {
                            System.out.println(ANSI_GREEN + "Login successful!" + ANSI_RESET);
                            return db.getBuyerById(id);
                        } else if (i == 3) {
                            System.out.println(ANSI_RED + "3 incorrect password attempts\n" + ANSI_RESET);
                            break;
                        }
                        System.out.println(ANSI_RED + "Error: Sorry, try again." + ANSI_RESET);
                    }
                }
                case "2" -> {
                    System.out.print("Enter Username: ");
                    username = scanner.next();
                    while (true) {
                        System.out.print("Enter email: ");
                        email = scanner.next();
                        if (isEmailExist(email)) {
                            System.out.println(ANSI_ORANGE + "Error: Current email exist, try again." + ANSI_RESET);
                        } else if (!isValidEmail(email)) {
                            System.out.println(ANSI_RED + "Error: Invalid email, try again." + ANSI_RESET);
                        } else {
                            break;
                        }
                    }
                    while (true) {
                        System.out.println(PasswordInterface.PASSWORD_REQUIREMENTS);
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
                    signUpUser(username, email, password);
                    int id = getId(email, password);
                    return new Buyer(id, email, password);
                }
                case "3" -> {
                    System.out.println(ANSI_BLUE + "Bye!" + ANSI_RESET);
                    System.exit(0);
                }
                default -> {
                    System.out.println(ANSI_RED + "Error: Invalid option, try again" + ANSI_RESET);
                }
            }

            System.out.println("*************************");

        }
    }

    @Override
    public boolean isValidPassword(String password) {
        Matcher matcher = PasswordInterface.VALID_PASSWORD_REGEX.matcher(password);
        return matcher.matches();
    }

    @Override
    public boolean isValidEmail(String emailStr) {
        Matcher matcher = EmailInterface.VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    @Override
    public boolean isEmailExist(String email) {
        Connection conn = null;
        try {
            conn = db.getConnection();
            String query = String.format("select * from %s where email='%s'", "users", email);
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                assert conn != null;
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public int getId(String email, String password) {
        Connection conn = null;
        try {
            conn = db.getConnection();
            String hashedPassword = getHashPassword(password);
            String query = String.format("select * from users where email='%s' and password='%s'", email, hashedPassword);
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                return rs.getInt("id");
            }
            return -1;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }

    @Override
    public void signUpUser(String username, String email, String password) {
        Connection conn = null;
        try {
            conn = db.getConnection();
            String hashedPassword = getHashPassword(password);
            String query = String.format("insert into users (username, email, password, orders, wishlist, cart, wallet) values('%s', '%s', '%s', ARRAY[]::integer[], ARRAY[]::integer[], ARRAY[]::integer[], 0);", username, email, hashedPassword);
            Statement statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println(ANSI_GREEN + "Registration successful!" + ANSI_RESET);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getHashPassword(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(password.getBytes());
            byte[] resultByteArray = messageDigest.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : resultByteArray) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}



