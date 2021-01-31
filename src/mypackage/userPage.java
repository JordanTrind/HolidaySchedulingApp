package mypackage;

import javax.swing.*;
import java.awt.*;

public class userPage {
    user userInst = user.getInstance();
    public void userView() {
        JFrame uFrame = new JFrame("Holiday Scheduling - User Menu");
        uFrame.setPreferredSize(new Dimension(500,500));
        uFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar uMenuBar = new JMenuBar();
        JMenu uMenu = new JMenu("Menu");
        uMenuBar.add(uMenu);
        JMenuItem requestHoliday = new JMenuItem("Request Holiday");
        uMenu.add(requestHoliday);
        JMenuItem statusHoliday = new JMenuItem("Holiday Status");
        uMenu.add(statusHoliday);
        uMenu.addSeparator();
        JMenuItem accountInfo = new JMenuItem("Account Information");
        uMenu.add(accountInfo);
        JMenuItem viewAdminPage = new JMenuItem("Access Admin Page");
        uMenu.add(viewAdminPage);
        if (userInst.getUserAdmin()) {
            viewAdminPage.setVisible(true);
        } else {
            viewAdminPage.setVisible(false);
        }
        uMenu.addSeparator();
        JMenuItem logout = new JMenuItem("Logout");
        uMenu.add(logout);

        uFrame.setJMenuBar(uMenuBar);
        uFrame.setVisible(true);
    }
}
