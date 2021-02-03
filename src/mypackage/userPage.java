package mypackage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

public class userPage {
    user userInst = user.getInstance();
    DatabaseQuerys dbquery = DatabaseQuerys.getDatabaseQuerysInst();
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
        JLabel lblUsername = new JLabel("Username: " + userInst.getUsername());
        JLabel lblRank = new JLabel("Rank: " + userInst.getUserRank());
        JLabel lblPasswordReq = new JLabel("Password's must be at least 8 digits long and contain a number, uppercase and lowercase number");
        JLabel lblPassword1 = new JLabel("Change Password: ");
        JPasswordField txtPassword1 = new JPasswordField();
        JLabel lblPassword2 = new JLabel("Confirm new Password: ");
        JPasswordField txtPassword2 = new JPasswordField();
        JButton btnPasswordChange = new JButton("Change Password");
        aInfoPanel.add(lblUsername);
        aInfoPanel.add(lblRank);
        aInfoPanel.add(lblPasswordReq);
        aInfoPanel.add(lblPassword1);
        aInfoPanel.add(txtPassword1);
        aInfoPanel.add(lblPassword2);
        aInfoPanel.add(txtPassword2);
        aInfoPanel.add(btnPasswordChange);
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

        //Move this to password handler
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
                        //password hash goes here
                        try {
                            if (dbquery.passwordUpdate(userInst.getUserID(), firstPass)) {

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
    }
}
