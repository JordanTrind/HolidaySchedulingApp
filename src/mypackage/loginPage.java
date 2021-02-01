package mypackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class loginPage {
    private user userInst = user.getInstance();
    private DatabaseQuerys dbquery =DatabaseQuerys.getDatabaseQuerysInst();

    public void loginView() {
        JFrame frame = new JFrame("Holiday Scheduling");
        frame.setSize(new Dimension(390, 190));
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel pnlWelcome = new JPanel(new GridBagLayout());
        frame.add(pnlWelcome);

        JLabel lblWelcome = new JLabel("Welcome Back...");
        lblWelcome.setMinimumSize(new Dimension(35,10));
        JLabel lblUsername = new JLabel("username: ");
        JLabel lblPassword = new JLabel("Password: ");
        JTextField txtUsername = new JTextField(16);
        JPasswordField txtPassword = new JPasswordField(16);
        JButton btnLogin = new JButton("Login");

        GridBagConstraints welcomeGrid = new GridBagConstraints();
        welcomeGrid.weightx = 0;
        welcomeGrid.gridx = 0;
        welcomeGrid.gridy = 0;
        welcomeGrid.gridwidth = 3;
        pnlWelcome.add(lblWelcome, welcomeGrid);

        GridBagConstraints usernameLblGrid = new GridBagConstraints();
        usernameLblGrid.weightx = 0.5;
        usernameLblGrid.gridx = 0;
        usernameLblGrid.gridy = 1;
        pnlWelcome.add(lblUsername, usernameLblGrid);
        GridBagConstraints usernameTxtGrid = new GridBagConstraints();
        usernameTxtGrid.weightx = 0.5;
        usernameTxtGrid.gridx = 3;
        usernameTxtGrid.gridy = 1;
        pnlWelcome.add(txtUsername, usernameTxtGrid);

        GridBagConstraints passwordLblGrid = new GridBagConstraints();
        passwordLblGrid.weightx = 0.5;
        passwordLblGrid.gridx = 0;
        passwordLblGrid.gridy = 2;
        pnlWelcome.add(lblPassword, passwordLblGrid);
        GridBagConstraints passwordTxtGrid = new GridBagConstraints();
        passwordTxtGrid.weightx = 0.5;
        passwordTxtGrid.gridx = 3;
        passwordTxtGrid.gridy = 2;
        pnlWelcome.add(txtPassword, passwordTxtGrid);

        GridBagConstraints loginBtnGrid = new GridBagConstraints();
        loginBtnGrid.weightx = 1;
        loginBtnGrid.gridx = 3;
        loginBtnGrid.gridy = 3;
        loginBtnGrid.insets = new Insets(10,0,0,0);
        pnlWelcome.add(btnLogin, loginBtnGrid);

        frame.setVisible(true);

        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String Username = txtUsername.getText();
                char[] Password = txtPassword.getPassword();
                if (loginAction(Username, Password)) {
                    lblWelcome.setText("Login Successful, Please wait...");
                    frame.dispose();
                    if (userInst.getUserAdmin()) {
                        adminPage adminpage = new adminPage();
                        adminpage.adminView();
                    } else {
                        userPage userpage = new userPage();
                        userpage.userView();
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
            loginSuccess = dbquery.loginSelect(Username, Password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            return loginSuccess;
        }
    }
}
