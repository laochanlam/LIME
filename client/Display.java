package client;

import java.net.*;
import java.awt.*;
import java.io.*;
import java.util.*;

import javax.net.ssl.ExtendedSSLSession;
import javax.swing.*;

import server.Chat;

import java.awt.event.*;


public class Display implements Runnable {
    private ObjectInputStream serverInput;
    private MainWindow mainWindow;
    public Display(ObjectInputStream serverInput, MainWindow mainWindow) {
        this.serverInput = serverInput;
        this.mainWindow = mainWindow;
    }

    public void run() {
        try {
            while (true) {
                WrapObject receiveObject = (WrapObject) serverInput.readObject();
                
                switch(receiveObject.objectType) {
                    case (WrapObject.MESSAGE):
                        Message receiveMessage = receiveObject.msg;
                        System.out.println("I am receiving this : \n" + receiveMessage.getInfo());
                        
                        // Make it red
                        JButton button;
                        if (!receiveMessage.getReceiver().equals("World")) {
                            TextArea textArea = MainWindow.textAreaList.get(receiveMessage.getSender());
							Font font2 = new Font(Font.MONOSPACED,Font.PLAIN, 15);
                        	textArea.setFont(font2);
							textArea.append(receiveMessage.show());
							button = MainWindow.nameButtonList.get(receiveMessage.getSender());
                        }
                        else {
                            MainWindow.textAreaList.get("World").append(receiveMessage.show());
                            button = MainWindow.nameButtonList.get("World");
                        }

                        

                        /** 
                        * Cayon when receiving Message
                        */
                        if (!receiveMessage.getSender().equals(MainWindow.user.getUserName()))
                            button.setForeground(Color.RED);


                        break;
                    case (WrapObject.USER):
                        User user = receiveObject.user;
                        System.out.println("[Someone Online]");
                        // Add Friend to Friend List 

                        // if textArea non-exist
                        if ((!MainWindow.textAreaList.containsKey(user.getUserName())) && (!user.getUserName().equals(MainWindow.user.getUserName()))) {
                            TextArea textArea = new TextArea();
                            
							Font font3 = new Font(Font.MONOSPACED,Font.PLAIN,15);
                        	textArea.setFont(font3);

                            textArea.setBounds(MainWindow.screenWidth/100, MainWindow.screenHeight*15/100,
                      		MainWindow.screenWidth*2/3, MainWindow.screenHeight*60/100);
                            textArea.setEditable(false);
                            textArea.setVisible(false);
                            mainWindow.add(textArea);
                            MainWindow.textAreaList.put(user.getUserName(), textArea);
                            System.out.println("[TextArea]" + user.getUserName() + "'s TextArea added'");
                        }


                        System.out.println(user.getUserName() + receiveObject.isFirst);
                        // Reset friend list here.

                        if (receiveObject.isFirst == 1) {

                            mainWindow.remove(mainWindow.friendList);
                            /**
                             * Cayon real panel here.
                             */
                            mainWindow.friendList = new Panel(new GridLayout(20, 20));
                            mainWindow.friendList.setBounds(MainWindow.screenWidth-MainWindow.screenWidth*7/30,MainWindow.screenHeight*5/100,MainWindow.screenWidth*4/30, MainWindow.screenHeight*17/20);

                            mainWindow.add(mainWindow.friendList);
                            MainWindow.nameButtonList.clear();

                            // BoardCast Button
                            JButton btn = new JButton("World");
                            MainWindow.nameButtonList.put("World", btn);
                            btn.addActionListener(new nameButtonHandler("World"));
                            mainWindow.friendList.add(btn);
                            mainWindow.friendList.revalidate();

                            /**
                             * Cayon real button here.
                             */
                            JButton nameButton = new JButton(user.getUserName());
                            // Add to name button list.
                            MainWindow.nameButtonList.put(user.getUserName(), nameButton);
                            nameButton.addActionListener(new nameButtonHandler(user.getUserName()));
                            mainWindow.friendList.add(nameButton);
                            mainWindow.friendList.revalidate();
                        } else {
                            JButton nameButton = new JButton(user.getUserName());
                            // Add to name button list.
                            MainWindow.nameButtonList.put(user.getUserName(), nameButton);
                            nameButton.addActionListener(new nameButtonHandler(user.getUserName()));
                            mainWindow.friendList.add(nameButton);
                            mainWindow.friendList.revalidate();
                        }

                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class nameButtonHandler implements ActionListener {
        String userName;
        nameButtonHandler(String userName) {
            this.userName = userName;
        }
        public void actionPerformed(ActionEvent e) {
            MainWindow.receiver = userName;
            System.out.println("[Receiver]Receiver changed to " + userName);

                Iterator iterator = MainWindow.textAreaList.entrySet().iterator();

                while (iterator.hasNext()) {
                    // each text Area
                    Map.Entry mapEntry = (Map.Entry) iterator.next();
                    TextArea textArea = (TextArea) mapEntry.getValue();
                    textArea.setVisible(false);
                }

                if (!userName.equals(MainWindow.user.getUserName())) {
                    TextArea textArea = (TextArea) MainWindow.textAreaList.get(userName);
                    System.out.println(textArea);
                    textArea.setVisible(true);
                } else
                    System.out.println("Because " + userName + " = " + MainWindow.user.getUserName() + " , so no screen.");

                JButton btn = MainWindow.nameButtonList.get(userName);

                /** 
                 * Cayon when click the button
                */
                btn.setForeground(Color.BLACK);
        }
    }
}

