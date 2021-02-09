package mypackage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Date;

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
        int allowance = -1;
        String tempPassString = new String(password);
        String hashedPassword = "";
        boolean resultOfQuery = false;
        passwordHandler pwCheck = new passwordHandler();

        try {
            con = this.getConnection();
            String sqlLoginQuery = "SELECT id, password, rank, admin, allowance FROM users WHERE username=?;";
            psLogin = con.prepareStatement(sqlLoginQuery);
            psLogin.setString(1, username);
            resultsLogin = psLogin.executeQuery();
            while (resultsLogin.next()) {
                id = resultsLogin.getInt("id");
                hashedPassword = resultsLogin.getString("password");
                rank = resultsLogin.getString("rank");
                admin = resultsLogin.getBoolean("admin");
                allowance = resultsLogin.getInt("allowance");
            }
            if ((id != -1) && (pwCheck.checkPassword(tempPassString, hashedPassword))) {
                newUser.setUserID(id);
                newUser.setUsername(username);
                newUser.setUserRank(rank);
                newUser.setUserAdmin(admin);
                newUser.setUserAllowance(allowance);
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
            String sqlPasswordUpdate = "UPDATE users SET password = ? WHERE id = ?;";
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

    public boolean holidayAdd(int id, String cDate, String sDate, String eDate) throws SQLException {
        Connection con = null;
        PreparedStatement psHolidayAdd = null;
        int recordCounter = 0;
        Boolean resultAdd = false;

        try {
            con = this.getConnection();
            String sqlHolidayAdd = "INSERT INTO holidays(user_id, date_requested, holiday_start, holiday_end, status)values(?,?,?,?,?);";
            psHolidayAdd = con.prepareStatement(sqlHolidayAdd);
            psHolidayAdd.setString(1, Integer.toString(id));
            psHolidayAdd.setString(2, cDate);
            psHolidayAdd.setString(3, sDate);
            psHolidayAdd.setString(4, eDate);
            psHolidayAdd.setString(5, "Not Reviewed");
            recordCounter = psHolidayAdd.executeUpdate();
            if (recordCounter == 1) {
                resultAdd = true;
            } else {
                resultAdd = false;
            }
        } catch(Exception e) {
            throw new IllegalStateException("Adding holiday failed!",e);
        } finally {
            if (psHolidayAdd != null) {
                psHolidayAdd.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return resultAdd;
    }

    public boolean allowanceUpdate(int id, int allowance) throws SQLException {
        Connection con = null;
        PreparedStatement psAllowanceUpdate = null;
        int recordCount = 0;
        Boolean resultUpdate = false;

        try {
            con = this.getConnection();
            String sqlAllowanceUpdate = "UPDATE users SET allowance = ? WHERE id = ?;";
            psAllowanceUpdate = con.prepareStatement(sqlAllowanceUpdate);
            psAllowanceUpdate.setString(1, Integer.toString(allowance));
            psAllowanceUpdate.setString(2, Integer.toString(id));
            recordCount = psAllowanceUpdate.executeUpdate();
            if (recordCount == 1) {
                resultUpdate = true;
            } else {
                resultUpdate = false;
            }
        } catch(Exception e) {
            throw new IllegalStateException("Updating allowance failed!",e);
        } finally {
            if (psAllowanceUpdate != null) {
                psAllowanceUpdate.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return resultUpdate;
    }
}
