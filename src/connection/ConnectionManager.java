package connection;

import javafx.application.Platform;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static String url = "jdbc:mysql://127.0.0.1:3306/atl_beltline?useSSL=false";
    private static String driverName = "com.mysql.jdbc.Driver";
//    private static String username = "root";
//    private static String password = "960506quaN.";
    private static String username = "dlzzq";
    private static String password = "abcd1234";
    private static Connection con;

    public static ConnectionManager connectionManager;
    public static Connection conn;

    public static void init() {
        connectionManager = new ConnectionManager();
        conn = connectionManager.getConnection();
        System.out.println("Manager initializing!");
    }

    public static Connection getConn() {
        try {
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static Connection getConnection() {
        try {
            Class.forName(driverName);
            try {
                con = DriverManager.getConnection(url, username, password);
            } catch (SQLException ex) {
                System.out.println("Failed to create the database connection.");
            }
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver not found.");
        }
        return con;
    }

    public static void close() {
        System.out.println("Stop");

        try {
            conn.close();
        } catch (SQLException e){
            System.out.println(e);
        }
        Platform.exit();
    }
}