package sample;

import connection.ConnectionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.concurrent.Service;

import java.io.IOException;
import java.sql.*;

public class Sample {

    public TextField tftest;
    public Label lbtest;
    public TextField tfstatus;
    public TextField tfuname;
    public TextField tflname;
    public TextField tfpw;
    public TextField tffname;
    public TextField tfdel;
    public Button btnnext;

    private static Connection conn;


    public void initialize() {
        System.out.println("Sample initializing!");
        conn = ConnectionManager.getConn();
//        btnnext.setOnAction(event -> {
//            //btnnext.setText("fuck Database!");
//            // show the label
//            tfdel.setVisible(true);
//            // hide finish label
//            tffname.setVisible(false);
//            // start background computation
//        });
    }

    public void read(ActionEvent actionEvent) throws SQLException {
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
    }

    public void insert(ActionEvent actionEvent) throws SQLException {
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
    }

    public void delete(ActionEvent actionEvent) throws SQLException {
        String sql = "DELETE FROM user\n" +
                "WHERE username ='"+tfdel.getText()+"'";
        Statement statement = conn.createStatement();
        statement.executeUpdate(sql);
        statement.close();
    }

    public void nextScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("sample2.fxml"));
        Stage stage = (Stage)btnnext.getScene().getWindow();
        stage.setScene(new Scene(root, 600, 500));
    }
}
