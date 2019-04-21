package sample;

import connection.ConnectionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pojo.TakeTransitRow15;
import pojo.TransitDetailRow36;
import pojo.UserInfo;
import tools.MyAlert;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;

public class TransitDetail36 {
    public TableView table;
    public TableColumn col1;
    public TableColumn col2;
    public TableColumn col3;
    public TableColumn col4;
    public DatePicker datepicker;
    public Label siteName;
    public static String lastFxml;
    public ComboBox cbTransportType;
    private static Connection conn;
    public Button refreshBttn;

    public void setLastFxml(String lastFxml) {
        this.lastFxml = lastFxml;
    }

    public void setSite(String name){
        siteName.setText(name);
    }


    public void initialize(){
        col1.setCellValueFactory(new PropertyValueFactory<>("route"));
        col2.setCellValueFactory(new PropertyValueFactory<>("transportType"));
        col3.setCellValueFactory(new PropertyValueFactory<>("price"));
        col4.setCellValueFactory(new PropertyValueFactory<>("numConnectedSites"));
        conn = ConnectionManager.getConn();
        cbTransportType.getItems().addAll(
                "MARTA",
                "Bus","Bike"
        );
    }

    public void addElement(String route, String transportType, double price, int numConnectedSites) {
        TransitDetailRow36 row = new TransitDetailRow36(route, transportType,price,numConnectedSites);
        table.getItems().add(row);
    }


    public void btnBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(lastFxml));
        Stage stage = (Stage)table.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void btnLogTransit(ActionEvent actionEvent) throws SQLException  {
        if(table.getSelectionModel().getSelectedItem() == null) {
            MyAlert.showAlert("You need to select a transit.");
            return;
        }
        Statement statement = conn.createStatement();
        TransitDetailRow36 selectedItem = (TransitDetailRow36)table.getSelectionModel().getSelectedItem();
        ///following jobs
        String formattedDate = datepicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));


        //**********Here should be modified!!***********
        String sqlForInsert = "insert into take(username, type, route, takedate) values('"+ UserInfo.username +"','"+ selectedItem.getTransportType() +"', '"+selectedItem.getRoute()+"', '"+formattedDate+"');";
        statement.executeUpdate(sqlForInsert);
        statement.close();
    }

    public void refreshTransit(ActionEvent actionEvent) throws SQLException {
        Statement statement = conn.createStatement();
        String transportType = cbTransportType.getValue().toString();
        String sitename = siteName.getText();
        String sqlForDifferentType = "select t.route,t.type, t.price, count(case when c.route=t.route then 1 end) as connectedsites" +
        " from transit as t, connect as c" +
        " where t.route in (select route from connect where name='" + sitename + "') and t.type='" + transportType+"'" +
        " group by t.route";

        ResultSet resultSet = statement.executeQuery(sqlForDifferentType);
        while(resultSet.next()){
            addElement(resultSet.getString(1),resultSet.getString(2),resultSet.getDouble(3),resultSet.getInt(4));
        }
        statement.close();
    }
}
