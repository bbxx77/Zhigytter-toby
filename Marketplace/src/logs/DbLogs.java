package logs;

public class DbLogs extends Logs{
    public static void connectionFailed() {
        System.out.println("\u001B[31m" + "Connection Failed!" + "\u001B[0m");
    }
    public static void invalidProductId() {
        System.out.println(ANSI_RED + "Error: Invalid product ID." + ANSI_RESET);
    }
}
