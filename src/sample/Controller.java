package sample;

import connection.ConnectionManager;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.*;

public class Controller {
    public TextField tftest;
    public Label lbtest;
    public TextField tfstatus;
    public TextField tfuname;
    public TextField tflname;
    public TextField tfpw;
    public TextField tffname;
    public TextField tfdel;

    public void read(ActionEvent actionEvent) throws SQLException {
        ConnectionManager connectionManager = new ConnectionManager();
        Connection conn = connectionManager.getConnection();
        String sql = "SELECT * FROM user";
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        lbtest.setText("");
        while (resultSet.next()) {
            String username = resultSet.getString("username");
            String firstname = resultSet.getString("firstname");
            String lastname = resultSet.getString("lastname");
            String status = resultSet.getString("status");
            String password = resultSet.getString("password");
            lbtest.setText(lbtest.getText() + username + " " + firstname + " " + lastname + " " + status + " " + password + "\n");
        }
        statement.close();
        conn.close();
    }

    public void insert(ActionEvent actionEvent) throws SQLException {
        ConnectionManager connectionManager = new ConnectionManager();
        Connection conn = connectionManager.getConnection();
        String sql = "INSERT INTO user\n" +
                "(`username`,\n" +
                "`firstname`,\n" +
                "`lastname`,\n" +
                "`status`,\n" +
                "`password`)\n" +
                "VALUES\n" +
                "('"+tfuname.getText()+"',\n'" +
                tffname.getText()+"',\n'" +
                tflname.getText()+"',\n'" +
                tfstatus.getText()+"',\n'" +
                tfpw.getText()+"')";
        Statement statement = conn.createStatement();
        statement.executeUpdate(sql);
        statement.close();
        conn.close();
    }

    public void delete(ActionEvent actionEvent) throws SQLException {
        ConnectionManager connectionManager = new ConnectionManager();
        Connection conn = connectionManager.getConnection();
        String sql = "DELETE FROM user\n" +
                "WHERE username ='"+tfdel.getText()+"'";
        Statement statement = conn.createStatement();
        statement.executeUpdate(sql);
        statement.close();
        conn.close();
    }
}
