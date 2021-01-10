package mypackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class loginPage {
    private DatabaseQuerys dbquery =  new DatabaseQuerys();
    public void loginPageInt() {
        JFrame frame = new JFrame("Holiday Scheduling");
        frame.setMinimumSize(new Dimension(1200, 500));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        JPanel pnlWelcome = new JPanel(new GridBagLayout());
        frame.add(pnlWelcome);
        frame.setVisible(true);

        JLabel lblWelcome = new JLabel("Welcome Back...");
        JLabel lblUsername = new JLabel("username: ");
        JLabel lblPassword = new JLabel("Password: ");
        JTextField txtUsername = new JTextField();
        JPasswordField txtPassword = new JPasswordField();
        JButton btnLogin = new JButton("Login");
        pnlWelcome.add(lblWelcome);
        pnlWelcome.add(lblUsername);
        pnlWelcome.add(txtUsername);
        pnlWelcome.add(lblPassword);
        pnlWelcome.add(txtPassword);
        pnlWelcome.add(btnLogin);

        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String Username = txtUsername.getText();
                char[] Password = txtPassword.getPassword();
                if (loginAction(Username, Password)) {
                    lblWelcome.setText("Login Successful, Please wait...");
                    frame.dispose();
                    if (dbquery.getisAdmin()) {
                        adminPage adminpage = new adminPage();
                        adminpage.adminPageInt();
                    } else {
                        userPage userpage = new userPage();
                        userpage.userPageInt();
                    }
                } else {
                    lblWelcome.setText("Login Unsuccessful, Try again!");
                    txtUsername.setText("");
                    txtPassword.setText("");
                }
            }
        });
    }

    private boolean loginAction(String Username, char[] Password) {
        boolean loginSuccess = false;
        try {
            loginSuccess = dbquery.LoginQuery(Username, Password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return loginSuccess;
    }
}
