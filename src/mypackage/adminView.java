package mypackage;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class adminView {
    databaseQuerys dbquery = databaseQuerys.getDatabaseQuerysInst();
    HashMap<String, ranks> allRanks = new HashMap<>();
    adminActions adminAct = new adminActions();
    public void adminView() {
        try {
            allRanks = dbquery.rankSelectAll();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String[] ranksArr = allRanks.keySet().toArray(new String[0]);

        JFrame aFrame = new JFrame("Holiday Scheduling - Admin Menu");
        aFrame.setSize(new Dimension(900,700));
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
        JMenuItem addRank = new JMenuItem("Add Rank");
        aMenu.add(addRank);
        JMenuItem editRank = new JMenuItem("Edit Rank");
        aMenu.add(editRank);
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

        JPanel addRankPanel = new JPanel(new GridBagLayout());
        JLabel lblRanKName = new JLabel("Rank Name: ");
        JTextField txtRankName = new JTextField(16);
        JLabel lblRankAmount = new JLabel("Amount Needed per Day: ");
        JSpinner jspnAmount = new JSpinner();
        JButton btnConfirmRank = new JButton("Add Rank");

        GridBagConstraints lblRankNameGrid = new GridBagConstraints();
        lblRankNameGrid.weightx = 1;
        lblRankNameGrid.gridx = 0;
        lblRankNameGrid.gridy = 0;
        addRankPanel.add(lblRanKName, lblRankNameGrid);

        GridBagConstraints txtRankNameGrid = new GridBagConstraints();
        txtRankNameGrid.weightx = 1;
        txtRankNameGrid.gridx = 1;
        txtRankNameGrid.gridy = 0;
        addRankPanel.add(txtRankName, txtRankNameGrid);

        GridBagConstraints lblRankAmountGrid = new GridBagConstraints();
        lblRankAmountGrid.weightx = 1;
        lblRankAmountGrid.gridx = 0;
        lblRankAmountGrid.gridy = 1;
        addRankPanel.add(lblRankAmount, lblRankAmountGrid);

        GridBagConstraints jspnAmountGrid = new GridBagConstraints();
        jspnAmountGrid.weightx = 1;
        jspnAmountGrid.gridx = 1;
        jspnAmountGrid.gridy = 1;
        addRankPanel.add(jspnAmount, jspnAmountGrid);

        GridBagConstraints btnConfirmRankGrid = new GridBagConstraints();
        btnConfirmRankGrid.weightx = 1;
        btnConfirmRankGrid.gridx = 0;
        btnConfirmRankGrid.gridy = 2;
        addRankPanel.add(btnConfirmRank, btnConfirmRankGrid);
        parentPanel.add(addRankPanel, "addRankPanel");

        JPanel editRankPanel = new JPanel(new GridBagLayout());
        JComboBox cmbEdtRankSelect = new JComboBox(ranksArr);
        JLabel lblEditRank = new JLabel("Amount Needed per Day: ");
        JSpinner jspnEditRankAmount = new JSpinner();
        JButton btnRankEdit = new JButton("Edit Rank");
        JButton btnRankDelete = new JButton("Delete Rank");

        GridBagConstraints cmbEdtRankSelectGrid = new GridBagConstraints();
        cmbEdtRankSelectGrid.weightx = 1;
        cmbEdtRankSelectGrid.gridx = 0;
        cmbEdtRankSelectGrid.gridy = 0;
        cmbEdtRankSelectGrid.gridwidth = 2;
        editRankPanel.add(cmbEdtRankSelect, cmbEdtRankSelectGrid);

        GridBagConstraints lblEditRankGrid = new GridBagConstraints();
        lblEditRankGrid.weightx = 1;
        lblEditRankGrid.gridx = 0;
        lblEditRankGrid.gridy = 1;
        editRankPanel.add(lblEditRank, lblEditRankGrid);

        GridBagConstraints jspnEditRankAmountGrid = new GridBagConstraints();
        jspnEditRankAmountGrid.weightx = 1;
        jspnEditRankAmountGrid.gridx = 1;
        jspnEditRankAmountGrid.gridy = 1;
        editRankPanel.add(jspnEditRankAmount, jspnEditRankAmountGrid);

        GridBagConstraints btnRankDeleteGrid = new GridBagConstraints();
        btnRankDeleteGrid.weightx = 1;
        btnRankDeleteGrid.gridx = 0;
        btnRankDeleteGrid.gridy = 2;
        editRankPanel.add(btnRankDelete, btnRankDeleteGrid);

        GridBagConstraints btnRankEditGrid = new GridBagConstraints();
        btnRankEditGrid.weightx = 1;
        btnRankEditGrid.gridx = 1;
        btnRankEditGrid.gridy = 2;
        editRankPanel.add(btnRankEdit, btnRankEditGrid);
        parentPanel.add(editRankPanel, "editRankPanel");

        JPanel manageRequestPanel = new JPanel(new GridBagLayout());
        JLabel lblOrderBy = new JLabel("Order By: ");
        String[] arrOrderBy = {"ID", "User ID", "Holiday Start", "Holiday End", "Date Requested"};
        String[] arrDescAsc = {"Descending", "Ascending"};
        JComboBox cmbOrderBy = new JComboBox(arrOrderBy);
        JComboBox cmbDescAsc = new JComboBox(arrDescAsc);
        JTable tblRequests = new JTable();
        JScrollPane jspaneRequests = new JScrollPane(tblRequests);
        DefaultTableModel tblHolModel = null;
        try {
            tblHolModel = dbquery.holidayNotRevSelect("id", "DESC");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        tblRequests.setModel(tblHolModel);
        JButton btnAccept = new JButton("Accept");
        JButton btnDeny = new JButton("Deny");

        GridBagConstraints lblOrderByGrid = new GridBagConstraints();
        lblOrderByGrid.weightx = 1;
        lblOrderByGrid.gridx = 0;
        lblOrderByGrid.gridy = 0;
        manageRequestPanel.add(lblOrderBy, lblOrderByGrid);

        GridBagConstraints cmbOrderByGrid = new GridBagConstraints();
        cmbOrderByGrid.weightx = 1;
        cmbOrderByGrid.gridx = 1;
        cmbOrderByGrid.gridy = 0;
        manageRequestPanel.add(cmbOrderBy, cmbOrderByGrid);

        GridBagConstraints cmbDescAscGrid = new GridBagConstraints();
        cmbDescAscGrid.weightx = 1;
        cmbDescAscGrid.gridx = 2;
        cmbDescAscGrid.gridy = 0;
        manageRequestPanel.add(cmbDescAsc, cmbDescAscGrid);

        GridBagConstraints jspaneRequestsGrid = new GridBagConstraints();
        jspaneRequestsGrid.weightx = 1;
        jspaneRequestsGrid.gridx = 0;
        jspaneRequestsGrid.gridy = 1;
        jspaneRequestsGrid.gridwidth = 3;
         manageRequestPanel.add(jspaneRequests, jspaneRequestsGrid);

        GridBagConstraints btnAcceptGrid = new GridBagConstraints();
        btnAcceptGrid.weightx = 1;
        btnAcceptGrid.gridx = 0;
        btnAcceptGrid.gridy = 2;
        manageRequestPanel.add(btnAccept, btnAcceptGrid);

        GridBagConstraints btnDenyGrid = new GridBagConstraints();
        btnDenyGrid.weightx = 1;
        btnDenyGrid.gridx = 2;
        btnDenyGrid.gridy = 2;
        manageRequestPanel.add(btnDeny, btnDenyGrid);
        parentPanel.add(manageRequestPanel, "manageRequestPanel");

        JPanel generateSchedulePanel = new JPanel(new GridBagLayout());
        JLabel lblStart = new JLabel("Start Date: ");
        UtilDateModel sdateModel = new UtilDateModel();
        Properties sdateProp = new Properties();
        sdateProp.put("text.today", "Today");
        sdateProp.put("text.month", "Month");
        sdateProp.put("text.year", "Year");
        JDatePanelImpl sdatePanel = new JDatePanelImpl(sdateModel, sdateProp);
        JDatePickerImpl startDate = new JDatePickerImpl(sdatePanel, new dateTextFormatter());
        JLabel lblEnd = new JLabel("End Date: ");
        UtilDateModel edateModel = new UtilDateModel();
        Properties edateProp = new Properties();
        edateProp.put("text.today", "Today");
        edateProp.put("text.month", "Month");
        edateProp.put("text.year", "Year");
        JDatePanelImpl edatePanel = new JDatePanelImpl(edateModel, edateProp);
        JDatePickerImpl endDate = new JDatePickerImpl(edatePanel, new dateTextFormatter());
        JButton btnGenerate = new JButton("Generate Schedule");
        JLabel lblAcceptedTable = new JLabel("Accepted Holidays");
        JTable tblAccepted = new JTable();
        JScrollPane jspaneAccepted = new JScrollPane(tblAccepted);
        JLabel lblRejectedTable = new JLabel("Rejected Holidays");
        JTable tblRejected = new JTable();
        JScrollPane jspaneRejected = new JScrollPane(tblRejected);
        JButton btnAcceptSchedule = new JButton("Accept Schedule");
        JButton btnRejectSchedule = new JButton("Reject Schedule");

        GridBagConstraints lblStartGrid = new GridBagConstraints();
        lblStartGrid.weightx = 1;
        lblStartGrid.gridx = 0;
        lblStartGrid.gridy = 1;
        generateSchedulePanel.add(lblStart, lblStartGrid);

        GridBagConstraints sDateGrid = new GridBagConstraints();
        sDateGrid.weightx = 1;
        sDateGrid.gridx = 1;
        sDateGrid.gridy = 1;
        generateSchedulePanel.add(startDate, sDateGrid);

        GridBagConstraints lblEndGrid = new GridBagConstraints();
        lblEndGrid.weightx = 1;
        lblEndGrid.gridx = 0;
        lblEndGrid.gridy = 2;
        generateSchedulePanel.add(lblEnd, lblEndGrid);

        GridBagConstraints eDateGrid = new GridBagConstraints();
        eDateGrid.weightx = 1;
        eDateGrid.gridx = 1;
        eDateGrid.gridy = 2;
        generateSchedulePanel.add(endDate, eDateGrid);

        GridBagConstraints btnGenerateGrid = new GridBagConstraints();
        btnGenerateGrid.weightx = 1;
        btnGenerateGrid.gridx = 1;
        btnGenerateGrid.gridy = 3;
        generateSchedulePanel.add(btnGenerate, btnGenerateGrid);

        GridBagConstraints lblAcceptedTableGrid = new GridBagConstraints();
        lblAcceptedTableGrid.weightx = 1;
        lblAcceptedTableGrid.gridx = 0;
        lblAcceptedTableGrid.gridy = 0;
        generateSchedulePanel.add(lblAcceptedTable, lblAcceptedTableGrid);
        lblAcceptedTable.setVisible(false);

        GridBagConstraints lblRejectedTableGrid = new GridBagConstraints();
        lblRejectedTableGrid.weightx = 1;
        lblRejectedTableGrid.gridx = 1;
        lblRejectedTableGrid.gridy = 0;
        generateSchedulePanel.add(lblRejectedTable, lblRejectedTableGrid);
        lblRejectedTable.setVisible(false);

        GridBagConstraints jspaneAcceptedGrid = new GridBagConstraints();
        jspaneAcceptedGrid.weightx = 1;
        jspaneAcceptedGrid.gridx = 0;
        jspaneAcceptedGrid.gridy = 1;
        generateSchedulePanel.add(jspaneAccepted, jspaneAcceptedGrid);
        jspaneAccepted.setVisible(false);

        GridBagConstraints jspaneRejectedGrid = new GridBagConstraints();
        jspaneRejectedGrid.weightx = 1;
        jspaneRejectedGrid.gridx = 1;
        jspaneRejectedGrid.gridy = 1;
        generateSchedulePanel.add(jspaneRejected, jspaneRejectedGrid);
        jspaneRejected.setVisible(false);

        GridBagConstraints btnAcceptScheduleGrid = new GridBagConstraints();
        btnAcceptScheduleGrid.weightx = 1;
        btnAcceptScheduleGrid.gridx = 0;
        btnAcceptScheduleGrid.gridy = 2;
        generateSchedulePanel.add(btnAcceptSchedule, btnAcceptScheduleGrid);
        btnAcceptSchedule.setVisible(false);

        GridBagConstraints btnRejectScheduleGrid = new GridBagConstraints();
        btnRejectScheduleGrid.weightx = 1;
        btnRejectScheduleGrid.gridx = 1;
        btnRejectScheduleGrid.gridy = 2;
        generateSchedulePanel.add(btnRejectSchedule, btnRejectScheduleGrid);
        btnRejectSchedule.setVisible(false);

        parentPanel.add(generateSchedulePanel, "generateSchedulePanel");

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

        addRank.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { cLayout.show(parentPanel, "addRankPanel"); }
        });

        editRank.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { cLayout.show(parentPanel, "editRankPanel"); }
        });

        generateSchedule.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { cLayout.show(parentPanel, "generateSchedulePanel"); }
        });

        viewUserPage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aFrame.dispose();
                userView rtrntouser = new userView();
                rtrntouser.userView();
            }
        });

        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aFrame.dispose();
                loginView rtrntologin = new loginView();
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
                adminAct.addUserFunc(username, rank, admin, allowance);
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
                tblUsers.setModel(adminAct.searchUserFunc(searchBy, value));

            }
        });

        btnDeleteUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = (String) (tblUsers.getValueAt(tblUsers.getSelectedRow(),0));
                int id2 = Integer.parseInt(id);
                adminAct.deleteUserFunc(id2);
            }
        });

        btnPasswordReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = (String) (tblUsers.getValueAt(tblUsers.getSelectedRow(),0));
                int id2 = Integer.parseInt(id);
                adminAct.sendPasswordResetFunc(id2);
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
                adminAct.editUserFunc(id2, changeBy, value);
            }
        });

        btnConfirmRank.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String rankName = txtRankName.getText();
                String rankAmount = jspnAmount.getValue().toString();
                adminAct.addRankFunc(rankName, rankAmount);
            }
        });

        btnRankDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminAct.deleteRankFunc(cmbEdtRankSelect.getSelectedItem().toString());
            }
        });

        btnRankEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminAct.editRankAmountFunc(cmbEdtRankSelect.getSelectedItem().toString(), jspnEditRankAmount.getValue().toString());
            }
        });

        btnAccept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = (int) (tblRequests.getValueAt(tblRequests.getSelectedRow(),0));
                int userid = (int) (tblRequests.getValueAt(tblRequests.getSelectedRow(),1));
                Date sDate = (Date) (tblRequests.getValueAt(tblRequests.getSelectedRow(),3));;
                Date eDate = (Date) (tblRequests.getValueAt(tblRequests.getSelectedRow(),4));;
                String value = "Accepted";
                String orderBy = "";
                String ascOrDesc = "";
                switch (cmbOrderBy.getSelectedIndex()) {
                    case 0:
                        orderBy = "id";
                        break;
                    case 1:
                        orderBy = "user_id";
                        break;
                    case 2:
                        orderBy = "holiday_start";
                        break;
                    case 3:
                        orderBy = "holiday_end";
                        break;
                    case 4:
                        orderBy = "date_requested";
                        break;
                }
                if (cmbDescAsc.getSelectedIndex() == 0) {
                    ascOrDesc = "DESC";
                } else {
                    ascOrDesc = "ASC";
                }
                tblRequests.setModel(adminAct.acceptDenyHolidayFunc(id, userid, sDate, eDate, value, orderBy, ascOrDesc));
            }
        });

        btnDeny.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = (int) (tblRequests.getValueAt(tblRequests.getSelectedRow(),0));
                int userid = (int) (tblRequests.getValueAt(tblRequests.getSelectedRow(),1));
                Date sDate = (Date) (tblRequests.getValueAt(tblRequests.getSelectedRow(),3));;
                Date eDate = (Date) (tblRequests.getValueAt(tblRequests.getSelectedRow(),4));;
                String value = "Rejected";
                String orderBy = "";
                String ascOrDesc = "";
                switch (cmbOrderBy.getSelectedIndex()) {
                    case 0:
                        orderBy = "id";
                        break;
                    case 1:
                        orderBy = "user_id";
                        break;
                    case 2:
                        orderBy = "holiday_start";
                        break;
                    case 3:
                        orderBy = "holiday_end";
                        break;
                    case 4:
                        orderBy = "date_requested";
                        break;
                }
                if (cmbDescAsc.getSelectedIndex() == 0) {
                    ascOrDesc = "DESC";
                } else {
                    ascOrDesc = "ASC";
                }
                tblRequests.setModel(adminAct.acceptDenyHolidayFunc(id, userid, sDate, eDate, value, orderBy, ascOrDesc));
            }
        });

         btnGenerate.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 String sDateStr = startDate.getJFormattedTextField().getText();
                 String eDateStr = endDate.getJFormattedTextField().getText();
                 endDate.setVisible(false);
                 startDate.setVisible(false);
                 btnGenerate.setVisible(false);
                 lblStart.setVisible(false);
                 lblEnd.setVisible(false);
                 lblAcceptedTable.setVisible(true);
                 lblRejectedTable.setVisible(true);
                 jspaneAccepted.setVisible(true);
                 jspaneRejected.setVisible(true);
                 btnAcceptSchedule.setVisible(true);
                 btnRejectSchedule.setVisible(true);
                 adminAct.generateScheduleFunc(sDateStr, eDateStr);
                 tblAccepted.setModel(adminAct.getAcceptedHolidaysModel());
                 tblRejected.setModel(adminAct.getRejectedHolidaysModel());
             }
         });

         btnAcceptSchedule.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 endDate.setVisible(true);
                 startDate.setVisible(true);
                 btnGenerate.setVisible(true);
                 lblStart.setVisible(true);
                 lblEnd.setVisible(true);
                 lblAcceptedTable.setVisible(false);
                 lblRejectedTable.setVisible(false);
                 jspaneAccepted.setVisible(false);
                 jspaneRejected.setVisible(false);
                 btnAcceptSchedule.setVisible(false);
                 btnRejectSchedule.setVisible(false);
                 adminAct.acceptScheduleFunc();
             }
         });

         btnRejectSchedule.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 endDate.setVisible(true);
                 startDate.setVisible(true);
                 btnGenerate.setVisible(true);
                 lblStart.setVisible(true);
                 lblEnd.setVisible(true);
                 lblAcceptedTable.setVisible(false);
                 lblRejectedTable.setVisible(false);
                 jspaneAccepted.setVisible(false);
                 jspaneRejected.setVisible(false);
                 btnAcceptSchedule.setVisible(false);
                 btnRejectSchedule.setVisible(false);
             }
         });

        cmbDescAsc.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String orderBy = "";
                String ascOrDesc = "";
                switch (cmbOrderBy.getSelectedIndex()) {
                    case 0:
                        orderBy = "id";
                        break;
                    case 1:
                        orderBy = "user_id";
                        break;
                    case 2:
                        orderBy = "holiday_start";
                        break;
                    case 3:
                        orderBy = "holiday_end";
                        break;
                    case 4:
                        orderBy = "date_requested";
                        break;
                }
                if (cmbDescAsc.getSelectedIndex() == 0) {
                    ascOrDesc = "DESC";
                } else {
                    ascOrDesc = "ASC";
                }
                try {
                    tblRequests.setModel(dbquery.holidayNotRevSelect(orderBy, ascOrDesc));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        cmbOrderBy.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String orderBy = "";
                String ascOrDesc = "";
                switch (cmbOrderBy.getSelectedIndex()) {
                    case 0:
                        orderBy = "id";
                        break;
                    case 1:
                        orderBy = "user_id";
                        break;
                    case 2:
                        orderBy = "holiday_start";
                        break;
                    case 3:
                        orderBy = "holiday_end";
                        break;
                    case 4:
                        orderBy = "date_requested";
                        break;
                }
                if (cmbDescAsc.getSelectedIndex() == 0) {
                    ascOrDesc = "DESC";
                } else {
                    ascOrDesc = "ASC";
                }
                try {
                    tblRequests.setModel(dbquery.holidayNotRevSelect(orderBy, ascOrDesc));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
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

        cmbEdtRankSelect.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                ranks selectedRank = allRanks.get(cmbEdtRankSelect.getSelectedItem().toString());
                int amountNeeded = selectedRank.getAmountNeeded();
                jspnEditRankAmount.setValue(amountNeeded);
            }
        });
    }
}
