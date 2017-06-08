package client;

import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.awt.*;
import javax.swing.*;
import java.awt.Toolkit;
import java.util.*;

import server.Chat;

public class MainWindow extends JFrame implements ActionListener{

    private Socket connectionSock;
    private ObjectOutputStream serverOutput;
    private ObjectInputStream serverInput;

    public static User user;
    public static TextField textField;
    public static JLabel profileLabel;
    public static TextArea mainTextArea;
    public static Panel friendList;
    public static Map<String,TextArea> textAreaList; 


    public static int screenHeight;
    public static int screenWidth;
    

    public static String receiver;

    public MainWindow(User user) {
        super("LIME");
        this.user = user;
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Full Screen Size
        
        setLayout(null);

		//get screen size
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
		//the screen height
		screenSize.getHeight();
		
		//the screen width
		screenSize.getWidth();

		screenHeight = screenSize.height;
		screenWidth = screenSize.width;

		System.out.println("screenHeight="+screenHeight);
		System.out.println("screenWidth="+screenWidth);
		
        //Type Field
        textField = new TextField("New");
        textField.setBounds(screenWidth/100,screenHeight*80/100,screenWidth*2/3,screenHeight/10);
        this.add(textField);

        // Profile
        profileLabel = new JLabel(user.getUserName());
        profileLabel.setBounds(screenWidth/100,screenHeight*5/100,screenWidth*2/3, screenHeight/10);
        this.add(profileLabel);

        // Chat-Frame
        textAreaList = new HashMap<String, TextArea>();

        // Friend List
        friendList = new Panel();
        friendList.setBounds(screenWidth*70/100,screenHeight*5/100, screenWidth*7/30,screenHeight*17/20);
        friendList.setBackground(Color.BLACK);
        this.add(friendList);

        String sender = user.getUserName();

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
                        textAreaList.get(message.getReceiver()).append(message.show());
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
