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
                        mainWindow.mainTextArea.append(receiveMessage.show());
                        break;
                    case (WrapObject.USER):
                        User user = receiveObject.user;
                        System.out.println("[Someone Online]");
                        // Add Friend to Friend List 

                        System.out.println(user.getUserName() + receiveObject.isFirst);
                        // Reset friend list here.

                        if (receiveObject.isFirst == 1) {
                            mainWindow.remove(mainWindow.friendList);
                            mainWindow.friendList = new Panel();
                            mainWindow.friendList.setBounds(1100, 25, 230, 700);
                            mainWindow.friendList.setBackground(Color.BLACK);
                            mainWindow.add(mainWindow.friendList);

                            mainWindow.friendList.add(new JButton(user.getUserName()));
                            mainWindow.friendList.revalidate();
                        } else {
                            mainWindow.friendList.add(new JButton(user.getUserName()));
                            mainWindow.friendList.revalidate();
                        }
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

