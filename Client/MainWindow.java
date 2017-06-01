package client;

import java.awt.event.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.awt.*;
import javax.swing.*;
public class MainWindow extends JFrame implements ActionListener{

    private JFrame mainWindow;

    public MainWindow(User userInfo) {
        super("LIME");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Full Screen Size
        
        setLayout(null);
        
        //Type Field
        TextField textField = new TextField("New");
        textField.setBounds(20, 650, 1000, 50);
        this.add(textField);

        // Profile
        JLabel profileLabel = new JLabel(userInfo.getUserName());
        profileLabel.setBounds(20, 20, 1000, 50);
        this.add(profileLabel);

        // Chat-Frame
        TextArea mainTextArea = new TextArea();
        mainTextArea.setBounds(20, 100, 1000, 500);
        mainTextArea.setEditable(false);
        this.add(mainTextArea);

        // Friend List
        Panel friendList = new Panel();
        friendList.setBounds(1100, 25, 230, 700);
        friendList.setBackground(Color.BLACK);
        this.add(friendList);

        String receiver = "jack";
        String sender = userInfo.getUserName();

        try {
            Socket connectionSock = new Socket("127.0.0.1", 8787);
            BufferedReader serverInput = new BufferedReader(new InputStreamReader(connectionSock.getInputStream()));
            DataOutputStream serverOutput = new DataOutputStream(connectionSock.getOutputStream());


            textField.addKeyListener(new KeyListener(){
                public void keyPressed(KeyEvent keyEvent) {
                    // Trigger when Enter is clicked 
                    if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
                        String context = textField.getText();
                        textField.setText("");



                        // Add Friend to Friend List 
                        friendList.add(new JButton());
                        friendList.revalidate();
                        
                        try {
                            serverOutput.writeBytes(context + "\n" + sender + "\n" + receiver + "\n");

                            String replyMessage = serverInput.readLine();
                            System.out.println(replyMessage);
                            
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




    }
    
    public void showup(){
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}