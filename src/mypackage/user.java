package mypackage;

/*
This class acts as the instance for all user variables.
In this class multiple get setters are used for variables as well as for instance.
The class uses lazy instantiation of a single pattern (Intializing when needed)
 */

public class user {
    //Sets up instance variables
    private static user userObj;
    private user() {}

    //Variables for the user object
    private int userID;
    private String username;
    private String userRank;
    private boolean userAdmin;

    //Lazy instantiation used to create instance when needed, function then used to get instance
    public static user getInstance() {
        if (userObj == null) {
            userObj = new user();
        }
        return userObj;
    }

    //Get setters of the user variables are listed below
    public int getUserID() {
        return userID;
    }
    public void setUserID (int id) {
        this.userID = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String name) {
        this.username = name;
    }

    public String getUserRank() {
        return userRank;
    }
    public void setUserRank(String rank) {
        this.userRank = rank;
    }

    public boolean getUserAdmin() {
        return userAdmin;
    }
    public void setUserAdmin (boolean admin) {
        this.userAdmin = admin;
    }
}
