package mypackage;

import javax.swing.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class userActions {
    user userInst = user.getInstance();
    databaseQuerys dbquery = databaseQuerys.getDatabaseQuerysInst();

    public void passwordChangeFunc(char[] firstPass, char[] secondPass) {
        passwordHandler pCheck = new passwordHandler();
        switch (pCheck.passwordReqCheck(firstPass, secondPass)) {
            case "passwordmatcherror":
                JOptionPane.showMessageDialog(null, "Passwords do not match", "Password Error", JOptionPane.INFORMATION_MESSAGE);
                break;
            case "passwordreqerror":
                JOptionPane.showMessageDialog(null, "Passwords must be 8 digits long have an uppercase and lowercase letter and contain a number", "Password Error", JOptionPane.INFORMATION_MESSAGE);
                break;
            case "success":
                int id = userInst.getUserID();
                String newPass = pCheck.newPassword(firstPass);
                try {
                    if (dbquery.passwordUpdate(id, newPass)) {

                    } else {
                        JOptionPane.showMessageDialog(null, "Error changing password", "Password Error", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception exec) {
                    new IllegalStateException("Password update failed at Database Query", exec);
                }
                JOptionPane.showMessageDialog(null, "Password successfully changed", "Password Change Success", JOptionPane.INFORMATION_MESSAGE);
                break;
            default:
                JOptionPane.showMessageDialog(null, "Error changing password", "Password Error", JOptionPane.INFORMATION_MESSAGE);
                break;
        }
    }

    public void requestHolidayFunc(String sDateStr, String eDateStr) {
        SimpleDateFormat dateForm = new SimpleDateFormat("yyyy-MM-dd");
        constraints constraint = new constraints();
        Date sDate = new Date();
        Date eDate = new Date();
        Date cDate = new Date();
        String cDateStr = dateForm.format(cDate);
        int allowance = userInst.getUserAllowance();
        try {
            sDate = dateForm.parse(sDateStr);
            eDate = dateForm.parse(eDateStr);
        } catch (ParseException parseException) {
            parseException.printStackTrace();
        }
        int dateDiff = (int) ((eDate.getTime() - sDate.getTime()) / (1000 * 60 * 60 * 24));
        int newAllowance = (int) (allowance - (dateDiff + 1));

        if ((newAllowance >= 0) && (sDate.after(cDate)) && (eDate.after(cDate)) && (eDate.after(sDate) || (sDate.compareTo(eDate) == 0))) {
            try {
                if (constraint.staffCheck(userInst.getUserRankId(), sDate, eDate) == false) {
                    HashMap<Date, Date> alternativeHolidays = constraint.alternativeHoliday(userInst.getUserRankId(), sDate, eDate);
                    String stringAlternates = "";
                    Iterator<Map.Entry<Date, Date>> iterate = alternativeHolidays.entrySet().iterator();
                    while (iterate.hasNext()) {
                        Map.Entry<Date, Date> currentEntry = iterate.next();
                        Date startDate = currentEntry.getKey();
                        Date endDate = currentEntry.getValue();
                        String entrySDateStr = dateForm.format(startDate);
                        String entryEDateStr = dateForm.format(endDate);
                        stringAlternates = stringAlternates + ("\n"+ entrySDateStr + " - " + entryEDateStr);
                    }
                    String alternateMessage = ("There are too many staff off during this time period \nAlternate holiday dates are shown below:" + stringAlternates);
                    JOptionPane.showMessageDialog(null, alternateMessage, "Holiday Error", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                if (dbquery.holidayAdd(userInst.getUserID(), cDateStr, sDateStr, eDateStr)) {
                    JOptionPane.showMessageDialog(null, "Requested holiday has been entered", "Holiday Accepted", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Error requesting holiday try again!", "Holiday Error", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                if (dbquery.allowanceUpdate(userInst.getUserID(), newAllowance)) {
                    userInst.setUserAllowance(newAllowance);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Error requesting holiday please check holiday allowance and dates entered!", "Holiday Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
