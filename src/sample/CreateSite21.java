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
import java.util.HashMap;
import java.util.Map;

import static sample.EditSite20.sitename;

public class CreateSite21 {
    public TextField tfName;
    public TextField tfAddress;
    public TextField tfZipcode;
    public CheckBox cbOpenEveryday;
    public static String lastFxml;
    public ComboBox cbManager;
    private static Connection conn;
    private Map<String, String> map = new HashMap<>();

    public void initialize() throws SQLException {
        conn = ConnectionManager.getConn();
        String sql = "select distinct u.firstname, u.lastname, u.username\n" +
                "from site as s, employee as e, user as u\n" +
                "where e.username not in (select managerusername from site) and e.employeetype = 'manager' and u.username = e.username and u.status = 'approved';\n";
        System.out.println(sql);
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while(resultSet.next()){
            cbManager.getItems().add(resultSet.getString(1)+" "+resultSet.getString(2));
            map.put(resultSet.getString(1)+" "+resultSet.getString(2), resultSet.getString(3));
        }

        cbManager.getSelectionModel().select(0);

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

    public void btnCreate(ActionEvent actionEvent) throws SQLException {
        if(tfName.getText().length()==0 || tfZipcode.getText().length()==0 || tfAddress.getText().length()==0){
            MyAlert.showAlert("Please fill all fields");
            return;
        }

        String username = map.get(cbManager.getValue().toString());
        String openeverydaySql = (cbOpenEveryday.isSelected())?"Yes":"No";
        String sql = "insert into site(name, zipcode, address, openeveryday, managerusername) values('"+tfName.getText()+"','"+tfZipcode.getText()+"','"+tfAddress.getText()+"','"+openeverydaySql+"','"+username+"');";
        System.out.println(sql);
        Statement statement = conn.createStatement();
        statement.executeUpdate(sql);
        MyAlert.showAlert("Site created.");
    }
}
