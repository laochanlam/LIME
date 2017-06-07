package client;

import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.awt.*;
import javax.swing.*;

import server.Chat;

public class MainWindow extends JFrame implements ActionListener{

    private Socket connectionSock;
    private ObjectOutputStream serverOutput;
    private ObjectInputStream serverInput;

    public static int counter;
    public static TextField textField;
    public static JLabel profileLabel;
    public static TextArea mainTextArea;
    public static Panel friendList;
    public static Panel emptyFriendList;

    public MainWindow(User user) {
        super("LIME");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Full Screen Size
        
        setLayout(null);
        
        //Type Field
        textField = new TextField("New");
        textField.setBounds(20, 650, 1000, 50);
        this.add(textField);

        // Profile
        profileLabel = new JLabel(user.getUserName());
        profileLabel.setBounds(20, 20, 1000, 50);
        this.add(profileLabel);

        // Chat-Frame
        mainTextArea = new TextArea();
        mainTextArea.setBounds(20, 100, 1000, 500);
        mainTextArea.setEditable(false);
        this.add(mainTextArea);

        // Friend List
        friendList = new Panel();
        friendList.setBounds(1100, 25, 230, 700);
        friendList.setBackground(Color.BLACK);
        this.add(friendList);

        //Set a empty list.
        emptyFriendList = friendList;

        String receiver;

        String sender = user.getUserName();
        if (sender.equals("lam"))
            receiver = "jack";
        else
            receiver = "lam";

        try {
            // Socket connectionSock = new Socket("140.116.245.244", 8787);
            Socket connectionSock = new Socket("127.0.0.1", 8787);
            serverOutput = new ObjectOutputStream(connectionSock.getOutputStream());
            serverInput = new ObjectInputStream(connectionSock.getInputStream());
            
            // Let Server know who you are
            serverOutput.writeObject(user);

            Display display = new Display(serverInput, this);
            Thread displayThread = new Thread(display);
            displayThread.start();
            System.out.println("[Thread]Thread Start...");


        } catch (Exception e) {
            e.printStackTrace();
        }

        textField.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent keyEvent) {
                // Trigger when Enter is clicked 
                if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
                    String context = textField.getText();
                    textField.setText("");

                    try {
                        Message message = new Message(context, sender, receiver);
                        serverOutput.writeObject(message);
                        System.out.println("I am writing this : \n" + message.getInfo());
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

    }
    
    // public static void resetFriendList() {
    //     this.remove(friendList);
    //     this.add(emptyFriendList);
    // }
    public void showup(){
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}