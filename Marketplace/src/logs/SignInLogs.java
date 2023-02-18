package logs;

public class SignInLogs extends Logs{

    public static void successfulLogin() {
        System.out.println(ANSI_GREEN + "Login successful!" + ANSI_RESET);
    }
    public static void threeIncorrectAttempts() {
        System.out.println(ANSI_RED + "3 incorrect password attempts\n" + ANSI_RESET);
    }
    public static void emailNotExists() {
        System.out.println(ANSI_RED + "Error: This account does not exist." + ANSI_RESET);
        System.out.println(ANSI_RED + "Error: Enter another account or register a new one." + ANSI_RESET);
    }
}
