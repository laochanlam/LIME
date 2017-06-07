package server;

import java.net.*;
import java.util.*;
import java.io.*;
import client.*;
public class Chat implements Runnable {
    private Socket socket;
    public static int counter;
    private ObjectInputStream clientInput;
    private ObjectOutputStream clientOutput;
    private ObjectOutputStream onlineListOutput;
    public Chat(Socket socket) {
        this.socket = socket;
    }


    public void run() {
        try {
            clientInput = new ObjectInputStream(socket.getInputStream());
            clientOutput = new ObjectOutputStream(socket.getOutputStream());
            // onlineListOutput = new ObjectOutputStream(socket.getOutputStream());
            // Add to the list if online

            User user = (User)clientInput.readObject();
            System.out.println("[online]" + user.getUserName() + " online!");


            System.out.println("[List]\"" + user.getUserName() + "\" is add to the online list!\n");
            Server.onlineList.put(user.getUserName(), this);

            // Server.
            WrapObject obj = new WrapObject(user);
            clientOutput.writeObject(obj);


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

                WrapObject sendObject = new WrapObject(message);
                clientOutput.writeObject(sendObject);
                Server.forward(sendObject);

                // String[] ipInfo = senderIP.split(":"ç);
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

    public void write(WrapObject obj) {
        try {   
            this.clientOutput.writeObject(obj);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}