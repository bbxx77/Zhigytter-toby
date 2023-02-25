package repositories;

import data.interfaces.IDB;
import entities.Buyer;
import entities.User;
import repositories.interfaces.IColors;
import repositories.interfaces.IUserRepository;
import repositories.auth.interfaces.PasswordInterface;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class UserRepository extends User implements IUserRepository, IColors, PasswordInterface {
    private final IDB db;
    private final Buyer buyer;

    public UserRepository(IDB db, Buyer buyer) {
        this.db = db;
        this.buyer = buyer;
    }

    @Override
    public void updatePassword(String new_password) {
        Connection conn = null;
        try {
            conn = db.getConnection();
            String hashed_new_password = getHashPassword(new_password);
            String query = String.format("update users set password='%s' where id='%d'", hashed_new_password, getId());
            Statement statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println(ANSI_GREEN + "Password updated successfully!" + ANSI_RESET);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteUser() {
        Connection conn = null;
        try{
            conn = db.getConnection();
            String query=String.format("delete from users where id='%s'", getId());
            Statement statement=conn.createStatement();
            statement.executeUpdate(query);
            System.out.println(ANSI_GREEN + "Account deleted successfully!" + ANSI_RESET);
        } catch (SQLException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isValidPassword(String password) {
        return false;
    }

    @Override
    public String getHashPassword(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(password.getBytes());
            byte[] resultByteArray = messageDigest.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : resultByteArray) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
