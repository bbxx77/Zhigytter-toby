import style.Colors;

import java.sql.Connection;
import java.sql.Statement;

public class User extends Colors {
    static Connection conn = db.connection();

    private int id = -1;
    private String username;
    private String email;

    public User() {
        
    }

    public User(int id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public String getUsername() {return username;}
    public String getEmail() {return email;}
    public int getId() {return id;}

    public void updatePassword(String new_password) {
        try {
            String query = String.format("update users set password='%s' where id='%d'", new_password, id);
            Statement statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println(ANSI_GREEN + "Password updated successfully!" + ANSI_RESET);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void deleteUser() {
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
}
