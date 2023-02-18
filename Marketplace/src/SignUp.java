import logs.SignUpLogs;
import java.util.Scanner;

public class SignUp extends SignUpLogs {
    static Scanner scan = new Scanner(System.in);

    public static Buyer signUp() {
        System.out.print("Enter Username: ");
        String username = scan.next();
        String email, password, confirmPassword;
        while (true) {
            System.out.print("Enter email: ");
            email = scan.next();
            if (db.checkEmail(email)) {
                emailExists();
            } else if (!EmailValidator.isValid(email)) {
                invalidEmail();
            } else {
                break;
            }
        }
        while (true) {
            PasswordValidator.print();
            System.out.print("Create a password: ");
            password = scan.next();
            if (PasswordValidator.isValid(password)) {
                break;
            }
            invalidPassword();
        }
        while (true) {
            System.out.print("Confirm a password: ");
            confirmPassword = scan.next();
            if (password.equals(confirmPassword)) {
                break;
            }
            passwordsNotMatch();
        }
        db.signUpUser(username, email, password);
        int id = db.getId(email, password);
        return new Buyer(id, username, email);
    }

}


