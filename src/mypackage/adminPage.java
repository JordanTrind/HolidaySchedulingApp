package mypackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

public class adminPage {
    DatabaseQuerys dbquery = DatabaseQuerys.getDatabaseQuerysInst();
    public void adminView() {
        ArrayList<ranks> allRanks = new ArrayList<ranks>();
        try {
            allRanks = dbquery.rankSelectAll();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

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

        JPanel addUserPanel = new JPanel();
        JLabel lblUsername = new JLabel("Username: ");
        JTextField txtUsername = new JTextField(16);
        JLabel lblRank = new JLabel("Rank: ");
        String[] ranksArr = new String[allRanks.size()];
        for (int i = 0; i < allRanks.size(); i++) {
            ranksArr[i] = allRanks.get(i).getRank();
        }
        JComboBox cmbRank = new JComboBox(ranksArr);
        JLabel lblAdmin = new JLabel("Admin: ");
        JCheckBox chkAdmin = new JCheckBox();
        JLabel lblAllowance = new JLabel("Allowance: ");
        JSpinner spnAllowance = new JSpinner(new SpinnerNumberModel(0, 0, 28, 1));
        JButton btnAddUser = new JButton("Add User");

        addUserPanel.add(lblUsername);
        addUserPanel.add(txtUsername);
        addUserPanel.add(lblRank);
        addUserPanel.add(cmbRank);
        addUserPanel.add(lblAdmin);
        addUserPanel.add(chkAdmin);
        addUserPanel.add(lblAllowance);
        addUserPanel.add(spnAllowance);
        addUserPanel.add(btnAddUser);

        parentPanel.add(addUserPanel, "addUserPanel");

        cLayout.show(parentPanel, "manageRequestPanel");
        aFrame.setVisible(true);

        addUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { cLayout.show(parentPanel, "addUserPanel"); }
        });

        btnAddUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = txtUsername.getText();
                int rank = 1;
                int admin = 0;
                if (chkAdmin.isSelected()) {
                    admin = 1;
                }
                int allowance = (int) spnAllowance.getValue();
                try {
                    dbquery.userAdd(username, rank, admin, allowance);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }
}
