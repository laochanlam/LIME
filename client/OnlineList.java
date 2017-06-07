package client;

import java.net.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

public class OnlineList implements Runnable {
    private ObjectInputStream onlineListInput;
    private Panel friendList;

    public OnlineList(ObjectInputStream onlineListInput, Panel friendList) {
        this.onlineListInput = onlineListInput;
        this.friendList = friendList;
    }

    public void run() {
        try {
            while (true) {
                User user = (User) onlineListInput.readObject();
                System.out.println("[Someone Online]");
                // Add Friend to Friend List 
                friendList.add(new JButton(user.getUserName()));
                friendList.revalidate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
