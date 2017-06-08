package client;

import java.net.*;
import java.awt.*;
import java.io.*;
import java.util.*;
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
                        MainWindow.textAreaList.get(receiveMessage.getSender()).append(receiveMessage.show());
                        System.out.print(MainWindow.nameButtonList.get(receiveMessage.getSender()));
                        JButton button = MainWindow.nameButtonList.get(receiveMessage.getSender());

                        /** 
                        * Cayon when receiving Message
                        */
                        button.setForeground(Color.RED);


                        break;
                    case (WrapObject.USER):
                        User user = receiveObject.user;
                        System.out.println("[Someone Online]");
                        // Add Friend to Friend List 

                        // if textArea non-exist
                        if ((!MainWindow.textAreaList.containsKey(user.getUserName())) && (!user.getUserName().equals(MainWindow.user.getUserName()))) {
                            TextArea textArea = new TextArea();
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
                            mainWindow.friendList = new Panel();
                            mainWindow.friendList.setBounds(1100, 25, 230, 700);
                            mainWindow.friendList.setBackground(Color.BLACK);
                            mainWindow.add(mainWindow.friendList);

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
            TextArea textArea = (TextArea) MainWindow.textAreaList.get(userName);
            textArea.setVisible(true);
            Button btn = MainWindow.nameButtonList.get(userName);
            
            /** 
             * Cayon when click the button
            */
            btn.setForeground(Color.BLACK);
        }
    }
}

