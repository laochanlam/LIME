package server;

import java.io.BufferedReader;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.*;

public class Chat implements Runnable {
    private Socket socket;
    public Chat(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            ObjectInputStream clientInput = new ObjectInputStream(socket.getInputStream());
            DataOutputStream replyOutput = new DataOutputStream(socket.getOutputStream());
            
            while (true) {

                client.Message message = (client.Message)clientInput.readObject();
                message.setSenderIP(socket.getRemoteSocketAddress().toString().substring(1));
                
                String messageText = message.getMessage();
                String sender = message.getSender();
                String receiver = message.getReceiver();
                String senderIP = message.getSenderIP();
                
                
                // // DataOutputStream forwardMessage = new DataOutputStream()
                System.out.println("From Client: " + messageText);
                System.out.println("Sender: " + sender);
                System.out.println("Receiver: " + receiver);
                System.out.println("IP: " + senderIP);
                

                replyOutput.writeBytes("Receive!" + "\n");
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