import java.sql.SQLException;

public class Main {
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "1234567";
    private static final String DB_NAME = "marketplacejava";

    public static void main(String[] args) throws SQLException {
        User user;
        do {
            user = Auth.main(DB_NAME, DB_USERNAME, DB_PASSWORD);
        } while (user.equals(new User()));

        Run.main(DB_NAME, DB_USERNAME, DB_PASSWORD);
    }
}