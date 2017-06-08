package client;
import java.io.*;
public class Message extends WrapObject implements Serializable {
    private String sender;
    private String receiver;
    private String message;
    private String senderIP;

    public Message(String message, String sender, String receiver) {
        this.message = message;
        this.sender = sender;
        this.receiver = receiver;
    }

    public void setSenderIP(String senderIP) {
        this.senderIP = senderIP;
    }
    public String getSenderIP() {
        return senderIP;
    }
    
    public String getSender() {
        return sender;
    }
    
    public String getReceiver() {
        return receiver;
    }
    
    public String getMessage() {
        return message;
    }

    public String getInfo() {
        return "[Message Info]\n" + "From Client: " + message + "\n"
                + "Sender: " + sender + "\n" 
                + "Receiver: " + receiver + "\n"
                + "IP: " + senderIP + "\n";
    }

    public String show() {
        return sender + ": " + "\n\n   "
                + message + "\n\n" ;
    }
}