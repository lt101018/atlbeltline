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
    }

    public void login(ActionEvent actionEvent) throws IOException, SQLException {
//        String nextFxml = tfemail.getText();
//        Parent root = FXMLLoader.load(getClass().getResource(nextFxml+".fxml"));
//        Stage stage = (Stage)tfemail.getScene().getWindow();
//        stage.setScene(new Scene(root));


        String sql = "select email from email where email = '"+ tfemail.getText()+"'";
        System.out.println(sql);
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        if (resultSet.next() == false) {
            MyAlert.showAlert("User not found");
            return;
        }

        sql = "select username, status, usertype\n" +
                "from user natural join email\n" +
                "where email = '"+tfemail.getText()+"' and password = '"+tfpassword.getText()+"';";
        System.out.println(sql);
        ResultSet resultSet1 = statement.executeQuery(sql);
        if (resultSet1.next() == false) {
            MyAlert.showAlert("Password is wrong");
        } else {
            UserInfo.username = resultSet1.getString("username");
            UserInfo.status = resultSet1.getString("status");
            UserInfo.usertype = resultSet1.getString("usertype");
            System.out.println("Login Success: "+UserInfo.username+","+UserInfo.status+","+UserInfo.usertype);


            //////////need to fix from here
            String nextFxml = "userlogin";
            if(UserInfo.usertype == "user"){

            }else if(UserInfo.usertype == "employee"){

            }else if(UserInfo.usertype == "visitor"){

            }else if(UserInfo.usertype == "employeevisitor"){

            }else{
                MyAlert.showAlert("User tpye has error. Unknown user type.");
            }
            //////////need to fix end here

            Parent root = FXMLLoader.load(getClass().getResource(nextFxml+".fxml"));
            Stage stage = (Stage)tfemail.getScene().getWindow();
            stage.setScene(new Scene(root));
        }
        statement.close();

    }

    public void register(ActionEvent actionEvent) {
    }


}
