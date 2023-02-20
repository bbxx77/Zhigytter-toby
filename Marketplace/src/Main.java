import entities.auth.Auth;
import entities.Buyer;
import db.Database;
import entities.Run;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "1234567";
    private static final String DB_NAME = "marketplacejava";

    public static void main(String[] args) throws SQLException {
        Database db = new Database();
        Connection conn = db.connect(DB_NAME, DB_USERNAME, DB_PASSWORD);
        while(true) {
            Buyer buyer;
            do {
                buyer = new Auth().main(conn, db);
            } while (buyer.getId() == -1);
            new Run().main(conn, buyer);
        }
    }
}


