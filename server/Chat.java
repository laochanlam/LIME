package server;

import java.net.*;
import java.util.*;

import javax.net.ssl.ExtendedSSLSession;

import java.io.*;
import client.*;
public class Chat implements Runnable {
    private Socket socket;
    public static int counter;
    private WrapObject sendObject;
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

            // Add to the list if online

            User user = (User)clientInput.readObject();
            System.out.println("[online]" + user.getUserName() + " online!");


            System.out.println("[List]\"" + user.getUserName() + "\" is add to the online list!\n");
            Server.onlineList.put(user.getUserName(), this);
            Server.nameList.put(user.getUserName(), user);

            // Server output.
            Iterator biterator = Server.onlineList.entrySet().iterator();

            while (biterator.hasNext()) {
                // each stream
                Map.Entry bmapEntry = (Map.Entry) biterator.next();
                Chat chat = (Chat) bmapEntry.getValue();
                System.out.println("Stream : " + bmapEntry.getKey());
                ObjectOutputStream streams = (ObjectOutputStream) chat.clientOutput;

                Iterator iterator = Server.onlineList.entrySet().iterator();
                // each user
                boolean isFirst = true;
                while (iterator.hasNext()) {
                    Map.Entry mapEntry = (Map.Entry) iterator.next();

                    // Name
                    System.out.println(mapEntry.getKey());

                    User onlineUser = Server.nameList.get(mapEntry.getKey());
                    if (isFirst) {
                        sendObject = new WrapObject(onlineUser, 1);
                        System.out.println("[Send]" + mapEntry.getKey() + 1);
                        isFirst = false;
                    } else {
                        sendObject = new WrapObject(onlineUser, 0);
                        System.out.println("[Send]" + mapEntry.getKey() + 0);
                    }

                    streams.writeObject(sendObject);
                }

            }

            // WrapObject obj = new WrapObject(user);
            // clientOutput.writeObject(obj);


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

                // Forward to whom you talk to.
                sendObject = new WrapObject(message);
                // clientOutput.writeObject(sendObject);
                Server.forward(sendObject);

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