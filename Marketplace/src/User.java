public class User {

    private int id = 1;
    private String first_name = null; 
    private String last_name = null;
    private String email = null; 
    private String password = null;

    public User() {
        
    }
    public User(int id, String first_name, String last_name, String email, String password) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
    }

    public String getFirst_name() {return first_name;}
    public String getLast_name() {
        return last_name;
    }
    public String getEmail() {return email;}
    public String getPassword() {return password;}
    public int getId() {
        return id;
    }
}