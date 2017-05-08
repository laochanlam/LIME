import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class MainWindow extends JFrame implements ActionListener{

    private JFrame mainWindow;

    public MainWindow() {
        super("LIME");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Full Screen Size
    }
    
    public void showup(){
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}