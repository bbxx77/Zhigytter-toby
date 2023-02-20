package logs;

import style.Colors;

public class Logs extends Colors {
    public static void invalidEmail() {
        System.out.println(ANSI_RED + "Error: Invalid email, try again." + ANSI_RESET);
    }
    public static void invalidPassword() {
        System.out.println(ANSI_RED + "Error: Invalid password, try again." + ANSI_RESET);
    }
    public static void invalidOption() {
        System.out.println(ANSI_RED + "Error: Invalid option, try again" + ANSI_RESET);
    }
    public static void exitProgram() {
        System.out.println(ANSI_BLUE + "Bye!" + ANSI_RESET);
        System.exit(0);
    }
}
