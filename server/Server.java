package server;

import java.util.*;
import java.net.*;
import java.io.*;
import client.*;
public class Server {

    public static Map<String, Chat> onlineList;
    public static void main(String[] args) {
        
        try {
            ServerSocket serverSock = new ServerSocket(8787);
            System.out.println("Server Started...\n");
            onlineList = new HashMap<String, Chat>();
            
            while (true) {
                Socket cSock = serverSock.accept();
                Chat chat = new Chat(cSock);
                Thread chatThread = new Thread(chat);
                chatThread.start();
            }

        } catch (IOException e) {
            System.out.println("Disconnected...\n");
        }
    }

    public static void forward(Message message){
        Chat receiveChat = onlineList.get(message.getReceiver());
        receiveChat.write(message);
    }

}