import java.sql.Connection;

public class Main {
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "1234567";
    private static final String DB_NAME = "marketplacejava";

    public static void main(String[] args)  {
        DbFunctions db = new DbFunctions();
        Connection conn = db.connect_to_db(DB_NAME, DB_USERNAME, DB_PASSWORD);
        while(true) {
            User user;
            do {
                user = Auth.main(conn);
            } while (user.getId() == -1);
            Run.main(conn, user);
        }
    }
}