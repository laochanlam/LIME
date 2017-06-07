package client;

import java.io.Serializable;
public class WrapObject implements Serializable{
    public int objectType;
    public User user;
    public Message msg;

    public final static int USER = 0;
    public final static int MESSAGE = 1;

    public WrapObject(User user){
        this.user = user;
        objectType = USER;
  
    } 
    public WrapObject(Message msg){
        this.msg = msg;
        objectType = MESSAGE;
    }

    public WrapObject() {

    }
}
