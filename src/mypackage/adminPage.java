package mypackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class adminPage {
    DatabaseQuerys dbquery = DatabaseQuerys.getDatabaseQuerysInst();
    HashMap<String, ranks> allRanks = new HashMap<>();
    public void adminView() {
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

        JPanel addUserPanel = new JPanel(new GridBagLayout());
        JLabel lblUsername = new JLabel("Username: ");
        JTextField txtUsername = new JTextField(16);
        JLabel lblRank = new JLabel("Rank: ");
        String[] ranksArr = allRanks.keySet().toArray(new String[0]);
        JComboBox cmbRank = new JComboBox(ranksArr);
        JLabel lblAdmin = new JLabel("Admin: ");
        JCheckBox chkAdmin = new JCheckBox();
        JLabel lblAllowance = new JLabel("Allowance: ");
        JSpinner spnAllowance = new JSpinner(new SpinnerNumberModel(28, 0, 28, 1));
        JButton btnAddUser = new JButton("Add User");

        GridBagConstraints lblUsernameGrid = new GridBagConstraints();
        lblUsernameGrid.weightx = 1;
        lblUsernameGrid.gridx = 0;
        lblUsernameGrid.gridy = 0;
        addUserPanel.add(lblUsername, lblUsernameGrid);

        GridBagConstraints txtUsernameGrid = new GridBagConstraints();
        lblUsernameGrid.weightx = 1;
        lblUsernameGrid.gridx = 1;
        lblUsernameGrid.gridy = 0;
        addUserPanel.add(txtUsername, txtUsernameGrid);

        GridBagConstraints lblRankGrid = new GridBagConstraints();
        lblRankGrid.weightx = 1;
        lblRankGrid.gridx = 0;
        lblRankGrid.gridy = 1;
        addUserPanel.add(lblRank, lblRankGrid);

        GridBagConstraints cmbRankGrid = new GridBagConstraints();
        cmbRankGrid.weightx = 1;
        cmbRankGrid.gridx = 1;
        cmbRankGrid.gridy = 1;
        addUserPanel.add(cmbRank, cmbRankGrid);

        GridBagConstraints lblAdminGrid = new GridBagConstraints();
        lblAdminGrid.weightx = 1;
        lblAdminGrid.gridx = 0;
        lblAdminGrid.gridy = 2;
        addUserPanel.add(lblAdmin, lblAdminGrid);

        GridBagConstraints chkAdminGrid = new GridBagConstraints();
        chkAdminGrid.weightx = 1;
        chkAdminGrid.gridx = 1;
        chkAdminGrid.gridy = 2;
        addUserPanel.add(chkAdmin, chkAdminGrid);

        GridBagConstraints lblAllowanceGrid = new GridBagConstraints();
        lblAllowanceGrid.weightx = 1;
        lblAllowanceGrid.gridx = 0;
        lblAllowanceGrid.gridy = 3;
        addUserPanel.add(lblAllowance, lblAllowanceGrid);

        GridBagConstraints spnAllowanceGrid = new GridBagConstraints();
        spnAllowanceGrid.weightx = 1;
        spnAllowanceGrid.gridx = 1;
        spnAllowanceGrid.gridy = 3;
        addUserPanel.add(spnAllowance, spnAllowanceGrid);

        GridBagConstraints btnAddUserGrid = new GridBagConstraints();
        btnAddUserGrid.weightx = 1;
        btnAddUserGrid.gridx = 1;
        btnAddUserGrid.gridy = 4;
        addUserPanel.add(btnAddUser, btnAddUserGrid);

        parentPanel.add(addUserPanel, "addUserPanel");

        cLayout.show(parentPanel, "manageRequestPanel");
        aFrame.setVisible(true);

        addUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { cLayout.show(parentPanel, "addUserPanel"); }
        });

        viewUserPage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aFrame.dispose();
                userPage rtrntouser = new userPage();
                rtrntouser.userView();
            }
        });

        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aFrame.dispose();
                loginPage rtrntologin = new loginPage();
                rtrntologin.loginView();
            }
        });

        btnAddUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = txtUsername.getText();
                ranks selectedRank = allRanks.get(cmbRank.getSelectedItem());
                int rank = selectedRank.getRankID();
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
