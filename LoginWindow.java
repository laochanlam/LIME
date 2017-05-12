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
        setSize(400, 120);
        setLocation(300, 200);

        // Text Field
        userNameTextField = new JTextField(30);
        passwordTextField = new JTextField(30);
        JLabel userNameLabel = new JLabel("用戶名");
        JLabel passwordLabel = new JLabel("密碼");
        
        // Button
        loginButton = new JButton("登入");
        loginButton.addActionListener(this);
        registerButton = new JButton("注冊");
        registerButton.addActionListener(this);

        // Layout
        setLayout(new GridLayout(3, 2));
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

