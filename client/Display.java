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
                WrapObject receiveObject = (WrapObject) serverInput.readObject();
                if ( receiveObject.objectType == WrapObject.MESSAGE) {
                    Message receiveMessage = receiveObject.msg;
                    System.out.println("I am receiving this : \n" + receiveMessage.getInfo());
                    mainTextArea.append(receiveMessage.show());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

