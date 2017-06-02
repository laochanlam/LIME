package server;

import java.net.*;
import java.io.*;

public class Chat implements Runnable {
    private Socket socket;
    public Chat(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            ObjectInputStream clientInput = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream replyOutput = new ObjectOutputStream(socket.getOutputStream());
            
            while (true) {

                client.Message message = (client.Message)clientInput.readObject();
                message.setSenderIP(socket.getRemoteSocketAddress().toString().substring(1));
                
                String messageText = message.getMessage();
                String sender = message.getSender();
                String receiver = message.getReceiver();
                String senderIP = message.getSenderIP();
                
                
                // // DataOutputStream forwardMessage = new DataOutputStream()
                System.out.println(message.getInfo());
                replyOutput.writeObject(message);
                // replyOutput.flush();

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