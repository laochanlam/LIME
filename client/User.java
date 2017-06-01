package client;

public class User {
    private String name;
    private String proilePicUrl;

    public User(String name) {
        this.name = name;
    }

    public String getUserName() {
        return name;
    }
}