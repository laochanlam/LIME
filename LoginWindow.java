import java.awt.*;
import javax.swing.*;
public class LoginWindow {
    JFrame loginWindow;
    public LoginWindow() {
        // Window
        loginWindow = new JFrame();
        loginWindow.setSize(400, 100);
        loginWindow.setLocation(300, 200);
        

        // Text Area
        JTextField userName = new JTextField("Username", 30);
        JTextField password = new JTextField("Password", 30);
        JLabel userNameLab = new JLabel("用戶名");
        JLabel passwordLab = new JLabel("密碼");

        // Layout
        JPanel panel = new JPanel(new GridLayout());
        GridBagConstraints parameter = new GridBagConstraints();
        parameter.gridx = 100;
        parameter.gridy = 0;
        parameter.gridwidth = 100;
        parameter.gridheight = 100;

        
        loginWindow.setLayout(new GridLayout(2, 2));
        loginWindow.add(userNameLab, parameter);
        loginWindow.add(userName);
        loginWindow.add(passwordLab);
        loginWindow.add(password);
    }

    public void show() {
        loginWindow.setVisible(true);
    }
}