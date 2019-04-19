package sample;

import connection.ConnectionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pojo.ManageSiteRow19;
import pojo.ManageUserRow18;
import tools.MyAlert;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ManageSite19 {
    public TableView table;
    public TableColumn col1;
    public TableColumn col2;
    public TableColumn col3;
    public static String lastFxml;
    public ComboBox cbsite;
    public ComboBox cbmanager;
    public ComboBox cbopeneveryday;
    private static Connection conn;

    public void setLastFxml(String lastFxml) {
        this.lastFxml = lastFxml;
    }

    public void initialize() throws SQLException {
        col1.setCellValueFactory(new PropertyValueFactory<>("name"));
        col2.setCellValueFactory(new PropertyValueFactory<>("manager"));
        col3.setCellValueFactory(new PropertyValueFactory<>("openeveryday"));
        cbopeneveryday.getItems().addAll(
                "ALL",
                "Yes",
                "No",
                "Other"
        );
        conn = ConnectionManager.getConn();

        String sql = "select name from site";
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        cbsite.getItems().add("ALL");
        while(resultSet.next()){
            cbsite.getItems().add(resultSet.getString("name"));
        }
        cbsite.getItems().add("Other");

        sql = "select firstname, lastname\n" +
                "from user\n" +
                "where username in (select username from employee where employeetype = 'manager');";
        ResultSet resultSet1 = statement.executeQuery(sql);

        cbmanager.getItems().add("ALL");
        while(resultSet1.next()){
            cbmanager.getItems().add(resultSet1.getString(1)+" "+resultSet1.getString(2));
        }
        cbmanager.getItems().add("Other");

        cbsite.getSelectionModel().select(0);
        cbmanager.getSelectionModel().select(0);
        cbopeneveryday.getSelectionModel().select(0);

        statement.close();
    }

    public void addElement(String name, String manager, String openeveryday) {
        ManageSiteRow19 row = new ManageSiteRow19(name, manager, openeveryday);
        table.getItems().add(row);
    }


    public void btnBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(lastFxml));
        Stage stage = (Stage)table.getScene().getWindow();
        stage.setScene(new Scene(root));
    }


    ///////////////the result is wrong
    public void btnFilter(ActionEvent actionEvent) throws SQLException {
        table.getItems().clear();

        String siteSql = "";
        if(!cbsite.getValue().toString().equals("ALL"))
            siteSql = "and s.name='"+cbsite.getValue().toString()+"'";

        String managerSql = "";
        if(!cbmanager.getValue().toString().equals("ALL")){
            String[] names = cbmanager.getValue().toString().split(" ");
            managerSql = "where user.firstname = '"+names[0]+"' and user.lastname = '"+names[1]+"'";
        }

        String openeverydaySql = "";
        if(!cbopeneveryday.getValue().toString().equals("ALL")){
            openeverydaySql = "and s.openeveryday = '"+cbopeneveryday.getValue().toString()+"'";
        }

        String sql = "select s.name, u.firstname, u.lastname, s.openeveryday\n" +
                "from site as s, employee as e, user as u \n" +
                "where u.username = s.managerusername and e.username = u.username "+siteSql+" \n" +
                "and e.username in (select username from user "+managerSql+") \n" +
                openeverydaySql;
        System.out.println(sql);
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while(resultSet.next()){
            addElement(resultSet.getString(1), resultSet.getString(2)+" "+resultSet.getString(3), resultSet.getString(4));
        }
        statement.close();
    }

    public void btnEdit(ActionEvent actionEvent) throws IOException {
        if(table.getSelectionModel().getSelectedItem() == null) {
            MyAlert.showAlert("You need to select a site.");
            return;
        }
        ManageSiteRow19 selectedItem = (ManageSiteRow19)table.getSelectionModel().getSelectedItem();
        ///following jobs

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("editsite20.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        EditSite20 controller = fxmlLoader.getController();
        controller.setLastFxml("managesite19.fxml");
        Stage stage = (Stage)table.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void btnDelete(ActionEvent actionEvent) throws SQLException {
        if(table.getSelectionModel().getSelectedItem() == null) {
            MyAlert.showAlert("You need to select a site.");
            return;
        }
        ManageSiteRow19 selectedItem = (ManageSiteRow19)table.getSelectionModel().getSelectedItem();

        String sql = "delete from site\n" +
                "where name = '"+selectedItem.getName()+"'";
        System.out.println(sql);
        Statement statement = conn.createStatement();
        statement.executeUpdate(sql);

        MyAlert.showAlert("Site deleted");
        btnFilter(null);

        sql = "select name from site";
        ResultSet resultSet = statement.executeQuery(sql);
        cbsite.getItems().clear();
        cbsite.getItems().add("ALL");
        while(resultSet.next()){
            cbsite.getItems().add(resultSet.getString("name"));
        }
        cbsite.getItems().add("Other");
        statement.close();
    }

    public void btnCreate(ActionEvent actionEvent) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("createsite21.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        CreateSite21 controller = fxmlLoader.getController();
        controller.setLastFxml("managesite19.fxml");
        Stage stage = (Stage)table.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
