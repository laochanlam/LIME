import javax.swing.*;

public class MainWindow {

    private JFrame mainWindow;

    public MainWindow() {
        mainWindow = new JFrame("LIME");
        mainWindow.setExtendedState(JFrame.MAXIMIZED_BOTH); // Full Screen Size
    }
    public void show(){
        mainWindow.setVisible(true);
    }
}