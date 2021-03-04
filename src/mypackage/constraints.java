package mypackage;

/*
Hard Constraint
At least 5 Managers per day
At least 70 Crew per day
At least 15 trainers per day

Soft Constraints
Preference of days to work
staff can only work 5 days a week
 */

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class constraints {
    DatabaseQuerys dbquery = DatabaseQuerys.getDatabaseQuerysInst();
    public Boolean staffCheck(int rank, Date sDate, Date eDate) {
        HashMap<Integer, holiday>  approvedHolidays = new HashMap<>();
        int totalStaffForRank = 0;
        int totalRequired = 0;
        try {
            totalRequired = dbquery.rankSelectAmount(rank);
            totalStaffForRank = dbquery.countTotalAtRank(rank);
            approvedHolidays = dbquery.holidaySelectApproved();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        /*
        if start date before or during requested time off check end date if it is before it is fine if not
        add 1 to counter for people on holiday during time.
         */
        int amountOfCounter = 0;
        Iterator<Map.Entry<Integer, holiday>> iterate = approvedHolidays.entrySet().iterator();
        while (iterate.hasNext()) {
            Map.Entry<Integer, holiday> currentEntry = iterate.next();
            int key = currentEntry.getKey();
            holiday currHoliday = currentEntry.getValue();
            Date entrySDate = currHoliday.getHolidayS();
            Date entryEDate= currHoliday.getHolidayE();
            int entryRank = currHoliday.getUserRank();
            if ((entrySDate.before(eDate)) && (entryEDate.after(sDate)) && (entryRank == rank)) {
                amountOfCounter++;
            }
        }
        if ((totalStaffForRank - amountOfCounter) <= totalRequired) {
            return false;
        } else {
            return true;
        }
    }
}
