package entities;

import entities.interfaces.Colors;
import entities.interfaces.Hasher;

import java.sql.Connection;
import java.sql.Statement;

public class User implements Colors, Hasher {
    private int id = -1;
    private String username;
    private String email;

    public User() {}

    public User(int id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }
    public void updatePassword(Connection conn, String new_password) {
        try {
            String hashed_new_password = hashPassword(new_password);
            String query = String.format("update users set password='%s' where id='%d'", hashed_new_password, id);
            Statement statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println(ANSI_GREEN + "Password updated successfully!" + ANSI_RESET);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void deleteUser(Connection conn) {
        Statement statement;
        try{
            String query=String.format("delete from users where id='%s'", id);
            statement=conn.createStatement();
            statement.executeUpdate(query);
            System.out.println(ANSI_GREEN + "Account deleted successfully!" + ANSI_RESET);
        } catch (Exception e){
            System.out.println(e);
        }
    }
    public String toString() {
        return "Username: " + this.email + "\n" + "email: " + this.email;
    }
    public void setUsername(String username) {this.username = username;}
    public void setEmail(String email) {this.email = email;}
    public void setId(int id) {this.id = id;}
    public String getUsername() {return username;}
    public String getEmail() {return email;}
    public int getId() {return id;}
}
