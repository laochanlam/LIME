package client;

import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class LoginWindow extends JFrame implements ActionListener {
    JButton loginButton;
    JButton registerButton;
    JTextField userNameTextField;
    JTextField passwordTextField;

    public LoginWindow() {
        // Window
        super("LIME");

		//get screen size
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenSize.getHeight();
		screenSize.getWidth();

		int screenHeight = screenSize.height;
		int screenWidth = screenSize.width;

		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLayout(null);

        setSize(300, 500);
        setLocation(screenWidth/2-300/2, screenHeight/2-500/2);

        // Text Field
        userNameTextField = new JTextField(30);
		userNameTextField.setBounds(screenWidth/2-300,screenHeight/2+100,500,35);

        passwordTextField = new JTextField(30);
        passwordTextField.setBounds(screenWidth/2-300,screenHeight/2+100+70,500,35);

		JLabel userNameLabel = new JLabel("用戶名");
		userNameLabel.setBounds(screenWidth/2-350,screenHeight/2+100,200,40);

        JLabel passwordLabel = new JLabel("密碼");
        passwordLabel.setBounds(screenWidth/2-350,screenHeight/2+100+70,200,40);

        // Button
        loginButton = new JButton("登入");
        loginButton.addActionListener(this);
		loginButton.setLocation(screenWidth/2-300/2,screenHeight/2+250);
		loginButton.setSize(200,40);

        registerButton = new JButton("注冊");
        registerButton.addActionListener(this);
		registerButton.setLocation(screenWidth/2-300/2,screenHeight/2+300);
		registerButton.setSize(200,40);

        // Layout
       // setLayout(new GridLayout(3, 2));
        add(userNameLabel);
        add(userNameTextField);
        add(passwordLabel);
        add(passwordTextField);
        add(loginButton);
        add(registerButton);   
    }

    public void showup(){
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String userName = userNameTextField.getText();
        String password = passwordTextField.getText();
        // For transforming to Mainwindow

        User userInfo = new User(userName);
        DatabaseInfo databaseInfo = new DatabaseInfo();
        Connection connection = null;
        Statement statement = null;
        String JDBC_DRIVER = databaseInfo.getDrive();
        String DB_URL = databaseInfo.getUrl();
        String USER = databaseInfo.getUser();
        String PASS = databaseInfo.getPassword();
        
        
        if (e.getSource() == loginButton) {
            try{
                System.out.println("[Login]");
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(DB_URL, USER, PASS);
                System.out.println("Connected database successfully...");

                statement = connection.createStatement();

                // Find User From Database
                String sql = "SELECT * FROM user WHERE name = '" + userName + "' AND password = '" + password + "'";
                ResultSet rs = statement.executeQuery(sql);

                //If Non-extsts
                if (!rs.next()) {
                    System.out.println("[Query] \"" + userName + "\" Login Fail!");
                    JOptionPane.showMessageDialog(this, "Login Failed, Password or Username Incorrect!", "Login", JOptionPane.ERROR_MESSAGE);
                } else {
                    System.out.println("[Query] \"" + userName + "\" Login Success!");
                    JOptionPane.showMessageDialog(this, "Login Successful!", "Login", JOptionPane.INFORMATION_MESSAGE);
                    
                    // Entrance of Mainwindow
                    MainWindow mainWindow = new MainWindow(userInfo);
                    mainWindow.showup(); 
                    dispose();
                }
                
             // Expection Handling
            } catch (SQLException se) {
                se.printStackTrace();
            } catch (Exception ee) {
                ee.printStackTrace();
            } 

        }

        if (e.getSource() == registerButton) {
            System.out.println("[Register]");

            // Work with Database
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(DB_URL, USER, PASS);
                System.out.println("Connected database successfully...");
                statement = connection.createStatement();
                
                // Find User From Database
                String sql = "SELECT * FROM user WHERE name = '" + userName + "'";
                ResultSet rs = statement.executeQuery(sql);

                // If Non-exist
                 if (!rs.next()) {
                    sql = "INSERT INTO user(name, password) " + "VALUES ('" + userName + "', '" + password + "')";
                    statement.executeUpdate(sql);
                    System.out.println("[Insert]"+ "Username: " + userName + " Password: " + password);
                    JOptionPane.showMessageDialog(this, "Registration Successful!", "Registration", JOptionPane.INFORMATION_MESSAGE);
                 } else {
                    System.out.println("[Error] User exists");
                    JOptionPane.showMessageDialog(this, "Registration Failed, User Exists!", "Registration", JOptionPane.ERROR_MESSAGE);
                 }
            
            // Expection Handling
            } catch (SQLException se) {
                se.printStackTrace();
            } catch (Exception ee) {
                ee.printStackTrace();
            } 
        }

    } 
}
