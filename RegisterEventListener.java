import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class RegisterEventListener implements ActionListener {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://IP/LIME";
    static final String USER = "root"; // TODO: Hidden the sensitive information
    static final String PASS = "";
    
    public void actionPerformed(ActionEvent e) {
        System.out.print("Register");

        Connection conn = null;
        Statement stmt = null;

        try{
            Class.forName("com.mysql.jdbc.Driver");

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            System.out.println("Connected database successfully...");

            System.out.println("Inserting records into the table...");
            stmt = conn.createStatement();

            String sql = "INSERT INTO users " + "VALUES ('laolamUser', 'laolamPass')";
            stmt.executeUpdate(sql);
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception ee) {
            ee.printStackTrace();
        } 

    } 

}