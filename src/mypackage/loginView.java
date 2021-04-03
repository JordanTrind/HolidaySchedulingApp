package mypackage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
This class is the login page for the program it allows a user to log into the system with credentials.
The methods contained in this class are:
    loginView (The view for the login page)
    loginAction (The action method of logging in)
 */

public class loginView {
    public void loginView() {
        //Gets two instances needed from the user and loginAction classes
        user userInst = user.getInstance();
        loginActions loginAct = new loginActions();

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
        lblWelcome.setPreferredSize(new Dimension(175,25));
        JLabel lblUsername = new JLabel("username: ");
        JLabel lblPassword = new JLabel("Password: ");
        JTextField txtUsername = new JTextField(16);
        JPasswordField txtPassword = new JPasswordField(16);
        JButton btnLogin = new JButton("Login");

        //Below each component's variables are defined for the GridBagLayout
        GridBagConstraints welcomeGrid = new GridBagConstraints();
        welcomeGrid.weightx = 1;
        welcomeGrid.gridx = 0;
        welcomeGrid.gridy = 0;
        welcomeGrid.gridwidth = 3;
        pnlWelcome.add(lblWelcome, welcomeGrid);

        GridBagConstraints usernameLblGrid = new GridBagConstraints();
        usernameLblGrid.weightx = 1;
        usernameLblGrid.gridx = 0;
        usernameLblGrid.gridy = 1;
        pnlWelcome.add(lblUsername, usernameLblGrid);
        GridBagConstraints usernameTxtGrid = new GridBagConstraints();
        usernameTxtGrid.weightx = 1;
        usernameTxtGrid.gridx = 3;
        usernameTxtGrid.gridy = 1;
        pnlWelcome.add(txtUsername, usernameTxtGrid);

        GridBagConstraints passwordLblGrid = new GridBagConstraints();
        passwordLblGrid.weightx = 1;
        passwordLblGrid.gridx = 0;
        passwordLblGrid.gridy = 2;
        pnlWelcome.add(lblPassword, passwordLblGrid);
        GridBagConstraints passwordTxtGrid = new GridBagConstraints();
        passwordTxtGrid.weightx = 1;
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
                if (loginAct.loginActionFunc(Username, Password)) {
                    //Precuation change of label to ensure the user is aware, dispose of now not used frame
                    lblWelcome.setText("Login Successful, Please wait...");
                    frame.dispose();

                    //Uses the userInst to determine wether user is admin or not and display correct page
                    if (userInst.getUserAdmin()) {
                        adminView adminpage = new adminView();
                        adminpage.adminView();
                    } else {
                        userView userpage = new userView();
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
}
