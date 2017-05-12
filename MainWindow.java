import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class MainWindow extends JFrame implements ActionListener{

    private JFrame mainWindow;

    public MainWindow(User userInfo) {
        super("LIME");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Full Screen Size
        
        setLayout(null);
        TextField textField = new TextField("New");
        textField.setBounds(20, 650, 1000, 60);
        textField.setVisible(true);
        this.add(textField);


        JLabel profileLabel = new JLabel(userInfo.getUserName());
        profileLabel.setBounds(20, 20, 1000, 50);
        profileLabel.setVisible(true);
        this.add(profileLabel);

        TextArea mainTextArea = new TextArea();
        mainTextArea.setBounds(20, 100, 1000, 500);
        mainTextArea.setVisible(true);
        this.add(mainTextArea);

        JMenu menuFile = new JMenu("File");
        JMenuBar menuBar = new JMenuBar();
        JMenuItem item = new JMenuItem();



    }
    
    public void showup(){
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}