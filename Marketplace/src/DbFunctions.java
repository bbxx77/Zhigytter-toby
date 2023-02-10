import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DbFunctions {
    public Connection connect_to_db(String dbname, String user, String pass) {
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + dbname, user, pass);
            if (conn != null) {
                System.out.println("\u001B[32m" + "Connection Established!" + "\u001B[0m");
            } else {
                System.out.println("\u001B[31m" + "Connection Failed!" + "\u001B[0m");
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        return conn;
    }

    public void createTable(Connection conn, String table_name) {
        Statement statement;
        try {
            String query = "create table " + table_name + "(id SERIAL, first_name varchar(200), last_name varchar(200), email varchar(200), password varchar(200), primary key(id));";
            statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Table Created");
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void insert_row(Connection conn, String table_name, String first_name, String last_name, String email, String password) {
        Statement statement;
        try {
            String query = String.format("insert into %s(first_name, last_name, email, password) values('%s', '%s', '%s', '%s');", table_name, first_name, last_name, email, password);
            statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Row inserted");
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void read_data(Connection conn, String table_name) {
        Statement statement;
        ResultSet rs = null;
        try {
            String query = String.format("select * from %s", table_name);
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                System.out.println(rs.getString("id") + " ");
                System.out.println(rs.getString("first_name") + " ");
                System.out.println(rs.getString("last_name") + " ");
                System.out.println(rs.getString("email") + " ");
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void update_firstName(Connection conn, String table_name, int id, String new_firstName) {
        Statement statement;
        try {
            String query = String.format("update %s set first_name='%s' where id='%d'", table_name, new_firstName, id);
            statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Data Updated");
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void search_by_name(Connection conn, String table_name, String first_name, String last_name) {
        Statement statement;
        ResultSet rs = null;
        try {
            String query = String.format("select * from %s where first_name='%s' and last_name='%s'", table_name, first_name, last_name);
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                System.out.print(rs.getString("id") + " ");
                System.out.print(rs.getString("first_name") + " ");
                System.out.print(rs.getString("last_name") + " ");
                System.out.println(rs.getString("email"));

            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void search_by_id(Connection conn, String table_name, int id) {
        Statement statement;
        ResultSet rs = null;
        try {
            String query = String.format("select * from %s where id='%d'", table_name, id);
            statement = conn.createStatement();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}