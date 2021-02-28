package mypackage;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

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

    private String usernameSelect(String id) throws SQLException {
        Connection con = null;
        PreparedStatement psUsernameSelect = null;
        ResultSet resultsUsernameSelect = null;
        String username = "";

        try {
            con = this.getConnection();
            String sqlUsernameSelectQuery = "";
            sqlUsernameSelectQuery = "SELECT username FROM users WHERE id = ?;";
            psUsernameSelect = con.prepareStatement(sqlUsernameSelectQuery);
            psUsernameSelect.setString(1, id);
            resultsUsernameSelect = psUsernameSelect.executeQuery();
            while (resultsUsernameSelect.next()) {
                username = resultsUsernameSelect.getString("username");
            }
        } catch (Exception e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }

        finally {
            if (resultsUsernameSelect != null) {
                resultsUsernameSelect.close();
            }
            if (psUsernameSelect != null) {
                psUsernameSelect.close();
            }
            if (con != null) {
                con.close();
            }
            return username;
        }
    }

    public String userRankSelect(String id) throws SQLException {
        Connection con = null;
        PreparedStatement psUserrankSelect = null;
        ResultSet resultsUserrankSelect = null;
        String rank = "";

        try {
            con = this.getConnection();
            String sqlUserrankSelectQuery = "";
            sqlUserrankSelectQuery = "SELECT rank FROM users WHERE id = ?;";
            psUserrankSelect = con.prepareStatement(sqlUserrankSelectQuery);
            psUserrankSelect.setString(1, id);
            resultsUserrankSelect = psUserrankSelect.executeQuery();
            while (resultsUserrankSelect.next()) {
                rank = resultsUserrankSelect.getString("rank");
            }
        } catch (Exception e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }

        finally {
            if (resultsUserrankSelect != null) {
                resultsUserrankSelect.close();
            }
            if (psUserrankSelect != null) {
                psUserrankSelect.close();
            }
            if (con != null) {
                con.close();
            }
            return rank;
        }
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
                newUser.setUserRankId(rank);
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

    public boolean userAdd(String username, String password, int rank, int admin, int allowance) throws SQLException {
        Connection con = null;
        PreparedStatement psUserAdd = null;
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

    public HashMap<String, ranks> rankSelectAll() throws SQLException {
        HashMap<String, ranks> ranksAll = new HashMap<>();
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
                ranksAll.put(rankName, rank);
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

    public boolean checkUserSelect (String username) throws SQLException {
        Connection con = null;
        PreparedStatement psUserSelect = null;
        ResultSet resultsUserSelect = null;
        int id = -1;

        try {
            con = this.getConnection();
            String sqlUserSelectQuery = "SELECT id FROM users WHERE username = ?;";
            psUserSelect = con.prepareStatement(sqlUserSelectQuery);
            psUserSelect.setString(1, username);
            resultsUserSelect = psUserSelect.executeQuery();
            while (resultsUserSelect.next()) {
                id = resultsUserSelect.getInt("id");;
            }
            if (id != -1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }

        finally {
            if (resultsUserSelect != null) {
                resultsUserSelect.close();
            }
            if (psUserSelect != null) {
                psUserSelect.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public DefaultTableModel userSelect(String searchBy, String value) throws SQLException {
        DefaultTableModel model = new DefaultTableModel(new String[] {"ID", "Username", "Rank", "Admin","Allowance"}, 0);

        Connection con = null;
        PreparedStatement psUserSelect = null;
        ResultSet resultsUserSelect = null;
        String id, username, rankid, rank, adminInt, admin, allowance = "";

        try {
            con = this.getConnection();

            String sqlUSelectQuery = "";
            switch (searchBy) {
                case "Username":
                    sqlUSelectQuery = "SELECT id, username, rank, admin, allowance FROM users WHERE username = ?;";
                    break;
                case "Rank":
                    sqlUSelectQuery = "SELECT id, username, rank, admin, allowance FROM users WHERE rank = ?;";
                    break;
                case "Admin":
                    sqlUSelectQuery = "SELECT id, username, rank, admin, allowance FROM users WHERE admin = ?;";
                    break;
                case "Allowance":
                    sqlUSelectQuery = "SELECT id, username, rank, admin, allowance FROM users WHERE allowance = ?;";
                    break;
                default:
                    sqlUSelectQuery = "SELECT id, username, rank, admin, allowance FROM users WHERE username = ?;";
                    break;
            }
            psUserSelect = con.prepareStatement(sqlUSelectQuery);
            psUserSelect.setString(1, value);
            resultsUserSelect = psUserSelect.executeQuery();
            while (resultsUserSelect.next()) {
                id = resultsUserSelect.getString("id");
                username = resultsUserSelect.getString("username");
                rankid = resultsUserSelect.getString("rank");
                rank = rankSelect(Integer.parseInt(rankid));
                adminInt = resultsUserSelect.getString("admin");
                if (adminInt.equals("1")) {
                    admin = "True";
                } else {
                    admin = "False";
                }
                allowance = resultsUserSelect.getString("allowance");
                model.addRow(new Object[] {id, username, rank, admin, allowance});
            }

        } catch (Exception e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }

        finally {
            if (resultsUserSelect != null) {
                resultsUserSelect.close();
            }
            if (psUserSelect != null) {
                psUserSelect.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return model;
    }

    public boolean userDelete(int id) throws SQLException {
        Connection con = null;
        PreparedStatement psUserDelete = null;
        int recordCounter = 0;
        Boolean resultDelete = false;

        try {
            con = this.getConnection();
            String sqlUserDelete = "DELETE FROM users WHERE id = ?;";
            psUserDelete = con.prepareStatement(sqlUserDelete);
            psUserDelete.setString(1, Integer.toString(id));
            recordCounter = psUserDelete.executeUpdate();
            if (recordCounter == 1) {
                resultDelete = true;
            } else {
                resultDelete = false;
            }
        } catch(Exception e) {
            throw new IllegalStateException("Adding user failed!",e);
        } finally {
            if (psUserDelete != null) {
                psUserDelete.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return resultDelete;
    }

    public boolean userUpdate(int id, String changeBy, String value) throws SQLException {
        Connection con = null;
        PreparedStatement psUserUpdate = null;
        int recordCount = 0;
        boolean resultUpdate = false;

        try {
            con = this.getConnection();
            String sqlUUpdateQuery = "";
            switch (changeBy) {
                case "Username":
                    sqlUUpdateQuery = "UPDATE users SET username = ? WHERE id = ?;";
                    break;
                case "Rank":
                    sqlUUpdateQuery = "UPDATE users SET rank = ? WHERE id = ?;";
                    break;
                case "Admin":
                    sqlUUpdateQuery = "UPDATE users SET admin = ? WHERE id = ?;";
                    break;
                case "Allowance":
                    sqlUUpdateQuery = "UPDATE users SET allowance = ? WHERE id = ?;";
                    break;
            }
            psUserUpdate = con.prepareStatement(sqlUUpdateQuery);
            psUserUpdate.setString(1, value);
            psUserUpdate.setString(2, Integer.toString(id));
            recordCount = psUserUpdate.executeUpdate();
            if (recordCount == 1) {
                resultUpdate = true;
            } else {
                resultUpdate = false;
            }
        } catch(Exception e) {
            throw new IllegalStateException("User Update Failed",e);
        } finally {
            if (psUserUpdate != null) {
                psUserUpdate.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return resultUpdate;
    }

    public DefaultTableModel holidayNotRevSelect() throws SQLException {
        DefaultTableModel model = new DefaultTableModel(new String[] {"ID", "User ID", "Username", "Holiday Start", "Holiday End", "Date Requested","Status"}, 0);

        Connection con = null;
        PreparedStatement psReviewHol = null;
        ResultSet resultsReviewHol = null;
        String id, userId, username, sDate, eDate, rDate, aDate, status = "";

        try {
            con = this.getConnection();
            String sqlReviewHolidayQuery = "SELECT id, user_id, holiday_start, holiday_end, date_requested, status FROM holidays WHERE status = 'Not Reviewed';";
            psReviewHol = con.prepareStatement(sqlReviewHolidayQuery);
            resultsReviewHol = psReviewHol.executeQuery();
            while (resultsReviewHol.next()) {
                id = resultsReviewHol.getString("id");
                userId = resultsReviewHol.getString("user_id");
                username = usernameSelect(userId);
                sDate = resultsReviewHol.getString("holiday_start");
                eDate = resultsReviewHol.getString("holiday_end");
                rDate = resultsReviewHol.getString("date_requested");
                status = resultsReviewHol.getString("status");
                model.addRow(new Object[] {id, userId, username, sDate, eDate, rDate, status});
            }

        } catch (Exception e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }

        finally {
            if (resultsReviewHol != null) {
                resultsReviewHol.close();
            }
            if (psReviewHol != null) {
                psReviewHol.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return model;
    }

    public HashMap<Integer, holiday> holidaySelectApproved() throws  SQLException {
        HashMap<Integer, holiday> approvedHolidays = new HashMap<>();
        Connection con = null;
        PreparedStatement psApproveHol = null;
        ResultSet resultsApproveHol = null;
        String id, userId, userRank, sDate, eDate, rDate, aDate, status = "";

        try {
            con = this.getConnection();
            String sqlApproveHolidayQuery = "SELECT id, user_id, holiday_start, holiday_end, date_requested, approval_date, status FROM holidays WHERE status = 'Accepted';";
            psApproveHol = con.prepareStatement(sqlApproveHolidayQuery);
            resultsApproveHol = psApproveHol.executeQuery();
            while (resultsApproveHol.next()) {
                id = resultsApproveHol.getString("id");
                userId = resultsApproveHol.getString("user_id");
                userRank = userRankSelect(userId);
                sDate = resultsApproveHol.getString("holiday_start");
                eDate = resultsApproveHol.getString("holiday_end");
                rDate = resultsApproveHol.getString("date_requested");
                aDate = resultsApproveHol.getString("approval_date");
                status = resultsApproveHol.getString("status");
                holiday holidayEntry = new holiday(Integer.parseInt(id), Integer.parseInt(userId), Integer.parseInt(userRank), rDate, sDate, eDate, aDate, status);
                approvedHolidays.put(Integer.parseInt(id), holidayEntry);
            }

        } catch (Exception e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }

        finally {
            if (resultsApproveHol != null) {
                resultsApproveHol.close();
            }
            if (psApproveHol != null) {
                psApproveHol.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return approvedHolidays;
    }

    public boolean holidayUpdate(String id, String cDate, String value) throws SQLException {
        Connection con = null;
        PreparedStatement psHolidayUpdate = null;
        int recordCount = 0;
        boolean resultUpdate = false;

        try {
            con = this.getConnection();
            String sqlHolidayUpdateQuery = "";
            sqlHolidayUpdateQuery = "UPDATE holidays SET approval_date = ? , status = ? WHERE id = ?;";
            psHolidayUpdate = con.prepareStatement(sqlHolidayUpdateQuery);
            psHolidayUpdate.setString(1, cDate);
            psHolidayUpdate.setString(2, value);
            psHolidayUpdate.setString(3, id);
            recordCount = psHolidayUpdate.executeUpdate();
            if (recordCount == 1) {
                resultUpdate = true;
            } else {
                resultUpdate = false;
            }
        } catch(Exception e) {
            throw new IllegalStateException("Holiday Update Failed",e);
        } finally {
            if (psHolidayUpdate != null) {
                psHolidayUpdate.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return resultUpdate;
    }

    public int countTotalAtRank(int rank) throws SQLException {
        Connection con = null;
        PreparedStatement psRankCount = null;
        int count = 0;
        ResultSet resultsRankCount = null;

        try {
            con = this.getConnection();
            String sqlRankCountQuery = "";
            sqlRankCountQuery = "SELECT COUNT(*) FROM users WHERE rank = ?;";
            psRankCount = con.prepareStatement(sqlRankCountQuery);
            psRankCount.setString(1, Integer.toString(rank));
            resultsRankCount = psRankCount.executeQuery();
            resultsRankCount.next();
            count = resultsRankCount.getInt(1);
        } catch(Exception e) {
            throw new IllegalStateException("Rank Count Failed",e);
        } finally {
            if (resultsRankCount != null) {
                resultsRankCount.close();
            }
            if (psRankCount != null) {
                psRankCount.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return count;
    }
}
