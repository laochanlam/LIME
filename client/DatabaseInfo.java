package client;

public class DatabaseInfo {
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://140.116.245.244/LIME?useSSL=false";
    private static final String USER = "root";
    private static final String PASS = "~1qaz2wsx";

    public String getDrive () {
        return JDBC_DRIVER;
    }
    public String getUrl () {
        return DB_URL;
    }
    public String getUser () {
        return USER;
    }
    public String getPassword () {
        return PASS;
    }
}