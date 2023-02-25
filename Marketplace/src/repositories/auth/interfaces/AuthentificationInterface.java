package repositories.auth.interfaces;

public interface AuthentificationInterface {
    int getId(String email, String password);
    void signUpUser(String username, String email, String password);
    boolean isEmailExist(String email);
}
