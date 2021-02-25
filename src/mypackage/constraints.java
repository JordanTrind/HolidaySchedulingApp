package mypackage;

/*
Hard Constraint
At least 5 Managers per day
At least 70 Crew per day
At least 14 trainers per day

Soft Constraints
Preference of days to work
staff can only work 5 days a week
 */

import java.sql.SQLException;
import java.util.HashMap;

public class constraints {
    DatabaseQuerys dbquery = DatabaseQuerys.getDatabaseQuerysInst();
    public Boolean staffCheck(int rank, String sDate, String eDate) {
        Boolean statusCheck = false;
        HashMap<Integer, holiday>  approvedHolidays = new HashMap<>();
        try {
            approvedHolidays = dbquery.holidaySelectApproved();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        /*
        if start date before or during requested time off check end date if it is before it is fine if not
        add 1 to counter for people on holiday during time.
         */

    }
}
