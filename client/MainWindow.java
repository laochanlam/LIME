package client;

import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.awt.*;
import javax.swing.*;

public class MainWindow extends JFrame implements ActionListener{

    private JFrame mainWindow;

    public MainWindow(User user) {
        super("LIME");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Full Screen Size
        
        setLayout(null);
        
        //Type Field
        TextField textField = new TextField("New");
        textField.setBounds(20, 650, 1000, 50);
        this.add(textField);

        // Profile
        JLabel profileLabel = new JLabel(user.getUserName());
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
        String sender = user.getUserName();

        try {
            Socket connectionSock = new Socket("127.0.0.1", 8787);
            ObjectOutputStream serverOutput = new ObjectOutputStream(connectionSock.getOutputStream());
            ObjectInputStream serverInput = new ObjectInputStream(connectionSock.getInputStream());
            
            // Let Server know who you are
            serverOutput.writeObject(user);

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
                            Message message = new Message(context, sender, receiver);
                            serverOutput.writeObject(message);

                            Message receiveMessage = (Message) serverInput.readObject();
                            System.out.println(receiveMessage.getInfo());
                            mainTextArea.append(receiveMessage.show());

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        
                        System.out.println("[Textfield] Enter\n");
                    }
                }
                public void keyReleased(KeyEvent keyEvent) {

                }
                public void keyTyped(KeyEvent keyEvent) {
                
                }
            });
        } catch(Exception e) {
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