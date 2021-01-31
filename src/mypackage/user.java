package mypackage;

public class user {
    private static user userObj;
    private user() {}

    private int userID;
    private String username;
    private String userRank;
    private boolean userAdmin;

    public static user getInstance() {
        if (userObj == null) {
            userObj = new user();
        }
        return userObj;
    }

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
