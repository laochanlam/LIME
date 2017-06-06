package client;

import java.net.*;
import java.awt.TextArea;
import java.io.*;
import java.util.*;

public class Display implements Runnable {
    private ObjectInputStream serverInput;
    private TextArea mainTextArea;
    public Display(ObjectInputStream serverInput, TextArea textArea) {
        this.serverInput = serverInput;
        this.mainTextArea = textArea;
    }

    public void run() {
        try {
            while (true) {
                Message receiveMessage = (Message) serverInput.readObject();
                System.out.println("I am receiving this : \n" + receiveMessage.getInfo());
                mainTextArea.append(receiveMessage.show());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

