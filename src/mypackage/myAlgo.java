package mypackage;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.*;

public class myAlgo {
    /*
    Approved Holidays go into one Hashmap
    Order next set of holidays by date requested

    Take element of unapproved check using staff check if false add to approved if false add to rejected

    Start adding holidays to hashmap carry on checking until all holidays checked
     */
    constraints constraintCheck = new constraints();
    DatabaseQuerys dbquery = DatabaseQuerys.getDatabaseQuerysInst();
    public HashMap<Integer, holidays> algoBegin(String sDateStr, String eDateStr) {
        SimpleDateFormat dateForm = new SimpleDateFormat("yyyy-MM-dd");
        Date sDate = null;
        Date eDate = null;
        try {
            sDate = dateForm.parse(sDateStr);
            eDate = dateForm.parse(eDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        HashMap<Integer, holidays> approvedHolidays = constraintCheck.approvedHolidays(sDate, eDate);
        HashMap<Integer, holidays> unapprovedHolidays = null;
        try {
            unapprovedHolidays = dbquery.holidaySelectSchedule(sDateStr, eDateStr);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        HashMap<Integer, holidays> addedToApprovedHolidays = new HashMap<Integer, holidays>();
        HashMap<Integer, holidays> rejectedHolidays = new HashMap<Integer, holidays>();
        Iterator<Map.Entry<Integer, holidays>> iterate = unapprovedHolidays.entrySet().iterator();
        while (iterate.hasNext()) {
            Map.Entry<Integer, holidays> currentEntry = iterate.next();
            int currentKey = currentEntry.getKey();
            holidays currentHoliday = currentEntry.getValue();
            Date currentStartDate = currentHoliday.getHolidayS();
            Date currentEndDate = currentHoliday.getHolidayE();
            int currentRank = currentHoliday.getUserRank();
            if (constraintCheck.algoCheck(currentRank, currentStartDate, currentEndDate, addedToApprovedHolidays)) {
               addedToApprovedHolidays.put(currentKey, currentHoliday);
            } else {
               rejectedHolidays.put(currentKey, currentHoliday);
            }
        }
        approvedHolidays.putAll(addedToApprovedHolidays);
        return approvedHolidays;
    }
}
