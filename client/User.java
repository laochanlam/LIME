package client;

import java.io.Serializable;

public class User extends WrapObject implements Serializable{
    private String name;
    private String proilePicUrl;

    public User(String name) {
        this.name = name;
    }

    public String getUserName() {
        return name;
    }
}