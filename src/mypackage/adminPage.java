package mypackage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class adminPage {
    DatabaseQuerys dbquery = DatabaseQuerys.getDatabaseQuerysInst();
    user userInst = user.getInstance();
    HashMap<String, ranks> allRanks = new HashMap<>();
    public void adminView() {
        try {
            allRanks = dbquery.rankSelectAll();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String[] ranksArr = allRanks.keySet().toArray(new String[0]);

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

        JPanel editUserPanel = new JPanel(new GridBagLayout());
        JLabel lblSearch = new JLabel("Search by: ");
        String[] arrSearch = {"Username", "Rank", "Admin", "Allowance"};
        JComboBox cmbSearch = new JComboBox(arrSearch);
        JLabel lblSearchWhere = new JLabel("Where it equals: ");
        JComboBox cmbRankSearch = new JComboBox(ranksArr);
        String[] arrAdmin = {"True", "False"};
        JComboBox cmbAdminSearch = new JComboBox(arrAdmin);
        JTextField txtSearch = new JTextField(16);
        JButton btnSearchUsers = new JButton("Search");
        JTable tblUsers = new JTable();
        JScrollPane jspaneUserTbl = new JScrollPane(tblUsers);
        JButton btnPasswordReset = new JButton("Reset Password");
        JButton btnDeleteUser = new JButton("Delete User");
        JButton btnConfirmEdits = new JButton("Confirm Changes");

        GridBagConstraints lblSearchGrid = new GridBagConstraints();
        lblSearchGrid.weightx = 1;
        lblSearchGrid.gridx = 0;
        lblSearchGrid.gridy = 0;
        editUserPanel.add(lblSearch, lblSearchGrid);

        GridBagConstraints cmbSearchGrid = new GridBagConstraints();
        cmbSearchGrid.weightx = 1;
        cmbSearchGrid.gridx = 1;
        cmbSearchGrid.gridy = 0;
        editUserPanel.add(cmbSearch, cmbSearchGrid);

        GridBagConstraints lblSearchWhereGrid = new GridBagConstraints();
        lblSearchWhereGrid.weightx = 1;
        lblSearchWhereGrid.gridx = 2;
        lblSearchWhereGrid.gridy = 0;
        editUserPanel.add(lblSearchWhere, lblSearchWhereGrid);

        GridBagConstraints txtSearchGrid = new GridBagConstraints();
        txtSearchGrid.weightx = 1;
        lblSearchGrid.gridx = 3;
        lblSearchGrid.gridy = 0;
        editUserPanel.add(txtSearch, txtSearchGrid);

        GridBagConstraints cmbRankSearchGrid = new GridBagConstraints();
        cmbRankSearchGrid.weightx = 1;
        cmbRankSearchGrid.gridx = 3;
        cmbRankSearchGrid.gridy = 0;
        editUserPanel.add(cmbRankSearch, cmbRankSearchGrid);
        cmbRankSearch.setVisible(false);

        GridBagConstraints cmbAdminSearchGrid = new GridBagConstraints();
        cmbAdminSearchGrid.weightx = 1;
        cmbAdminSearchGrid.gridx = 3;
        cmbAdminSearchGrid.gridy = 0;
        editUserPanel.add(cmbAdminSearch, cmbAdminSearchGrid);
        cmbAdminSearch.setVisible(false);

        GridBagConstraints jspaneUsersGrid = new GridBagConstraints();
        jspaneUsersGrid.weightx = 1;
        jspaneUsersGrid.gridx = 0;
        jspaneUsersGrid.gridy = 1;
        jspaneUsersGrid.gridwidth = 4;
        editUserPanel.add(jspaneUserTbl, jspaneUsersGrid);

        GridBagConstraints btnSearchUsersGrid = new GridBagConstraints();
        btnSearchUsersGrid.weightx = 1;
        btnSearchUsersGrid.gridx = 0;
        btnSearchUsersGrid.gridy = 2;
        editUserPanel.add(btnSearchUsers, btnSearchUsersGrid);

        GridBagConstraints btnPasswordResetGrid = new GridBagConstraints();
        btnPasswordResetGrid.weightx = 1;
        btnPasswordResetGrid.gridx = 1;
        btnPasswordResetGrid.gridy = 2;
        editUserPanel.add(btnPasswordReset, btnPasswordResetGrid);

        GridBagConstraints btnDeleteUserGrid = new GridBagConstraints();
        btnDeleteUserGrid.weightx = 1;
        btnDeleteUserGrid.gridx = 2;
        btnDeleteUserGrid.gridy = 2;
        editUserPanel.add(btnDeleteUser, btnDeleteUserGrid);

        GridBagConstraints btnConfirmEditsGrid = new GridBagConstraints();
        btnConfirmEditsGrid.weightx = 1;
        btnConfirmEditsGrid.gridx = 3;
        btnConfirmEditsGrid.gridy = 2;
        editUserPanel.add(btnConfirmEdits, btnConfirmEditsGrid);
        parentPanel.add(editUserPanel, "editUserPanel");

        cLayout.show(parentPanel, "manageRequestPanel");
        aFrame.setVisible(true);

        addUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { cLayout.show(parentPanel, "addUserPanel"); }
        });

        editUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { cLayout.show(parentPanel, "editUserPanel"); }
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
                addUserFunc(username, rank, admin, allowance);
            }
        });

        btnSearchUsers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchBy = cmbSearch.getSelectedItem().toString();
                String value = "";
                if (searchBy.equals("Username") || searchBy.equals("Allowance")) {
                    value = txtSearch.getText();
                }
                if (searchBy.equals("Admin")) {
                    value = cmbAdminSearch.getSelectedItem().toString();
                    if (value.equals("True")) {
                        value = "1";
                    } else {
                        value = "0";
                    }
                }
                if (searchBy.equals("Rank")) {
                    ranks selectedRank = allRanks.get(cmbRankSearch.getSelectedItem());
                    value = Integer.toString(selectedRank.getRankID());
                }
                tblUsers.setModel(searchUserFunc(searchBy, value));

            }
        });

        btnDeleteUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = (String) (tblUsers.getValueAt(tblUsers.getSelectedRow(),0));
                int id2 = Integer.parseInt(id);
                deleteUserFunc(id2);
            }
        });

        cmbSearch.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (cmbSearch.getSelectedItem().toString().equals("Admin")) {
                    cmbAdminSearch.setVisible(true);
                    cmbRankSearch.setVisible(false);
                    txtSearch.setVisible(false);
                }
                if (cmbSearch.getSelectedItem().toString().equals("Rank")) {
                    cmbRankSearch.setVisible(true);
                    cmbAdminSearch.setVisible(false);
                    txtSearch.setVisible(false);
                }
                if(cmbSearch.getSelectedItem().toString().equals("Username") || cmbSearch.getSelectedItem().toString().equals("Allowance")) {
                    txtSearch.setVisible(true);
                    cmbAdminSearch.setVisible(false);
                    cmbRankSearch.setVisible(false);
                }
            }
        });
    }

    private void addUserFunc(String username, int rank, int admin, int allowance) {
        boolean userSelectRes = false;
        try {
            userSelectRes = dbquery.checkUserSelect(username);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if (username.equals("") || userSelectRes) {
            JOptionPane.showMessageDialog(null, "Username already exists or no username entered!", "Add User Error", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        try {
            boolean result = dbquery.userAdd(username, rank, admin, allowance);
            if (result) {
                JOptionPane.showMessageDialog(null, "User added!", "Add User", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error adding user!", "Add User Error", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private DefaultTableModel searchUserFunc(String searchBy, String value) {
        DefaultTableModel userModel = null;
        try {
            userModel = dbquery.userSelect(searchBy, value);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userModel;
    }

    private void deleteUserFunc (int id) {
        if (id == userInst.getUserID()) {
            JOptionPane.showMessageDialog(null, "You cannot delete yourself!", "Delete User", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        try {
            if(dbquery.userDelete(id)) {
                JOptionPane.showMessageDialog(null, "User deleted!", "Delete User", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error Deleting User!", "Delete User", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
