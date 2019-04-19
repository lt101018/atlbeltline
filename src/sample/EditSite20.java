package sample;

import connection.ConnectionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tools.MyAlert;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EditSite20 {
    public TextField tfName;
    public TextField tfAddress;
    public TextField tfZipcode;
    public CheckBox cbOpenEveryday;
    public static String lastFxml;
    public ComboBox cbManager;
    public static String sitename;
    private static Connection conn;

    public void initialize1() throws SQLException {
        conn = ConnectionManager.getConn();
        String sql = "(select u.firstname, u.lastname\n" +
                "from user as u, employee as e, site as s\n" +
                "where s.name = '"+sitename+"' and s.managerusername = e.username and u.username = e.username)\n" +
                "union\n" +
                "(select u.firstname, u.lastname\n" +
                "from site as s, employee as e, user as u\n" +
                "where e.username not in (select managerusername from site) and e.employeetype = 'manager' and s.name = '"+sitename+"' and u.username = e.username and u.status = 'approved');";
        System.out.println(sql);
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        tfName.setText(sitename);

        while(resultSet.next()){
            cbManager.getItems().add(resultSet.getString(1)+" "+resultSet.getString(2));
        }

        cbManager.getSelectionModel().select(0);


        sql = "select zipcode, address, openeveryday from site";
        ResultSet resultSet1 = statement.executeQuery(sql);
        if(resultSet1.next()){
            tfZipcode.setText(resultSet1.getString(1));
            tfAddress.setText(resultSet1.getString(2));
            cbOpenEveryday.setSelected(resultSet1.getString(3).equals("Yes"));
        }else{
            MyAlert.showAlert("Site not exist.");
        }


        statement.close();
    }

    public void setLastFxml(String lastFxml) {
        this.lastFxml = lastFxml;
    }

    public void btnBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(lastFxml));
        Stage stage = (Stage)tfName.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    //////////still need to update the manager
    public void btnUpdate(ActionEvent actionEvent) throws SQLException {
        if(tfName.getText().length()==0 || tfZipcode.getText().length()==0 || tfAddress.getText().length()==0){
            MyAlert.showAlert("Please fill all fields");
            return;
        }
        String openeverydaySql = (cbOpenEveryday.isSelected())?"Yes":"No";
        String sql = "update site\n" +
                "set name = '"+tfName.getText()+"', zipcode = '"+tfZipcode.getText()+"', address = '"+tfAddress.getText()+"', openeveryday = '"+openeverydaySql+"'\n" +
                "where name = '"+sitename+"';";
        sitename = tfName.getText();
        System.out.println(sql);
        Statement statement = conn.createStatement();
        statement.executeUpdate(sql);
        MyAlert.showAlert("Site updated.");
    }
}
