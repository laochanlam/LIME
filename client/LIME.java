package client;

public class LIME {
    public static void main(String[] args){
        System.out.println("[Start]");
       	LoginWindow loginWindow = new LoginWindow();
        loginWindow.showup();

        // For testing
        // MainWindow mainWindow = new MainWindow(new User("lam"));
        // mainWindow.showup();
    }
}
