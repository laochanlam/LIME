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
    private TextArea mainTextArea;
    private Panel friendList;
    public Display(ObjectInputStream serverInput, TextArea textArea, Panel friendList) {
        this.serverInput = serverInput;
        this.mainTextArea = textArea;
        this.friendList = friendList;
    }

    public void run() {
        try {
            while (true) {
                WrapObject receiveObject = (WrapObject) serverInput.readObject();
                
                switch(receiveObject.objectType) {
                    case (WrapObject.MESSAGE):
                        Message receiveMessage = receiveObject.msg;
                        System.out.println("I am receiving this : \n" + receiveMessage.getInfo());
                        mainTextArea.append(receiveMessage.show());
                        break;
                    case (WrapObject.USER):
                        User user = receiveObject.user;
                        System.out.println("[Someone Online]");
                        // Add Friend to Friend List 
                        MainWindow.friendList.add(new JButton(user.getUserName()));
                        MainWindow.friendList.revalidate();
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

