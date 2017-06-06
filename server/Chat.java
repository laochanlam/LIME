package server;

import java.net.*;
import java.util.*;
import java.io.*;
import client.*;
public class Chat implements Runnable {
    private Socket socket;
    private static int counter;
    private ObjectInputStream clientInput;
    private ObjectOutputStream clientOutput;
    public Chat(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            clientInput = new ObjectInputStream(socket.getInputStream());
            clientOutput = new ObjectOutputStream(socket.getOutputStream());
            
            // Add to the list if online
            User user = (User)clientInput.readObject();
            System.out.println("[List]\"" + user.getUserName() + "\" is add to the online list!\n");
            Server.onlineList.put(user.getUserName() + counter, this);
            counter++;
            System.out.println("[List]");
            // for (String element : Server.onlineList)
            //     System.out.print("\"" + element + "\"");
            System.out.print(Server.onlineList.keySet());
            System.out.println(" are online!\n");

            
            while (true) {

                Message message = (Message)clientInput.readObject();
                message.setSenderIP(socket.getRemoteSocketAddress().toString().substring(1));
                
                String messageText = message.getMessage();
                String sender = message.getSender();
                String receiver = message.getReceiver();
                String senderIP = message.getSenderIP();
                
                System.out.println(message.getInfo());
                clientOutput.writeObject(message);
                Server.forward(message);

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

    public void write(Message message) {
        try {
            this.clientOutput.writeObject(message);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}