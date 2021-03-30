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

import java.sql.Array;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class constraints {
    DatabaseQuerys dbquery = DatabaseQuerys.getDatabaseQuerysInst();
    public Boolean staffCheck(int rank, Date sDate, Date eDate) {
        HashMap<Integer, holidays>  approvedHolidays = approvedHolidays(sDate, eDate);
        int totalStaffForRank = totalStaffAtRank(rank);
        int totalRequired = totalRequired(rank);

        /*
        if start date before or during requested time off check end date if it is before it is fine if not
        add 1 to counter for people on holiday during time.
         */
        int amountOfCounter = 0;
        Iterator<Map.Entry<Integer, holidays>> iterate = approvedHolidays.entrySet().iterator();
        while (iterate.hasNext()) {
            Map.Entry<Integer, holidays> currentEntry = iterate.next();
            int key = currentEntry.getKey();
            holidays currHolidays = currentEntry.getValue();
            Date entrySDate = currHolidays.getHolidayS();
            Date entryEDate= currHolidays.getHolidayE();
            int entryRank = currHolidays.getUserRank();
            if ((entrySDate.compareTo(eDate) <= 0) && (entryEDate.compareTo(sDate) >= 0) && (entryRank == rank)) {
                amountOfCounter++;
            }
        }
        if ((totalStaffForRank - amountOfCounter) <= totalRequired) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean algoCheck(int rank, Date sDate, Date eDate, HashMap<Integer, holidays> addedHolidays) {
        HashMap<Integer, holidays>  approvedHolidays = approvedHolidays(sDate, eDate);
        approvedHolidays.putAll(addedHolidays);
        int totalStaffForRank = totalStaffAtRank(rank);
        int totalRequired = totalRequired(rank);

        /*
        if start date before or during requested time off check end date if it is before it is fine if not
        add 1 to counter for people on holiday during time.
         */
        int amountOfCounter = 0;
        Iterator<Map.Entry<Integer, holidays>> iterate = approvedHolidays.entrySet().iterator();
        while (iterate.hasNext()) {
            Map.Entry<Integer, holidays> currentEntry = iterate.next();
            int key = currentEntry.getKey();
            holidays currHolidays = currentEntry.getValue();
            Date entrySDate = currHolidays.getHolidayS();
            Date entryEDate= currHolidays.getHolidayE();
            int entryRank = currHolidays.getUserRank();
            if ((entrySDate.compareTo(eDate) <= 0) && (entryEDate.compareTo(sDate) >= 0) && (entryRank == rank)) {
                amountOfCounter++;
            }
        }
        if ((totalStaffForRank - amountOfCounter) <= totalRequired) {
            return false;
        } else {
            return true;
        }
    }

    public HashMap<Date, Date> alternativeHoliday(int rank, Date sDate, Date eDate) {
        HashMap<Date, Date> alternativeDates = new HashMap<>();
        HashMap<Integer, holidays>  approvedHolidays = approvedHolidays(sDate, eDate);
        int totalStaffForRank = totalStaffAtRank(rank);
        int totalRequired = totalRequired(rank);
        Date tmpSDate = sDate;
        Date tmpEDate = sDate;
        while (tmpEDate.compareTo(eDate) <= 0) {
            int amountOfCounter = 0;
            Iterator<Map.Entry<Integer, holidays>> iterate = approvedHolidays.entrySet().iterator();
            while (iterate.hasNext()) {
                Map.Entry<Integer, holidays> currentEntry = iterate.next();
                int key = currentEntry.getKey();
                holidays currHolidays = currentEntry.getValue();
                Date entrySDate = currHolidays.getHolidayS();
                Date entryEDate = currHolidays.getHolidayE();
                int entryRank = currHolidays.getUserRank();
                if ((entrySDate.compareTo(tmpEDate) <= 0) && (entryEDate.compareTo(tmpSDate) >= 0) && (entryRank == rank)) {
                    amountOfCounter++;
                }
            }
            if ((totalStaffForRank - amountOfCounter) <= totalRequired) {
                tmpEDate = incrimentDate(tmpEDate);
                tmpSDate = tmpEDate;
            } else {
                alternativeDates.put(tmpSDate, tmpEDate);
                tmpEDate = incrimentDate(tmpEDate);
            }
        }
        return alternativeDates;
    }

    public HashMap<Integer, holidays> approvedHolidays(Date sDate, Date eDate) {
        HashMap<Integer, holidays>  approvedHolidaysMap = new HashMap<>();
        SimpleDateFormat dateForm = new SimpleDateFormat("yyyy-MM-dd");
        String sDateStr = dateForm.format(sDate);
        String eDateStr = dateForm.format(eDate);
        try {
            approvedHolidaysMap = dbquery.holidaySelectApproved(sDateStr, eDateStr);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return approvedHolidaysMap;
    }

    private int totalStaffAtRank(int rank) {
        int totalStaffForRank = 0;
        try {
            totalStaffForRank = dbquery.countTotalAtRank(rank);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return totalStaffForRank;
    }

    private int totalRequired(int rank) {
        int totalRequired = 0;
        try {
            totalRequired = dbquery.rankSelectAmount(rank);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return totalRequired;
    }

    private Date incrimentDate(Date originalDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(originalDate);
        cal.add(Calendar.DATE, 1);
        return cal.getTime();
    }
}
