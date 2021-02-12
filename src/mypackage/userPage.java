package mypackage;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Pattern;

public class userPage {
    user userInst = user.getInstance();
    DatabaseQuerys dbquery = DatabaseQuerys.getDatabaseQuerysInst();
    public void userView() {
        JFrame uFrame = new JFrame("Holiday Scheduling - User Menu");
        uFrame.setSize(new Dimension(600,500));
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

        JPanel rHolPanel = new JPanel(new GridBagLayout());
        JLabel lblAllowance = new JLabel("Holiday Allowance: " + userInst.getUserAllowance());
        JLabel lblStart = new JLabel("Start Date: ");
        UtilDateModel sdateModel = new UtilDateModel();
        Properties sdateProp = new Properties();
        sdateProp.put("text.today", "Today");
        sdateProp.put("text.month", "Month");
        sdateProp.put("text.year", "Year");
        JDatePanelImpl sdatePanel = new JDatePanelImpl(sdateModel, sdateProp);
        JDatePickerImpl startDate = new JDatePickerImpl(sdatePanel, new DateTextFormatter());
        JLabel lblEnd = new JLabel("End Date: ");
        UtilDateModel edateModel = new UtilDateModel();
        Properties edateProp = new Properties();
        sdateProp.put("text.today", "Today");
        sdateProp.put("text.month", "Month");
        sdateProp.put("text.year", "Year");
        JDatePanelImpl edatePanel = new JDatePanelImpl(edateModel, edateProp);
        JDatePickerImpl endDate = new JDatePickerImpl(edatePanel, new DateTextFormatter());
        JButton btnRHol = new JButton("Submit Holiday");

        GridBagConstraints allowanceGrid = new GridBagConstraints();
        allowanceGrid.weightx = 1;
        allowanceGrid.gridx = 0;
        allowanceGrid.gridy = 0;
        allowanceGrid.gridwidth = 2;
        rHolPanel.add(lblAllowance, allowanceGrid);

        GridBagConstraints lblStartGrid = new GridBagConstraints();
        lblStartGrid.weightx = 1;
        lblStartGrid.gridx = 0;
        lblStartGrid.gridy = 1;
        rHolPanel.add(lblStart, lblStartGrid);

        GridBagConstraints sDateGrid = new GridBagConstraints();
        sDateGrid.weightx = 1;
        sDateGrid.gridx = 1;
        sDateGrid.gridy = 1;
        rHolPanel.add(startDate, sDateGrid);

        GridBagConstraints lblEndGrid = new GridBagConstraints();
        lblEndGrid.weightx = 1;
        lblEndGrid.gridx = 0;
        lblEndGrid.gridy = 2;
        rHolPanel.add(lblEnd, lblEndGrid);

        GridBagConstraints eDateGrid = new GridBagConstraints();
        eDateGrid.weightx = 1;
        eDateGrid.gridx = 1;
        eDateGrid.gridy = 2;
        rHolPanel.add(endDate, eDateGrid);

        GridBagConstraints btnRHolGrid = new GridBagConstraints();
        btnRHolGrid.weightx = 1;
        btnRHolGrid.gridx = 1;
        btnRHolGrid.gridy = 3;
        rHolPanel.add(btnRHol, btnRHolGrid);

        parentPanel.add(rHolPanel, "rHolPanel");

        JPanel sHolPanel = new JPanel();
        JLabel lblStatus = new JLabel("This is Holiday Status");
        sHolPanel.add(lblStatus);
        parentPanel.add(sHolPanel, "sHolPanel");

        JPanel aInfoPanel = new JPanel(new GridBagLayout());
        JLabel lblUsername = new JLabel("Username: " + userInst.getUsername());
        JLabel lblRank = new JLabel("Rank: " + userInst.getUserRank());
        JLabel lblPasswordReq = new JLabel("Password's must be at least 8 digits long and contain a number, uppercase and lowercase number");
        JLabel lblPassword1 = new JLabel("Change Password: ");
        JPasswordField txtPassword1 = new JPasswordField(16);
        JLabel lblPassword2 = new JLabel("Confirm new Password: ");
        JPasswordField txtPassword2 = new JPasswordField(16);
        JButton btnPasswordChange = new JButton("Change Password");

        GridBagConstraints usernameGrid = new GridBagConstraints();
        usernameGrid.weightx = 1;
        usernameGrid.gridx = 0;
        usernameGrid.gridy = 0;
        usernameGrid.gridwidth = 2;
        aInfoPanel.add(lblUsername, usernameGrid);

        GridBagConstraints rankGrid = new GridBagConstraints();
        rankGrid.weightx = 1;
        rankGrid.gridx = 0;
        rankGrid.gridy = 1;
        rankGrid.gridwidth = 2;
        aInfoPanel.add(lblRank, rankGrid);

        GridBagConstraints reqGrid = new GridBagConstraints();
        reqGrid.weightx = 1;
        reqGrid.gridx = 0;
        reqGrid.gridy = 2;
        reqGrid.gridwidth = 2;
        aInfoPanel.add(lblPasswordReq, reqGrid);

        GridBagConstraints lblPass1Grid = new GridBagConstraints();
        lblPass1Grid.weightx = 1;
        lblPass1Grid.gridx = 0;
        lblPass1Grid.gridy = 3;
        aInfoPanel.add(lblPassword1, lblPass1Grid);

        GridBagConstraints txtPass1Grid = new GridBagConstraints();
        txtPass1Grid.weightx = 1;
        txtPass1Grid.gridx = 1;
        txtPass1Grid.gridy = 3;
        aInfoPanel.add(txtPassword1,txtPass1Grid);

        GridBagConstraints lblPass2Grid = new GridBagConstraints();
        lblPass2Grid.weightx = 1;
        lblPass2Grid.gridx = 0;
        lblPass2Grid.gridy = 4;
        aInfoPanel.add(lblPassword2, lblPass2Grid);

        GridBagConstraints txtPass2Grid = new GridBagConstraints();
        txtPass2Grid.weightx = 1;
        txtPass2Grid.gridx = 1;
        txtPass2Grid.gridy = 4;
        aInfoPanel.add(txtPassword2, txtPass2Grid);

        GridBagConstraints btnChangeGrid = new GridBagConstraints();
        btnChangeGrid.weightx = 1;
        btnChangeGrid.gridx = 1;
        btnChangeGrid.gridy = 5;
        aInfoPanel.add(btnPasswordChange, btnChangeGrid);

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

        btnPasswordChange.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                char[] firstPass = txtPassword1.getPassword();
                char[] secondPass = txtPassword2.getPassword();
                passwordHandler pCheck = new passwordHandler();

                switch (pCheck.passwordReqCheck(firstPass, secondPass)) {
                    case "passwordmatcherror":
                        JOptionPane.showMessageDialog(null, "Passwords do not match", "Password Error", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    case "passwordreqerror":
                        JOptionPane.showMessageDialog(null, "Passwords must be 8 digits long have an uppercase and lowercase letter and contain a number", "Password Error", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    case "success":
                        int id = userInst.getUserID();
                        String newPass = pCheck.newPassword(firstPass);
                        try {
                            if (dbquery.passwordUpdate(id, newPass)) {

                            } else {
                                JOptionPane.showMessageDialog(null, "Error changing password", "Password Error", JOptionPane.INFORMATION_MESSAGE);
                            }
                        } catch (Exception exec) {
                            new IllegalStateException("Password update failed at Database Query", exec);
                        }
                        JOptionPane.showMessageDialog(null, "Password successfully changed", "Password Change Success", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Error changing password", "Password Error", JOptionPane.INFORMATION_MESSAGE);
                        break;
                }

                txtPassword1.setText("");
                txtPassword2.setText("");
            }
        });

        btnRHol.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimpleDateFormat dateForm = new SimpleDateFormat("yyyy-MM-dd");
                Date sDate = new Date();
                Date eDate = new Date();
                Date cDate = new Date();
                String sDateStr = startDate.getJFormattedTextField().getText();
                String eDateStr = endDate.getJFormattedTextField().getText();
                String cDateStr = dateForm.format(cDate);
                int allowance = userInst.getUserAllowance();

                try {
                    sDate = dateForm.parse(sDateStr);
                    eDate = dateForm.parse(eDateStr);
                } catch (ParseException parseException) {
                    parseException.printStackTrace();
                }
                int dateDiff = (int) ((eDate.getTime() - sDate.getTime()) / (1000 * 60 * 60 * 24));
                System.out.println(sDate);
                int newAllowance = (int) (allowance - (dateDiff + 1));
                System.out.println(newAllowance);

                if ((newAllowance >= 0) && (sDate.after(cDate)) && (eDate.after(cDate)) && (eDate.after(sDate) || (sDate.compareTo(eDate) == 0))) {
                    try {
                        if (dbquery.holidayAdd(userInst.getUserID(), cDateStr, sDateStr, eDateStr)) {
                            JOptionPane.showMessageDialog(null, "Requested holiday has been entered", "Holiday Accepted", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Error requesting holiday try again!", "Holiday Error", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    try {
                        if (dbquery.allowanceUpdate(userInst.getUserID(), newAllowance)) {
                            userInst.setUserAllowance(newAllowance);
                            lblAllowance.setText("Holiday Allowance: " + userInst.getUserAllowance());
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Error requesting holiday please check holiday allowance and dates entered!", "Holiday Error", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }
}
