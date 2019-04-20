package sample;

import connection.ConnectionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pojo.ManageUserRow18;
import pojo.TakeTransitRow15;
import tools.MyAlert;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ManageUser18 {
    public TextField tfusername;
    public TableView table;
    public TableColumn col1;
    public TableColumn col2;
    public TableColumn col3;
    public TableColumn col4;
    public static String lastFxml;
    public ComboBox cbusertype;
    public ComboBox cbstatus;
    private static Connection conn;

    public void setLastFxml(String lastFxml) {
        this.lastFxml = lastFxml;
    }

    public void initialize(){
        col1.setCellValueFactory(new PropertyValueFactory<>("username"));
        col2.setCellValueFactory(new PropertyValueFactory<>("emailCount"));
        col3.setCellValueFactory(new PropertyValueFactory<>("userType"));
        col4.setCellValueFactory(new PropertyValueFactory<>("status"));
        conn = ConnectionManager.getConn();
        cbstatus.getItems().addAll(
                "ALL",
                "Approved",
                "Pending",
                "Declined"
        );
        cbusertype.getItems().addAll(
                "ALL",
                "User",
                "Visitor",
                "Staff",
                "Manager"
        );
        cbstatus.getSelectionModel().select(0);
        cbusertype.getSelectionModel().select(0);
    }

    public void addElement(String username, int emailCount, String userType, String status) {
        ManageUserRow18 row = new ManageUserRow18(username, emailCount, userType, status);
        table.getItems().add(row);
    }

    public void btnBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(lastFxml));
        Stage stage = (Stage)tfusername.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void btnFilter(ActionEvent actionEvent) throws SQLException {
        table.getItems().clear();
        String inputType = cbusertype.getValue().toString();

        String usernameSql = "";
        if(tfusername.getText().length()!=0)
            usernameSql = "and u.username = '"+tfusername.getText()+"'";

        String statusSql = "";
        if(!cbstatus.getValue().toString().equals("ALL")){
            statusSql = "and u.status = '"+cbstatus.getValue().toString()+"'";
        }

        String userTypeSql1 = "";
        if(!inputType.equals("ALL"))
            userTypeSql1 = "and emp.employeetype = '"+inputType+"'";
        String userTypeSql2 = "";
        if(!inputType.equals("ALL"))
            userTypeSql2 = "and u.usertype = '"+inputType+"'";
        String sql1 = "select u.username, count(*) as email_count, emp.employeetype , u.status \n" +
                "from user as u, email as e, employee as emp\n" +
                "where u.username = e.username and u.username = emp.username "+usernameSql+" "+statusSql+" "+userTypeSql1+"\n" +
                "group by e.username order by username";
        String sql2 = "select u.username, count(*) as email_count, u.usertype , u.status\n" +
                "from user as u, email as e\n" +
                "where u.username = e.username "+usernameSql+" "+statusSql+" "+userTypeSql2+"\n" +
                "group by e.username\n";
        if(inputType.equals("Manager")||inputType.equals("Staff")){
            System.out.println(sql1);
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql1);
            while(resultSet.next()){
                addElement(resultSet.getString(1),resultSet.getInt(2),resultSet.getString(3),resultSet.getString(4));
            }
            statement.close();
        }
        else if(inputType.equals("User")||inputType.equals("Visitor")){
            System.out.println(sql2);
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql2);
            while(resultSet.next()){
                addElement(resultSet.getString(1),resultSet.getInt(2),resultSet.getString(3),resultSet.getString(4));
            }
            statement.close();
        }
        else{
            String sql3 = "("+sql1+") union ("+sql2+") order by username";
            System.out.println(sql3);
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql3);
            while(resultSet.next()){
                addElement(resultSet.getString(1),resultSet.getInt(2),resultSet.getString(3),resultSet.getString(4));
            }
            statement.close();
        }
    }

    public void btnApprove(ActionEvent actionEvent) {
        if(table.getSelectionModel().getSelectedItem() == null) {
            MyAlert.showAlert("You need to select a user.");
            return;
        }
        ManageUserRow18 selectedItem = (ManageUserRow18)table.getSelectionModel().getSelectedItem();
        ///following jobs
    }

    public void btnDecline(ActionEvent actionEvent) {
        if(table.getSelectionModel().getSelectedItem() == null) {
            MyAlert.showAlert("You need to select a user.");
            return;
        }
        ManageUserRow18 selectedItem = (ManageUserRow18)table.getSelectionModel().getSelectedItem();
        ///following jobs
    }
}
