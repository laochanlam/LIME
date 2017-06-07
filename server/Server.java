package server;

import java.util.*;
import java.net.*;
import java.io.*;
import client.*;
public class Server {

    public static Map<String, Chat> onlineList;
    public static Map<String, User> nameList;
    public static void main(String[] args) {
        
        try {
            ServerSocket serverSock = new ServerSocket(8787);
            System.out.println("Server Started...\n");
            onlineList = new HashMap<String, Chat>();
            nameList = new HashMap<String, User>();
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

    public static void forward(WrapObject obj){
        switch(obj.objectType) {
            case (WrapObject.MESSAGE):
            Chat receiveChat = onlineList.get(obj.msg.getReceiver());
            // Get Stream
            receiveChat.write(obj);
            break;
            case (WrapObject.USER):

            break;
        }
    }

}