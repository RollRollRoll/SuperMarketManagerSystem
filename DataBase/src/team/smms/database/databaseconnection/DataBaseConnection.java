package team.smms.database.databaseconnection;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

/**
 * Created by Student on 2016/7/21.
 */

public class DataBaseConnection {
    private static final String driver = "com.mysql.jdbc.Driver";
    private String user = null;
    private String password = null;
    private static final String url = "jdbc:mysql://192.168.124.128:3306/supermarket";
    private Connection con;

    public DataBaseConnection(String user, String password) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        this.user = user;
        this.password = password;
        con = DriverManager.getConnection(url, this.user, this.password);
    }

    public Connection getConnection() {
        return this.con;
    }

    public void close() throws SQLException {
        if (con != null) {
            con.close();
        }
    }
}