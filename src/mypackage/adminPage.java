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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
        JSpinner jsUSearch = new JSpinner(new SpinnerNumberModel(28, 0, 99, 1));
        JButton btnSearchUsers = new JButton("Search");
        JLabel lblUChange = new JLabel("Change: ");
        JComboBox cmbUChange = new JComboBox(arrSearch);
        JLabel lblUChangeWhere = new JLabel("To: ");
        JComboBox cmbAdminChange = new JComboBox(arrAdmin);
        JComboBox cmbRankChange = new JComboBox(ranksArr);
        JTextField txtUTextChange = new JTextField(16);
        JSpinner jsUChange = new JSpinner(new SpinnerNumberModel(28, 0, 99, 1));
        JTable tblUsers = new JTable();
        JScrollPane jspaneUserTbl = new JScrollPane(tblUsers);
        JButton btnPasswordReset = new JButton("Reset Password");
        JButton btnDeleteUser = new JButton("Delete User");
        JButton btnConfirmEdits = new JButton("Confirm Change");

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

        GridBagConstraints jsUSearchGrid = new GridBagConstraints();
        jsUSearchGrid.weightx = 1;
        jsUSearchGrid.gridx = 3;
        jsUSearchGrid.gridy = 0;
        editUserPanel.add(jsUSearch, jsUSearchGrid);
        jsUSearch.setVisible(false);

        GridBagConstraints lblUChangeGrid = new GridBagConstraints();
        lblUChangeGrid.weightx = 1;
        lblUChangeGrid.gridx = 0;
        lblUChangeGrid.gridy = 1;
        editUserPanel.add(lblUChange, lblUChangeGrid);

        GridBagConstraints cmbUChangeGrid = new GridBagConstraints();
        cmbUChangeGrid.weightx = 1;
        cmbUChangeGrid.gridx = 1;
        cmbUChangeGrid.gridy = 1;
        editUserPanel.add(cmbUChange, cmbUChangeGrid);

        GridBagConstraints lblUChangeWhereGrid = new GridBagConstraints();
        lblUChangeWhereGrid.weightx = 1;
        lblUChangeWhereGrid.gridx = 2;
        lblUChangeWhereGrid.gridy = 1;
        editUserPanel.add(lblUChangeWhere, lblUChangeWhereGrid);

        GridBagConstraints txtUTextChangeGrid = new GridBagConstraints();
        txtUTextChangeGrid.weightx = 1;
        txtUTextChangeGrid.gridx = 3;
        txtUTextChangeGrid.gridy = 1;
        editUserPanel.add(txtUTextChange, txtUTextChangeGrid);

        GridBagConstraints cmbRankChangeGrid = new GridBagConstraints();
        cmbRankChangeGrid.weightx = 1;
        cmbRankChangeGrid.gridx = 3;
        cmbRankChangeGrid.gridy = 1;
        editUserPanel.add(cmbRankChange, cmbRankChangeGrid);
        cmbRankChange.setVisible(false);

        GridBagConstraints cmbAdminChangeGrid = new GridBagConstraints();
        cmbAdminChangeGrid.weightx = 1;
        cmbAdminChangeGrid.gridx = 3;
        cmbAdminChangeGrid.gridy = 1;
        editUserPanel.add(cmbAdminChange, cmbAdminChangeGrid);
        cmbAdminChange.setVisible(false);

        GridBagConstraints jsUChangeGrid = new GridBagConstraints();
        jsUChangeGrid.weightx = 1;
        jsUChangeGrid.gridx = 3;
        jsUChangeGrid.gridy = 1;
        editUserPanel.add(jsUChange, jsUChangeGrid);
        jsUSearch.setVisible(false);

        GridBagConstraints jspaneUsersGrid = new GridBagConstraints();
        jspaneUsersGrid.weightx = 1;
        jspaneUsersGrid.gridx = 0;
        jspaneUsersGrid.gridy = 2;
        jspaneUsersGrid.gridwidth = 4;
        editUserPanel.add(jspaneUserTbl, jspaneUsersGrid);

        GridBagConstraints btnSearchUsersGrid = new GridBagConstraints();
        btnSearchUsersGrid.weightx = 1;
        btnSearchUsersGrid.gridx = 0;
        btnSearchUsersGrid.gridy = 3;
        editUserPanel.add(btnSearchUsers, btnSearchUsersGrid);

        GridBagConstraints btnPasswordResetGrid = new GridBagConstraints();
        btnPasswordResetGrid.weightx = 1;
        btnPasswordResetGrid.gridx = 1;
        btnPasswordResetGrid.gridy = 3;
        editUserPanel.add(btnPasswordReset, btnPasswordResetGrid);

        GridBagConstraints btnDeleteUserGrid = new GridBagConstraints();
        btnDeleteUserGrid.weightx = 1;
        btnDeleteUserGrid.gridx = 2;
        btnDeleteUserGrid.gridy = 3;
        editUserPanel.add(btnDeleteUser, btnDeleteUserGrid);

        GridBagConstraints btnConfirmEditsGrid = new GridBagConstraints();
        btnConfirmEditsGrid.weightx = 1;
        btnConfirmEditsGrid.gridx = 3;
        btnConfirmEditsGrid.gridy = 3;
        editUserPanel.add(btnConfirmEdits, btnConfirmEditsGrid);
        parentPanel.add(editUserPanel, "editUserPanel");

        JPanel manageRequestPanel = new JPanel(new GridBagLayout());
        JTable tblRequests = new JTable();
        JScrollPane jspaneRequests = new JScrollPane(tblRequests);
        DefaultTableModel tblHolModel = null;
        try {
            tblHolModel = dbquery.holidayNotRevSelect();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        tblRequests.setModel(tblHolModel);
        JButton btnAccept = new JButton("Accept");
        JButton btnDeny = new JButton("Deny");

        GridBagConstraints jspaneRequestsGrid = new GridBagConstraints();
        jspaneRequestsGrid.weightx = 1;
        jspaneRequestsGrid.gridx = 0;
        jspaneRequestsGrid.gridy = 0;
        jspaneRequestsGrid.gridwidth = 2;
        manageRequestPanel.add(jspaneRequests, jspaneRequestsGrid);

        GridBagConstraints btnAcceptGrid = new GridBagConstraints();
        btnAcceptGrid.weightx = 1;
        btnAcceptGrid.gridx = 0;
        btnAcceptGrid.gridy = 1;
        manageRequestPanel.add(btnAccept, btnAcceptGrid);

        GridBagConstraints btnDenyGrid = new GridBagConstraints();
        btnDenyGrid.weightx = 1;
        btnDenyGrid.gridx = 1;
        btnDenyGrid.gridy = 1;
        manageRequestPanel.add(btnDeny, btnDenyGrid);
        parentPanel.add(manageRequestPanel, "manageRequestPanel");

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

        manageRequests.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { cLayout.show(parentPanel, "manageRequestPanel"); }
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
                if (searchBy.equals("Username")) {
                    value = txtSearch.getText();
                }
                if (searchBy.equals("Allowance")) {
                    value = jsUSearch.getValue().toString();
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

        btnPasswordReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = (String) (tblUsers.getValueAt(tblUsers.getSelectedRow(),0));
                int id2 = Integer.parseInt(id);
                sendPasswordResetFunc(id2);
            }
        });

        btnConfirmEdits.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String changeBy = cmbUChange.getSelectedItem().toString();
                String value = "";
                if (changeBy.equals("Username")) {
                    value = txtUTextChange.getText();
                }
                if (changeBy.equals("Allowance")) {
                    value = jsUChange.getValue().toString();
                }
                if (changeBy.equals("Admin")) {
                    value = cmbAdminChange.getSelectedItem().toString();
                    if (value.equals("True")) {
                        value = "1";
                    } else {
                        value = "0";
                    }
                }
                if (changeBy.equals("Rank")) {
                    ranks selectedRank = allRanks.get(cmbRankChange.getSelectedItem());
                    value = Integer.toString(selectedRank.getRankID());
                }
                String id = (String) (tblUsers.getValueAt(tblUsers.getSelectedRow(),0));
                int id2 = Integer.parseInt(id);
                editUserFunc(id2, changeBy, value);
            }
        });

        btnAccept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = (String) (tblRequests.getValueAt(tblRequests.getSelectedRow(),0));
                String value = "Accepted";
                tblRequests.setModel(acceptDenyHolidayFunc(id, value));
            }
        });

        btnDeny.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = (String) (tblRequests.getValueAt(tblRequests.getSelectedRow(),0));
                String value = "Rejected";
                tblRequests.setModel(acceptDenyHolidayFunc(id, value));
            }
        });

        cmbUChange.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (cmbUChange.getSelectedItem().toString().equals("Admin")) {
                    cmbAdminChange.setVisible(true);
                    cmbRankChange.setVisible(false);
                    txtUTextChange.setVisible(false);
                    jsUChange.setVisible(false);
                }
                if (cmbUChange.getSelectedItem().toString().equals("Rank")) {
                    cmbRankChange.setVisible(true);
                    cmbAdminChange.setVisible(false);
                    txtUTextChange.setVisible(false);
                    jsUChange.setVisible(false);
                }
                if(cmbUChange.getSelectedItem().toString().equals("Username")) {
                    txtUTextChange.setVisible(true);
                    cmbAdminChange.setVisible(false);
                    cmbRankChange.setVisible(false);
                    jsUChange.setVisible(false);
                }
                if(cmbUChange.getSelectedItem().toString().equals("Allowance")) {
                    jsUChange.setVisible(true);
                    txtUTextChange.setVisible(false);
                    cmbAdminChange.setVisible(false);
                    cmbRankChange.setVisible(false);
                }
            }
        });

        cmbSearch.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (cmbSearch.getSelectedItem().toString().equals("Admin")) {
                    cmbAdminSearch.setVisible(true);
                    cmbRankSearch.setVisible(false);
                    txtSearch.setVisible(false);
                    jsUSearch.setVisible(false);
                }
                if (cmbSearch.getSelectedItem().toString().equals("Rank")) {
                    cmbRankSearch.setVisible(true);
                    cmbAdminSearch.setVisible(false);
                    txtSearch.setVisible(false);
                    jsUSearch.setVisible(false);
                }
                if(cmbSearch.getSelectedItem().toString().equals("Username")) {
                    txtSearch.setVisible(true);
                    cmbAdminSearch.setVisible(false);
                    cmbRankSearch.setVisible(false);
                    jsUSearch.setVisible(false);
                }
                if(cmbSearch.getSelectedItem().toString().equals("Allowance")) {
                    jsUSearch.setVisible(true);
                    txtSearch.setVisible(false);
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
            passwordHandler pwDefault = new passwordHandler();
            char[] defaultPass = {'C','h','a','n','g','e','M','e','1'};
            String password = pwDefault.newPassword(defaultPass);
            boolean result = dbquery.userAdd(username, password, rank, admin, allowance);
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

    private void sendPasswordResetFunc (int id) {
        if (id == userInst.getUserID()) {
            JOptionPane.showMessageDialog(null, "Use the user page to change your own password!", "User Password Reset", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        try {
            passwordHandler pwDefault = new passwordHandler();
            char[] defaultPass = {'C','h','a','n','g','e','M','e','1'};
            String password = pwDefault.newPassword(defaultPass);
            if(dbquery.passwordUpdate(id, password)) {
                JOptionPane.showMessageDialog(null, "Password reset to default: ChangeMe1", "User Password Reset", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error Resetting User Password!", "User Password Reset", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void editUserFunc(int id, String changeBy, String value) {
        if (changeBy.equals("Allowance")) {
            try {
                Integer.parseInt(value);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Allowance value must be numeric", "User Update", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }
        if (changeBy.equals("Username")) {
            try {
                if (dbquery.checkUserSelect(value)) {
                    JOptionPane.showMessageDialog(null, "Username already exists!", "User Update", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        try {
            if(dbquery.userUpdate(id, changeBy, value)) {
                JOptionPane.showMessageDialog(null, "User value updated", "User Update", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error updating user details!", "User Update", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private DefaultTableModel acceptDenyHolidayFunc(String id, String value) {
        DefaultTableModel holModel = null;
        SimpleDateFormat dateForm = new SimpleDateFormat("yyyy-MM-dd");
        Date cDate = new Date();
        String cDateStr = dateForm.format(cDate);
        try {
            dbquery.holidayUpdate(id, cDateStr, value);
            holModel = dbquery.holidayNotRevSelect();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return holModel;
    }
}
