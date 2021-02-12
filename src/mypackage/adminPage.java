package mypackage;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;

public class adminPage {
    public void adminView() {
        JFrame aFrame = new JFrame("Holiday Scheduling - Admin Menu");
        aFrame.setSize(new Dimension(600,500));
        aFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel parentPanel = new JPanel();
        CardLayout cLayout = new CardLayout();
        parentPanel.setLayout(cLayout);
        aFrame.add(parentPanel);

        JMenuBar aMenuBar = new JMenuBar();
        JMenu aMenu = new JMenu("Menu");
        aMenuBar.add(aMenu);
        JMenuItem addUser = new JMenuItem("Add User");
        aMenu.add(addUser);
        JMenuItem editUser = new JMenuItem("Edit User");
        aMenu.add(editUser);
        aMenu.addSeparator();
        JMenuItem addConstraint = new JMenuItem("Add Constraint");
        aMenu.add(addConstraint);
        JMenuItem editConstraint = new JMenuItem("Edit Constraint");
        aMenu.add(editConstraint);
        aMenu.addSeparator();
        JMenuItem manageRequests = new JMenuItem("Manage Holiday Requests");
        aMenu.add(manageRequests);
        JMenuItem generateSchedule = new JMenuItem("Generate Holiday Schedule");
        aMenu.add(generateSchedule);
        aMenu.addSeparator();
        JMenuItem viewUserPage = new JMenuItem("Access User Page");
        aMenu.add(viewUserPage);
        aMenu.addSeparator();
        JMenuItem logout = new JMenuItem("Logout");
        aMenu.add(logout);
        aFrame.setJMenuBar(aMenuBar);

        aFrame.setVisible(true);
    }
}
