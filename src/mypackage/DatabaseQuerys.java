package mypackage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

class DatabaseQuerys {
    private static DatabaseQuerys javaDB = new DatabaseQuerys();
    private DatabaseQuerys() {}
    public static DatabaseQuerys getDatabaseQuerysInst() {
        return javaDB;
    }

    private static Connection getConnection() throws SQLException {
        Connection conn = null;
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/holidayschedulingapp", "java", "hsppassword");
        return conn;
    }

    public boolean loginSelect(String username, char[] password) throws SQLException {
        user newUser = user.getInstance();
        Connection con = null;
        PreparedStatement psLogin = null;
        ResultSet resultsLogin = null;
        int id = -1;
        Boolean admin = null;
        String rank = null;
        String tempPassString = new String(password);
        String hashedPassword = "";
        boolean resultOfQuery = false;
        passwordHandler pwCheck = new passwordHandler();

        try {
            con = this.getConnection();
            String sqlLoginQuery = "SELECT id, password, rank, admin FROM users WHERE username=?;";
            psLogin = con.prepareStatement(sqlLoginQuery);
            psLogin.setString(1, username);
            resultsLogin = psLogin.executeQuery();
            while (resultsLogin.next()) {
                id = resultsLogin.getInt("id");
                hashedPassword = resultsLogin.getString("password");
                rank = resultsLogin.getString("rank");
                admin = resultsLogin.getBoolean("admin");
            }
            if ((id != -1) && (pwCheck.checkPassword(tempPassString, hashedPassword))) {
                newUser.setUserID(id);
                newUser.setUsername(username);
                newUser.setUserRank(rank);
                newUser.setUserAdmin(admin);
                resultOfQuery = true;
            } else {
                resultOfQuery = false;
            }

        } catch (Exception e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }

        finally {
            if (resultsLogin != null) {
                resultsLogin.close();
            }
            if (psLogin != null) {
                psLogin.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return resultOfQuery;
    }

    public boolean passwordUpdate(int id, String password) throws SQLException {
        Connection con = null;
        PreparedStatement psPasswordUpdate = null;
        int recordCount = 0;
        boolean resultUpdate = false;

        try {
            con = this.getConnection();
            String sqlPasswordUpdate = "UPDATE users SET password = ? WHERE id = ?";
            psPasswordUpdate = con.prepareStatement(sqlPasswordUpdate);
            psPasswordUpdate.setString(1, password);
            psPasswordUpdate.setString(2, Integer.toString(id));
            recordCount = psPasswordUpdate.executeUpdate();
            if (recordCount == 1) {
                resultUpdate = true;
            } else {
                resultUpdate = false;
            }
        } catch(Exception e) {
            throw new IllegalStateException("Password Update Failed",e);
        } finally {
            if (psPasswordUpdate != null) {
                psPasswordUpdate.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return resultUpdate;
    }
}
