package repositories.auth.interfaces;

import java.util.regex.Pattern;

public interface EmailInterface {
    Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    boolean isValidEmail(String email);
}
