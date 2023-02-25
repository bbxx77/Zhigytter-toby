package repositories.auth.interfaces;

import java.util.regex.Pattern;

public interface PasswordInterface {
    String PASSWORD_REQUIREMENTS =
            "1. Contains at least one digit (0-9).\n" +
                    "2. Contains at least one lowercase letter (a-z).\n" +
                    "3. Contains at least one uppercase letter (A-Z).\n" +
                    "4. Does not contain whitespace.\n" +
                    "5. Is at least 8 characters long.";
    Pattern VALID_PASSWORD_REGEX = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$");
    boolean isValidPassword(String password);
    String getHashPassword(String password);
}
