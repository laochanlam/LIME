import java.awt.*;
import java.sql.*;
import java.awt.event.*;
import javax.swing.*;
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
    
        // Connect with DB

        // TODO: MAKE EVERYTHING INTO ONE PANEL
        // JPanel panel = new JPanel(new GridLayout());
        // GridBagConstraints parameter = new GridBagConstraints();
        // parameter.gridx = 100;
        // parameter.gridy = 0;
        // parameter.gridwidth = 100;
        // parameter.gridheight = 100;                             
    }

    public void actionPerformed(ActionEvent e) {
        String userName = userNameTextField.getText();
        String password = passwordTextField.getText();
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

                String sql = "SELECT * FROM user WHERE name = '" + userName + "' AND password = '" + password + "'";
                ResultSet rs = statement.executeQuery(sql);
                if (!rs.next())
                    System.out.println("[Query] Fail!");
                else 
                    System.out.println("[Query] Success!");
            } catch (SQLException se) {
                se.printStackTrace();
            } catch (Exception ee) {
                ee.printStackTrace();
            } 

        }

        if (e.getSource() == registerButton) {
            System.out.println("[Register]");

            // Work with Database
            try{
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(DB_URL, USER, PASS);
                System.out.println("Connected database successfully...");

                statement = connection.createStatement();

                String sql = "INSERT INTO user(name, password) " + "VALUES ('" + userName + "', '" + password + "')";
                statement.executeUpdate(sql);
                System.out.println("[Insert]"+ "Username: " + userName + " Password: " + password);

            } catch (SQLException se) {
                se.printStackTrace();
            } catch (Exception ee) {
                ee.printStackTrace();
            } 
        }

    } 
}

