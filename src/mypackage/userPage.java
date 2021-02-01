package mypackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class userPage {
    user userInst = user.getInstance();
    public void userView() {
        JFrame uFrame = new JFrame("Holiday Scheduling - User Menu");
        uFrame.setPreferredSize(new Dimension(500,500));
        uFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel parentPanel = new JPanel();
        CardLayout cLayout = new CardLayout();
        parentPanel.setLayout(cLayout);
        uFrame.add(parentPanel);

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

        JPanel rHolPanel = new JPanel();
        JLabel lblHol = new JLabel("This is Holiday Request");
        rHolPanel.add(lblHol);
        parentPanel.add(rHolPanel, "rHolPanel");

        JPanel sHolPanel = new JPanel();
        JLabel lblStatus = new JLabel("This is Holiday Status");
        sHolPanel.add(lblStatus);
        parentPanel.add(sHolPanel, "sHolPanel");

        JPanel aInfoPanel = new JPanel();
        JLabel lblAccInfo = new JLabel("This is Account Info");
        aInfoPanel.add(lblAccInfo);
        parentPanel.add(aInfoPanel, "aInfoPanel");

        cLayout.show(parentPanel, "rHolPanel");
        uFrame.setVisible(true);

        accountInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cLayout.show(parentPanel, "aInfoPanel");
            }
        });

        statusHoliday.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cLayout.show(parentPanel,"sHolPanel");
            }
        });

        requestHoliday.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cLayout.show(parentPanel, "rHolPanel");
            }
        });

        accountInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        viewAdminPage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uFrame.dispose();
                adminPage rtrntoadmin = new adminPage();
                rtrntoadmin.adminView();
            }
        });

        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uFrame.dispose();
                loginPage rtrntologin = new loginPage();
                rtrntologin.loginView();
            }
        });
    }
}
