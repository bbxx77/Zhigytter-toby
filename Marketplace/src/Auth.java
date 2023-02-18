import logs.Logs;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Auth extends Logs {
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    static Scanner scan = new Scanner(System.in);

    public static Buyer auth() {
        while(true) {
            System.out.println("1. Login");
            System.out.println("2. Sign up");
            System.out.println(ANSI_RED + "3. Exit.\n" + ANSI_RESET);
            System.out.println("*************************");
            System.out.print("Choose an option: ");
            String choice = scan.next();
            switch (choice) {
                case "1" -> {
                    return SignIn.signIn();
                }
                case "2" -> {
                    return SignUp.signUp();
                }
                case "3" -> exitProgram();
                default -> {
                    invalidOption();
                    System.out.println("*************************\n");
                }
            }
        }
    }
    public static boolean isValidEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return !matcher.find();
    }
}
