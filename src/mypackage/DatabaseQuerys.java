package mypackage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class DatabaseQuerys {
    private boolean isAdmin;
    private final String url = "jdbc:mysql://localhost:3306/holidayschedulingapp";
    private final String username = "java";
    private final String password = "hsppassword";

    public boolean LoginQuery(String Username, char[] Password) throws SQLException {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Database connected!");
            String id = null;
            String admin = null;
            String password2 = new String(Password);
            PreparedStatement psLogin = null;
            String sqlLoginQuery = "SELECT id, admin FROM users WHERE username=? and password=?;";
            psLogin = connection.prepareStatement(sqlLoginQuery);
            psLogin.setString(1, Username);
            psLogin.setString(2, password2);
            ResultSet resultsLogin = psLogin.executeQuery();
            while (resultsLogin.next()) {
                id = resultsLogin.getString("id");
                admin = resultsLogin.getString("admin");
            }
            if (id != null) {
                if (admin.equals("True")) {
                    isAdmin = true;
                } else {
                    isAdmin = false;
                }
               return true;
            } else {
                return false;
            }
        } catch (SQLException exc) {
            throw new IllegalStateException("Cannot connect the database!", exc);
        }
    }

    public boolean getisAdmin () {
        return isAdmin;
    }
}
