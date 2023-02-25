package repositories.interfaces;

public interface IUserRepository {
    void updatePassword(String new_password);
    void deleteUser();
}
