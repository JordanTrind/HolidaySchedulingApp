package mypackage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class adminActions {
    databaseQuerys dbquery = databaseQuerys.getDatabaseQuerysInst();
    user userInst = user.getInstance();
    DefaultTableModel acceptedHolidaysModel = new DefaultTableModel(new String[] {"ID", "User ID", "Holiday Start", "Holiday End", "Date Requested","Status"}, 0);
    DefaultTableModel rejectedHolidaysModel = new DefaultTableModel(new String[] {"ID", "User ID", "Holiday Start", "Holiday End", "Date Requested","Status"}, 0);
    HashMap<Integer, holidays> addedToApprovedHolidays;
    HashMap<Integer, holidays> rejectedHolidays;

    public void addUserFunc(String username, int rank, int admin, int allowance) {
        boolean userSelectRes = false;
        try {
            userSelectRes = dbquery.checkUserSelect(username);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if (username.equals("") || userSelectRes) {
            JOptionPane.showMessageDialog(null, "Username already exists or no username entered!", "Add User Error", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        try {
            passwordHandler pwDefault = new passwordHandler();
            char[] defaultPass = {'C','h','a','n','g','e','M','e','1'};
            String password = pwDefault.newPassword(defaultPass);
            boolean result = dbquery.userAdd(username, password, rank, admin, allowance);
            if (result) {
                JOptionPane.showMessageDialog(null, "User added!", "Add User", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error adding user!", "Add User Error", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public DefaultTableModel searchUserFunc(String searchBy, String value) {
        DefaultTableModel userModel = null;
        try {
            userModel = dbquery.userSelect(searchBy, value);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userModel;
    }

    public void deleteUserFunc (int id) {
        if (id == userInst.getUserID()) {
            JOptionPane.showMessageDialog(null, "You cannot delete yourself!", "Delete User", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        try {
            if(dbquery.userDelete(id)) {
                JOptionPane.showMessageDialog(null, "User deleted!", "Delete User", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error Deleting User!", "Delete User", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void sendPasswordResetFunc (int id) {
        if (id == userInst.getUserID()) {
            JOptionPane.showMessageDialog(null, "Use the user page to change your own password!", "User Password Reset", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        try {
            passwordHandler pwDefault = new passwordHandler();
            char[] defaultPass = {'C','h','a','n','g','e','M','e','1'};
            String password = pwDefault.newPassword(defaultPass);
            if(dbquery.passwordUpdate(id, password)) {
                JOptionPane.showMessageDialog(null, "Password reset to default: ChangeMe1", "User Password Reset", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error Resetting User Password!", "User Password Reset", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void editUserFunc(int id, String changeBy, String value) {
        if (changeBy.equals("Allowance")) {
            try {
                Integer.parseInt(value);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Allowance value must be numeric", "User Update", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }
        if (changeBy.equals("Username")) {
            try {
                if (dbquery.checkUserSelect(value)) {
                    JOptionPane.showMessageDialog(null, "Username already exists!", "User Update", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        try {
            if(dbquery.userUpdate(id, changeBy, value)) {
                JOptionPane.showMessageDialog(null, "User value updated", "User Update", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error updating user details!", "User Update", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void addRankFunc(String rankName, String rankAmount) {
        if (rankName.equals("")) {
            JOptionPane.showMessageDialog(null, "Error adding rank no value entered for rank name!", "Add Rank", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        try {
            HashMap<String, ranks> ranksHashMap = dbquery.rankSelectAll();
            if (ranksHashMap.containsKey(rankName)) {
                JOptionPane.showMessageDialog(null, "Error adding rank name already exists!", "Add Rank", JOptionPane.INFORMATION_MESSAGE);
                return;
            } else {
                if (dbquery.rankAdd(rankName, rankAmount)) {
                    JOptionPane.showMessageDialog(null, "Rank Added!", "Add Rank", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Error adding rank!", "Add Rank", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteRankFunc(String rankName) {
        try {
            HashMap<String, ranks> ranksHashMap = dbquery.rankSelectAll();
            ranks currentRank = ranksHashMap.get(rankName);
            if(dbquery.rankDelete(currentRank.getRankID())) {
                JOptionPane.showMessageDialog(null, "Rank Deleted!", "Delete Rank", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error deleting Rank!", "Delete Rank", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void editRankAmountFunc (String rankName, String amountNeeded) {
        try {
            int amountNeededInt = Integer.parseInt(amountNeeded);
            HashMap<String, ranks> ranksHashMap = dbquery.rankSelectAll();
            ranks currentRank = ranksHashMap.get(rankName);
            if(dbquery.rankamountUpdate(currentRank.getRankID(), amountNeededInt)) {
                JOptionPane.showMessageDialog(null, "Rank Amount Updated!", "Update Rank", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error updating rank amount!", "Update Rank", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public DefaultTableModel acceptDenyHolidayFunc(int id, int userid, Date sDate, Date eDate, String value, String orderBy, String ascOrDesc) {
        constraints constraint = new constraints();
        Boolean executeUpdate = true;
        DefaultTableModel holModel = null;
        SimpleDateFormat dateForm = new SimpleDateFormat("yyyy-MM-dd");
        Date cDate = new Date();
        String cDateStr = dateForm.format(cDate);
        int rankId = -1;
        String rankName = "";

        if (value.equals("Accepted")) {
            try {
                rankId = dbquery.userRankSelect(userid);
                rankName = dbquery.rankSelectName(rankId);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            if (constraint.staffCheck(rankId, sDate, eDate) == false) {
                String messageBuilder = String.format("There are too many %s off during the time period shown, continuing may cause scheduling issues. Continue?", rankName);
                int reply = JOptionPane.showConfirmDialog(null, messageBuilder, "Holiday Error", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.NO_OPTION) {
                    executeUpdate = false;
                }
            }
        }
        try {
            if (executeUpdate) {
                dbquery.holidayUpdate(id, cDateStr, value);
            }
            if  (value.equals("Rejected")) {
                int allowance = dbquery.userAllowanceSelect(userid);
                int dateDiff = (int) ((eDate.getTime() - sDate.getTime()) / (1000 * 60 * 60 * 24));
                allowance += (dateDiff + 1);
                dbquery.allowanceUpdate(userid, allowance);
            }
            holModel = dbquery.holidayNotRevSelect(orderBy, ascOrDesc);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return holModel;
    }

    public void generateScheduleFunc(String sDateStr, String eDateStr) {
        myAlgo algorithm = new myAlgo();
        algorithm.algoBegin(sDateStr, eDateStr);
        HashMap<Integer, holidays> approvedHolidays = algorithm.getapprovedHolidays();
        addedToApprovedHolidays = algorithm.getAddedToApprovedHolidays();
        rejectedHolidays = algorithm.getRejectedHolidays();
        acceptedHolidaysModel = new DefaultTableModel(new String[] {"ID", "User ID", "Holiday Start", "Holiday End", "Date Requested","Status"}, 0);
        rejectedHolidaysModel = new DefaultTableModel(new String[] {"ID", "User ID", "Holiday Start", "Holiday End", "Date Requested","Status"}, 0);

        Iterator<Map.Entry<Integer, holidays>> iterate = approvedHolidays.entrySet().iterator();
        while (iterate.hasNext()) {
            Map.Entry<Integer, holidays> currentEntry = iterate.next();
            holidays currHoliday = currentEntry.getValue();
            int id = currHoliday.getId();
            int userId = currHoliday.getUserId();
            Date sDate = currHoliday.getHolidayS();
            Date eDate = currHoliday.getHolidayE();
            Date rDate = currHoliday.getDateReq();
            String status = currHoliday.getHolidayStatus();
            acceptedHolidaysModel.addRow(new Object[]{id, userId, sDate, eDate, rDate, status});
        }

        iterate = rejectedHolidays.entrySet().iterator();
        while (iterate.hasNext()) {
            Map.Entry<Integer, holidays> currentEntry = iterate.next();
            holidays currHoliday = currentEntry.getValue();
            int id = currHoliday.getId();
            int userId = currHoliday.getUserId();
            Date sDate = currHoliday.getHolidayS();
            Date eDate = currHoliday.getHolidayE();
            Date rDate = currHoliday.getDateReq();
            String status = currHoliday.getHolidayStatus();
            rejectedHolidaysModel.addRow(new Object[]{id, userId, sDate, eDate, rDate, status});
        }
    }

    public DefaultTableModel getAcceptedHolidaysModel() {
        return acceptedHolidaysModel;
    }

    public DefaultTableModel getRejectedHolidaysModel() {
        return rejectedHolidaysModel;
    }

    public void acceptScheduleFunc () {
        SimpleDateFormat dateForm = new SimpleDateFormat("yyyy-MM-dd");
        Date cDate = new Date();
        String cDateStr = dateForm.format(cDate);

        Iterator<Map.Entry<Integer, holidays>> iterate = addedToApprovedHolidays.entrySet().iterator();
        while (iterate.hasNext()) {
            Map.Entry<Integer, holidays> currentEntry = iterate.next();
            holidays currHoliday = currentEntry.getValue();
            int id = currHoliday.getId();
            boolean result = false;
            try {
                result = dbquery.holidayUpdate(id, cDateStr,"Accepted");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        iterate = rejectedHolidays.entrySet().iterator();
        while (iterate.hasNext()) {
            Map.Entry<Integer, holidays> currentEntry = iterate.next();
            holidays currHoliday = currentEntry.getValue();
            int id = currHoliday.getId();
            boolean result = false;
            try {
                result = dbquery.holidayUpdate(id, cDateStr,"Rejected");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        JOptionPane.showMessageDialog(null, "Holiday schedule approved!", "Generate Holiday Schedule", JOptionPane.INFORMATION_MESSAGE);
    }
}
