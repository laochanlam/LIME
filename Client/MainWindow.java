package Client;

import java.awt.event.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.awt.*;
import javax.swing.*;

public class MainWindow extends JFrame implements ActionListener{

    private JFrame mainWindow;

    public MainWindow(User userInfo) {
        super("LIME");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Full Screen Size
        
        setLayout(null);
        
        TextField textField = new TextField("New");
        textField.setBounds(20, 650, 1000, 50);
        textField.setVisible(true);
        this.add(textField);

        try {
            Socket connectionSock = new Socket("127.0.0.1", 8787);
            DataOutputStream serverOutput = new DataOutputStream(connectionSock.getOutputStream());

            textField.addKeyListener(new KeyListener(){
                public void keyPressed(KeyEvent keyEvent) {
                    if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
                        String context = textField.getText();
                        textField.setText("");

                        try {
                            serverOutput.writeBytes(context + "\n");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        
                        System.out.println("[Textfield] Enter");
                    }
                }
                public void keyReleased(KeyEvent keyEvent) {

                }
                public void keyTyped(KeyEvent keyEvent) {
                
                }
            });
        } catch(IOException e) {
            e.printStackTrace();
        }

        JLabel profileLabel = new JLabel(userInfo.getUserName());
        profileLabel.setBounds(20, 20, 1000, 50);
        profileLabel.setVisible(true);
        this.add(profileLabel);

        TextArea mainTextArea = new TextArea();
        mainTextArea.setBounds(20, 100, 1000, 500);
        mainTextArea.setVisible(true);
        mainTextArea.setEditable(false);
        this.add(mainTextArea);




    }
    
    public void showup(){
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}