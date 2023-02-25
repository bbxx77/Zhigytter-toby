package controllers;

import repositories.interfaces.IUserRepository;

public class UserController {
    private final IUserRepository repo;

    public UserController(IUserRepository repo) {
        this.repo = repo;
    }

    public void updatePassword(String password) {
        repo.updatePassword(password);
    }

    public void deleteUser() {
        repo.deleteUser();
    }

}
