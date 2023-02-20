package logs;

public class SignUpLogs extends Logs{
    public static void emailExists() {
        System.out.println(ANSI_ORANGE + "Error: Current email exist, try again." + ANSI_RESET);
    }
    public static void passwordsNotMatch() {
        System.out.println(ANSI_RED + "Error: Passwords do not match." + ANSI_RESET);
    }
}
