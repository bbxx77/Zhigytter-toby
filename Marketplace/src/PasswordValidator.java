import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator extends Colors {
    private static final String passwordRequirements = ANSI_BLUE +
        "1. Contains at least one digit (0-9).\n" +
        "2. Contains at least one lowercase letter (a-z).\n" +
        "3. Contains at least one uppercase letter (A-Z).\n" +
        "4. Does not contain whitespace.\n" +
        "5. Is at least 8 characters long." + ANSI_RESET;

    private static final Pattern VALID_PASSWORD_REGEX =
        Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$");

    public static void print() {
        System.out.println(passwordRequirements);
    }

    public static boolean isValid(String password) {
        Matcher matcher = VALID_PASSWORD_REGEX.matcher(password);
        return matcher.matches();
    }
}
