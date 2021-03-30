package mypackage;

import java.util.Date;

public class holidays {
    int id;
    int userId;
    int userRank;
    Date dateReq;
    Date holidayS;
    Date holidayE;
    Date dateApprove;
    String status;

    public holidays(int id, int userId, int userRank, Date dateReq, Date holidayS, Date holidayE, Date dateApprove, String status) {
        this.id = id;
        this.userId = userId;
        this.userRank = userRank;
        this.dateReq = dateReq;
        this.holidayS = holidayS;
        this.holidayE = holidayE;
        this.dateApprove = dateApprove;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getUserRank() {
        return userRank;
    }

    public Date getHolidayS() { return holidayS; }

    public Date getDateReq() { return dateReq; }

    public Date getHolidayE() { return holidayE; }

    public String getHolidayStatus() { return status; }
}
