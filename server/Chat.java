package server;

import java.net.*;
import java.util.*;
import java.io.*;

public class Chat implements Runnable {
    private Socket socket;
    private static int counter;
    public static ArrayList<String> onlineList;

    public Chat(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            ObjectInputStream clientInput = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream replyOutput = new ObjectOutputStream(socket.getOutputStream());
            
            // Add to the list if online
            client.User user = (client.User)clientInput.readObject();
            System.out.println("[List]\"" + user.getUserName() + "\" is add to the online list!\n");
            onlineList.add(user.getUserName() + counter);
            counter++;
            System.out.println("[List]");
            for (String element : onlineList)
                System.out.print("\"" + element + "\"");
            System.out.println(" are online!\n");

            
            while (true) {

                client.Message message = (client.Message)clientInput.readObject();
                message.setSenderIP(socket.getRemoteSocketAddress().toString().substring(1));
                
                String messageText = message.getMessage();
                String sender = message.getSender();
                String receiver = message.getReceiver();
                String senderIP = message.getSenderIP();
                
                System.out.println(message.getInfo());
                replyOutput.writeObject(message);

                // String[] ipInfo = senderIP.split(":");
                // System.out.println("123: " + ipInfo[0]);
                // System.out.println("321: " + ipInfo[1]);
                // Socket forwardSocket = new Socket(ipInfo[0], Integer.parseInt(ipInfo[1]));
                // DataOutput forwardOutput = new DataOutputStream(forwardSocket.getOutputStream());
                // forwardOutput.writeBytes("fuckuhere");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}