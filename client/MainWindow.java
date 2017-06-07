package client;

import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.awt.*;
import javax.swing.*;
import java.awt.Toolkit;

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

    public MainWindow(User user) {
        super("LIME");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Full Screen Size
        
        setLayout(null);

		//get screen size
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
		//the screen height
		screenSize.getHeight();
		
		//the screen width
		screenSize.getWidth();

		int screenHeight = screenSize.height;
		int screenWidth = screenSize.width;

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
        mainTextArea = new TextArea();
        mainTextArea.setBounds(screenWidth/100,screenHeight*15/100,screenWidth*2/3,screenHeight*60/100);
        mainTextArea.setEditable(false);
        this.add(mainTextArea);

        // Friend List
        friendList = new Panel();
        friendList.setBounds(screenWidth*70/100,screenHeight*5/100, screenWidth*7/30,screenHeight*17/20);
        friendList.setBackground(Color.BLACK);
        this.add(friendList);

        String receiver;

        String sender = user.getUserName();
        if (sender.equals("lam"))
            receiver = "jack";
        else
            receiver = "lam";

        try {
            Socket connectionSock = new Socket("140.116.245.244", 8787);
            serverOutput = new ObjectOutputStream(connectionSock.getOutputStream());
            serverInput = new ObjectInputStream(connectionSock.getInputStream());
            
            // Let Server know who you are
            serverOutput.writeObject(user);

            Display display = new Display(serverInput, mainTextArea, friendList);
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
    
    public void showup(){
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
