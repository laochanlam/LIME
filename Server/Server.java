package Server;

import java.net.*;
import java.io.*;
public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSock = new ServerSocket(8787);
            System.out.print("Server Started...\n");

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
}