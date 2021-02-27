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
    public Boolean staffCheck(int rank, String sDateString, String eDateString) {
        SimpleDateFormat dateForm = new SimpleDateFormat("yyyy-MM-dd");
        Date sDate = new Date();
        Date eDate = new Date();
        HashMap<Integer, holiday>  approvedHolidays = new HashMap<>();
        int totalStaffForRank = 0;
        int totalRequired = 0;
        switch (rank) {
            case 1:
                //Admin
                totalRequired = 0;
                break;
            case 2:
                //Worker
                totalRequired = 70;
                break;
            case 3:
                //Manager
                totalRequired = 5;
                break;
            case 4:
                //Trainer
                totalRequired = 15;
                break;
        }
        try {
            sDate = dateForm.parse(sDateString);
            eDate = dateForm.parse(eDateString);
        } catch (ParseException parseException) {
            parseException.printStackTrace();
        }
        try {
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
            Date entrySDate = new Date();
            Date entryEDate = new Date();
            String entrySDateString = currHoliday.getHolidayS();
            String entryEDateString = currHoliday.getHolidayE();
            int entryRank = currHoliday.getUserRank();
            try {
                entrySDate = dateForm.parse(entrySDateString);
                entryEDate = dateForm.parse(entryEDateString);
            } catch (ParseException parseException) {
                parseException.printStackTrace();
            }
            if ((entrySDate.before(eDate)) && (entryEDate.after(sDate)) && (entryRank == rank)) {
                amountOfCounter++;
            }
        }
        if ((totalStaffForRank - amountOfCounter) < totalRequired) {
            return false;
        } else {
            return true;
        }
    }
}
