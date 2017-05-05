import java.awt.*;
import javax.swing.*;
public class LoginWindow {
    JFrame loginWindow;
    public LoginWindow() {
        // Window
        loginWindow = new JFrame();
        loginWindow.setSize(400, 120);
        loginWindow.setLocation(300, 200);

        // Text Area
        JTextField userName = new JTextField(30);
        JTextField password = new JTextField(30);
        JLabel userNameLab = new JLabel("用戶名");
        JLabel passwordLab = new JLabel("密碼");

        // Button
        JButton submitButton = new JButton("登入");
        submitButton.addActionListener(new LoginEventListener());
        JButton registerButton = new JButton("注冊");
        registerButton.addActionListener(new RegisterEventListener());

        // TODO: MAKE EVERYTHING INTO ONE PANEL
        // JPanel panel = new JPanel(new GridLayout());
        // GridBagConstraints parameter = new GridBagConstraints();
        // parameter.gridx = 100;
        // parameter.gridy = 0;
        // parameter.gridwidth = 100;
        // parameter.gridheight = 100;                             

        
        // Layout
        loginWindow.setLayout(new GridLayout(3, 2));
        loginWindow.add(userNameLab);
        loginWindow.add(userName);
        loginWindow.add(passwordLab);
        loginWindow.add(password);
        loginWindow.add(submitButton);
        loginWindow.add(registerButton);
    }

    public void show() {
        loginWindow.setVisible(true);
    }
}

