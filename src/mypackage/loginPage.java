package mypackage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/*
This class is the login page for the program it allows a user to log into the system with credentials.
The methods contained in this class are:
    loginView (The view for the login page)
    loginAction (The action method of logging in)
 */

public class loginPage {

    //Gets two instances needed from the user and databaseQuerys classes
    private user userInst = user.getInstance();
    private DatabaseQuerys dbquery =DatabaseQuerys.getDatabaseQuerysInst();

    public void loginView() {
        //Sets up the JFrame
        JFrame frame = new JFrame("Holiday Scheduling");
        frame.setSize(new Dimension(390, 190));
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Sets up the initial panel with GridBagLayout
        JPanel pnlWelcome = new JPanel(new GridBagLayout());
        frame.add(pnlWelcome);

        //Setting up Components of the Panel
        JLabel lblWelcome = new JLabel("Welcome Back...");
        lblWelcome.setMinimumSize(new Dimension(35,10));
        JLabel lblUsername = new JLabel("username: ");
        JLabel lblPassword = new JLabel("Password: ");
        JTextField txtUsername = new JTextField(16);
        JPasswordField txtPassword = new JPasswordField(16);
        JButton btnLogin = new JButton("Login");

        //Below each component's variables are defined for the GridBagLayout
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

        //Frame ready and set as visible
        frame.setVisible(true);

        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Password stored as Char for security
                String Username = txtUsername.getText();
                char[] Password = txtPassword.getPassword();

                //Uses method loginAction with supplied variables
                if (loginAction(Username, Password)) {
                    //Precuation change of label to ensure the user is aware, dispose of now not used frame
                    lblWelcome.setText("Login Successful, Please wait...");
                    frame.dispose();

                    //Uses the userInst to determine wether user is admin or not and display correct page
                    if (userInst.getUserAdmin()) {
                        adminPage adminpage = new adminPage();
                        adminpage.adminView();
                    } else {
                        userPage userpage = new userPage();
                        userpage.userView();
                    }
                } else {
                    //Reset some components if unsuccessful entry
                    lblWelcome.setText("Login Unsuccessful, Try again!");
                    txtUsername.setText("");
                    txtPassword.setText("");
                }
            }
        });
    }

    private boolean loginAction(String Username, char[] Password) {
        //Default to false as user will not be admin majority of time
        boolean loginSuccess = false;
        try {
            //Call database instance to determine login success
            loginSuccess = dbquery.loginSelect(Username, Password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            return loginSuccess;
        }
    }
}
