package server;

import java.io.BufferedReader;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.*;

public class Chat implements Runnable {
    private Socket socket;
    public Chat(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            BufferedReader clientInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream replyOutput = new DataOutputStream(socket.getOutputStream());
            
            while (true) {
                String messageText = clientInput.readLine();
                String sender = clientInput.readLine();
                String receiver = clientInput.readLine();
                // DataOutputStream forwardMessage = new DataOutputStream()
                System.out.println("From Client: " + messageText);
                System.out.println("Sender: " + sender);
                System.out.println("Receiver: " + receiver);
                String senderIP = socket.getRemoteSocketAddress().toString().substring(1);
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