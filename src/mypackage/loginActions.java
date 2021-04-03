package mypackage;

import java.sql.SQLException;

public class loginActions {
    public boolean loginActionFunc(String Username, char[] Password) {
        //gets the Database instance
        databaseQuerys dbquery = databaseQuerys.getDatabaseQuerysInst();

        //Default to false as user will not be admin majority of time
        boolean loginSuccess = false;
        try {
            //Call database instance to determine login success
            loginSuccess = dbquery.loginSelect(Username, Password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            return loginSuccess;
        }
    }
}
