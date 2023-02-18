import logs.SignInLogs;

import java.util.Scanner;

public class SignIn extends SignInLogs {
    static Scanner scan = new Scanner(System.in);

    public static Buyer signIn() {
        String email, password;
        while (true) {
            System.out.print("Enter email: ");
            email = scan.next();
            if (!EmailValidator.validate(email)) {
                invalidEmail();
            } else if (db.checkEmail(email)) {
                break;
            } else {
                emailNotExists();
            }
        }
        for (int i = 1; true; i++) {
            System.out.print("Enter password: ");
            password = scan.next();
            if (db.checkUser(email, password)) {
                successfulLogin();
                int id = db.getId(email, password);
                return db.getBuyerById(id);
            } else if (i == 3) {
                threeIncorrectAttempts();
                return new Buyer();
            }
            invalidPassword();
        }
    }
}


