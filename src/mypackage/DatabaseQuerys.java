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

    public boolean loginSelect(String Username, char[] Password) throws SQLException {
        user newUser = user.getInstance();
        Connection con = null;
        PreparedStatement psLogin = null;
        ResultSet resultsLogin = null;
        int id = -1;
        Boolean admin = null;
        String password2 = new String(Password);
        boolean resultOfQuery = false;

        try {
            con = this.getConnection();
            String sqlLoginQuery = "SELECT id, admin FROM users WHERE username=? and password=?;";
            psLogin = con.prepareStatement(sqlLoginQuery);
            psLogin.setString(1, Username);
            psLogin.setString(2, password2);
            resultsLogin = psLogin.executeQuery();
            while (resultsLogin.next()) {
                id = resultsLogin.getInt("id");
                admin = resultsLogin.getBoolean("admin");
            }
            if (id != -1) {
                newUser.setUserID(id);
                newUser.setUserAdmin(admin);
                resultOfQuery = true;
            } else {
                resultOfQuery = false;
            }
        } catch (SQLException exc) {
            throw new IllegalStateException("Cannot connect the database!", exc);
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
}
