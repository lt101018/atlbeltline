package sample;

import connection.ConnectionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pojo.UserInfo;
import tools.MyAlert;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class UserLogin {
    public TextField tfemail;
    public TextField tfpassword;
    private static Connection conn;

    public void initialize() {
        System.out.println("Sample initializing!");
        conn = ConnectionManager.getConn();

        ////admin for testing
//        tfemail.setText("jsmith@gmail.com");
//        tfpassword.setText("jsmith123");
        ////manager for testing
//        tfemail.setText("m2@beltline.com");
//        tfpassword.setText("manager2");
        ////staff for testing
        tfemail.setText("msmith@gmail.com");
        tfpassword.setText("msmith456");

        ////for testing
//        tfemail.setText("dsmith@outlook.com");
//        tfpassword.setText("dsmith456");
    }

    public void login(ActionEvent actionEvent) throws IOException {
//        String nextFxml = tfemail.getText();
//        Parent root = FXMLLoader.load(getClass().getResource(nextFxml+".fxml"));
//        Stage stage = (Stage)tfemail.getScene().getWindow();
//        stage.setScene(new Scene(root));

        try{
        String sql = "select email from email where email = '"+ tfemail.getText()+"'";
        System.out.println(sql);
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        if (!resultSet.next()) {
            MyAlert.showAlert("User not found");
            return;
        }

        sql = "select username, status, usertype\n" +
                "from user natural join email\n" +
                "where email = '"+tfemail.getText()+"' and password = '"+tfpassword.getText()+"';";
        System.out.println(sql);
        ResultSet resultSet1 = statement.executeQuery(sql);
        if (!resultSet1.next()) {
            MyAlert.showAlert("Password is wrong");
        } else {
            UserInfo.username = resultSet1.getString("username");
            UserInfo.status = resultSet1.getString("status");
            UserInfo.usertype = resultSet1.getString("usertype");
            System.out.println("Login Success: "+UserInfo.username+","+UserInfo.status+","+UserInfo.usertype);

            if(UserInfo.usertype.equals("employee")||UserInfo.usertype.equals("employeevisitor")){
                sql = "select employeetype from employee where username= '"+UserInfo.username+"'";
                System.out.println(sql);
                ResultSet resultSet2 = statement.executeQuery(sql);
                if(resultSet2.next()){
                    UserInfo.employeetype = resultSet2.getString("employeetype");
                }else{
                    MyAlert.showAlert("Employee type not found");
                }
            }

            String nextFxml = "userlogin";
            if(UserInfo.usertype.equals("user")){
                nextFxml = "userfunc7";
            }else if(UserInfo.usertype.equals("visitor")){
                nextFxml = "visitorfunc14";
            }else if(UserInfo.usertype.equals("employee")){
                if(UserInfo.employeetype.equals("administrator"))
                    nextFxml = "adminfunc8";
                else if(UserInfo.employeetype.equals("manager"))
                    nextFxml = "managerfunc10";
                else if(UserInfo.employeetype.equals("staff"))
                    nextFxml = "stafffunc12";
                else
                    MyAlert.showAlert("Employee type not found");
            }else if(UserInfo.usertype.equals("employeevisitor")){
                if(UserInfo.employeetype.equals("administrator"))
                    nextFxml = "adminvisitorfunc9";
                else if(UserInfo.employeetype.equals("manager"))
                    nextFxml = "managervisitorfunc11";
                else if(UserInfo.employeetype.equals("staff"))
                    nextFxml = "staffvisitorfunc13";
                else
                    MyAlert.showAlert("Employee type not found");
            }else{
                MyAlert.showAlert("User tpye has error. Unknown user type.");
            }

            Parent root = FXMLLoader.load(getClass().getResource(nextFxml+".fxml"));
            Stage stage = (Stage)tfemail.getScene().getWindow();
            stage.setScene(new Scene(root));
        }
        statement.close();
        }catch (SQLException e){
            MyAlert.showAlert(e.getMessage());
        }
    }

    public void register(ActionEvent actionEvent) throws IOException {
        String nextFxml = "registernavigation2";
        Parent root = FXMLLoader.load(getClass().getResource(nextFxml+".fxml"));
        Stage stage = (Stage)tfemail.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
