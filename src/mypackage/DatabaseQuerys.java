package mypackage;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;

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
        int rank = -1;
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
                rank = resultsLogin.getInt("rank");
                admin = resultsLogin.getBoolean("admin");
                allowance = resultsLogin.getInt("allowance");
            }
            if ((id != -1) && (pwCheck.checkPassword(tempPassString, hashedPassword))) {
                newUser.setUserID(id);
                newUser.setUsername(username);
                newUser.setUserRank(rankSelect(rank));
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

    public DefaultTableModel userHolidaySelect(int id) throws SQLException {
        DefaultTableModel model = new DefaultTableModel(new String[] {"Holiday Start", "Holiday End", "Date Requested", "Date Approved","Status"}, 0);

        Connection con = null;
        PreparedStatement psUserHol = null;
        ResultSet resultsUserHol = null;
        String sDate, eDate, rDate, aDate, status = "";

        try {
            con = this.getConnection();
            String sqlUHolidayQuery = "SELECT holiday_start, holiday_end, date_requested, approval_date, status FROM holidays WHERE user_id = ?;";
            psUserHol = con.prepareStatement(sqlUHolidayQuery);
            psUserHol.setString(1, Integer.toString(id));
            resultsUserHol = psUserHol.executeQuery();
            while (resultsUserHol.next()) {
                sDate = resultsUserHol.getString("holiday_start");
                eDate = resultsUserHol.getString("holiday_end");
                rDate = resultsUserHol.getString("date_requested");
                aDate = resultsUserHol.getString("approval_date");
                status = resultsUserHol.getString("status");
                model.addRow(new Object[] {sDate, eDate, rDate, aDate, status});
            }

        } catch (Exception e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }

        finally {
            if (resultsUserHol != null) {
                resultsUserHol.close();
            }
            if (psUserHol != null) {
                psUserHol.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return model;
    }

    public boolean userAdd(String username, int rank, int admin, int allowance) throws SQLException {
        Connection con = null;
        PreparedStatement psUserAdd = null;
        passwordHandler pwDefault = new passwordHandler();
        char[] defaultPass = {'C','h','a','n','g','e','M','e','1'};
        String password = pwDefault.newPassword(defaultPass);
        int recordCounter = 0;
        Boolean resultAdd = false;

        try {
            con = this.getConnection();
            String sqlUserAdd = "INSERT INTO users(username, password, rank, admin, allowance)values(?,?,?,?,?);";
            psUserAdd = con.prepareStatement(sqlUserAdd);
            psUserAdd.setString(1, username);
            psUserAdd.setString(2, password);
            psUserAdd.setString(3, Integer.toString(rank));
            psUserAdd.setString(4, Integer.toString(admin));
            psUserAdd.setString(5, Integer.toString(allowance));
            recordCounter = psUserAdd.executeUpdate();
            if (recordCounter == 1) {
                resultAdd = true;
            } else {
                resultAdd = false;
            }
        } catch(Exception e) {
            throw new IllegalStateException("Adding user failed!",e);
        } finally {
            if (psUserAdd != null) {
                psUserAdd.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return resultAdd;
    }

    public String rankSelect(int id) throws SQLException {
        Connection con = null;
        PreparedStatement psRank = null;
        ResultSet resultsRank = null;
        String rank = "";

        try {
            con = this.getConnection();
            String sqlRankQuery = "SELECT rank FROM ranks WHERE id = ?;";
            psRank = con.prepareStatement(sqlRankQuery);
            psRank.setString(1, Integer.toString(id));
            resultsRank = psRank.executeQuery();
            while (resultsRank.next()) {
                rank = resultsRank.getString("rank");;
            }

        } catch (Exception e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }

        finally {
            if (resultsRank != null) {
                resultsRank.close();
            }
            if (psRank != null) {
                psRank.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return rank;
    }

    public ArrayList<ranks> rankSelectAll() throws SQLException {
        ArrayList<ranks> ranksAll = new ArrayList<ranks>();
        Connection con = null;
        PreparedStatement psRankAll = null;
        ResultSet resultsRankAll = null;
        int rankId = -1;
        String rankName = "";
        int importance = -1;

        try {
            con = this.getConnection();
            String sqlRankQuery = "SELECT id, rank, importance FROM ranks;";
            psRankAll = con.prepareStatement(sqlRankQuery);
            resultsRankAll = psRankAll.executeQuery();
            while (resultsRankAll.next()) {
                rankId = resultsRankAll.getInt("id");
                rankName = resultsRankAll.getString("rank");
                importance = resultsRankAll.getInt("importance");
                ranks rank = new ranks(rankId, rankName, importance);
                ranksAll.add(rank);
            }

        } catch (Exception e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }

        finally {
            if (resultsRankAll != null) {
                resultsRankAll.close();
            }
            if (psRankAll != null) {
                psRankAll.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return ranksAll;
    }
}
